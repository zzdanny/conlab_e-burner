package tw.ntust.e_burning.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import tw.ntust.e_burning.R;

public class IxBurning extends AppCompatActivity {

    private String btAddr;
    private boolean isBtConnected = false;

    BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Android SPP UUID.

    private Button btnSend, btnDisconnect;
    private EditText txtSendStr;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ixburning);
        initComponents();

        btAddr = getIntent().getStringExtra(SelectDevice.EXTRA_ADDRESS); //receive the address of the bluetooth device


        new ConnectBT().execute(); // call the class to connect
    }

    private void initComponents() {
        setTitle("BT Transfer Test");
        btnSend = (Button)findViewById(R.id.ixburning_btnSend);
        btnDisconnect = (Button)findViewById(R.id.ixburning_btnDisconnect);
        txtSendStr = (EditText)findViewById(R.id.ixburning_txtSendStr);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSendMsg(txtSendStr.getText().toString());
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btDisconnect();
            }
        });
    }

    private void btSendMsg(String msg) {
        try {
            btSocket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            msg("Error sending message");
            e.printStackTrace();
        }
    }

    private void btDisconnect() {
        if (btSocket != null) // if the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                msg("Error disconnect");
                e.printStackTrace();
            }
        }
        finish();
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean connectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(IxBurning.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    btAdapter = BluetoothAdapter.getDefaultAdapter(); // get the mobile bluetooth device
                    BluetoothDevice dispositivo = btAdapter.getRemoteDevice(btAddr); // connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID); // create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                connectSuccess = false; //if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!connectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
