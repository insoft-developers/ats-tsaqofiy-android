package com.insoft.attsaqofiy.json;

import com.insoft.attsaqofiy.model.Keluar;

import java.util.ArrayList;
import java.util.List;

public class KeluarResponseJson {
    private String status;
    private List<Keluar> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Keluar> getData() {
        return data;
    }

    public void setData(List<Keluar> data) {
        this.data = data;
    }
}
