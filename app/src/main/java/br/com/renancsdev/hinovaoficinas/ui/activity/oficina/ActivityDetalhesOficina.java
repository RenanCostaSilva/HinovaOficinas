package br.com.renancsdev.hinovaoficinas.ui.activity.oficina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityDetalhesOficinaBinding;
import br.com.renancsdev.hinovaoficinas.ui.activity.camera.ActivityCameraPreview;
import br.com.renancsdev.hinovaoficinas.util.gps.Localizacao;

public class ActivityDetalhesOficina extends AppCompatActivity {

    private ActivityDetalhesOficinaBinding binding;
    private static final  Integer LAYOUT = R.layout.activity_detalhes_oficina;
    private String avaliacao = "avaliacao";
    private String telefone = "telefone";

    Double iLatitude = 0.0;
    Double iLongitude = 0.0;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setarConfig();
        setarCoordenadas(getIntent().getStringExtra("latitude") , getIntent().getStringExtra("longitude"));
        sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostarValores();
        evontoBotoes();
    }

    /* Configuração Inicial */
    private void setarConfig(){
        setarConfigLayout();
        setarConfigDataBinding();
    }

    private void setarConfigLayout(){
        setContentView(LAYOUT);
    }

    private void setarConfigDataBinding(){
        binding = DataBindingUtil.setContentView(this , LAYOUT);
    }

    private void setarCoordenadas(String latitude , String longitude){
        iLatitude  = Double.valueOf(latitude);
        iLongitude = Double.valueOf(longitude);
    }

    private void  salvarDadosOficina(){
        myEdit = sharedPreferences.edit();
        myEdit.putString("fotoOficina", getIntent().getStringExtra("foto"));
        myEdit.putString("nomeOficina", getIntent().getStringExtra("nome"));
        myEdit.putString("telefoneOficina",  getIntent().getStringExtra(telefone));
        myEdit.putInt("avaliacaoOficina",    getIntent().getIntExtra(avaliacao , 0));
        myEdit.putString("enderecoOficina",  getIntent().getStringExtra("endereco"));
        myEdit.putString("descricaoOficina", getIntent().getStringExtra("descricao"));
        myEdit.putString("latitudeOficina",  getIntent().getStringExtra("latitude"));
        myEdit.putString("longitudeOficina", getIntent().getStringExtra("longitude"));
        myEdit.commit();
    }

    private void mostarValores(){

        binding.imgDetalheOficinaFoto.setImageBitmap(base64ToBitmap(getIntent().getStringExtra("foto")));
        binding.tvDetalheOficinaNome.setText(getIntent().getStringExtra("nome"));

        if(getIntent().getStringExtra(telefone) != null){
            binding.tvDetalheOficinaTelefone.setText(getIntent().getStringExtra(telefone));
        }else{
            binding.tvDetalheOficinaTelefone.setText("não cadastrado");
        }
        if( (getIntent().getIntExtra(avaliacao , 0) ) != 0){
            binding.rbDetalheOficinaOpniao.setRating(getIntent().getIntExtra(avaliacao , 0));
        }else{
            binding.rbDetalheOficinaOpniao.setRating(1);
        }
        binding.tvDetalheOficinaDescricaoEndereco.setText(getIntent().getStringExtra("endereco"));
        binding.tvDetalheOficinaDescricaoCurta.setText(getIntent().getStringExtra("descricao"));
        buscarInfoGPS(Double.parseDouble(getIntent().getStringExtra("latitude")) , Double.parseDouble(getIntent().getStringExtra("longitude")));

    }

    private Bitmap base64ToBitmap(String base64){
        return BitmapFactory.decodeByteArray(Base64.decode(base64 , Base64.DEFAULT), 0, Base64.decode(base64 , Base64.DEFAULT).length);
    }

    private void evontoBotoes(){
        binding.btDetalheOficinaLerQrcode.setOnClickListener(v -> {
            salvarDadosOficina();
            Toast.makeText(this, "Função Inacabada!", Toast.LENGTH_SHORT).show();
        });
    }

    // Gps
    private void mostarGoogleMaps(double latitude, double longitude) {
        binding.wvOficinaDetalhe.getSettings().setJavaScriptEnabled(true);
        binding.wvOficinaDetalhe.loadUrl("https://www.google.com.br/maps/search/?api=1&query=" + latitude + "," + longitude);
    }

    private void buscarInfoGPS(double latitude, double longitude) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ActivityDetalhesOficina.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(ActivityDetalhesOficina.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(ActivityDetalhesOficina.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }

        LocationManager mLocManager = (LocationManager) getSystemService(ActivityDetalhesOficina.this.LOCATION_SERVICE);
        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new Localizacao());
        this.mostarGoogleMaps(latitude, longitude);
    }

}