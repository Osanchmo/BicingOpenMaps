package com.example.a21753725a.mapas;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    View view;
    private MapView map;
    RadiusMarkerClusterer stationsCluster;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        map = (MapView) view.findViewById(R.id.map);

        initializeMap();

        return view;
    }

    private void initializeMap() {
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(13);
        mapController.setCenter(new GeoPoint(41.3851,2.1734));

        loadMarks();
    }

    private void loadMarks(){
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }

    private class RefreshDataTask extends AsyncTask<Void,Void,ArrayList<Station> > {
        @Override
        protected ArrayList<Station> doInBackground(Void... voids) {
            StationAPI sap = new StationAPI();

            ArrayList<Station> stations = sap.getStations();

            return stations;
        }

        @Override
        protected void onPostExecute(ArrayList<Station> stations) {
            stationsCluster = new RadiusMarkerClusterer(getContext());
            for (Station s: stations){
                Marker test = checkNBikes(s);
                stationsCluster.add(test);
            }
            Drawable clusterIconD = ContextCompat.getDrawable(getContext(),R.drawable.marker_cluster);
            Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
            stationsCluster.setIcon(clusterIcon);
            map.getOverlays().add(stationsCluster);
            map.invalidate();
        }
    }

    private Marker checkNBikes(Station s){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(new GeoPoint(Double.valueOf(s.getLat()),Double.valueOf(s.getLon())));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle(s.getId() + " | " + s.getStreetName());

        int bicis = Integer.valueOf(s.getBikes());
        int slots = Integer.valueOf(s.getSlots())+bicis;
        int slotsDisp = slots+bicis;
        double disp;

        if (bicis == 0){
            disp = 0;
        }else {
            disp = (bicis * 100) / slotsDisp;
        }

        if(s.isMecanic()){
            if (disp == 0){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.mark0));
            }else if(disp >= 0 && disp < 25){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.mark25));
            }else if(disp >= 25 && disp < 50){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.mark50));
            }else if (disp >= 50 && disp < 75){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.mark75));
            }else if (disp>= 75 && disp < 100){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.mark100));
            }
        }else {
            if (disp == 0){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.elec0));
            }else if(disp >= 0 && disp < 25){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.elec25));
            }else if(disp >= 25 && disp < 50){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.elec50));
            }else if (disp>= 50 && disp < 75){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.elec75));
            }else if (disp >=75 && disp < 100){
                startMarker.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.elec100));
            }
        }

        String description = "Bicicletas: " + bicis + "\n slots: " + slots;
        startMarker.setSubDescription(description);
        return startMarker;
    }

}

