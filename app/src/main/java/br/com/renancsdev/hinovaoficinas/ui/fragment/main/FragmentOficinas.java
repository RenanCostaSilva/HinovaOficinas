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

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.adpter.recycler.RecyclerOficina;
import br.com.renancsdev.hinovaoficinas.api.interfaces.Endpoints;
import br.com.renancsdev.hinovaoficinas.api.request.RequestOficina;
import br.com.renancsdev.hinovaoficinas.api.service.ServiceBuilder;
import br.com.renancsdev.hinovaoficinas.databinding.FragmentOficinasBinding;
import br.com.renancsdev.hinovaoficinas.model.oficina.ListaOficinasItem;
import br.com.renancsdev.hinovaoficinas.model.oficina.ResulListOficinas;
import br.com.renancsdev.hinovaoficinas.ui.activity.login.Login;
import br.com.renancsdev.hinovaoficinas.util.gps.Localizacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOficinas extends Fragment {

    FragmentOficinasBinding binding;

    Endpoints endpoints;

    RequestOficina request;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor myEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarSerico();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
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
            sharedPreferences.edit().clear().commit();
            startActivity(new Intent(binding.getRoot().getContext() , Login.class));
        });
    }

    private void iniciarRequest(){
        buscarLocalizacaoAtual(binding.getRoot().getContext());
        request = new RequestOficina(endpoints);
        request.buscarListaOficinas(binding , binding.getRoot().getContext());
    }

    private void buscarLocalizacaoAtual(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }

        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, new Localizacao());
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double latitude=0;
        double longitude=0;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        binding.tvOficinaMinhaLocalizacao.setText(latitude +" , "+longitude);
    }

}