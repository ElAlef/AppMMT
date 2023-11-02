package com.example.mmt;

import com.google.firebase.Timestamp;

public class Publicacion {
    private String id;
    private String content;
    private Timestamp creationDate;
    private String genre;
    private String location;
    private String title;

    public Publicacion() {
        //nothing
    }
    public Publicacion(String content, Timestamp creationDate, String genre, String location, String title) {
        this.content = content;
        this.creationDate = creationDate;
        this.genre = genre;
        this.location = location;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getId() { return id;}
    public Publicacion setId(String id) {
        this.id = id;
        return this;
    }
}
