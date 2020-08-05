package com.example.applicationabsence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationabsence.adapter.AbsenceAdapter;
import com.example.applicationabsence.entity.Absence;
import com.example.applicationabsence.viewmodel.AbsenceViewModel;
import com.example.applicationabsence.viewmodel.factory.ViewModelFactory;

import java.util.List;

public class AbsencesActivity extends AppCompatActivity {

    private AbsenceViewModel absenceViewModel;
    private AbsenceAdapter adapter;
    private int etudiantId;
    private String etudiantNom;
    private TextView textViewNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absences);

        Intent intent = getIntent();
        etudiantId = intent.getIntExtra(EtudiantsActivity.EXTRA_ETUDIANT,-1);
        etudiantNom = intent.getStringExtra(EtudiantsActivity.EXTRA_ETUDIANT_NOM);

        textViewNom = findViewById(R.id.textView_nom);
        textViewNom.setText(etudiantNom);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_absence);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AbsenceAdapter();
        recyclerView.setAdapter(adapter);

        absenceViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(AbsenceViewModel.class);
        absenceViewModel.getAbsences(etudiantId).observe(this, new Observer<List<Absence>>() {
            @Override
            public void onChanged(List<Absence> absences) {
                adapter.setAbsences(absences);
            }
        });

        adapter.setOnAbsenceDeleteListener(new AbsenceAdapter.OnAbsenceDeleteListener() {
            @Override
            public void onAbsenceDelete(Absence absence) {
                absenceViewModel.delete(absence);
                Toast.makeText(AbsencesActivity.this, "Absence supprimée", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.addOnObservationChangedListener(new AbsenceAdapter.OnObservationChangedListener() {
            @Override
            public void onObservationChanged(Absence absence, String observation) {
                if(!absence.observation.equals(observation)){
                    absence.observation = observation;
                    absenceViewModel.update(absence);
                    Toast.makeText(AbsencesActivity.this, "Observation modifiée", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}