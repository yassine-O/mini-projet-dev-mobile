package com.example.applicationabsence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import com.example.applicationabsence.entity.Absence;
import com.example.applicationabsence.entity.Etudiant;
import com.example.applicationabsence.viewmodel.AbsenceViewModel;
import com.example.applicationabsence.viewmodel.EtudiantViewModel;
import com.example.applicationabsence.viewmodel.factory.ViewModelFactory;

import java.util.Date;
import java.util.List;

public class EtudiantsActivity extends AppCompatActivity {

    public static final String EXTRA_CLASSE = "extra_classe";
    public static final String EXTRA_ETUDIANT = "extra_etudiant";
    public static final String EXTRA_ETUDIANT_NOM = "extra_etudiant_nom";

    private EtudiantViewModel etudiantViewModel;
    private AbsenceViewModel absenceViewModel;
    private EtudiantAdapter adapter;
    private long classeId;
    private String classeNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Intent intent = getIntent();
        classeId = intent.getLongExtra(EXTRA_CLASSE,-1);
        classeNom = intent.getStringExtra(EXTRA_ETUDIANT_NOM);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(classeNom);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_etudiants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new EtudiantAdapter();
        recyclerView.setAdapter(adapter);

        absenceViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(AbsenceViewModel.class);
        etudiantViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(EtudiantViewModel.class);
        etudiantViewModel.getEtidiants(classeId).observe(this, new Observer<List<Etudiant>>() {
            @Override
            public void onChanged(List<Etudiant> etudiants) {
                adapter.setEtudiants(etudiants);
            }
        });
        
        adapter.setOnItemLongClickListener(new EtudiantAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Etudiant etudiant) {
                Intent intent = new Intent(EtudiantsActivity.this, AbsencesActivity.class);
                intent.putExtra(EXTRA_ETUDIANT,etudiant.id);
                intent.putExtra(EXTRA_ETUDIANT_NOM,etudiant.nom);
                startActivity(intent);
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
        String currentDate = java.text.DateFormat.getDateInstance().format(new Date());
        Absence absence = new Absence(currentDate);
        for (Etudiant etd : selectedEtudiants){
            absence.etudiantID = etd.id;
            absenceViewModel.insert(absence);
        }
        finish();
        Toast.makeText(this, "Absense bien notée", Toast.LENGTH_SHORT).show();
    }
}