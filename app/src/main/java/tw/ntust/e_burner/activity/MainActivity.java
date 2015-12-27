package tw.ntust.e_burner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tw.ntust.e_burner.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        // ---------- FOR DEBUG ----------
        // startActivity(new Intent(MainActivity.this, SelectDevice.class));
        // -------------------------------
    }

    private void initComponents() {
        btnLogin = (Button) findViewById(R.id.main_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChooseActivity.class));
                finish();
            }
        });
    }

}
