package com.juegoantena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private final char[] word;

    public WordAdapter(char[] word) {
        this.word = word;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.letterText.setText(String.valueOf(word[position]));
    }

    @Override
    public int getItemCount() {
        return word.length;
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView letterText;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            letterText = itemView.findViewById(R.id.txt_letter);
        }
    }
}
