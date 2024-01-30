package br.com.renancsdev.hinovaoficinas.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.databinding.FragmentIndicacaoBinding;
import br.com.renancsdev.hinovaoficinas.ui.activity.indicacao.ActivityIndicacao;

public class FragmentIndicacao extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        FragmentIndicacaoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_indicacao, container, false);

        eventoBotoes(binding);
        return binding.getRoot();
    }


    private void eventoBotoes(FragmentIndicacaoBinding binding){
        binding.btnEnviarIndicacao.setOnClickListener(v -> redirecionar(binding.getRoot().getContext()));
    }

    private void redirecionar(Context context){
        context.startActivity(new Intent(context , ActivityIndicacao.class));
    }

}