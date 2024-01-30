package br.com.renancsdev.hinovaoficinas.ui.activity.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityIntroBinding;

public class ActivityIntro extends AppCompatActivity {

    ActivityIntroBinding binding;
    private static final  Integer LAYOUT = R.layout.activity_intro;


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
            startActivity(new Intent(ActivityIntro.this , ActivityIntro2.class));
            overridePendingTransition(R.anim.fade_in_slow, R.anim.fade_out_slow);
        }, 2500);
    }

}