package com.example.dogg;

public class uploadresponse {
    String messege,status,level;

    public uploadresponse(String messege,String status,String level) {
        this.messege = messege;
        this.status=status;
        this.level=level;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
