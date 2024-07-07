package com.gdsanadevlog.pcbanganalyzer.pcbang.dto;

import com.gdsanadevlog.pcbanganalyzer.pcbang.domain.Pcbang;
import lombok.Getter;

@Getter
public class PcbangUpdateDto {
    private String ip;
    private int port;
    private String name;
    private String address;
    private int seatCount;
    private String telecom;
    private String pcSpec;
    private String memo;

    @Override
    public String toString() {
        return "PcbangUpdateDto{" +
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
