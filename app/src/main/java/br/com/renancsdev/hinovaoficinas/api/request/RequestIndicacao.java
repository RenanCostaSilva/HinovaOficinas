package br.com.renancsdev.hinovaoficinas.api.request;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.api.interfaces.Endpoints;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityIndicacaoBinding;
import br.com.renancsdev.hinovaoficinas.databinding.CustomLayoutDialogBinding;
import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseEntradaIndicacao;
import br.com.renancsdev.hinovaoficinas.model.retorno.RespostaEnvio;
import br.com.renancsdev.hinovaoficinas.ui.activity.indicacao.ActivityIndicacao;
import br.com.renancsdev.hinovaoficinas.ui.activity.intro.ActivityIntro;
import br.com.renancsdev.hinovaoficinas.ui.activity.intro.ActivityIntro2;
import br.com.renancsdev.hinovaoficinas.ui.activity.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestIndicacao {

    Endpoints endpoints;

     ActivityIndicacaoBinding binding;


    public RequestIndicacao(ActivityIndicacaoBinding binding , Endpoints endpoints) {
        this.binding = binding;
        this.endpoints = endpoints;
    }

    public void enviarIndicacao(ClasseEntradaIndicacao classeEntradaIndicacao , Context context){

        Call<RespostaEnvio> call = endpoints.sendIndicacao(classeEntradaIndicacao);
        call.enqueue(new Callback<RespostaEnvio>() {
            @Override
            public void onResponse(Call<RespostaEnvio> call, Response<RespostaEnvio> response) {

                try {
                    dialogMessage("Hinova Oficinas" , "Sucesso: "+response.body().getSucesso());
                    Log.e("App123" , "Sucesso: "+response.body().getSucesso());
                }catch (NullPointerException np){
                    dialogMessage("Hinova Oficinas" , "Erro de Retorno: "+response.body().getRetornoErro().getRetornoErro().toString());
                    Log.e("App123" , "Erro de Retorno: "+response.body().getRetornoErro().getRetornoErro().toString());
                }

            }

            @Override
            public void onFailure(Call<RespostaEnvio> call, Throwable t) {
                call.cancel();
                Log.e("App123", "Error: "+t.getMessage());
            }
        });

    }

    private void dialogMessage(String titulo , String mensagem){
        CustomLayoutDialogBinding alertCustomLayout = CustomLayoutDialogBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()));
        AlertDialog builder = new AlertDialog.Builder(binding.getRoot().getContext()).create();
        builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertCustomLayout.tvDialogTituloCustomMessage.setText(titulo);
        alertCustomLayout.tvDialogCustomMessage.setText(mensagem);

        builder.setView(alertCustomLayout.getRoot());
        builder.show();
    }

}
