package tw.ntust.e_burner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import tw.ntust.e_burner.R;
import tw.ntust.e_burner.components.AmountItem;
import tw.ntust.e_burner.components.AmountListAdapter;

public class ChooseActivity extends Activity {

    private int remainingAncestor, remainingEmperor;
    private ArrayList<AmountItem> amountItems;

    public final int REQUEST_IXBURNUNG = 10;

    // -- layout.activity_choose
    private ProgressBar progressEmperor, progressAncestor;
    private TextView txtEmperorProgress, txtAncestorPorgress;
    // -- layout.choose_target
    private ImageView imgEmperor, imgAncestor,imgGhost;
    private LinearLayout selector;

    // -- layout.choose_amount
    private Button btnStart, btnCancel;
    private Button Choosecancel;
    private ListView listAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_target);


        findViewById(R.id.selector_simple).setVisibility(View.INVISIBLE);

        initComponents_chooseTarget();

        // animate progressBar from 0 to current value when activity start
        animateProgressBar(progressAncestor, remainingAncestor);
        animateProgressBar(progressEmperor, remainingEmperor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Request code=" + requestCode);
        if (requestCode == REQUEST_IXBURNUNG) {
            setContentView(R.layout.choose_target);
            initComponents_chooseTarget();
        }
    }

    private void initComponents_choose() {
      /*  progressAncestor = (ProgressBar) findViewById(R.id.choose_progressAncestor);
        progressEmperor = (ProgressBar) findViewById(R.id.choose_progressEmperor);
        txtAncestorPorgress = (TextView) findViewById(R.id.choose_txtAncestorProgress);
        txtEmperorProgress = (TextView) findViewById(R.id.choose_txtEmperorProgress);

        progressAncestor.setProgress(remainingAncestor);
        progressEmperor.setProgress(remainingEmperor);
        txtAncestorPorgress.setText(String.format("%3d", remainingAncestor));
        txtEmperorProgress.setText(String.format("%3d", remainingEmperor));*/
    }

    private void initComponents_chooseTarget() {
        initComponents_choose();

        imgEmperor = (ImageView) findViewById(R.id.chooseTarget_imgEmperor);
        imgAncestor = (ImageView) findViewById(R.id.chooseTarget_imgAncestor);
        selector = (LinearLayout)findViewById(R.id.selector_simple);
        imgGhost = (ImageView) findViewById(R.id.chooseTarget_imgGhost);
        View.OnClickListener onChooseEmperorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector.setVisibility(View.VISIBLE);
            }
        };

        View.OnClickListener onChooseAncestorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data from Shared Preferences
                // ----- default value for testing
                amountItems = new ArrayList<>();
                amountItems.add(new AmountItem(0, "拜地基主"));
                amountItems.add(new AmountItem(1, "祈求平安"));
                amountItems.add(new AmountItem(2, "求財開運"));
                // -----
                selector.setVisibility(View.VISIBLE);

                initComponents_chooseAmount();

            }
        };

        View.OnClickListener onChooseGhostClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data from Shared Preferences
                // ----- default value for testing
                amountItems = new ArrayList<>();
                amountItems.add(new AmountItem(0, "中元普渡"));
                amountItems.add(new AmountItem(1, "其他"));
                // -----

                selector.setVisibility(View.VISIBLE);
            }
        };

        View.OnClickListener onChooseGodClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data from Shared Preferences
                // ----- default value for testing
                amountItems = new ArrayList<>();
                amountItems.add(new AmountItem(0, "冥誕"));
                amountItems.add(new AmountItem(1, "祭日"));
                amountItems.add(new AmountItem(3, "祈求平安"));
                // -----

                initComponents_chooseAmount();
            }
        };
        imgEmperor.setOnClickListener(onChooseAncestorClickListener);
        imgAncestor.setOnClickListener(onChooseEmperorClickListener);
    }

    private void initComponents_chooseAmount() {
        initComponents_choose();

        listAmount = (ListView) findViewById(R.id.chooseAmount_listAmount);
        btnStart = (Button) findViewById(R.id.chooseAmount_btnStart);
        Choosecancel=(Button) findViewById(R.id.choose_btnCancel);

        listAmount.setAdapter(new AmountListAdapter(ChooseActivity.this, amountItems));
        listAmount.setItemChecked(0, true);
        Choosecancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                selector.setVisibility(View.INVISIBLE);
                Log.e("hi", "hihihihi");
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ChooseActivity.this, IxBurning.class), REQUEST_IXBURNUNG);
            }
        });
    }

    private void animateProgressBar(final ProgressBar progressBar, final int value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= value; i++) {
                    final int num = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            progressBar.setProgress(num);
                        }
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
