package com.gdsanadevlog.pcbanganalyzer.pcbang.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analyze_histories")
public class AnalyzeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pcbang_id")
    private Pcbang pcbang;

    private int openCount;
    private int closeCount;

    private LocalDateTime analyzedAt;
}
