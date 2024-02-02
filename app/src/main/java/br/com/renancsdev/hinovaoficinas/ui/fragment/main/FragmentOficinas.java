package br.com.renancsdev.hinovaoficinas.ui.fragment.main;

import static android.content.Context.MODE_PRIVATE;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.api.interfaces.Endpoints;
import br.com.renancsdev.hinovaoficinas.api.request.RequestOficina;
import br.com.renancsdev.hinovaoficinas.api.service.ServiceBuilder;
import br.com.renancsdev.hinovaoficinas.databinding.FragmentOficinasBinding;
import br.com.renancsdev.hinovaoficinas.ui.activity.login.Login;
import br.com.renancsdev.hinovaoficinas.util.gps.Geolocalizacao;
import br.com.renancsdev.hinovaoficinas.util.gps.Localizacao;

public class FragmentOficinas extends Fragment {

    FragmentOficinasBinding binding;

    Endpoints endpoints;

    RequestOficina request;

    SharedPreferences sharedPreferences;

    Geolocalizacao geolocalizacao;

    String[] permisson = {
     Manifest.permission.ACCESS_FINE_LOCATION,
     Manifest.permission.ACCESS_COARSE_LOCATION,
     Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarSerico();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_oficinas, container, false);
        sharedPreferences = binding.getRoot().getContext().getSharedPreferences("shared",MODE_PRIVATE);

        iniciarRequest();
        eventoBotoes();
        return binding.getRoot();
    }

    private void iniciarSerico(){
        endpoints = ServiceBuilder.getClient().create(Endpoints.class);
    }

    private void eventoBotoes(){
        binding.btnDeslogar.setOnClickListener(v -> {
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(binding.getRoot().getContext() , Login.class));
        });
    }

    private void iniciarRequest(){
        buscarLocalizacaoAtual(binding.getRoot().getContext());
        request = new RequestOficina(endpoints);
        request.buscarListaOficinas(binding , binding.getRoot().getContext());
    }

    private void buscarLocalizacaoAtual(Context context) {

        if (androidx.core.content.ContextCompat.checkSelfPermission(context, permisson[0]) != PackageManager.PERMISSION_GRANTED &&
                androidx.core.content.ContextCompat.checkSelfPermission(context, permisson[1]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{permisson[0]}, 1);
            ActivityCompat.requestPermissions((Activity) context, new String[]{permisson[1]}, 1);
            ActivityCompat.requestPermissions((Activity) context, new String[]{permisson[2]}, 1);
            return;
        }

        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, new Localizacao());
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        geolocalizacao = new Geolocalizacao(binding , location);
        geolocalizacao.buscarLocalizacaoLocal();
    }

}