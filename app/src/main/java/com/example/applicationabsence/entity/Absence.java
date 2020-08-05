package com.example.applicationabsence.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Etudiant.class, parentColumns = "id", childColumns = "etudiantID"))
public class Absence {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;

    public String observation = "";

    public int etudiantID;

    public Absence(String date) {
        this.date = date;
    }
}
