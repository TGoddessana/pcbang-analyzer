package com.gdsanadevlog.pcbanganalyzer.pcbang.service;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.AnalyzeHistory;
import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.AnalyzeHistoryReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.AnalyzeHistoryRepository;

import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.PcbangRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final AnalyzeHistoryRepository analyzeHistoryRepository;
    private final PcbangRepository pcbangRepository;

    public Page<AnalyzeHistoryReadDto> listAnalyzeHistoriesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "analyzedAt"));
        Page<AnalyzeHistory> analyzeHistoryPage = analyzeHistoryRepository.findAll(pageable);

        return analyzeHistoryPage.map(AnalyzeHistoryReadDto::fromEntity);
    }

    public Page<AnalyzeHistoryReadDto> searchAnalyzeHistoriesByPcbangName(String pcbangName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "analyzedAt"));
        Page<AnalyzeHistory> analyzeHistoryPage = analyzeHistoryRepository.findByPcbangNameContainingIgnoreCase(pcbangName, pageable);

        return analyzeHistoryPage.map(AnalyzeHistoryReadDto::fromEntity);
    }



//    @Scheduled(cron = "0 0/30 * * * *")
    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void analyzePcbang() {
        System.out.println("Analyze PCBangs at " + LocalDateTime.now());
        List<Pcbang> pcbangs = pcbangRepository.findAll();

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));


        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<?>> futures = new ArrayList<>();

        for (Pcbang pcbang : pcbangs) {
            Future<?> future = executor.submit(() -> {
                System.out.println("Analyze PCBang " + pcbang.getName() + " at " + LocalDateTime.now());
                AnalyzeHistory analyzeHistory = pcbang.analyze(now);
                analyzeHistoryRepository.save(analyzeHistory);
            });
            futures.add(future);
        }

       for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
