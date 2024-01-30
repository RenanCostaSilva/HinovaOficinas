package br.com.renancsdev.hinovaoficinas.ui.activity.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityIntro2Binding;
import br.com.renancsdev.hinovaoficinas.ui.activity.login.Login;

public class ActivityIntro2 extends AppCompatActivity {

    ActivityIntro2Binding binding;
    private static final  Integer LAYOUT = R.layout.activity_intro2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setarConfig();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chamarAnimacaoTransicaoDevagar();
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

    private void chamarAnimacaoTransicaoDevagar(){
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(ActivityIntro2.this , Login.class));
            overridePendingTransition(R.anim.fade_in_slow, R.anim.fade_out_slow);
        }, 2500);

    }

}