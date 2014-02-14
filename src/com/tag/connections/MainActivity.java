package com.tag.connections;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int REQUEST_ENABLE_BT = 12;
	private TextView out;
	private BluetoothAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		out = (TextView) findViewById(R.id.tvBluetoothInfo);
		setBluetoothData();

		if (Connections.blueTooth()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		out.setText("");
		setBluetoothData();
	}

	private void setBluetoothData() {

		// Getting the Bluetooth adapter
		adapter = BluetoothAdapter.getDefaultAdapter();
		out.append("\nAdapter: " + adapter.toString() + "\n\nName: "
				+ adapter.getName() + "\nAddress: " + adapter.getAddress());

		// Check for Bluetooth support in the first place
		// Emulator doesn't support Bluetooth and will return null

		if (adapter == null) {
			Toast.makeText(this, "Bluetooth NOT supported. Aborting.",
					Toast.LENGTH_LONG).show();
		}

		// Starting the device discovery
		out.append("\n\nStarting discovery...");
		adapter.startDiscovery();
		out.append("\nDone with discovery...\n");

		// Listing paired devices
		out.append("\nDevices Pared:");
		Set<BluetoothDevice> devices = adapter.getBondedDevices();
		for (BluetoothDevice device : devices) {
			out.append("\nFound device: " + device.getName() + " Add: "
					+ device.getAddress());
		}
	}

}
