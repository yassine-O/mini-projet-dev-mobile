package com.example.applicationabsence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationabsence.R;
import com.example.applicationabsence.entity.Classe;

import java.util.ArrayList;
import java.util.List;

public class ClasseAdapter extends RecyclerView.Adapter<ClasseAdapter.ClasseHolder> {

    private List<Classe> classes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ClasseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classe_item, parent,false);
        return new ClasseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClasseHolder holder, int position) {
        Classe currentClasse = classes.get(position);
        holder.textViewClasse.setText(currentClasse.nom);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
        notifyDataSetChanged();
    }

    class ClasseHolder extends RecyclerView.ViewHolder{
        private TextView textViewClasse;

        public ClasseHolder(@NonNull View itemView) {
            super(itemView);
            textViewClasse = itemView.findViewById(R.id.textView_classe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null || position == RecyclerView.NO_POSITION){
                        listener.onItemClick(classes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Classe classe);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
