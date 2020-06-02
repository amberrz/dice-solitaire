package edu.cnm.deepdive.dicesolitaire;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

  private TextView[] scratchLabels;
  private ProgressBar[] scratchCounts;
  private int minPairValue = 2;
  private int maxPairValue = 2 * Roll.NUM_FACES;
  private TextView[] pairLabels;
  private ProgressBar[] pairCount;
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
    setupPlayControls();
    setupScratchControls(res, formatter);
  }

  private void setupScratchControls(Resources res, NumberFormat formatter) {
    scratchLabels = new TextView[Roll.NUM_FACES];
    scratchCounts = new ProgressBar[Roll.NUM_FACES];
    for (int i = 1; i <= Roll.NUM_FACES; i++) {
      String scratchLabelIdString = String.format(SCRATCH_LABEL_ID_FORMAT, i);
      int scratchLabelId = res.getIdentifier(scratchLabelIdString, "id", getPackageName());
      scratchLabels[i - 1] = findViewById(scratchLabelId);
      scratchLabels[i - 1] .setText(formatter.format(i));
      String scratchCountIdString = String.format(SCRATCH_COUNT_ID_FORMAT, i);
      int scratchCountId = res.getIdentifier(scratchCountIdString, "id", getPackageName());
     scratchCounts[i - 1] = findViewById(scratchCountId);
      scratchCounts[i - 1].setProgress(1 + rng.nextInt(7));
    }
  }

  private void setupPlayControls() {
    class RollerListener implements OnClickListener {
      @Override
      public void onClick(View v) {
        Roll roll = new Roll(rng);
        // TODO Display dice images.
      }
    }
    // TODO Find and write up dice ImageView object.
    roller.setOnClickListener(new RollerListener());
  }

  private void setupPairControls(Resources res, NumberFormat formatter) {
    pairLabels = new TextView[maxPairValue - minPairValue + 1];
    pairCount = new ProgressBar[maxPairValue - minPairValue + 1];
    for (int i = minPairValue; i <= maxPairValue; i++) {
      String labelISting = String.format(PAIR_LABEL_ID_FORMAT, i);
      int labelId = res.getIdentifier(labelISting, "id", getPackageName());
      pairLabels[i- minPairValue] = findViewById(labelId);
      pairLabels[i - minPairValue].setText(formatter.format(i));
      String countIdString = String.format(PAIR_COUNT_ID_FORMAT, i);
      int countId = res.getIdentifier(countIdString, "id" , getPackageName());
      pairCount[i - minPairValue] = findViewById(countId);
      pairCount[i - minPairValue].setProgress(1 + rng.nextInt(10));

    }
  }


}
