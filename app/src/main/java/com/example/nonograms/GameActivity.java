package com.example.nonograms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private Canvas canvas;
    private Paint paint;
    private Paint white;
    private Paint gray;
    private Paint navy;
    private Bitmap bitmap;
    private TextView livesView;
    private Game game;
    private Button toggleButton;
    private int background;

    private final int m = 3;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        int id = getIntent().getIntExtra("puzzle", 1);
        Puzzle puzzle = new Puzzle(id);
        game = new Game(puzzle);
        ImageView imageView = findViewById(R.id.image);
        bitmap = Bitmap.createBitmap(m * (190), m * 190, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.RIGHT);
        navy = new Paint(Paint.ANTI_ALIAS_FLAG);
        navy.setColor(Color.rgb(0, 0, 52));
        white = new Paint(Paint.ANTI_ALIAS_FLAG);
        white.setColor(Color.WHITE);
        gray = new Paint(Paint.ANTI_ALIAS_FLAG);
        gray.setColor(Color.LTGRAY);
        imageView.setImageBitmap(bitmap);
        Button quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                finish();
            }
        });
        toggleButton = findViewById(R.id.toggleButton);
        livesView = findViewById(R.id.livesView);
        livesView.setTextSize(40);
        livesView.setText(game.getLives() + "," + game.getPuzzle().getCount() + "," + game.getCount());
        Button solveButton = findViewById(R.id.solveButton);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                solve();
            }
        });
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                game.toggleMode();
                if (game.getMode() == 1) {
                    toggleButton.setText("Fill");
                } else {
                    toggleButton.setText("No Fill");
                }
            }
        });
        if (game.getMode() == 1) {
            toggleButton.setText("Fill");
        } else {
            toggleButton.setText("No Fill");
        }
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double x = event.getX();
                double y = event.getY();
                click(x, y);
                return false;
            }
        });
        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                paint.setColor(Color.BLACK);
                navy.setColor(Color.rgb(0, 0, 52));
                white.setColor(Color.WHITE);
                gray.setColor(Color.LTGRAY);
                background = (Color.rgb(250, 250, 250));
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                background = Color.rgb(46, 46, 46);
                paint.setColor(Color.WHITE);
                navy.setColor(Color.LTGRAY);
                white.setColor(Color.rgb(46, 46, 46));
                gray.setColor(Color.rgb(65, 65, 65));
                break;
        }
        renderGame();
    }
    protected void onConfigurationChange() {
        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                paint.setColor(Color.BLACK);
                navy.setColor(Color.rgb(0, 0, 52));
                white.setColor(Color.WHITE);
                gray.setColor(Color.LTGRAY);
                background = (Color.rgb(250, 250, 250));
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                background = Color.rgb(46, 46, 46);
                paint.setColor(Color.WHITE);
                navy.setColor(Color.LTGRAY);
                white.setColor(Color.rgb(46, 46, 46));
                gray.setColor(Color.rgb(65, 65, 65));
                break;
        }
    }
    private void renderGame() {
        try {
            canvas.drawColor(background);
            int[][] board = game.getPuzzle().getBoard();
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[0].length; y++) {
                    Rect r = new Rect(m * (30 + x * 10), m * (30 + y * 10),
                            m * (40 + x * 10), m * (40 + y * 10));
                    if (board[x][y] == 1) {
                        canvas.drawRect(r, navy);
                    } else if (board[x][y] == -1) {
                        canvas.drawRect(r, gray);
                    } else {
                        canvas.drawRect(r, white);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        for (int x = 0; x < 16; x++) {
            canvas.drawLine(m * (30 + x * 10), m * (30), m * (30 + x * 10),
                    m * (180), paint);
        }
        for (int y = 0; y < 16; y++) {
            canvas.drawLine(m * (30), m * (30 + 10 * y), m * (180),
                    m * (30 + y * 10), paint);
        }
        for (int y = 0; y < 15; y++) {
            paint.setTextSize(18 - game.getPuzzle().getRowList(y).size());
            canvas.drawText(game.getPuzzle().getRowList(y).toString(), m * (28),m * (35 + 10 * y), paint);
        }
        for (int x = 0; x < 15; x++) {
            Path path = new Path();
            path.moveTo(100 + m * 10 * x,0);
            path.lineTo(100 + m * 10 * x, 28 * m);
            paint.setTextSize(18 - game.getPuzzle().getColList(x).size());
            canvas.drawTextOnPath(game.getPuzzle().getColList(x).toString(), path, 0, 0, paint);
        }
        if (!game.getStatus() && game.getLives() == 0) {
            livesView.setText("Game over!");
        } else if (game.testWin()) {
            livesView.setText("You win!");
        } else {
            livesView.setText(game.getLives() + "");
        }
        findViewById(R.id.image).invalidate();
    }
    private void click(double X, double Y) {
        double x = 190 * m * X / 1080;
        double y = 190 * m * Y / 1080;
        int cx = (int) ((x - 30 * m) / (10 * m));
        int cy = (int) ((y - 30 * m) / (10 * m));
        System.out.println("Converted: (" + x + ", " + y + ")");
        System.out.println("CellCoord: (" + cx + ", " + cy + ")");
        boolean yCheck = (0 <= cy && 14 >= cy);
        boolean xCheck = (0 <= cx && 14 >= cx);
        if (game.getStatus() && yCheck && xCheck && game.getPuzzle().getValue(cx, cy) == 0) {
            game.tryPlace(cx, cy);
        }
        renderGame();
    }
    private void solve() {
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 14; y++) {
                int mode = game.getPuzzle().getSolution()[x][y];
                if (game.getPuzzle().getBoard()[x][y] != 0) {
                    continue;
                }
                game.setMode(mode);
                if (game.getMode() == 1) {
                    toggleButton.setText("Fill");
                } else {
                    toggleButton.setText("No Fill");
                }
                game.tryPlace(x, y);
                renderGame();
            }
        }
    }
}
