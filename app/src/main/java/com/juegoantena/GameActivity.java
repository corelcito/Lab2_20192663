package com.juegoantena;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private String[] wordList = {"FIBRA", "REDES", "ANTENA", "PROPA", "CLOUD", "TELECO"};
    private Letter[] letterList = {
            new Letter(false,"A"),
            new Letter(false,"B"),
            new Letter(false,"C"),
            new Letter(false,"D"),
            new Letter(false,"E"),
            new Letter(false,"F"),
            new Letter(false,"G"),
            new Letter(false,"H"),
            new Letter(false,"I"),
            new Letter(false,"J"),
            new Letter(false,"K"),
            new Letter(false,"L"),
            new Letter(false,"M"),
            new Letter(false,"N"),
            new Letter(false,"O"),
            new Letter(false,"P"),
            new Letter(false,"Q"),
            new Letter(false,"R"),
            new Letter(false,"S"),
            new Letter(false,"T"),
            new Letter(false,"U"),
            new Letter(false,"V"),
            new Letter(false,"W"),
            new Letter(false,"X"),
            new Letter(false,"Y"),
            new Letter(false,"Z"),
    };
    private String currentWord;
    private char[] currentGuess;
    private int mistakes;
    private long startTime;
    private ImageView[] hangmanParts;
    private MaterialButton btnNewGame;
    private TextView[] wordViews;
    private RecyclerView rvLetters;
    private LetterAdapter adapter;
    private ConstraintLayout toolbar;
    TextView textViewMessage;
    ImageView btnStats;
    private boolean gameFinished = true;
    PlayerStats playerStats = new PlayerStats("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playerStats.addNamePlayer(getIntent().getExtras().getString("name"));
        toolbar = findViewById(R.id.toolbar);
        btnStats = toolbar.findViewById(R.id.btn_stats);

        // Encuentra el botón de retroceso
        ImageView btnBack = toolbar.findViewById(R.id.btn_back);

        // Configura el OnClickListener para el botón de retroceso
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Regresa a la actividad anterior
            }
        });
        hangmanParts = new ImageView[] {
                findViewById(R.id.img_head),
                findViewById(R.id.img_body),
                findViewById(R.id.img_righ_arm),
                findViewById(R.id.img_left_arm),
                findViewById(R.id.img_left_leg),
                findViewById(R.id.img_right_leg)
        };

        textViewMessage  = findViewById(R.id.text_message);
        btnNewGame = findViewById(R.id.btn_new_game);
        rvLetters = findViewById(R.id.rv_letters);

        startNewGame();

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(GameActivity.this, btnStats);
                popupMenu.getMenuInflater().inflate(R.menu.menu_stats, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.option_stats) {
                            openStats();
                        }
                        return true;
                    }

                    ;
                });
                popupMenu.show();
            }});
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });

        rvLetters.setLayoutManager(new GridLayoutManager(this, 7));
        adapter = new LetterAdapter(this::onLetterClicked);
        adapter.letterList = letterList;
        rvLetters.setAdapter(adapter);
    }

    private void openStats() {
        Intent intent = new Intent(GameActivity.this, StatsActivity.class);
        intent.putExtra("stats",playerStats);
        startActivity(intent);
    }

    private void startNewGame() {
        if( !gameFinished) {
            saveStats(0, ResultType.CANCELLED);
        }
        gameFinished = false;
        textViewMessage.setText("");
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.length);
        Log.e("this","palabra "+wordList[randomIndex] );
        currentWord = wordList[randomIndex];
        currentGuess = new char[currentWord.length()];
        Arrays.fill(currentGuess, ' ');
        mistakes = 0;
        startTime = SystemClock.elapsedRealtime();

        for (ImageView part : hangmanParts) {
            part.setVisibility(View.INVISIBLE);
        }

        resetWordDisplay();
        for(int i = 0; i < letterList.length ; i++) {
            letterList[i].setSelected(false);
        }
        rvLetters.post(new Runnable() {
            @Override
            public void run() {
               rvLetters .getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void resetWordDisplay() {
        RecyclerView rvWord = findViewById(R.id.rv_word);
        WordAdapter wordAdapter = new WordAdapter(currentGuess);
        rvWord.setAdapter(wordAdapter);
    }

    private void onLetterClicked(Letter letter, LetterAdapter.LetterViewHolder holder) {
        boolean isCorrect = false;

        if (currentWord.contains(letter.letter)) {
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == letter.letter.charAt(0)) {
                    currentGuess[i] = letter.letter.charAt(0);
                    isCorrect = true;
                }
            }
        }

        if (isCorrect) {
            int indexCorrect = findLetterIndex(letterList, letter.letter.charAt(0));
            letterList[indexCorrect].setSelected(true);
            adapter.notifyItemChanged(indexCorrect);
            checkWinCondition();
        } else {
            mistakes++;
            if (mistakes <= hangmanParts.length) {
                hangmanParts[mistakes - 1].setVisibility(View.VISIBLE);
            }
            checkLoseCondition();
        }

        rvLetters.getAdapter().notifyDataSetChanged();
        resetWordDisplay();
    }

    private void checkWinCondition() {
        if (new String(currentGuess).equals(currentWord)) {
            gameFinished = true;
            long elapsedTime = (SystemClock.elapsedRealtime() - startTime) / 1000;
            displayMessage("Ganaste! Tiempo: " + elapsedTime + " segundos");
            saveStats(elapsedTime, ResultType.WIN);
        }
    }

    private void saveStats(long elapsedTime, ResultType resultType) {
        playerStats.addGameResult(new GameResult(elapsedTime,resultType));
    }

    private void checkLoseCondition() {
        if (mistakes >= hangmanParts.length) {
            gameFinished = true;
            long elapsedTime = (SystemClock.elapsedRealtime() - startTime) / 1000;
            displayMessage("Perdiste! La palabra era: " + currentWord);
            saveStats(elapsedTime, ResultType.LOSE);
        }
    }

    private void displayMessage(String message) {
        textViewMessage.setText(message);
    }

    public static int findLetterIndex(Letter[] letterList, char letterToFind) {
        for (int i = 0; i < letterList.length; i++) {
            if (letterList[i].getLetter().charAt(0) == letterToFind) {
                return i;
            }
        }
        return -1;
    }
}