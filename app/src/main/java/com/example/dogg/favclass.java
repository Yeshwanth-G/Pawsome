package com.example.dogg;

public class favclass {
    String image_id,sub_id;

    public favclass(String image_id,String sub_id) {
        this.image_id=image_id;
        this.sub_id = sub_id;
    }
    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }
}
