package com.example.applicationabsence.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Classe.class, parentColumns = "id", childColumns = "classeID"))
public class Etudiant {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nom;

    public int classeID;

    @Ignore
    public boolean ischecked = false;

    public Etudiant(String nom, int classeID) {
        this.nom = nom;
        this.classeID = classeID;
    }
}
