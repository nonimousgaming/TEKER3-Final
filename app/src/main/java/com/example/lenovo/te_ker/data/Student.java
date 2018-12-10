package com.example.lenovo.te_ker.data;

public class Student {

    public String id;
    public String name;
    public String email;
    public String parent_name;
    public String parent_number;
    public String status;

    public Student() {
        this.id = id;
        this.name = name;
        this.email = email;
        this.parent_name = parent_name;
        this.parent_number = parent_number;
        this.status = status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_number() {
        return parent_number;
    }

    public void setParent_number(String parent_number) {
        this.parent_number = parent_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
