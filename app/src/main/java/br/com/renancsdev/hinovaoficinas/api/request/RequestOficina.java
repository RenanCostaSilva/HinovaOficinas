package br.com.renancsdev.hinovaoficinas.api.request;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import br.com.renancsdev.hinovaoficinas.adpter.recycler.RecyclerOficina;
import br.com.renancsdev.hinovaoficinas.api.interfaces.Endpoints;
import br.com.renancsdev.hinovaoficinas.databinding.FragmentOficinasBinding;
import br.com.renancsdev.hinovaoficinas.model.oficina.ListaOficinasItem;
import br.com.renancsdev.hinovaoficinas.model.oficina.ResulListOficinas;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestOficina {

    Endpoints endpoints;

    public RequestOficina(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public void buscarListaOficinas(FragmentOficinasBinding binding , Context context){

        Call<ResulListOficinas> call = endpoints.getOficinas();
        call.enqueue(new Callback<ResulListOficinas>() {
            @Override
            public void onResponse(Call<ResulListOficinas> call, Response<ResulListOficinas> response) {
                iniciarAdapter(binding , verificarListaResposta(response) , context);
            }

            @Override
            public void onFailure(Call<ResulListOficinas> call, Throwable t) {
                call.cancel();
                Log.e("App123", "Error: "+t.getMessage());
            }
        });
    }

    // verificar lista de Resposta
    private List<ListaOficinasItem> verificarListaResposta(Response<ResulListOficinas> response){
        List<ListaOficinasItem> listaOficinas = new ArrayList<>();
        for (ListaOficinasItem lista1 : response.body().getListaOficinas()) {
            if(Boolean.TRUE.equals(lista1.isAtivo())){
                listaOficinas.add(lista1);
            }
        }
        return  listaOficinas;
    }

    private void iniciarAdapter(FragmentOficinasBinding binding , List<ListaOficinasItem> lista , Context context){
        RecyclerOficina adapter = new RecyclerOficina( lista , context);
        binding.rvOficina.setAdapter(adapter);
        binding.rvOficina.setLayoutManager(new LinearLayoutManager(context));
    }


}
