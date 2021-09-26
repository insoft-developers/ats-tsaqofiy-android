package com.insoft.attsaqofiy.json;

import com.insoft.attsaqofiy.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginResponseJson {
    private String status;
    private List<User> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
