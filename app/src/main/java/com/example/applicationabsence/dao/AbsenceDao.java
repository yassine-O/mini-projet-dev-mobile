package com.example.applicationabsence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.applicationabsence.entity.Absence;

import java.util.List;

@Dao
public interface AbsenceDao {

    @Insert
    void insert(Absence absence);

    @Delete
    void delete(Absence absence);

    @Update
    void update(Absence absence);

    @Query("select * from Absence where etudiantID = :etudiantId")
    LiveData<List<Absence>> getAbsenceEtudiant(int etudiantId);

}
