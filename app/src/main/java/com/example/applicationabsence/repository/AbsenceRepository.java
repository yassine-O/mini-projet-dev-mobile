package com.example.applicationabsence.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.applicationabsence.dao.AbsenceDao;
import com.example.applicationabsence.dao.AppDatabase;
import com.example.applicationabsence.entity.Absence;

import java.util.List;

public class AbsenceRepository {

    AbsenceDao absenceDao;

    public AbsenceRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.absenceDao = appDatabase.absenceDao();
    }

    public void insert(Absence absence){
        new InsertAbsenceAsyncTask(absenceDao).execute(absence);
    }

    public LiveData<List<Absence>> getAbsencesEtudiant(int etudiantId){
        return absenceDao.getAbsenceEtudiant(etudiantId);
    }


    private static class InsertAbsenceAsyncTask extends AsyncTask<Absence, Void, Void>{
        private AbsenceDao absenceDao;

        public InsertAbsenceAsyncTask(AbsenceDao absenceDao) {
            this.absenceDao = absenceDao;
        }

        @Override
        protected Void doInBackground(Absence... absences) {
            absenceDao.insert(absences[0]);
            return null;
        }
    }

}
