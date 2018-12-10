package com.example.lenovo.te_ker.data;

/**
 * Created by john paul on 10/14/2018.
 */

public class Section {
    public String id;
    public String name;
    public String subject;
    public String start_time;
    public String end_time;
    public String room;
    public String status;

    public Section() {

    }

    public Section(String id, String name, String subject, String start_time, String end_time, String room, String status) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.start_time = start_time;
        this.end_time = end_time;
        this.room = room;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
