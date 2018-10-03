package tob.leis.randomshot.helper;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by tobi on 16.02.16.
 */
public class BluetoothHelper {

    private static final String LOG_TAG = "Bluetooth";
    private static final String ERROR_RECEIVE = "Couldn't receive message";

    //UUID fuer Kommunikation mit Seriellen Modulen
    //private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothSocket socket = null;
    private OutputStream stream_out = null;
    private InputStream stream_in = null;
    private boolean isConnected = false;

    public BluetoothHelper() {
    }

    public void connect() {

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice bluetoothDevice = null;

        Set pairedDevices = adapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(Object device : pairedDevices) {
                bluetoothDevice = (BluetoothDevice) device;
                break;
            }
        }

        ParcelUuid[] uuids = null;
        try{
            uuids = bluetoothDevice.getUuids();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }


        try {
            socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter.cancelDiscovery();

        try {
            socket.connect();
            isConnected = true;
        } catch (IOException e) {
            isConnected = false;
            e.printStackTrace();
        }


        if (!isConnected) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {
            stream_out = socket.getOutputStream();
            stream_in = socket.getInputStream();
            Log.d(LOG_TAG, "OutputStream and InputStream created.");
        } catch (IOException e) {
            e.printStackTrace();
            isConnected = false;
        }

        if (isConnected) {
            Log.e(LOG_TAG, "Connected.");
        } else {
            Log.e(LOG_TAG,"Connection error.");
        }
    }

    public void send(String message) {
        byte[] msgBuffer = message.getBytes();
        if (isConnected) {
            Log.d(LOG_TAG, "Send message: " + message);
            try {
                stream_out.write(msgBuffer);
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Bluetooth exception:" + e.toString());
            }
        }
    }

    public String receive() {
        byte[] buffer = new byte[1024];
        int length;
        String msg = "";
        try {
            if (stream_in.available() > 0) {
                length = stream_in.read(buffer);
                for (int i = 0; i < length; i++) {
                    msg += (char) buffer[i];
                }
            } else {
                msg = ERROR_RECEIVE;
            }

        } catch (Exception e) {
            msg = ERROR_RECEIVE;
            e.printStackTrace();
        }
        return msg;
    }

    public void closeCon() {
        if (isConnected && stream_out != null) {
            isConnected = false;
            Log.d(LOG_TAG, "Disconnecting..");
            try {
                stream_out.flush();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
