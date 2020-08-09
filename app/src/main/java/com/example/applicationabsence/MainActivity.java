package com.example.applicationabsence;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.applicationabsence.adapter.ClasseAdapter;
import com.example.applicationabsence.entity.Classe;
import com.example.applicationabsence.entity.Etudiant;
import com.example.applicationabsence.viewmodel.ClasseViewModel;
import com.example.applicationabsence.viewmodel.EtudiantViewModel;
import com.example.applicationabsence.viewmodel.factory.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_FILE = 123;

    private ClasseViewModel classeViewModel;
    private EtudiantViewModel etudiantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        FloatingActionButton fabAddClasse = findViewById(R.id.fab_add_classe);
        fabAddClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ClasseAdapter classeAdapter = new ClasseAdapter();
        recyclerView.setAdapter(classeAdapter);

        etudiantViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(EtudiantViewModel.class);
        classeViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(ClasseViewModel.class);
        classeViewModel.getAllClasses().observe(this, new Observer<List<Classe>>() {
            @Override
            public void onChanged(List<Classe> classes) {
                classeAdapter.setClasses(classes);
                Button buttonAdd = findViewById(R.id.button_add);
                if (classeAdapter.getItemCount() == 0) {
                    buttonAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openFile();
                        }
                    });
                }
                else {
                    buttonAdd.setVisibility(View.GONE);
                }
            }
        });

        classeAdapter.setOnItemClickListener(new ClasseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Classe classe) {
                Intent intent = new Intent(MainActivity.this,EtudiantsActivity.class);
                intent.putExtra(EtudiantsActivity.EXTRA_CLASSE,classe.id);
                intent.putExtra(EtudiantsActivity.EXTRA_ETUDIANT_NOM, classe.nom);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                String displayName = classeViewModel.getFileNameFromUri(uri);
                String classeName = null;
                try {
                    if (displayName.contains(".txt")){
                        classeName = displayName.replace(".txt", "");
                    }
                    else if (displayName.contains(".csv")){
                        classeName = displayName.replace(".csv", "");
                    }
                    List<String> etdNames = classeViewModel.readTextFromUri(uri);
                    long classeId = classeViewModel.insert(new Classe(classeName));
                    for (String etd : etdNames){
                        etudiantViewModel.insert(new Etudiant(etd, classeId));
                    }
                    Toast.makeText(this, "Classe enregistrée", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimeTypes = {"text/plain", "text/comma-separated-values"};
        intent .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_FILE);
    }

}