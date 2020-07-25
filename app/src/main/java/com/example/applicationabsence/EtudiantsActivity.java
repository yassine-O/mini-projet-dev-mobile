package com.example.applicationabsence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.applicationabsence.adapter.EtudiantAdapter;
import com.example.applicationabsence.entity.Etudiant;
import com.example.applicationabsence.viewmodel.EtudiantViewModel;
import com.example.applicationabsence.viewmodel.factory.ViewModelFactory;

import java.util.List;

public class EtudiantsActivity extends AppCompatActivity {

    public static final String EXTRA_CLASSE = "extra_classe";

    private EtudiantViewModel etudiantViewModel;
    private EtudiantAdapter adapter;
    private int classeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);

        Intent intent = getIntent();
        classeId = intent.getIntExtra(EXTRA_CLASSE,-1);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_etudiants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new EtudiantAdapter();
        recyclerView.setAdapter(adapter);

        etudiantViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(EtudiantViewModel.class);
        etudiantViewModel.getEtidiants(classeId).observe(this, new Observer<List<Etudiant>>() {
            @Override
            public void onChanged(List<Etudiant> etudiants) {
                adapter.setEtudiants(etudiants);
                Toast.makeText(EtudiantsActivity.this, "etidiant activity open---"+classeId+"---", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.etudiants_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_etudiants_absence :
                saveEtudiantsAbsence();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void saveEtudiantsAbsence(){
        List<Etudiant> selectedEtudiants = adapter.getSelected();
        for (Etudiant etd : selectedEtudiants){
            Toast.makeText(this, etd.nom, Toast.LENGTH_SHORT).show();
        }
    }
}