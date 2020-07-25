package com.example.applicationabsence.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.applicationabsence.entity.Classe;
import com.example.applicationabsence.repository.ClasseRepository;

import java.util.List;

public class ClasseViewModel extends AndroidViewModel {

    private ClasseRepository classeRepository;
    private LiveData<List<Classe>> allClasses;

    public ClasseViewModel(@NonNull Application application) {
        super(application);
        classeRepository = new ClasseRepository(application);
        allClasses = classeRepository.getAllClasses();
    }

    public void insert(Classe classe){
        classeRepository.insert(classe);
    }

    public LiveData<List<Classe>> getAllClasses(){
        return allClasses;
    }

}
