package com.example.applicationabsence.viewmodel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationabsence.viewmodel.AbsenceViewModel;
import com.example.applicationabsence.viewmodel.ClasseViewModel;
import com.example.applicationabsence.viewmodel.EtudiantViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClasseViewModel.class)) {
            return (T) new ClasseViewModel(application);
        }
        else if (modelClass.isAssignableFrom(EtudiantViewModel.class)){
            return (T) new EtudiantViewModel(application);
        }
        else if (modelClass.isAssignableFrom(AbsenceViewModel.class)){
            return (T) new AbsenceViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
