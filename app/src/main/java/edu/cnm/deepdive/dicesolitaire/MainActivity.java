package edu.cnm.deepdive.dicesolitaire;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import edu.cnm.deepdive.dicesolitaire.modlel.Roll;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  private static final String PAIR_LABEL_ID_FORMAT = "pair_%d_label";
  private static final String PAIR_COUNT_ID_FORMAT = "pair_%d_count";
  private static final String SCRATCH_LABEL_ID_FORMAT = "scratch_%d_label";
  private static final String SCRATCH_COUNT_ID_FORMAT = "scratch_%d_count";
  private static final String DIE_IMAGE_ID_FORMAT = "die_%d";
  private static final String DICE_FACE_ID_FORMAT = "face_%d";
  private int minPairValue = 2;
  private int maxPairValue = 2 * Roll.NUM_FACES;
  private TextView[] pairLabels;
  private ProgressBar[] pairCount;
  private TextView[] scratchLabels;
  private ProgressBar[] scratchCounts;
  private ImageView[] diceImages;
  private Drawable[] diceFaces;
  private Button roller;
  private Random rng = new Random();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI();
  }

  private void setupUI() {
    setContentView(R.layout.activity_main);
    Resources res = getResources();
    NumberFormat formatter = NumberFormat.getNumberInstance();
    setupPairControls(res, formatter);
    setupPlayControls(res);
    setupScratchControls(res, formatter);
  }

  private void setupScratchControls(Resources res, NumberFormat formatter) {
    scratchLabels = new TextView[Roll.NUM_FACES];
    scratchCounts = new ProgressBar[Roll.NUM_FACES];
    for (int i = 1; i <= Roll.NUM_FACES; i++) {
      String scratchLabelIdString = String.format(SCRATCH_LABEL_ID_FORMAT, i);
      int scratchLabelId = res.getIdentifier(scratchLabelIdString, "id", getPackageName());
      scratchLabels[i - 1] = findViewById(scratchLabelId);
      scratchLabels[i - 1].setText(formatter.format(i));
      String scratchCountIdString = String.format(SCRATCH_COUNT_ID_FORMAT, i);
      int scratchCountId = res.getIdentifier(scratchCountIdString, "id", getPackageName());
      scratchCounts[i - 1] = findViewById(scratchCountId);
      scratchCounts[i - 1].setProgress(1 + rng.nextInt(7));
    }
  }

  private void setupPlayControls(Resources res) {
    roller = findViewById(R.id.roller);
    diceImages = new ImageView[Roll.NUM_DICE];
    for (int i = 0; i < Roll.NUM_DICE; i++) {
      String idString = String.format(DIE_IMAGE_ID_FORMAT, i + 1);
      int id = res.getIdentifier(idString, "id", getPackageName());
      diceImages[i] = findViewById(id);
      diceImages[i].setImageDrawable(getDrawable(R.drawable.face_6));
    }
    diceFaces = new Drawable[Roll.NUM_FACES];
    for (int i = 0; i < Roll.NUM_FACES; i++) {
      String idString = String.format(DICE_FACE_ID_FORMAT, i + 1);
      int id = res.getIdentifier(idString, "drawable", getPackageName());
      diceFaces[i] = getDrawable(id);
    }
    roller.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        roller.setEnabled(false);
        new DiceAnimator().start();
      }
    });
  }

  private void displayDiceFace(int die, int face) {
    diceImages[die].setImageDrawable(diceFaces[face]);
  }

  private void setupPairControls(Resources res, NumberFormat formatter) {
    pairLabels = new TextView[maxPairValue - minPairValue + 1];
    pairCount = new ProgressBar[maxPairValue - minPairValue + 1];
    for (int i = minPairValue; i <= maxPairValue; i++) {
      String labelISting = String.format(PAIR_LABEL_ID_FORMAT, i);
      int labelId = res.getIdentifier(labelISting, "id", getPackageName());
      pairLabels[i - minPairValue] = findViewById(labelId);
      pairLabels[i - minPairValue].setText(formatter.format(i));
      String countIdString = String.format(PAIR_COUNT_ID_FORMAT, i);
      int countId = res.getIdentifier(countIdString, "id", getPackageName());
      pairCount[i - minPairValue] = findViewById(countId);
      pairCount[i - minPairValue].setProgress(1 + rng.nextInt(10));

    }
  }

  private class DiceAnimator extends Thread {

    @Override
    public void run() {
      Roll roll = new Roll(rng);
      for (int i = 0; i < Roll.NUM_DICE; i++) {
        final int dieIndex = i;
        for (int j = 0; j < 10; j++) {
          int animationFace = rng.nextInt(Roll.NUM_FACES);
          displayFace(dieIndex, animationFace + 1);
          try {
            sleep(50);
          } catch (InterruptedException e) {
            // Ignore exception and get on with life
          }
          final int value = roll.getDice()[i];
          displayFace(dieIndex, value);

        }
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            roller.setEnabled(true);
          }
        });


      }

    }

    private void displayFace(final int dieIndex, final int value) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          displayDiceFace(dieIndex, value - 1);
        }
      });
    }

  }

}
