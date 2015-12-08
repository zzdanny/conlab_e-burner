package tw.ntust.e_burning.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import tw.ntust.e_burning.R;

public class SelectDevice extends AppCompatActivity {

    public static final String EXTRA_ADDRESS = "device_address"; // for intent extra parameter

    ListView deviceList;

    private BluetoothAdapter btAdapter = null;
    Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        initComponents();

        // if the device has bluetooth
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        } else if (!btAdapter.isEnabled()) {
            // ask to the user turn the bluetooth on
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        } else {
            getPairedDevicesList();
        }
    }

    private void initComponents() {
        setTitle("選擇電子金爐藍芽名稱");
        deviceList = (ListView) findViewById(R.id.selDevice_lvPaired);
    }

    private void getPairedDevicesList() {
        pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); // get the device's name and the address
            }
            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            deviceList.setAdapter(adapter);
            deviceList.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // start next activity
            Intent i = new Intent(SelectDevice.this, IxBurning.class);
            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };
}
