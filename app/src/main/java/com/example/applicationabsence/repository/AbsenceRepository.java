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

    public void delete(Absence absence){
        new DeleteAbsenceAsyncTask(absenceDao).execute(absence);
    }

    public void update(Absence absence){
        new UpdateAbsenceAsyncTask(absenceDao).execute(absence);
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

    private static class DeleteAbsenceAsyncTask extends AsyncTask<Absence, Void, Void>{
        private AbsenceDao absenceDao;

        public DeleteAbsenceAsyncTask(AbsenceDao absenceDao) {
            this.absenceDao = absenceDao;
        }

        @Override
        protected Void doInBackground(Absence... absences) {
            absenceDao.delete(absences[0]);
            return null;
        }
    }

    private static class UpdateAbsenceAsyncTask extends AsyncTask<Absence, Void, Void>{
        private AbsenceDao absenceDao;

        public UpdateAbsenceAsyncTask(AbsenceDao absenceDao) {
            this.absenceDao = absenceDao;
        }

        @Override
        protected Void doInBackground(Absence... absences) {
            absenceDao.update(absences[0]);
            return null;
        }
    }

}
