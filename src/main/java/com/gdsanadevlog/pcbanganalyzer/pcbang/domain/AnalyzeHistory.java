package com.gdsanadevlog.pcbanganalyzer.pcbang.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
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
