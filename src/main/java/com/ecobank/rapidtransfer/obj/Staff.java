package com.ecobank.rapidtransfer.obj;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

public class Staff {
    private int staffId;
    private String name;
    private String dept;

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }
}
