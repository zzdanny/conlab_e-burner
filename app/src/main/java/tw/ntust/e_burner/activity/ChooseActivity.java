package tw.ntust.e_burner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import tw.ntust.e_burner.R;

public class ChooseActivity extends Activity {

    // -- layout.activity_choose
    private ProgressBar progressEmperor, progressAncestor;
    // -- layout.choose_target
    private ImageView imgEmperor, imgAncestor;
    // -- layout.choose_amount
    private Button btnStart, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_target);
        initComponents_chooseTarget();
        
        animateProgressBar(progressAncestor, 50);
        animateProgressBar(progressEmperor, 50);
    }

    private void initComponents_choose() {
        progressAncestor = (ProgressBar) findViewById(R.id.choose_progressAncestor);
        progressEmperor = (ProgressBar) findViewById(R.id.choose_progressEmperor);
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

        btnStart = (Button) findViewById(R.id.chooseAmount_btnStart);
        btnCancel = (Button) findViewById(R.id.chooseAmount_btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.choose_target);
                initComponents_chooseTarget();
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

}
