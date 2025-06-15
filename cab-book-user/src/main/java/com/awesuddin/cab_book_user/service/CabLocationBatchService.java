package com.awesuddin.cab_book_user.service;

import com.awesuddin.cab_book_user.entity.CabLocation;
import com.awesuddin.cab_book_user.repository.CabLocationRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class CabLocationBatchService {

    private final CabLocationRepository cabLocationRepository;

    private final List<CabLocation> buffer = new ArrayList<>();
    private static final int BATCH_SIZE = 5;
    private static final long FLUSH_INTERVAL_MS = 5000;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public CabLocationBatchService init() {
        scheduler.scheduleAtFixedRate(this::flushBuffer, FLUSH_INTERVAL_MS, FLUSH_INTERVAL_MS, TimeUnit.MILLISECONDS);
        return this;
    }

    public void addToBuffer(CabLocation location) {
        synchronized (buffer) {
            buffer.add(location);
            if (buffer.size() >= BATCH_SIZE) {
                flushBuffer();
            }
        }
    }

    @Transactional
    public void flushBuffer() {
        List<CabLocation> batchToInsert;

        synchronized (buffer) {
            if (buffer.isEmpty()) return;

            // Atomic swap of buffer content
            batchToInsert = new ArrayList<>(buffer);
            buffer.clear();
        }

        try {
            cabLocationRepository.saveAll(batchToInsert);
            System.out.println("✅ Inserted batch of " + batchToInsert.size());
        } catch (Exception e) {
            System.err.println("❌ Batch insert failed: " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
        flushBuffer();
    }
}
