package tw.ntust.e_burner.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.ntust.e_burner.R;
import tw.ntust.e_burner.app.Constants;
import tw.ntust.e_burner.flingswipe.SwipeFlingAdapterView;

public class IxBurning extends BaseActivity {
    private String btAddr = "";
    private boolean isBtConnected = false;

    BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Android SPP UUID.

    @InjectView(R.id.frame)
    SwipeFlingAdapterView flingContainer;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if there's no default bluetooth device in the SharedPreferences, start SelectDeviceActivity to ask user
        btAddr = mPrefs.getString(Constants.PREF_DEFAULT_BT_DEVICE, "");
        if (btAddr.equals("")) {
            startActivityForResult(new Intent(IxBurning.this, SelectDevice.class),Constants. REQUEST_SELECT_DEVICE);
        } else {
            initComponents();
            new ConnectBT().execute(); // call the class to connect
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_SELECT_DEVICE && data != null) {
            btAddr = data.getStringExtra(SelectDevice.EXTRA_ADDRESS); //receive the address of the bluetooth device
            if (btAddr != null) {
                initComponents();
                new ConnectBT().execute(); // call the class to connect
            } else {
                finish();
            }
            // set selected bluetooth device address to default
            mPrefs.edit().putString(Constants.PREF_DEFAULT_BT_DEVICE, btAddr).commit();
        } else {
            finish();
        }
    }

    private void btSendMsg(String msg) {
        try {
            btSocket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            makeToast(getApplicationContext(), "Error sending message");
            e.printStackTrace();
            finish();
        }
    }

    private void btDisconnect() {
        if (btSocket != null) // if the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                makeToast(getApplicationContext(), "Error disconnect");
                e.printStackTrace();
            }
        }
        finish();
    }

    private void initComponents() {
        setContentView(R.layout.activity_ixburning);
        ButterKnife.inject(this);

        // 放在listView中的图片资源id
        int[] imgAry = new int[] { R.drawable.ic_coin};
        // 创建List集合对象
        final List a = new ArrayList();
        // 为List集合添加数据
        for (int i = 0; i < imgAry.length; i++) {
            Map map = new HashMap();
            map.put("image", imgAry[i]);
            // 金紙數量在這
            for (int k = 0; k < 100; k++) {
                a.add(map);
            }
        }
        // 設定item adapter
        final SimpleAdapter adapter = new SimpleAdapter(this, a,
                R.layout.item, new String[] { "image" },
                new int[] { R.id.helloText});

        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onUpCardExit(Object dataObject) {
                makeToast(IxBurning.this, "Up!");
                btSendMsg("1");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                Log.d("LIST", "notified");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                // View view = flingContainer.getSelectedView();
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(IxBurning.this, "Clicked!");
            }
        });
    }

    private static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean connectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(IxBurning.this, "Connecting...", "Please wait");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    btAdapter = BluetoothAdapter.getDefaultAdapter(); // get the mobile bluetooth device
                    BluetoothDevice btDevice = btAdapter.getRemoteDevice(btAddr); // connects to the device's address and checks if it's available
                    btSocket = btDevice.createInsecureRfcommSocketToServiceRecord(myUUID); // create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect(); //start connection
                }
            } catch (IOException e) {
                e.printStackTrace();
                connectSuccess = false; //if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!connectSuccess) {
                makeToast(getApplicationContext(), "Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                makeToast(getApplicationContext(), "Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
