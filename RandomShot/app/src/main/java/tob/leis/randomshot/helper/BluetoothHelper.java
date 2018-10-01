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
import java.util.UUID;


/**
 * Created by tobi on 16.02.16.
 */
public class BluetoothHelper {

    //private BluetoothAdapter blueAdapter;
    private BluetoothDevice bluetoothDevice;

    // UUID fuer Kommunikation mit Seriellen Modulen
    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String LOG_TAG = "FRAGDUINO";

    // Variablen
    private BluetoothAdapter adapter = null;
    private BluetoothSocket socket = null;
    private OutputStream stream_out = null;
    private InputStream stream_in = null;
    private boolean is_connected = false;

    public BluetoothHelper() {
    }

    public void verbinden() {

        adapter = BluetoothAdapter.getDefaultAdapter();

        Set pairedDevices = adapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(Object device : pairedDevices) {
                bluetoothDevice = (BluetoothDevice) device;
                break;
            }
        }

        ParcelUuid[] uuids = bluetoothDevice.getUuids();


        //BluetoothSocket mmSocket = null;

        /*try {
            mmSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
            mmSocket.connect();
            OutputStream mmOutputStream = mmSocket.getOutputStream();
            InputStream mmInputStream = mmSocket.getInputStream();

            String test = "Test123\n";
            mmOutputStream.write(test.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }      */

        // Socket erstellen
        try {
            socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
            Log.d(LOG_TAG, "Socket erstellt");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Socket Erstellung fehlgeschlagen: " + e.toString());
        }

        adapter.cancelDiscovery();

        // Socket verbinden
        try {
            socket.connect();
            is_connected = true;
            Log.d(LOG_TAG, "Socket verbunden");
        } catch (IOException e) {
            is_connected = false;
            Log.e(LOG_TAG, "Socket kann nicht verbinden: " + e.toString());
        }

        // Socket beenden, falls nicht verbunden werden konnte
        if (!is_connected) {
            try {
                socket.close();
            } catch (Exception e) {
                Log.e(LOG_TAG,
                        "Socket kann nicht beendet werden: " + e.toString());
            }
        }

        // Outputstream erstellen:
        try {
            stream_out = socket.getOutputStream();
            Log.d(LOG_TAG, "OutputStream erstellt");
        } catch (IOException e) {
            Log.e(LOG_TAG, "OutputStream Fehler: " + e.toString());
            is_connected = false;
        }

        // Inputstream erstellen
        try {
            stream_in = socket.getInputStream();
            Log.d(LOG_TAG, "InputStream erstellt");
        } catch (IOException e) {
            Log.e(LOG_TAG, "InputStream Fehler: " + e.toString());
            is_connected = false;
        }

        if (is_connected) {
            Log.e(LOG_TAG, "Verbunden mit");
        } else {
            Log.e(LOG_TAG,"Verbindungsfehler");
        }
    }

    public void senden() {
        String message = "Testat\n";
        byte[] msgBuffer = message.getBytes();
        if (is_connected) {
            Log.d(LOG_TAG, "Sende Nachricht: " + message);
            try {
                stream_out.write(msgBuffer);
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Bluetest: Exception beim Senden: " + e.toString());
            }
        }
    }

    public void empfangen() {
        byte[] buffer = new byte[1024]; // Puffer
        int laenge; // Anzahl empf. Bytes
        String msg = "";
        try {
            if (stream_in.available() > 0) {
                laenge = stream_in.read(buffer);
                Log.d(LOG_TAG,
                        "Anzahl empfangender Bytes: " + String.valueOf(laenge));

                // Message zusammensetzen:
                for (int i = 0; i < laenge; i++)
                    msg += (char) buffer[i];

                Log.d(LOG_TAG, "Message: " + msg);
            } else
                Log.e(LOG_TAG,"Nichts empfangen");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Fehler beim Empfangen: " + e.toString());
        }
    }

    public void trennen() {
        if (is_connected && stream_out != null) {
            is_connected = false;
            Log.d(LOG_TAG, "Trennen: Beende Verbindung");
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
