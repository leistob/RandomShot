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

    //UUID fuer Kommunikation mit Seriellen Modulen
    //private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothDevice bluetoothDevice;
    private BluetoothAdapter adapter = null;
    private BluetoothSocket socket = null;
    private OutputStream stream_out = null;
    private InputStream stream_in = null;
    private boolean isConnected = false;

    public BluetoothHelper() {
    }

    public void connect() {

        adapter = BluetoothAdapter.getDefaultAdapter();

        Set pairedDevices = adapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(Object device : pairedDevices) {
                bluetoothDevice = (BluetoothDevice) device;
                break;
            }
        }

        ParcelUuid[] uuids = bluetoothDevice.getUuids();

        //Create socket
        try {
            socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
            Log.d(LOG_TAG, "Created socket");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Socket creation failed: " + e.toString());
        }

        adapter.cancelDiscovery();

        //Connect socket
        try {
            socket.connect();
            isConnected = true;
            Log.d(LOG_TAG, "Socket connected");
        } catch (IOException e) {
            isConnected = false;
            Log.e(LOG_TAG, "Couldn't connect socket: " + e.toString());
        }

        //Close socket, if no connection could be established.
        if (!isConnected) {
            try {
                socket.close();
            } catch (Exception e) {
                Log.e(LOG_TAG,
                        "Socket couldn't be closed: " + e.toString());
            }
        }

        //Create Outputstream
        try {
            stream_out = socket.getOutputStream();
            stream_in = socket.getInputStream();
            Log.d(LOG_TAG, "OutputStream and InputStream created.");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error: " + e.toString());
            isConnected = false;
        }

        if (isConnected) {
            Log.e(LOG_TAG, "Connected.");
        } else {
            Log.e(LOG_TAG,"Connection error.");
        }
    }

    public void send() {
        String message = "Testat\n";
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

    public void receive() {
        byte[] buffer = new byte[1024];
        int laenge;
        String msg = "";
        try {
            if (stream_in.available() > 0) {
                laenge = stream_in.read(buffer);

                for (int i = 0; i < laenge; i++)
                    msg += (char) buffer[i];

                Log.d(LOG_TAG, "Message: " + msg);
            } else
                Log.e(LOG_TAG,"Nothing received");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error when receiving: " + e.toString());
        }
    }

    public void trennen() {
        if (isConnected && stream_out != null) {
            isConnected = false;
            Log.d(LOG_TAG, "Disconnecting..");
            try {
                stream_out.flush();
                socket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Fehler beim beenden des Streams und schliessen des Sockets: "
                                + e.toString());
            }
        } else
            Log.d(LOG_TAG, "Trennen: Keine Verbindung zum beenden");
    }
}
