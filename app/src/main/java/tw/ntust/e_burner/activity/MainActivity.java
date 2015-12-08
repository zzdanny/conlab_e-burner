package tw.ntust.e_burner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tw.ntust.e_burner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---------- FOR DEBUG ----------
        startActivity(new Intent(MainActivity.this, SelectDevice.class));
        // -------------------------------
    }
}
