package com.example.applicationabsence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationabsence.R;
import com.example.applicationabsence.entity.Absence;

import java.util.ArrayList;
import java.util.List;

public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.AbsenceHolder> {

    private List<Absence> absences = new ArrayList<>();
    private OnAbsenceDeleteListener deleteListener;
    private OnObservationChangedListener observationChangedListener;

    @NonNull
    @Override
    public AbsenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.absence_item,parent,false);
        return new AbsenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenceHolder holder, int position) {
        Absence currentAbsence = absences.get(position);
        holder.textViewDate.setText(currentAbsence.date);
        holder.editTextObservation.setText(currentAbsence.observation);
    }

    @Override
    public int getItemCount() {
        return absences.size();
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
        notifyDataSetChanged();
    }

    class AbsenceHolder extends RecyclerView.ViewHolder{
        private TextView textViewDate;
        private EditText editTextObservation;
        private ImageButton buttonDelete;

        public AbsenceHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textView_date);
            editTextObservation = itemView.findViewById(R.id.editText_observation);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (deleteListener != null && position != RecyclerView.NO_POSITION){
                        deleteListener.onAbsenceDelete(absences.get(position));
                    }
                }
            });
            editTextObservation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    int position = getAdapterPosition();
                    String observation = editTextObservation.getText().toString();
                    InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if (!b && observationChangedListener != null && position != RecyclerView.NO_POSITION ) {
                        observationChangedListener.onObservationChanged(absences.get(position), observation);
                    }
                }
            });
        }
    }

    public interface OnAbsenceDeleteListener {
        void onAbsenceDelete(Absence absence);
    }

    public void setOnAbsenceDeleteListener(OnAbsenceDeleteListener deleteListener){
        this.deleteListener = deleteListener;
    }

    public interface OnObservationChangedListener{
        void onObservationChanged(Absence absence, String observation);
    }

    public void addOnObservationChangedListener(OnObservationChangedListener observationChangedListener){
        this.observationChangedListener = observationChangedListener;
    }

}
