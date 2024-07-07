package com.gdsanadevlog.pcbanganalyzer.pcbang.dto;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PcbangReadDto {
    private Long id;
    private String ip;
    private int port;
    private String name;
    private String address;
    private int seatCount;
    private String telecom;
    private String pcSpec;
    private String memo;

    public static PcbangReadDto fromEntity(Pcbang pcbang) {
        return PcbangReadDto.builder()
                .id(pcbang.getId())
                .ip(pcbang.getIp())
                .port(pcbang.getPort())
                .name(pcbang.getName())
                .address(pcbang.getAddress())
                .seatCount(pcbang.getSeatCount())
                .telecom(pcbang.getTelecom())
                .pcSpec(pcbang.getPcSpec())
                .memo(pcbang.getMemo())
                .build();
    }
}
