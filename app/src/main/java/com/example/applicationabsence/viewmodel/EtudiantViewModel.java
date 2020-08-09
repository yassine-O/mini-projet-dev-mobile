package com.example.applicationabsence.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.applicationabsence.entity.Etudiant;
import com.example.applicationabsence.repository.EtudiantRepository;

import java.util.List;

public class EtudiantViewModel extends AndroidViewModel {

    private EtudiantRepository etudiantRepository;

    public EtudiantViewModel(@NonNull Application application) {
        super(application);
        etudiantRepository = new EtudiantRepository(application);
    }

    public void insert(Etudiant etudiant){
        this.etudiantRepository.insert(etudiant);
    }

    public LiveData<List<Etudiant>> getEtidiants(long classeId){
        return this.etudiantRepository.getEtudiants(classeId);
    }

}
