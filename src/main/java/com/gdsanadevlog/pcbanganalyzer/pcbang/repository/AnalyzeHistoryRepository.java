package com.gdsanadevlog.pcbanganalyzer.pcbang.repository;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.AnalyzeHistory;
import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyzeHistoryRepository extends JpaRepository<AnalyzeHistory, Long> {
    Page<AnalyzeHistory> findByPcbangNameContainingIgnoreCase(String pcbangName, Pageable pageable);
}
