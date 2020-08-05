package com.example.applicationabsence.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.applicationabsence.entity.Absence;
import com.example.applicationabsence.repository.AbsenceRepository;

import java.util.List;

public class AbsenceViewModel extends AndroidViewModel {

    private AbsenceRepository absenceRepository;

    public AbsenceViewModel(@NonNull Application application) {
        super(application);
        absenceRepository = new AbsenceRepository(application);
    }

    public void insert(Absence absence){
        this.absenceRepository.insert(absence);
    }

    public LiveData<List<Absence>> getAbsences(int etudiantId){
        return this.absenceRepository.getAbsencesEtudiant(etudiantId);
    }

    public void delete(Absence absence){
        absenceRepository.delete(absence);
    }

    public void update(Absence absence){
        absenceRepository.update(absence);
    }

}
