package com.example.applicationabsence.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.applicationabsence.dao.AppDatabase;
import com.example.applicationabsence.dao.ClasseDao;
import com.example.applicationabsence.entity.Classe;

import java.util.List;

public class ClasseRepository {

    private ClasseDao classeDao;
    private LiveData<List<Classe>> allClasses;

    public ClasseRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        classeDao = appDatabase.classeDao();
        allClasses = classeDao.getAllClasses();
    }

    public void insert(Classe classe){
        new InsertClasseAsyncTask(classeDao).execute(classe);
    }

    public LiveData<List<Classe>> getAllClasses(){
        return allClasses;
    }


    private static class InsertClasseAsyncTask extends AsyncTask<Classe, Void, Void> {

        private ClasseDao classeDao;

        private InsertClasseAsyncTask(ClasseDao classeDao) {
            this.classeDao = classeDao;
        }

        @Override
        protected Void doInBackground(Classe... classes) {
            classeDao.insert(classes[0]);
            return null;
        }
    }

}
