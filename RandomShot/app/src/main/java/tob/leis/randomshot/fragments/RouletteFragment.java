package tob.leis.randomshot.fragments;

import android.graphics.Color;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import tob.leis.randomshot.R;
import tob.leis.randomshot.Roadmap;
import tob.leis.randomshot.helper.Bluetooth;
import tob.leis.randomshot.helper.BluetoothHelper;

import static tob.leis.randomshot.helper.JSONHelper.LENGTH;
import static tob.leis.randomshot.helper.JSONHelper.RADIUS;
import static tob.leis.randomshot.helper.JSONHelper.SPEED;


public class RouletteFragment extends Fragment {

    public static final String TAG = "RouletteFragment";

    public static final int STATE_STOPPED = 0;
    public static final int STATE_PAUSED  = 1;
    public static final int STATE_RUNNING = 2;

    private Button startButton, pauseButton, resumeButton, stopButton;
    private List<Button> buttonList;
    private SeekBar radiusBar, lengthBar, speedBar;

    private Roadmap roadMap;

    private int restoredLength = 0, restoredRadius = 0, restoredSpeed = 0;

    int currentState = STATE_STOPPED;

    private BluetoothHelper bluetoothHelper;

    public RouletteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "Stored: length: " + lengthBar.getProgress() + " radius: " + radiusBar.getProgress());
        savedInstanceState.putInt(LENGTH, lengthBar.getProgress());
        savedInstanceState.putInt(RADIUS, radiusBar.getProgress());
        savedInstanceState.putInt(SPEED, speedBar.getProgress());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_russian_roulette, container, false);

        startButton = rootView.findViewById(R.id.start_button);
        pauseButton = rootView.findViewById(R.id.pause_button);
        resumeButton = rootView.findViewById(R.id.resume_button);
        stopButton = rootView.findViewById(R.id.stop_button);
        roadMap = rootView.findViewById(R.id.view_road);

        buttonList = Arrays.asList(startButton, pauseButton, resumeButton, stopButton);

        ButtonTouchListener listener = new ButtonTouchListener();
        for (Button button : buttonList) {
            button.setOnTouchListener(listener);
        }

        radiusBar = rootView.findViewById(R.id.seekBar_radius);
        lengthBar = rootView.findViewById(R.id.seekBar_length);
        speedBar = rootView.findViewById(R.id.seekBar_speed);

        radiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((TextView)rootView.findViewById(R.id.current_radius)).setText(String.format(Locale.GERMAN,"%03d", i));
                roadMap.setmRadius(i, lengthBar.getProgress());
                roadMap.invalidate();
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        lengthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((TextView)rootView.findViewById(R.id.current_length)).setText(String.format(Locale.GERMAN, "%03d", i));
                roadMap.setmLength(i);
                roadMap.invalidate();
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((TextView)rootView.findViewById(R.id.current_speed)).setText(String.format(Locale.GERMAN,"%03d", i));
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        if (savedInstanceState != null) {
            restoredLength = savedInstanceState.getInt(LENGTH);
            restoredRadius = savedInstanceState.getInt(RADIUS);
            restoredSpeed = savedInstanceState.getInt(SPEED);
        }

        Log.i(TAG, "restored radius: " + restoredRadius + " length: " + restoredLength);
        radiusBar.setProgress(restoredRadius);
        lengthBar.setProgress(restoredLength);
        speedBar.setProgress(restoredSpeed);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateButtons();
        roadMap.invalidate();

        bluetoothHelper = new BluetoothHelper();
        bluetoothHelper.connect();
    }



    private void updateButtons() {

        for (Button button : buttonList) {
            button.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 0));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        switch (currentState) {
            case STATE_STOPPED:
                startButton.setLayoutParams(params);
                break;
            case STATE_RUNNING:
                pauseButton.setLayoutParams(params);
                break;
            case STATE_PAUSED:
                resumeButton.setLayoutParams(params);
                stopButton.setLayoutParams(params);
                break;
        }
    }

    private class ButtonTouchListener implements View.OnTouchListener {

        private final String lightGrey = "#eeeeee";
        private final String darkGrey = "#727272";

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((Button) view).setTextColor(Color.parseColor(darkGrey));
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                ((Button) view).setTextColor(Color.parseColor(lightGrey));

                switch (view.getId()) {
                    case R.id.start_button:
                        currentState = STATE_RUNNING;
                        new Thread(new Runnable() {
                            public void run() {
                                System.out.println("Start");
                                bluetoothHelper.send();
                                System.out.println("Fin");
                            }
                        }).start();

                        //TODO: SEND START COMMAND VIA BLUETOOTH TO ARDUINO
                        //TODO: Calculate radius, speed, etc. from partial progressbar value
                        //String cmd = JSONHelper.buildStart();
                        break;
                    case R.id.pause_button:
                        currentState = STATE_PAUSED;
                        //TODO: SEND PAUSE COMMAND VIA BLUETOOTH TO ARDUINO
                        //String cmd = JSONHelper.buildPause();
                        break;
                    case R.id.resume_button:
                        currentState = STATE_RUNNING;
                        //TODO: SEND CONTINUE COMMAND VIA BLUETOOTH TO ARDUINO
                        break;
                    case R.id.stop_button:
                        currentState = STATE_STOPPED;
                        //TODO: SEND STOP COMMAND VIA BLUETOOTH TO ARDUINO
                        break;
                }
                updateButtons();
            }
            return true;
        }
    }
}
