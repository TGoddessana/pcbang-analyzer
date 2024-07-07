package com.gdsanadevlog.pcbanganalyzer.pcbang.controller;

import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangCreateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.PcbangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DashboardController {
    private final PcbangRepository pcbangRepository;

    @GetMapping("")
    public String index() {
        return "pages/index";
    }

    @GetMapping("/pcbangs")
    public String listPcbangs() {
        return "pages/dashboard/pcbangs/pcbang-list";
    }

    @PostMapping("/pcbangs")
    @ResponseBody
    public ResponseEntity<PcbangCreateDto> addPcbang(@RequestBody PcbangCreateDto pcbangCreateDto) {
        try {
            System.out.println("pcbangCreateDto = " + pcbangCreateDto);
            pcbangRepository.save(pcbangCreateDto.toEntity());
            return ResponseEntity.ok(pcbangCreateDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
