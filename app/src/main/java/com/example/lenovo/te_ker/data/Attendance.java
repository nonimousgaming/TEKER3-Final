package com.example.lenovo.te_ker.data;

public class Attendance {

    public String id;
    public String name;
    public String attendance;
    public String phone;
    public String total;

    public Attendance() {
        this.id = id;
        this.name = name;
        this.attendance = attendance;
        this.phone = phone;
        this.total = total;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
