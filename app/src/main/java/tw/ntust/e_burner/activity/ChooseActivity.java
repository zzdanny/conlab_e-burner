package tw.ntust.e_burner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.ntust.e_burner.R;
import tw.ntust.e_burner.components.AmountItem;
import tw.ntust.e_burner.components.AmountListAdapter;

public class ChooseActivity extends BaseActivity {

    private int remainingAncestor, remainingEmperor;
    private ArrayList<AmountItem> amountItems;

    public final int REQUEST_IXBURNUNG = 10;

    // -- layout.remain_display
    private ProgressBar progressEmperor, progressAncestor;
    private TextView txtEmperorProgress, txtAncestorPorgress;
    private View incNav_bottom, viewNavpad;
    private ImageView imgMenuNav, imgMenuChart, imgMenuMoney, imgMenuSettings;
    private List<View> menuExpandedViews;
    private boolean navMenuExpanded = false;

    // -- layout.choose_target
    private ImageView imgEmperor, imgAncestor;
    // -- layout.choose_amount
    private Button btnStart, btnCancel;
    private ListView listAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_target);

        // get data from Shared Preferences
        // ----- default value for testing
        remainingAncestor = 30;
        remainingEmperor = 40;
        amountItems = new ArrayList<>();
        amountItems.add(new AmountItem(0, "大節-5疊"));
        amountItems.add(new AmountItem(1, "大節-5疊"));
        amountItems.add(new AmountItem(2, "小節-3疊"));
        amountItems.add(new AmountItem(3, "小節-3疊"));
        // -----

        initComponents_chooseTarget();

        // animate progressBar from 0 to current value when activity start
        animateProgressBar(progressAncestor, remainingAncestor);
        animateProgressBar(progressEmperor, remainingEmperor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IXBURNUNG) {
            setContentView(R.layout.choose_target);
            initComponents_chooseTarget();
        }
    }

    private void initComponents_choose() {
        progressAncestor = (ProgressBar) findViewById(R.id.choose_progressAncestor);
        progressEmperor = (ProgressBar) findViewById(R.id.choose_progressEmperor);
        txtAncestorPorgress = (TextView) findViewById(R.id.choose_txtAncestorProgress);
        txtEmperorProgress = (TextView) findViewById(R.id.choose_txtEmperorProgress);
        incNav_bottom = findViewById(R.id.incNav_bottom);
        imgMenuNav = (ImageView) incNav_bottom.findViewById(R.id.imgMenuNav);
        imgMenuChart = (ImageView) incNav_bottom.findViewById(R.id.imgMenuChart);
        imgMenuMoney = (ImageView) incNav_bottom.findViewById(R.id.imgMenuMoney);
        imgMenuSettings = (ImageView) incNav_bottom.findViewById(R.id.imgMenuSettings);
        viewNavpad = incNav_bottom.findViewById(R.id.viewNavpad);

        progressAncestor.setProgress(remainingAncestor);
        progressEmperor.setProgress(remainingEmperor);
        txtAncestorPorgress.setText(String.format("%3d", remainingAncestor));
        txtEmperorProgress.setText(String.format("%3d", remainingEmperor));

        menuExpandedViews = new ArrayList<>(Arrays.asList(
                new View[]{viewNavpad, imgMenuChart, imgMenuMoney, imgMenuSettings}
        ));
        imgMenuNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (navMenuExpanded)
                    closeNavMenu();
                else
                    expandNavMenu();
            }
        });
        closeNavMenu();
    }

    private void initComponents_chooseTarget() {
        initComponents_choose();

        imgEmperor = (ImageView) findViewById(R.id.chooseTarget_imgEmperor);
        imgAncestor = (ImageView) findViewById(R.id.chooseTarget_imgAncestor);

        View.OnClickListener onChooseTargetClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.choose_amount);
                initComponents_chooseAmount();
            }
        };
        imgEmperor.setOnClickListener(onChooseTargetClickListener);
        imgAncestor.setOnClickListener(onChooseTargetClickListener);
    }

    private void initComponents_chooseAmount() {
        initComponents_choose();

        listAmount = (ListView) findViewById(R.id.chooseAmount_listAmount);
        btnStart = (Button) findViewById(R.id.chooseAmount_btnStart);
        btnCancel = (Button) findViewById(R.id.chooseAmount_btnCancel);

        listAmount.setAdapter(new AmountListAdapter(ChooseActivity.this, amountItems));
        listAmount.setItemChecked(0, true);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.choose_target);
                initComponents_chooseTarget();
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
                            progressBar.setProgress(num);
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

    private void closeNavMenu() {
        for (View v : menuExpandedViews) {
            v.setVisibility(View.INVISIBLE);
        }
        navMenuExpanded = false;
    }

    private void expandNavMenu() {
        for (View v : menuExpandedViews) {
            v.setVisibility(View.VISIBLE);
        }
        navMenuExpanded = true;
    }

}
