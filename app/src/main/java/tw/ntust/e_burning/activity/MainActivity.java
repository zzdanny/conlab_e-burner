package tw.ntust.e_burning.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tw.ntust.e_burning.R;

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
