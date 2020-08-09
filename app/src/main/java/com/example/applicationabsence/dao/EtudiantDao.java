package com.example.applicationabsence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.applicationabsence.entity.Etudiant;

import java.util.List;

@Dao
public interface EtudiantDao {

    @Insert
    void insert(Etudiant etudiant);

    @Query("select * from etudiant where classeID = :classeId")
    LiveData<List<Etudiant>> getClasseEtudiants(long classeId);

}