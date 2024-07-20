package com.gdsanadevlog.pcbanganalyzer.pcbang.repository;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PcbangRepository extends JpaRepository<Pcbang, Long> {
}
