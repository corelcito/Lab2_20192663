package com.juegoantena;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class StatsActivity extends AppCompatActivity {
    PlayerStats playerStats;
    RecyclerView rvStats;
    StatsAdapter statsAdapter;
    TextView textNamePlayer;
    ConstraintLayout toolbar;
    ImageView btnBack;
    MaterialButton btnNewGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar_stats);
        btnNewGame = findViewById(R.id.btn_new_game);
        btnBack = toolbar.findViewById(R.id.btn_back);
        playerStats = (PlayerStats) getIntent().getExtras().getSerializable("stats");
        textNamePlayer = findViewById(R.id.text_name_player);
        rvStats = findViewById(R.id.rv_stats);
        textNamePlayer.setText(playerStats.getPlayerName()+" ");
        rvStats.setLayoutManager(new LinearLayoutManager(this));

        statsAdapter = new StatsAdapter(playerStats);
        rvStats.setAdapter(statsAdapter);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}