package com.example.applicationabsence.viewmodel;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.applicationabsence.entity.Classe;
import com.example.applicationabsence.repository.ClasseRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClasseViewModel extends AndroidViewModel {

    private ClasseRepository classeRepository;
    private LiveData<List<Classe>> allClasses;

    public ClasseViewModel(@NonNull Application application) {
        super(application);
        classeRepository = new ClasseRepository(application);
        allClasses = classeRepository.getAllClasses();
    }

    public Long insert(Classe classe) throws Exception {
        return classeRepository.insert(classe);
    }

    public LiveData<List<Classe>> getAllClasses(){
        return allClasses;
    }

    public String getFileNameFromUri(Uri uri) {
        Cursor cursor = this.getApplication().getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                return displayName;
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    public List<String> readTextFromUri(Uri uri) throws IOException {
        List<String> etdNames = new ArrayList<>();
        try (InputStream inputStream = this
             .getApplication().
                     getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            if (getFileNameFromUri(uri).contains(".txt")){
                while ((line = reader.readLine()) != null) {
                    etdNames.add(line);
                }
            }
            else if (getFileNameFromUri(uri).contains(".csv")){
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    etdNames.add(line.split(";")[1]);
                }
            }
        }
        return etdNames;
    }

}
