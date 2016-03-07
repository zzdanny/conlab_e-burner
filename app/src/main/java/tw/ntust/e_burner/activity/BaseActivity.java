package tw.ntust.e_burner.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class BaseActivity extends Activity {
    protected SharedPreferences mPrefs;
//    private String btAddr = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_general, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_selectDevice:
//                startActivityForResult(new Intent(this, SelectDevice.class),Constants. REQUEST_SELECT_DEVICE);
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SELECT_DEVICE && data != null) {
//            btAddr = data.getStringExtra(SelectDevice.EXTRA_ADDRESS); //receive the address of the bluetooth device
//            if (btAddr != null) {
//                // set selected bluetooth device address to default
//                mPrefs.edit().putString(Constants.PREF_DEFAULT_BT_DEVICE, btAddr).commit();
//            }
//        }
//    }

}
