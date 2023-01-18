package fr.ensim.info.tp3aymaneouhadi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private Date creation;
    private String content;

    private String auteur;

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }


    public Address() {
    }

    public Address(Long id, Date creation, String content) {
        this.id = id;
        this.creation = creation;
        this.content = content;
    }

    public Address(Long id, Date creation, String content, String auteur) {
        this.id = id;
        this.creation = creation;
        this.content = content;
        this.auteur = auteur;
    }

    public Address(Date creation, String content) {
        this.creation = creation;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
