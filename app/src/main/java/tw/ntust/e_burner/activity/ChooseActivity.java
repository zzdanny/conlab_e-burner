package tw.ntust.e_burner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import tw.ntust.e_burner.R;

public class ChooseActivity extends Activity {

    // -- layout.choose_target
    private ImageView imgEmperor, imgAncestor;
    // -- layout.choose_amount
    private Button btnStart, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_target);
        initComponents_chooseTarget();
    }

    private void initComponents_chooseTarget() {
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

}
