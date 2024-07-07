package com.gdsanadevlog.pcbanganalyzer.pcbang.dto;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import lombok.Getter;

@Getter
public class PcbangCreateDto {
    private String ip;
    private int port;
    private String name;
    private String address;
    private int seatCount;
    private String telecom;
    private String pcSpec;
    private String memo;

    public Pcbang toEntity() {
        return Pcbang.builder()
                .ip(ip)
                .port(port)
                .name(name)
                .address(address)
                .seatCount(seatCount)
                .telecom(telecom)
                .pcSpec(pcSpec)
                .memo(memo)
                .build();
    }

    @Override
    public String toString() {
        return "PcbangCreateDto{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", seatCount=" + seatCount +
                ", telecom='" + telecom + '\'' +
                ", pcSpec='" + pcSpec + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
