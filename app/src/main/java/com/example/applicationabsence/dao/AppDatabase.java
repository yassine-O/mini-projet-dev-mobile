package com.example.applicationabsence.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.applicationabsence.entity.Absence;
import com.example.applicationabsence.entity.Classe;
import com.example.applicationabsence.entity.Etudiant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Database(entities = {Classe.class, Etudiant.class, Absence.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ClasseDao classeDao();
    public abstract EtudiantDao etudiantDao();
    public abstract AbsenceDao absenceDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "absence_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
