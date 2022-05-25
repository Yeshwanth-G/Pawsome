package com.example.dogg;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class helperclass implements Serializable {
    public class image_class{
        String id,url;

        public image_class(String id,String url) {
            this.id = id;
            this.url=url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    String url,name;
    ArrayList<helperclass>breeds;
    image_class image;
    String id,bred_for,breed_group,life_span,temperament;

    public ArrayList<helperclass> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<helperclass> breeds) {
        this.breeds = breeds;
    }

    public helperclass(String url, ArrayList<helperclass>breeds,image_class image, String name, String id, String bred_for, String breed_group, String life_span, String temperament) {
        this.url = url;
        this.bred_for=bred_for;
        this.breeds=breeds;
        this.breed_group=breed_group;
        this.id=id;
        this.life_span=life_span;
        this.name=name;
        this.temperament=temperament;
        this.image=image;
    }

    public image_class getImage() {
        return image;
    }

    public void setImage(image_class image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public String getBreed_group() {
        return breed_group;
    }

    public void setBreed_group(String breed_group) {
        this.breed_group = breed_group;
    }

    public String getBred_for() {
        return bred_for;
    }

    public void setBred_for(String bred_for) {
        this.bred_for = bred_for;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
