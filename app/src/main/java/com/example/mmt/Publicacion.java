package com.example.mmt;

import com.google.firebase.Timestamp;

public class Publicacion {

    private String content;
    private String genre;
    private String id;
    private String id_user;
//    private Timestamp creationDate;

    private String location;
    private String title;
    private String video;


    public Publicacion() {
        //nothing
    }
    public Publicacion(String content, String genre, String id, String id_user, String location, String title, String video) {
        this.content = content;
        this.genre = genre;
        this.id = id;
        this.id_user = id_user;
        this.location = location;
        this.title = title;
        this.video = video;
    }

    public String getContent() {
        return content;
    }

//    public Timestamp getCreationDate() {
//        return creationDate;
//    }

    public String getGenre() {
        return genre;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }
    public String getId_user() {
        return id_user;
    }

    public String getId() { return id;}
    public Publicacion setId(String id) {
        this.id = id;
        return this;
    }
    public String getVideo() {
        return video;
    }
}
