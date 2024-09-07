package com.juegoantena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.LetterViewHolder> {

    Letter[] letterList;
    private final LetterClickListener listener;

    public LetterAdapter(LetterClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {
        Letter letter = letterList[position];
        holder.letterButton.setText(letter.letter);

        if(letter.selected) {
            holder.letterButton.setEnabled(false);
            holder.letterButton.setBackgroundColor(0xFF4CAF50);
        } else {
            holder.letterButton.setEnabled(true);
            holder.letterButton.setBackgroundColor(0xFFFFFF);

        }
        holder.letterButton.setOnClickListener(v -> listener.onLetterClicked(letter, holder));
    }

    @Override
    public int getItemCount() {
        return letterList.length;
    }

    public static class LetterViewHolder extends RecyclerView.ViewHolder {
        TextView letterButton;

        public LetterViewHolder(@NonNull View itemView) {
            super(itemView);
            letterButton = itemView.findViewById(R.id.btn_letter);
        }
    }

    public interface LetterClickListener {
        void onLetterClicked(Letter letter, LetterViewHolder holder);
    }
}
