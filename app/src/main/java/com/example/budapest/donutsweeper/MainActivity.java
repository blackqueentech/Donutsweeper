package com.example.budapest.donutsweeper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GameView gameView = (GameView) findViewById(R.id.gameView);
        final Button btnClear = (Button) findViewById(R.id.btnClear);
        final Button btnMode = (Button) findViewById(R.id.btnMode);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DonutSweeperBoard.getInstance().isFlagging) {
                    DonutSweeperBoard.getInstance().isFlagging = true;
                    ((Button)v).setText(getString(R.string.flagModeString));
                } else {
                    DonutSweeperBoard.getInstance().isFlagging = false;
                    ((Button)v).setText(getString(R.string.revealModeString));
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.resetGame();
            }
        });
    }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}