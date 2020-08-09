package com.example.applicationabsence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.applicationabsence.entity.Classe;

import java.util.List;

@Dao
public interface ClasseDao {

    @Insert
    long insert(Classe classe);

    @Query("select * from classe")
    LiveData<List<Classe>> getAllClasses();

}
