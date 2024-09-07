package com.juegoantena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder> {

    private final PlayerStats playerStats;

    public StatsAdapter(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        GameResult gameResult = playerStats.getGameResults().get(position);

        String resultText;
        switch (gameResult.getResultType()) {
            case WIN:
                resultText = "Juego " + (position + 1) + ": Ganó en " + gameResult.getTimeInSeconds() + " segundos";
                break;
            case LOSE:
                resultText = "Juego " + (position + 1) + ": Perdió";
                break;
            case CANCELLED:
                resultText = "Juego " + (position + 1) + ": Cancelado";
                break;
            default:
                resultText = "";
        }

        holder.textStat.setText(resultText);
    }

    @Override
    public int getItemCount() {
        return playerStats.getGameResults().size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        TextView textStat;

        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            textStat = itemView.findViewById(R.id.text_stat);
        }
    }
}
