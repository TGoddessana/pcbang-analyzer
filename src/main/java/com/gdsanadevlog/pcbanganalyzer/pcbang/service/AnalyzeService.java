package com.gdsanadevlog.pcbanganalyzer.pcbang.service;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.AnalyzeHistory;
import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.AnalyzeHistoryReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.AnalyzeHistoryRepository;

import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.PcbangRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final AnalyzeHistoryRepository analyzeHistoryRepository;
    private final PcbangRepository pcbangRepository;

    public Page<AnalyzeHistoryReadDto> listAnalyzeHistoriesPaginated(int page, int size) {
        Page<AnalyzeHistory> analyzeHistoryPage = analyzeHistoryRepository.findAll(PageRequest.of(page, size));

        return analyzeHistoryPage.map(AnalyzeHistoryReadDto::fromEntity);
    }

//    @Scheduled(cron = "0 0/30 * * * *")
    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void analyzePcbang() {
        System.out.println("Analyze PCBangs at " + LocalDateTime.now());
        List<Pcbang> pcbangs = pcbangRepository.findAll();

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        for (Pcbang pcbang : pcbangs) {
            AnalyzeHistory analyzeHistory = pcbang.analyze(now);
            analyzeHistoryRepository.save(analyzeHistory);
        }
    }
}
