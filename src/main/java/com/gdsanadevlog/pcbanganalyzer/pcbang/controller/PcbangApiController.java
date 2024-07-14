package com.gdsanadevlog.pcbanganalyzer.pcbang.controller;

import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangCreateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangUpdateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.service.PcbangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pcbangs")
@RequiredArgsConstructor
public class PcbangApiController {
    private final PcbangService pcbangService;

    @PostMapping("")
    public ResponseEntity<PcbangCreateDto> createPcbang(@RequestBody PcbangCreateDto pcbangCreateDto) {
        try {
            pcbangService.savePcbang(pcbangCreateDto);
            return ResponseEntity.ok(pcbangCreateDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PcbangReadDto> getPcbangDetails(@PathVariable Long id) {
        try {
            PcbangReadDto pcbangReadDto = pcbangService.findPcbangById(id);
            return ResponseEntity.ok(pcbangReadDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PcbangReadDto> updatePcbang(@PathVariable Long id, @RequestBody PcbangUpdateDto pcbangUpdateDto) {
        try {
            pcbangService.updatePcbang(id, pcbangUpdateDto);
            return ResponseEntity.ok(pcbangService.findPcbangById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePcbang(@PathVariable Long id) {
        try {
            pcbangService.deletePcbangById(id);
            return ResponseEntity.ok("Pcbang deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
