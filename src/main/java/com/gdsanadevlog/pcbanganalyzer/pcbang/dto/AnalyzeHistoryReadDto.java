package com.gdsanadevlog.pcbanganalyzer.pcbang.dto;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.AnalyzeHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class AnalyzeHistoryReadDto {
    private Long id;
    private String pcbangName;

    private int openCount;
    private int closeCount;
    private float openRate;

    private LocalDateTime analyzedAt;

    public static AnalyzeHistoryReadDto fromEntity(AnalyzeHistory analyzeHistory) {
        return AnalyzeHistoryReadDto.builder()
                .id(analyzeHistory.getId())
                .pcbangName(analyzeHistory.getPcbang().getName())
                .openCount(analyzeHistory.getOpenCount())
                .closeCount(analyzeHistory.getCloseCount())
                .openRate(analyzeHistory.getOpenCount() / (float) (analyzeHistory.getOpenCount() + analyzeHistory.getCloseCount()) * 100)
                .analyzedAt(analyzeHistory.getAnalyzedAt())
                .build();
    }
}
