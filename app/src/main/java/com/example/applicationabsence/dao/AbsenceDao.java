package com.example.applicationabsence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.applicationabsence.entity.Absence;

import java.util.List;

@Dao
public interface AbsenceDao {

    @Insert
    void insert(Absence absence);

    @Query("select * from absence where etudiantID = :etudiantId")
    LiveData<List<Absence>> getAbsenceEtudiant(int etudiantId);

}
