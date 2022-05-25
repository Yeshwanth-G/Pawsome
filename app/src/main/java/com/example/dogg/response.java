package com.example.dogg;

public class response {
    String messege,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public response(String id, String messege) {
        this.id=id;
        this.messege = messege;
    }
}
