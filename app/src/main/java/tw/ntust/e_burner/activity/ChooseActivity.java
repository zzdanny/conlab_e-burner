package tw.ntust.e_burner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tw.ntust.e_burner.R;

public class ChooseActivity extends Activity {

    private ImageView imgEmperor, imgAncestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_target);
        initComponents_chooseTarget();
    }

    private void initComponents_chooseTarget() {
        imgEmperor = (ImageView) findViewById(R.id.chooseAmount_imgEmperor);
        imgAncestor = (ImageView) findViewById(R.id.chooseAmount_imgAncestor);

        View.OnClickListener onChooseTargetClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        };
        imgEmperor.setOnClickListener(onChooseTargetClickListener);
        imgAncestor.setOnClickListener(onChooseTargetClickListener);
    }

}
