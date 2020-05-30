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
private static final String PAIR_LABEl_ID_FORMAT = "pair_%d_label";
  private static final String PAIR_COUNT_ID_FORMAT = "pair_%d_count";

  private int minPairValue = 2;
  private int maxPairValue;
  private TextView[] pairLabels;
  private ProgressBar[] pairCount;
  private Button roller;
  private TextView rollerDisplay;
  private Random rng;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    maxPairValue = 2 * Roll.NUM_FACES;
    pairLabels = new TextView[maxPairValue - minPairValue + 1];
    pairCount = new ProgressBar[maxPairValue - minPairValue + 1];
    Resources res = getResources();
    rng = new Random();
    NumberFormat formatter = NumberFormat.getNumberInstance();
    for (int i = minPairValue; i <= maxPairValue; i++) {
      String labelISting = String.format(PAIR_LABEl_ID_FORMAT, i);
      int labelId = res.getIdentifier(labelISting, "id", getPackageName());
      pairLabels[i- minPairValue] = findViewById(labelId);
      pairLabels[i - minPairValue].setText(formatter.format(i));
      String countIdString = String.format(PAIR_COUNT_ID_FORMAT, i);
      int countId = res.getIdentifier(countIdString, "id" , getPackageName());
      pairCount[i - minPairValue] = findViewById(countId);
      pairCount[i - minPairValue].setProgress(1 + rng.nextInt(10));
    }
    roller = findViewById(R.id.roller);
    rollerDisplay = findViewById(R.id.roll_display);
    roller.setOnClickListener(new RollerListener());
  }

  private class RollerListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      Roll roll = new Roll(rng);
     rollerDisplay .setText(Arrays.toString(roll.getDice()));
    }
  }

}
