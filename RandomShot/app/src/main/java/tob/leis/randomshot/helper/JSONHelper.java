package tob.leis.randomshot.helper;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

    public static final String MODE = "mode";
    public static final String ROULETTE = "roulette";
    public static final String CONTROLLER = "controller";
    public static final String CMD = "command";

    public static final String RADIUS = "radius";
    public static final String LENGTH = "length";

    public static final String TIME = "time";

    public static final String START = "start";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String STOP = "stop";

    public static final String SPEED = "speed";
    public static final String ROTATION = "rotation";

    public static String buildStart(int radius, int length, int speed, int time) throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put(MODE, ROULETTE);
        obj.put(CMD, START);
        obj.put(RADIUS, radius);
        obj.put(LENGTH, length);
        obj.put(SPEED, speed);
        obj.put(TIME, time);

        return obj.toString();
    }

    public static String buildResume() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put(CMD, RESUME);

        return obj.toString();
    }

    public static String buildStop() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put(CMD, STOP);

        return obj.toString();
    }

    public static String buildPause() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put(CMD, PAUSE);

        return obj.toString();
    }

    public static String buildDrive(int speed, int rotation){
        JSONObject obj = new JSONObject();


        try {
            obj.put(CMD, CONTROLLER);
            obj.put(SPEED, speed);
            obj.put(ROTATION, rotation);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return obj.toString();
    }




}
