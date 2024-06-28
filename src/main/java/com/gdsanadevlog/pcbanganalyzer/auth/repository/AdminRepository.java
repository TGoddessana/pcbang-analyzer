package com.gdsanadevlog.pcbanganalyzer.auth.repository;

import com.gdsanadevlog.pcbanganalyzer.auth.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
