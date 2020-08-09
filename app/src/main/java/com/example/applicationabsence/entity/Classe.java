package com.example.applicationabsence.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Classe {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String nom;

    public Classe(String nom) {
        this.nom = nom;
    }
}
