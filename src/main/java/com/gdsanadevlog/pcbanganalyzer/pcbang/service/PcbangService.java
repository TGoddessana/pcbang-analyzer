package com.gdsanadevlog.pcbanganalyzer.pcbang.service;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangCreateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangUpdateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.AnalyzeHistoryRepository;
import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.PcbangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PcbangService {
    private final PcbangRepository pcbangRepository;
    private final AnalyzeHistoryRepository analyzeHistoryRepository;

    public void savePcbang(PcbangCreateDto pcbangCreateDto) {
        pcbangRepository.save(pcbangCreateDto.toEntity());
    }

    public Page<PcbangReadDto> listPcbangsPaginated(int page, int size) {
        Page<Pcbang> pcbangPage = pcbangRepository.findAll(PageRequest.of(page, size));

        return pcbangPage.map(PcbangReadDto::fromEntity);
    }

    public PcbangReadDto findPcbangById(Long id) {
        return pcbangRepository.findById(id)
                .map(PcbangReadDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Pcbang not found"));
    }

    public void updatePcbang(long id, PcbangUpdateDto pcbangUpdateDto) {
        Pcbang pcbang = pcbangRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pcbang not found"));

        pcbang.updatePcbang(
                pcbangUpdateDto.getIp(),
                pcbangUpdateDto.getPort(),
                pcbangUpdateDto.getName(),
                pcbangUpdateDto.getAddress(),
                pcbangUpdateDto.getSeatCount(),
                pcbangUpdateDto.getTelecom(),
                pcbangUpdateDto.getPcSpec(),
                pcbangUpdateDto.getMemo()
        );

        pcbangRepository.save(pcbang);
    }

    public void deletePcbangById(Long id) {
        pcbangRepository.deleteById(id);
    }

    //    @Scheduled(cron = "0 0/30 * * * *")
//    @Async
//    @Scheduled(cron = "0/10 * * * * *")
//    public void analyzePcbang() {
//        System.out.println("Analyze PCBangs at " + LocalDateTime.now());
//        List<Pcbang> pcbangs = pcbangRepository.findAll();
//
//        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
//        for (Pcbang pcbang : pcbangs) {
//            AnalyzeHistory analyzeHistory = pcbang.analyze(now);
//            analyzeHistoryRepository.save(analyzeHistory);
//        }
//    }
}
