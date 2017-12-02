package com.example.helio.gotochurchmobileproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.helio.gotochurchmobileproject.Basic.Address;
import com.example.helio.gotochurchmobileproject.Basic.Church;
import com.example.helio.gotochurchmobileproject.Util.WebService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GTC_MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressBar progress;

    public WebService ws;
    public String URL = "http://dayvsonnascimento.pythonanywhere.com/gotochurch/congregacao/listarCongregacao";
    public ArrayList<Church> church;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ws = new WebService();
        this.listChurch();

    }

    public void listChurch(){
        try {
            String stringChurch = this.ws.getUrlContents(this.URL); //Chama função que consome o web service

            JSONArray churchJson = new JSONArray(stringChurch);
            JSONObject church;

            this.church = new ArrayList<>();

            for (int i = 0; i < churchJson.length(); i++) {
                church = new JSONObject(churchJson.getString(i));

                Church c = new Church();
                Address a = new Address();
                c.setId(Integer.parseInt(church.getString("id")));
                c.setName(church.getString("nome"));
                c.setResponsible("Coordenador: "+church.getString("coordenador"));

                if(church.getString("latitude").equals("")){
                    c.setLat(0);
                }else{
                    c.setLat(Double.parseDouble(church.getString("latitude")));
                }

                if(church.getString("logitude").equals("")){
                    c.setLng(0);
                }else{
                    c.setLng(Double.parseDouble(church.getString("logitude")));
                }



                a.setCity(church.getString("cidade"));
                a.setHomeNumber(church.getString("numero"));
                a.setStreetName(church.getString("rua"));
                a.setDistrict(church.getString("bairro"));
                a.setState("Pernambuco");


                c.setAddress(a);

                this.church.add(c);

            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //LatLng sydney2 = new LatLng(-34, 141);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.churchmarker)));
        //mMap.addMarker(new MarkerOptions().position(sydney2).title("Marker in Sydney2").icon(BitmapDescriptorFactory.fromResource(R.drawable.churchmarker)));

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 4));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney2));

        Toast.makeText(this, ""+this.church, Toast.LENGTH_SHORT).show();

        for (Church c: this.church){
            //LatLng l = new LatLng(-8.056030,0);
            LatLng p = new LatLng(c.getLat(), c.getLng());
            mMap.addMarker(new MarkerOptions().position(p).title(c.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.churchmarker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p, 5));
        }

        /*try{

            Uri local = Uri.parse("geo:0,0?q=rua+21+de+abril,+43,+escada,+pernambuco,+brasil");

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    LatLng latlng = new LatLng(-34, 141);//new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latlng).title("Sua Localização").icon(BitmapDescriptorFactory.fromResource(R.drawable.churchmarker)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                    mMap.setMinZoomPreference(11);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }*/
    }

    void voltar(View v){
        finish();
    }
}
