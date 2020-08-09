package com.example.applicationabsence.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.applicationabsence.dao.AppDatabase;
import com.example.applicationabsence.dao.EtudiantDao;

import com.example.applicationabsence.entity.Etudiant;

import java.util.List;

public class EtudiantRepository {

    EtudiantDao etudiantDao;

    public EtudiantRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.etudiantDao = appDatabase.etudiantDao();
    }

    public void insert(Etudiant etudiant){
        new InsertEtudiantAsyncTask(etudiantDao).execute(etudiant);
    }

    public LiveData<List<Etudiant>> getEtudiants(long classeId){
        return etudiantDao.getClasseEtudiants(classeId);
    }


    private static class InsertEtudiantAsyncTask extends AsyncTask<Etudiant, Void, Void>{
        private EtudiantDao etudiantDao;

        public InsertEtudiantAsyncTask(EtudiantDao etudiantDao) {
            this.etudiantDao = etudiantDao;
        }

        @Override
        protected Void doInBackground(Etudiant... etudiants) {
            etudiantDao.insert(etudiants[0]);
            return null;
        }
    }
}
