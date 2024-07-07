package com.gdsanadevlog.pcbanganalyzer.pcbang.service;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangCreateDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.dto.PcbangReadDto;
import com.gdsanadevlog.pcbanganalyzer.pcbang.repository.PcbangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PcbangService {
    private final PcbangRepository pcbangRepository;

    public void savePcbang(PcbangCreateDto pcbangCreateDto) {
        pcbangRepository.save(pcbangCreateDto.toEntity());
    }

    public Page<PcbangReadDto> findPcbangsPaginated(int page, int size) {
        Page<Pcbang> pcbangPage = pcbangRepository.findAll(PageRequest.of(page, size));

        return pcbangPage.map(PcbangReadDto::fromEntity);
    }
}
