package com.insoft.attsaqofiy.json;

import com.insoft.attsaqofiy.model.Jadwal;

import java.util.ArrayList;
import java.util.List;

public class JadwalResponseJson {
    private String status;
    private List<Jadwal> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Jadwal> getData() {
        return data;
    }

    public void setData(List<Jadwal> data) {
        this.data = data;
    }
}
