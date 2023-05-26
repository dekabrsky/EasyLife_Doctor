package ru.dekabrsky.italks.minigamestwolast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.util.Random;

public class FootballFragment extends Fragment {

    ImageView button1,button2,button3,button4,button5,button6,button7,button8,button9;
    TextView textView, humanPoints, pcPoints;
    int pointsOfHuman, pointsOfAI;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LottieAnimationView lottie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_football, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1 = getView().findViewById(R.id.button1);
        button2 = getView().findViewById(R.id.button2);
        button3 = getView().findViewById(R.id.button3);
        button4 = getView().findViewById(R.id.button4);
        button5 = getView().findViewById(R.id.button5);
        button6 = getView().findViewById(R.id.button6);
        button7 = getView().findViewById(R.id.button7);
        button8 = getView().findViewById(R.id.button8);
        button9 = getView().findViewById(R.id.button9);
        textView = getView().findViewById(R.id.textView);
        lottie = getView().findViewById(R.id.anim);

        humanPoints = getView().findViewById(R.id.humanPoints);
        pcPoints = getView().findViewById(R.id.pcPoints);

        sharedPreferences = this.getActivity().getSharedPreferences("krestikNolik", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();

        pointsOfHuman = sharedPreferences.getInt("pointsOfHuman", 0);
        humanPoints.setText(""+pointsOfHuman);

        pointsOfAI = sharedPreferences.getInt("pointsOfAI", 0);
        pcPoints.setText(""+ pointsOfAI);

        textView.setText("");

        @SuppressLint("DiscouragedApi") int idView1 = getResources().getIdentifier("button1", "id", getContext().getPackageName());
        View eventView1 = view.findViewById(idView1);
        eventView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button1.getDrawable() == null && textView.getText() == "") {
                    button1.setImageResource(R.drawable.basket);
                    button1.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView2 = getResources().getIdentifier("button2", "id", getContext().getPackageName());
        View eventView2 = view.findViewById(idView2);
        eventView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button2.getDrawable() == null && textView.getText() == "") {
                    button2.setImageResource(R.drawable.basket);
                    button2.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView3 = getResources().getIdentifier("button3", "id", getContext().getPackageName());
        View eventView3 = view.findViewById(idView3);
        eventView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button3.getDrawable() == null && textView.getText() == "") {
                    button3.setImageResource(R.drawable.basket);
                    button3.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView4 = getResources().getIdentifier("button4", "id", getContext().getPackageName());
        View eventView4 = view.findViewById(idView4);
        eventView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button4.getDrawable() == null && textView.getText() == "") {
                    button4.setImageResource(R.drawable.basket);
                    button4.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView5 = getResources().getIdentifier("button5", "id", getContext().getPackageName());
        View eventView5 = view.findViewById(idView5);
        eventView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button5.getDrawable() == null && textView.getText() == "") {
                    button5.setImageResource(R.drawable.basket);
                    button5.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView6 = getResources().getIdentifier("button6", "id", getContext().getPackageName());
        View eventView6 = view.findViewById(idView6);
        eventView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button6.getDrawable() == null && textView.getText() == "") {
                    button6.setImageResource(R.drawable.basket);
                    button6.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView7 = getResources().getIdentifier("button7", "id", getContext().getPackageName());
        View eventView7 = view.findViewById(idView7);
        eventView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button7.getDrawable() == null && textView.getText() == "") {
                    button7.setImageResource(R.drawable.basket);
                    button7.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView8 = getResources().getIdentifier("button8", "id", getContext().getPackageName());
        View eventView8 = view.findViewById(idView8);
        eventView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button8.getDrawable() == null && textView.getText() == "") {
                    button8.setImageResource(R.drawable.basket);
                    button8.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView9 = getResources().getIdentifier("button9", "id", getContext().getPackageName());
        View eventView9 = view.findViewById(idView9);
        eventView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button9.getDrawable() == null && textView.getText() == "") {
                    button9.setImageResource(R.drawable.basket);
                    button9.setTag(R.drawable.basket);
                    isPlayerWinner();
                    if (textView.getText() == "") {
                        hodAI();
                    }
                }
            }
        });

        @SuppressLint("DiscouragedApi") int idView10 = getResources().getIdentifier("restart", "id", getContext().getPackageName());
        View eventView10 = view.findViewById(idView10);
        eventView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                lottie.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction().remove(FootballFragment.this).commit();
            }
        });
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void isPlayerWinner () {
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.basket && Integer.parseInt(button2.getTag().toString()) == R.drawable.basket && Integer.parseInt(button3.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        } else
        if ( Integer.parseInt(button4.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button6.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button7.getTag().toString()) == R.drawable.basket && Integer.parseInt(button8.getTag().toString()) == R.drawable.basket && Integer.parseInt(button9.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.basket && Integer.parseInt(button4.getTag().toString()) == R.drawable.basket && Integer.parseInt(button7.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button2.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button8.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button3.getTag().toString()) == R.drawable.basket && Integer.parseInt(button6.getTag().toString()) == R.drawable.basket && Integer.parseInt(button9.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button1.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button9.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
            pointsOfHuman++;
            humanPoints.setText(""+pointsOfHuman);
            editor.putInt("pointsOfHuman", pointsOfHuman);
            editor.apply();
        }else
        if ( Integer.parseInt(button3.getTag().toString()) == R.drawable.basket && Integer.parseInt(button5.getTag().toString()) == R.drawable.basket && Integer.parseInt(button7.getTag().toString()) == R.drawable.basket) {
            textView.setText(R.string.winner_message);
            lottie.playAnimation();
            lottie.setRepeatCount(LottieDrawable.REVERSE);
            lottie.setVisibility(View.VISIBLE);
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

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
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
}