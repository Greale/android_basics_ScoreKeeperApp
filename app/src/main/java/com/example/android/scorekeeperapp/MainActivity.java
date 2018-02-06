package com.example.android.scorekeeperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.scorekeeperapp.R;

public class MainActivity extends AppCompatActivity {
    boolean isGameFinished = false;
    int scoreGameA = 0;
    int scoreGameB = 0;
    boolean isFaultPersonA = false;
    boolean isFaultPersonB = false;
    int scoreSetA = 0;
    int scoreSetB = 0;
    int scoreSetCurrentA = 0;
    int scoreSetCurrentB = 0;
    int scoreSetOneA = 0;
    int scoreSetOneB = 0;
    int scoreSetTwoA = 0;
    int scoreSetTwoB = 0;
    int scoreSetThreeA = 0;
    int scoreSetThreeB = 0;
    boolean isSecondSet = false;
    boolean isThirdSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // on button click: if game is not finished: add score for personA, calculate, display
    public void addPointForPersonA(View v) {
        if (!isGameFinished) {
            scoreGameA += 1;
            isFaultPersonA = false;
            isFaultPersonB = false;
            if (scoreGameA == 4 && scoreGameB < 3) {
                wonGame(false);
            } else if (scoreGameA == 4 && scoreGameB == 4) {
                scoreGameA = 3;
                scoreGameB = 3;
            } else if (scoreGameA == 5) {
                wonGame(false);
            }
            displayScore();
        }
    }

    // 2 faults count as 1 point for the other person
    public void addFaultForPersonA(View v) {
        if (isFaultPersonA) {
            addPointForPersonB(v);
        } else {
            isFaultPersonA = true;
        }
    }

    // on button click: if game is not finished: add score for PersonB, calculate, display
    public void addPointForPersonB(View v) {
        if (!isGameFinished) {
            scoreGameB += 1;
            isFaultPersonA = false;
            isFaultPersonB = false;
            if (scoreGameB == 4 && scoreGameA < 3) {
                wonGame(true);
            } else if (scoreGameB == 4 && scoreGameA == 4) {
                scoreGameA = 3;
                scoreGameB = 3;
            } else if (scoreGameB == 5) {
                wonGame(true);
            }
            displayScore();
        }
    }

    // 2 faults count as 1 point for the other person
    public void addFaultForPersonB(View v) {
        if (isFaultPersonB) {
            addPointForPersonA(v);
        } else {
            isFaultPersonB = true;
        }
    }

    // someone won a Game, changing Set scores
    public void wonGame(boolean isBWinner) {
        scoreGameA = 0;
        scoreGameB = 0;
        if (!isBWinner) {
            scoreSetCurrentA += 1;
            if (scoreSetCurrentA == 6 && scoreSetCurrentB < 5) {
                wonSet(false);
            } else if (scoreSetCurrentA == 7) {
                wonSet(false);
            }
        } else {
            scoreSetCurrentB += 1;
            if (scoreSetCurrentB == 6 && scoreSetCurrentA < 5) {
                wonSet(true);
            } else if (scoreSetCurrentB == 7) {
                wonSet(true);
            }
        }
    }

    // someone won a Set, also checking if someone won
    public void wonSet(boolean isBWinner) {
        if (!isBWinner) {
            scoreSetA += 1;
        } else {
            scoreSetB += 1;
        }
        if (!isSecondSet && !isThirdSet) {
            scoreSetOneA = scoreSetCurrentA;
            scoreSetOneB = scoreSetCurrentB;
            scoreSetCurrentA = 0;
            scoreSetCurrentB = 0;
            isSecondSet = true;
            return;
        } else if (isSecondSet) {
            scoreSetTwoA = scoreSetCurrentA;
            scoreSetTwoB = scoreSetCurrentB;
            scoreSetCurrentA = 0;
            scoreSetCurrentB = 0;
            if (scoreSetA == 2 || scoreSetB == 2) {
                isGameFinished = true;
            }
            isSecondSet = false;
            isThirdSet = true;
            return;
        } else {
            scoreSetThreeA = scoreSetCurrentA;
            scoreSetThreeB = scoreSetCurrentB;
            scoreSetCurrentA = 0;
            scoreSetCurrentB = 0;
            isGameFinished = true;
            isThirdSet = false;
        }
    }

    // display scores
    public void displayScore() {
        if (isGameFinished) {
            displayWinner();
            return;
        }
        TextView scoreA = (TextView) findViewById(R.id.person_a_score);
        TextView scoreB = (TextView) findViewById(R.id.person_b_score);
        if (scoreGameA == 0) {
            scoreA.setText("0");
        } else if (scoreGameA == 1) {
            scoreA.setText("15");
        } else if (scoreGameA == 2) {
            scoreA.setText("30");
        } else if (scoreGameA == 3 && scoreGameB != 3) {
            scoreA.setText("40");
        } else if (scoreGameA == 3 && scoreGameB == 3) {
            scoreA.setText("Deuce");
            scoreB.setText("Deuce");
        } else if (scoreGameA == 4) {
            scoreA.setText("Adv.");
        }
        if (scoreGameB == 0) {
            scoreB.setText("0");
        } else if (scoreGameB == 1) {
            scoreB.setText("15");
        } else if (scoreGameB == 2) {
            scoreB.setText("30");
        } else if (scoreGameB == 3 && scoreGameA != 3) {
            scoreB.setText("40");
        } else if (scoreGameB == 4) {
            scoreB.setText("Adv.");
        }
        String scores = "";
        scores += scoreSetCurrentA + " - " + scoreSetCurrentB + "\n";
        scores += scoreSetA + " - " + scoreSetB + "\n";
        TextView scoreView = (TextView) findViewById(R.id.score_view);
        scoreView.setText(scores);
    }

    // display winner and scores
    public void displayWinner() {
        TextView scoreA = (TextView) findViewById(R.id.person_a_score);
        TextView scoreB = (TextView) findViewById(R.id.person_b_score);
        if (scoreSetA == 2) {
            scoreA.setText("WINNER!");
            scoreB.setText("LOSER!");
        } else {
            scoreA.setText("LOSER!");
            scoreB.setText("WINNER!");
        }
        String scores = "";
        scores += scoreSetCurrentA + " - " + scoreSetCurrentB + "\n";
        scores += scoreSetA + " - " + scoreSetB + "\n";
        scores += "Set1: " + scoreSetOneA + " - " + scoreSetOneB + "\n";
        scores += "Set2: " + scoreSetTwoA + " - " + scoreSetTwoB + "\n";
        if (!isThirdSet) {
            scores += "Set3: " + scoreSetThreeA + " - " + scoreSetThreeB + "\n";
        }
        TextView scoreView = (TextView) findViewById(R.id.score_view);
        scoreView.setText(scores);

    }

    // reset all values
    public void resetScore(View v) {
        isGameFinished = false;
        scoreGameA = 0;
        scoreGameB = 0;
        isFaultPersonA = false;
        isFaultPersonB = false;
        scoreSetA = 0;
        scoreSetB = 0;
        scoreSetCurrentA = 0;
        scoreSetCurrentB = 0;
        scoreSetOneA = 0;
        scoreSetOneB = 0;
        scoreSetTwoA = 0;
        scoreSetTwoB = 0;
        scoreSetThreeA = 0;
        scoreSetThreeB = 0;
        isSecondSet = false;
        isThirdSet = false;
        displayScore();
    }
}
