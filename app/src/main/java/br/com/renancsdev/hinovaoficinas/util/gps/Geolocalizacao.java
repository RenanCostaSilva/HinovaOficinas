package br.com.renancsdev.hinovaoficinas.util.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import br.com.renancsdev.hinovaoficinas.databinding.FragmentOficinasBinding;

public class Geolocalizacao extends AppCompatActivity implements LocationListener {

    FragmentOficinasBinding binding;
    FusedLocationProviderClient mFusedLocationClient;
    LocationManager locationManager;
    Integer locationPermissionCode = 2;
    String[] arrayPerm = {Manifest.permission.ACCESS_FINE_LOCATION};
    Location loc;
    double latitude=0;
    double longitude=0;

    public Geolocalizacao(FragmentOficinasBinding binding , Location loc) {
        this.binding = binding;
        this.loc = loc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(binding.getRoot().getContext());
    }

    public void buscarLocalizacaoLocal() {
        if (ContextCompat.checkSelfPermission(binding.getRoot().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(binding.getRoot().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) binding.getRoot().getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions((Activity) binding.getRoot().getContext(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions((Activity) binding.getRoot().getContext(), new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }

        LocationManager locManager = (LocationManager) binding.getRoot().getContext().getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, new Localizacao());
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        binding.tvOficinaMinhaLocalizacao.setText(buscarEnderecoCoordenada(latitude,longitude));
    }

    @Override
    public void onLocationChanged(Location location) {
        new Thread(() -> binding.tvOficinaMinhaLocalizacao.setText(buscarEnderecoCoordenada(location.getLatitude(),location.getLongitude()))).start();
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == locationPermissionCode) {
            if (!grantResults.toString().isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(binding.getRoot().getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(binding.getRoot().getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    private String buscarEnderecoCoordenada(Double lat , Double longt){
        String endereco;
        Geocoder geoCoder = new Geocoder(binding.getRoot().getContext(), Locale.getDefault());
        List<Address> address = null;
        try {
            address = geoCoder.getFromLocation(lat,longt,1);
        } catch (IOException e) {
            Log.e("App123Geolocalizacao" , "buscarEstadoCoordenada Error: "+e);
        }
        endereco = address.get(0).getAddressLine(0);
        if (endereco == null){
            endereco = address.get(0).getAddressLine(0);
        }
        return endereco;
    }

}
