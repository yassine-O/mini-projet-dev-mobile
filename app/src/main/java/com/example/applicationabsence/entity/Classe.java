package com.example.applicationabsence.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Classe {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nom;

    public Classe(String nom) {
        this.nom = nom;
    }
}
