package com.example.applicationabsence.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.applicationabsence.dao.AppDatabase;
import com.example.applicationabsence.dao.ClasseDao;
import com.example.applicationabsence.entity.Classe;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClasseRepository {

    private ClasseDao classeDao;
    private LiveData<List<Classe>> allClasses;

    public ClasseRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        classeDao = appDatabase.classeDao();
        allClasses = classeDao.getAllClasses();
    }

    public long insert(Classe classe) throws Exception {
        return new InsertClasseAsyncTask(classeDao).execute(classe).get();
    }

    public LiveData<List<Classe>> getAllClasses(){
        return allClasses;
    }


    private static class InsertClasseAsyncTask extends AsyncTask<Classe, Void, Long> {

        private ClasseDao classeDao;

        private InsertClasseAsyncTask(ClasseDao classeDao) {
            this.classeDao = classeDao;
        }

        @Override
        protected Long doInBackground(Classe... classes) {
            return classeDao.insert(classes[0]);
        }
    }

}
