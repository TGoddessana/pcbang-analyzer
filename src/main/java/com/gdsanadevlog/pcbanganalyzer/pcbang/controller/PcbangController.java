package com.gdsanadevlog.pcbanganalyzer.pcbang.controller;

import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangCreateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.service.PcbangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PcbangController {
    private final PcbangService pcbangService;

    @PostMapping("/pcbangs")
    @ResponseBody
    public ResponseEntity<PcbangCreateDto> createPcbang(@RequestBody PcbangCreateDto pcbangCreateDto) {
        try {
            pcbangService.savePcbang(pcbangCreateDto);
            return ResponseEntity.ok(pcbangCreateDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pcbangs")
    public String listPcbangs(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {

        Page<PcbangReadDto> pcbangDtoPage = pcbangService.listPcbangsPaginated(page, size);

        model.addAttribute("pcbangs", pcbangDtoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pcbangDtoPage.getTotalPages());

        return "pages/pcbang/pcbang-list";
    }
}
