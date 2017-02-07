package com.example.a21753725a.mapas;

import android.net.Uri;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 21753725a on 31/01/17.
 */

public class StationAPI {

    private final String BASE_URL = "http://wservice.viabicing.cat/v2/stations";

    public ArrayList<Station> getStations() {

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .build();
        String url = builtUri.toString();

        return doCall(url);
    }

    @Nullable
    private ArrayList<Station> doCall(String url) {
        try {
            String JsonResponse = HttpUtils.get(url);
            return processJson(JsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recoge las cartas de una API y almacena datos en un array de objetos carta
     * con los datos que queremos guardar
     *
     * @param jsonResponse
     * @return
     */
    private ArrayList<Station> processJson(String jsonResponse) {
        ArrayList<Station> stations = new ArrayList<>();

        try {
            JSONObject data = new JSONObject(jsonResponse);
            JSONArray jsonStation = data.getJSONArray("stations");

            for (int i = 0; i < jsonStation.length(); i++) {

                JSONObject jsonStat = jsonStation.getJSONObject(i);

                Station station = new Station();

                station.setId(jsonStat.getString("id"));
                String type = jsonStat.getString("type");
                if (type == "BIKE"){
                    station.setMecanic(true);
                }else{
                    station.setMecanic(false);
                }
                station.setLat(jsonStat.getString("latitude"));
                station.setLon(jsonStat.getString("longitude"));
                station.setStreetName(jsonStat.getString("streetName"));
                station.setAltitude(jsonStat.getString("altitude"));
                station.setSlots(jsonStat.getString("slots"));
                station.setBikes(jsonStat.getString("bikes"));
                station.setStatus(jsonStat.getString("status"));

                stations.add(station);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stations;
    }
}
