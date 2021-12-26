package com.example.nonograms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button startButton = findViewById(R.id.startButton);
        final TextView title = findViewById(R.id.textView);
        i = 0;
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                                       final int position, final long id) {
                i = position;
            }
            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // Called when the selection becomes empty
            }
        });
        TextView direction = findViewById(R.id.directions);
        direction.setText("Your aim in these puzzles is to colour the whole grid in to filled and non-filled squares.\nBeside each row of the grid are listed the lengths of the runs of filled squares on that row.\nAbove each column are listed the lengths of the runs of black squares in that column.\n" +
                "These numbers tell you the runs of filled squares in that row/column.\nSo, if you see '10 1', that tells you that there will be a run of exactly 10 black squares, followed by one or more white square, followed by a single black square.\nThere may be more white squares before/after this sequence.");
        //These directions can be found at this website: https://www.nonograms.org/instructions
        //“Learn to Solve Japanese Crosswords.” How to Solve Japanese Crosswords, https://www.nonograms.org/instructions.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                startGame();
            }
        });
    }
    private void startGame() {
        try {
            if (i == 0) {
                return;
            }
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("puzzle", i);
            startActivity(intent);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
