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
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ClasseDao classeDao;
        private EtudiantDao etudiantDao;
        private AbsenceDao absenceDao;

        public PopulateDbAsyncTask(AppDatabase db) {
            classeDao = db.classeDao();
            etudiantDao = db.etudiantDao();
            absenceDao = db.absenceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            classeDao.insert(new Classe("info"));
            classeDao.insert(new Classe("cp"));
            classeDao.insert(new Classe("gp"));
        /*    classeDao.insert(new Classe("fid"));
            classeDao.insert(new Classe("elec"));
            classeDao.insert(new Classe("indus"));
            classeDao.insert(new Classe("meca"));*/

            etudiantDao.insert(new Etudiant("info1",1));
            etudiantDao.insert(new Etudiant("info2",1));
            etudiantDao.insert(new Etudiant("cp1",2));

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = formatter.format(todayDate);


            return null;
        }
    }

}
