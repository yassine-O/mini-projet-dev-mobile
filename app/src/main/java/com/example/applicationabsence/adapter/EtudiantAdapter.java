package com.example.applicationabsence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationabsence.R;
import com.example.applicationabsence.entity.Etudiant;

import java.util.ArrayList;
import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantHolder> {

    private List<Etudiant> etudiants = new ArrayList<>();
    private OnItemLongClickListener longClickListener;

    @NonNull
    @Override
    public EtudiantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.etudiant_item, parent,false);
        return new EtudiantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantHolder holder, final int position) {
        final Etudiant currentEtudiant = etudiants.get(position);
        holder.textViewEtudiant.setText(currentEtudiant.nom);
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                etudiants.get(position).ischecked = b;
                if (b) {
                    compoundButton.setText("Absent");
                }
                else {
                    compoundButton.setText("");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        notifyDataSetChanged();
    }

    public List<Etudiant> getSelected(){
        List<Etudiant> selectedEtudiants = new ArrayList<>();
        for (Etudiant etd : etudiants){
            if (etd.ischecked == true){
                selectedEtudiants.add(etd);
            }
        }
        return selectedEtudiants;
    }


    class EtudiantHolder extends RecyclerView.ViewHolder{
        private TextView textViewEtudiant;
        private Switch aSwitch;

        public EtudiantHolder(@NonNull View itemView) {
            super(itemView);
            textViewEtudiant = itemView.findViewById(R.id.textView_etudiant);
            aSwitch = itemView.findViewById(R.id.switch_button);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (longClickListener != null || position == RecyclerView.NO_POSITION){
                        longClickListener.onItemLongClick(etudiants.get(position));
                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(Etudiant etudiant);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

}
