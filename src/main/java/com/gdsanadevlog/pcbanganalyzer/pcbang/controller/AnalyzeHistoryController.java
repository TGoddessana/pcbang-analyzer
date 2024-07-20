package com.gdsanadevlog.pcbanganalyzer.pcbang.controller;

import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.AnalyzeHistoryReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.service.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AnalyzeHistoryController {
    private final AnalyzeService analyzeHistoryService;

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(value = "pcbangName", required = false) String pcbangName,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

        Page<AnalyzeHistoryReadDto> analyzeHistoryDtoPage;

        if (pcbangName == null || pcbangName.isEmpty()) {
            analyzeHistoryDtoPage = analyzeHistoryService.listAllAnalyzeHistories(page, size);
        } else {
            analyzeHistoryDtoPage = analyzeHistoryService.searchAnalyzeHistoriesByPcbangName(pcbangName, page, size);
        }

        model.addAttribute("searchKeyword", pcbangName);
        model.addAttribute("analyzeHistories", analyzeHistoryDtoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", analyzeHistoryDtoPage.getTotalPages());

        return "pages/index";
    }
}
