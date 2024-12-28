package com.eveningoutpost.dexdrip.marco;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eveningoutpost.dexdrip.models.BgReading;
import com.eveningoutpost.dexdrip.models.UserError;
import com.eveningoutpost.dexdrip.xdrip;

import org.json.JSONException;
import org.json.JSONObject;

public class DexcomSender {
    private static final String TAG = DexcomSender.class.getSimpleName();
    private static final String HOME = "https://www.type1d.ch/dexcom";

    public static void sendDexcomDataHome(BgReading bgReading) throws JSONException {
        UserError.Log.e(TAG
                , "G7 sendDataHome working with url " + HOME);
        if (bgReading == null) {
            UserError.Log.e(TAG
                    , "BgReading is null");
        }

        RequestQueue queue = SendData.getInstance(xdrip.getAppContext())
                .getRequestQueue();
        JSONObject postData = new JSONObject();
        postData.put("raw_data", bgReading.raw_data);
        postData.put("calculated_value", bgReading.calculated_value);
        postData.put("filtered_data", bgReading.filtered_data);
        postData.put("sensor_location", bgReading.sensor.sensor_location);
        postData.put("dg_mgdl", bgReading.dg_mgdl);
        postData.put("dg_slope", bgReading.dg_slope);
        postData.put("raw_calculated",bgReading.raw_calculated);
        postData.put("timestamp", bgReading.timestamp);
        postData.put("sensor_uuid", bgReading.sensor_uuid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HOME,
                postData, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                UserError.Log.i(TAG, "Home answered correctly");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error){
                UserError.Log.e(TAG, "Home is not answering");
            }
        });
        queue.add(jsonObjectRequest);
    }
}
