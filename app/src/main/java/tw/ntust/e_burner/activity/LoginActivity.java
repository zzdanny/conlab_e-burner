package tw.ntust.e_burner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tw.ntust.e_burner.R;
import tw.ntust.e_burner.app.Constants;

public class LoginActivity extends BaseActivity {

    private Button btnLogin;
    private Button btnSelectDevice;
    private String btAddr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents() {
        btnLogin = (Button) findViewById(R.id.main_btnLogin);
        btnSelectDevice = (Button) findViewById(R.id.main_btnSelectDevice);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ChooseActivity.class));
                finish();
            }
        });

        btnSelectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, SelectDevice.class), Constants.REQUEST_SELECT_DEVICE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_SELECT_DEVICE && data != null) {
            btAddr = data.getStringExtra(SelectDevice.EXTRA_ADDRESS); //receive the address of the bluetooth device
            if (btAddr != null) {
                // set selected bluetooth device address to default
                mPrefs.edit().putString(Constants.PREF_DEFAULT_BT_DEVICE, btAddr).commit();
            }
        }
    }

}
