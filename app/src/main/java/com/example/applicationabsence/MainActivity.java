package com.example.applicationabsence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.applicationabsence.adapter.ClasseAdapter;
import com.example.applicationabsence.entity.Classe;
import com.example.applicationabsence.viewmodel.ClasseViewModel;
import com.example.applicationabsence.viewmodel.factory.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClasseViewModel classeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabAddClasse = findViewById(R.id.fab_add_classe);
        fabAddClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "fab clicked", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ClasseAdapter classeAdapter = new ClasseAdapter();
        recyclerView.setAdapter(classeAdapter);

        classeViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(ClasseViewModel.class);
        classeViewModel.getAllClasses().observe(this, new Observer<List<Classe>>() {
            @Override
            public void onChanged(List<Classe> classes) {
                classeAdapter.setClasses(classes);
                Toast.makeText(MainActivity.this, classes.get(0).nom, Toast.LENGTH_SHORT).show();
            }
        });

        classeAdapter.setOnItemClickListener(new ClasseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Classe classe) {
                Intent intent = new Intent(MainActivity.this,EtudiantsActivity.class);
                intent.putExtra(EtudiantsActivity.EXTRA_CLASSE,classe.id);
                startActivity(intent);
                Toast.makeText(MainActivity.this, classe.nom+" clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}