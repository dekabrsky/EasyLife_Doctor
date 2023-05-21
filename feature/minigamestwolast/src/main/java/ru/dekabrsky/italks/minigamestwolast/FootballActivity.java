package ru.dekabrsky.italks.minigamestwolast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class FootballActivity extends AppCompatActivity {
    ImageView button1,button2,button3,button4,button5,button6,button7,button8,button9;
    TextView textView, humanPoints, pcPoints;
    int pointsOfHuman, pointsOfAI;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        textView = findViewById(R.id.textView);

        humanPoints = findViewById(R.id.humanPoints);
        pcPoints = findViewById(R.id.pcPoints);

        sharedPreferences = this.getSharedPreferences("krestikNolik", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();

        pointsOfHuman = sharedPreferences.getInt("pointsOfHuman", 0);
        humanPoints.setText(""+pointsOfHuman);

        pointsOfAI = sharedPreferences.getInt("pointsOfAI", 0);
        pcPoints.setText(""+ pointsOfAI);

        textView.setText("");
    }
    public void clcBtn1(View v) {
        if (button1.getDrawable() == null && textView.getText() == "") {
            button1.setImageResource(R.drawable.basket);
            button1.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn2(View view) {
        if (button2.getDrawable() == null && textView.getText() == "") {
            button2.setImageResource(R.drawable.basket);
            button2.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn3(View view) {
        if (button3.getDrawable() == null && textView.getText() == "") {
            button3.setImageResource(R.drawable.basket);
            button3.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn4(View view) {
        if (button4.getDrawable() == null && textView.getText() == "") {
            button4.setImageResource(R.drawable.basket);
            button4.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn5(View view) {
        if (button5.getDrawable() == null && textView.getText() == "") {
            button5.setImageResource(R.drawable.basket);
            button5.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn6(View view) {
        if (button6.getDrawable() == null && textView.getText() == "") {
            button6.setImageResource(R.drawable.basket);
            button6.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn7(View view) {
        if (button7.getDrawable() == null && textView.getText() == "") {
            button7.setImageResource(R.drawable.basket);
            button7.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn8(View view) {
        if (button8.getDrawable() == null && textView.getText() == "") {
            button8.setImageResource(R.drawable.basket);
            button8.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }
    public void clcBtn9(View view) {
        if (button9.getDrawable() == null && textView.getText() == "") {
            button9.setImageResource(R.drawable.basket);
            button9.setTag(R.drawable.basket);
            isPlayerWinner();
            if (textView.getText() == "") {
                hodAI();
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void isPlayerWinner () {
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.basket && Integer.parseInt(button2.getTag().toString()) == R.drawable.basket && Integer.parseInt(button3.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        } else
        if ( Integer.parseInt(button4.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button6.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button7.getTag().toString()) == R.drawable.basket && Integer.parseInt(button8.getTag().toString()) == R.drawable.basket && Integer.parseInt(button9.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.basket && Integer.parseInt(button4.getTag().toString()) == R.drawable.basket && Integer.parseInt(button7.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button2.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button8.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button3.getTag().toString()) == R.drawable.basket && Integer.parseInt(button6.getTag().toString()) == R.drawable.basket && Integer.parseInt(button9.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button9.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button3.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button7.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( button1.getDrawable() != null && button2.getDrawable() != null && button3.getDrawable() != null &&
                button4.getDrawable() != null && button5.getDrawable() != null && button6.getDrawable() != null &&
                button7.getDrawable() != null && button8.getDrawable() != null && button9.getDrawable() != null) {
            textView.setText(R.string.draw_message);
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void isPСWinner () {
        Log.i("hodAI","pointsOfAI - " + pointsOfAI);
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.football && Integer.parseInt(button2.getTag().toString()) == R.drawable.football && Integer.parseInt(button3.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button4.getTag().toString()) == R.drawable.football && Integer.parseInt(button5.getTag().toString()) == R.drawable.football && Integer.parseInt(button6.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button7.getTag().toString()) == R.drawable.football && Integer.parseInt(button8.getTag().toString()) == R.drawable.football && Integer.parseInt(button9.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.football && Integer.parseInt(button4.getTag().toString()) == R.drawable.football && Integer.parseInt(button7.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button2.getTag().toString()) == R.drawable.football && Integer.parseInt(button5.getTag().toString()) == R.drawable.football && Integer.parseInt(button8.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button3.getTag().toString()) == R.drawable.football && Integer.parseInt(button6.getTag().toString()) == R.drawable.football && Integer.parseInt(button9.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.football && Integer.parseInt(button5.getTag().toString()) == R.drawable.football && Integer.parseInt(button9.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }else
        if ( Integer.parseInt(button3.getTag().toString()) == R.drawable.football && Integer.parseInt(button5.getTag().toString()) == R.drawable.football && Integer.parseInt(button7.getTag().toString()) == R.drawable.football) {
            textView.setText(R.string.pc_winner_message);
            pointsOfAI++;
            pcPoints.setText(""+ pointsOfAI);
            editor.putInt("pointsOfAI", pointsOfAI);
            editor.apply();
        }

    }

    private void hodAI() {
        Random random = new Random();
        int buttonPcClick = 1 + random.nextInt(9);
        Log.i("hodAI","buttonPcClick - " + buttonPcClick);
        switch(buttonPcClick) {
            case (1):  if (button1.getDrawable() == null){
                button1.setImageResource(R.drawable.football);
                button1.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (2):  if (button2.getDrawable() == null){
                button2.setImageResource(R.drawable.football);
                button2.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (3):  if (button3.getDrawable() == null){
                button3.setImageResource(R.drawable.football);
                button3.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (4):  if (button4.getDrawable() == null){
                button4.setImageResource(R.drawable.football);
                button4.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (5):  if (button5.getDrawable() == null){
                button5.setImageResource(R.drawable.football);
                button5.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (6):  if (button6.getDrawable() == null){
                button6.setImageResource(R.drawable.football);
                button6.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (7): if (button7.getDrawable() == null){
                button7.setImageResource(R.drawable.football);
                button7.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (8): if (button8.getDrawable() == null){
                button8.setImageResource(R.drawable.football);
                button8.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
            case (9): if (button9.getDrawable() == null){
                button9.setImageResource(R.drawable.football);
                button9.setTag(R.drawable.football);
                isPСWinner();
            } else{
                hodAI();}
                break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void clickRestart(View view) {
        button1.setImageDrawable(null);
        button2.setImageDrawable(null);
        button3.setImageDrawable(null);
        button4.setImageDrawable(null);
        button5.setImageDrawable(null);
        button6.setImageDrawable(null);
        button7.setImageDrawable(null);
        button8.setImageDrawable(null);
        button9.setImageDrawable(null);
        textView.setText("");
        startActivity(new Intent(FootballActivity.this, FootballActivity.class));
        overridePendingTransition(R.anim.one_intent, R.anim.two_intent);
        finish();
    }

    public void onBackMenu(View view) {
        onBackPressed();
    }
}