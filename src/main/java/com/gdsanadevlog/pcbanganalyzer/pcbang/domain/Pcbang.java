package com.gdsanadevlog.pcbanganalyzer.pcbang.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pc_bangs")
public class Pcbang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    private String ip;

    @NotNull
    private int port;

    @NotNull
    private String name;
    private String address;
    private int seatCount;
    private String telecom;
    private String pcSpec;
    private String memo;

    public void isOpen(int port) {
        int openCount = 0;
        int closeCount = 0;

        for (String ip : getAllIps()) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 200);
                socket.close();
                openCount++;
                System.out.println("Port is open at:" + ip + ":" + port);
            } catch (IOException e) {
                closeCount++;
                System.out.println("Port is not open at:" + ip + ":" + port);
            }
        }
    }

    public List<String> getAllIps() {
        List<String> ips = new ArrayList<>();
        String[] segments = ip.split("\\.");
        int start = Integer.parseInt(segments[3]);

        for (int i = 0; i < seatCount; i++) {
            segments[3] = String.valueOf(start + i);
            ips.add(String.join(".", segments));
        }

        return ips;
    }

    public void updatePcbang(
            String ip, int port, String name, String address, int seatCount, String telecom, String pcSpec, String memo
    ) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.address = address;
        this.seatCount = seatCount;
        this.telecom = telecom;
        this.pcSpec = pcSpec;
        this.memo = memo;
    }
}
