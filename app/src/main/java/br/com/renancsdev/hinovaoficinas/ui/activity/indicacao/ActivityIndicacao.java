package br.com.renancsdev.hinovaoficinas.ui.activity.indicacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.api.interfaces.Endpoints;
import br.com.renancsdev.hinovaoficinas.api.request.RequestIndicacao;
import br.com.renancsdev.hinovaoficinas.api.service.ServiceBuilder;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityIndicacaoBinding;
import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseEntradaIndicacao;
import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseIndicacao;
import br.com.renancsdev.hinovaoficinas.model.retorno.RespostaEnvio;
import br.com.renancsdev.hinovaoficinas.ui.activity.main.MainActivity;
import br.com.renancsdev.hinovaoficinas.util.Mask;
import br.com.renancsdev.hinovaoficinas.util.Validar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityIndicacao extends AppCompatActivity {

    private ActivityIndicacaoBinding binding;
    private static final  Integer LAYOUT = R.layout.activity_indicacao;
    Validar validar = new Validar();

    String[] copias = {};

    Endpoints endpoints;

    RequestIndicacao requestIndicacao;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setarConfig();
        iniciarRequestIndicacao();
        esconderComponentes();
        configurarMascaras();
        sharedPreferences = binding.getRoot().getContext().getSharedPreferences("shared",MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //binding.editFormIndicacaoNome.setText("Renan");
        //binding.editFormIndicacaoTelefone.setText("(31) 3462-9010");
        //binding.editFormIndicacaoEmail.setText("qweqw@gmail.com");

        binding.btFormIndicacaoEnviar.setOnClickListener(v -> {
            if(Boolean.TRUE.equals(verificarSeCamposEmBranco())){
                if(Boolean.TRUE.equals(checkTelefoneValido(binding.editFormIndicacaoTelefone.getText().toString())) &&
                     Boolean.TRUE.equals(checkEmailValido(binding.editFormIndicacaoEmail.getText().toString()))){

                       requestIndicacao.enviarIndicacao(gerarClasseEntradaIndicacao(gerarClasseIndicacao()) , ActivityIndicacao.this);
                       limparCampos();

                }
            }
        });
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

    private void iniciarRequestIndicacao(){
        endpoints = ServiceBuilder.getClient().create(Endpoints.class);
        requestIndicacao = new RequestIndicacao(binding , endpoints);
    }

    private void esconderComponentes(){
        binding.cardFormIndicacaoNome.setVisibility(View.GONE);
        binding.cardFormIndicacaoTelefone.setVisibility(View.GONE);
        binding.cardFormIndicacaoEmail.setVisibility(View.GONE);
    }

    private void limparCampos(){
        binding.editFormIndicacaoNome.setText(null);
        binding.editFormIndicacaoTelefone.setText(null);
        binding.editFormIndicacaoEmail.setText(null);
    }

    private ClasseIndicacao gerarClasseIndicacao(){
        ClasseIndicacao indicacao = new ClasseIndicacao();
        indicacao.setCodigoAssociacao(3555);
        indicacao.setDataCriacao(dataHojeParaDate());
        indicacao.setCpfAssociado(sharedPreferences.getString("cpfAssociado" , ""));
        indicacao.setEmailAssociado(sharedPreferences.getString("emailAssociado" , ""));
        indicacao.setNomeAssociado(sharedPreferences.getString("nomeAssociado" , ""));
        indicacao.setTelefoneAssociado(sharedPreferences.getString("telefoneAssociado" , ""));
        indicacao.setPlacaVeiculoAssociado(sharedPreferences.getString("placaVeiculoAssociado" , ""));
        indicacao.setNomeAmigo(binding.editFormIndicacaoNome.getText().toString());
        indicacao.setEmailAmigo(binding.editFormIndicacaoEmail.getText().toString());
        indicacao.setTelefoneAmigo(binding.editFormIndicacaoTelefone.getText().toString());
        indicacao.setObservacao("");
        return indicacao;
    }

    private ClasseEntradaIndicacao gerarClasseEntradaIndicacao(ClasseIndicacao indicacao){

        ClasseEntradaIndicacao classeEntradaIndicacao = new ClasseEntradaIndicacao();
        classeEntradaIndicacao.setIndicacao(indicacao);
        classeEntradaIndicacao.setCopias(copias);
        classeEntradaIndicacao.setRemetente("marcelo.teste@hinovamobile.com.br");

        return classeEntradaIndicacao;
    }

    private Date dataHojeParaDate(){
        Date data;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data = formato.parse(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return data;
    }



    private Boolean verificarSeCamposEmBranco(){

        boolean check = false;
        if(Boolean.FALSE.equals(validar.seCampEdtitTextVazio(binding.editFormIndicacaoNome))  &&
                Boolean.FALSE.equals(validar.seCampEdtitTextVazio(binding.editFormIndicacaoTelefone)) &&
                Boolean.FALSE.equals(validar.seCampEdtitTextVazio(binding.editFormIndicacaoEmail))){
            check = true;
        }else{
            if(Boolean.TRUE.equals(validar.seCampEdtitTextVazio(binding.editFormIndicacaoNome))){
                mostrarMensgensErro(binding.cardFormIndicacaoNome);
                setarMensgensErro(binding.tvFormIndicacaoNome , "Nome em branco !");
            }
            else if(Boolean.TRUE.equals(validar.seCampEdtitTextVazio(binding.editFormIndicacaoTelefone))){
                mostrarMensgensErro(binding.cardFormIndicacaoTelefone);
                setarMensgensErro(binding.tvFormIndicacaoTelefone , "Telefone em branco !");
            }
            else if(Boolean.TRUE.equals(validar.seCampEdtitTextVazio(binding.editFormIndicacaoEmail))){
                mostrarMensgensErro(binding.cardFormIndicacaoEmail);
                setarMensgensErro(binding.tvFormIndicacaoEmail , "Email em branco !");
            }
        }
        return check;
    }

    private boolean checkTelefoneValido(String telefone){

        Boolean check = false;
        if(Boolean.TRUE.equals(validar.seTelefoneCompativel(telefone)) ){
            check = true;
        }else{
            mostrarMensgensErro(binding.cardFormIndicacaoTelefone);
            setarMensgensErro(binding.tvFormIndicacaoTelefone , "Telefone inválido !");
        }
        return check;
    }

    private boolean checkEmailValido(String email){

        Boolean check = false;
        if(Boolean.TRUE.equals(validar.seEmailValido(email)) ){
            check = true;
        }else{
            mostrarMensgensErro(binding.cardFormIndicacaoEmail);
            setarMensgensErro(binding.tvFormIndicacaoEmail , "Email inválido!\n\ngmail.com - compatível\nnoutlook.com - compatível\nyahoo.com - compatível\nnhinovamobile.com.br - compatível");
        }
        return check;
    }

    // exibir mensagens
    private void mostrarMensgensErro(CardView card){
        card.setVisibility(View.VISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(() -> card.setVisibility(View.GONE), 2500);
    }
    private void setarMensgensErro(TextView text , String mensagem){
        text.setText(mensagem);
    }

    // Evento de adicionar Mascara
    void configurarMascaras(){
        // CPF
        adicionarMascara(binding.editFormIndicacaoTelefone , Mask.FORMAT_TELEFONE);
    }

    // Mascara
    void adicionarMascara(EditText editText, String formato){
        editText.addTextChangedListener(Mask.mask(editText, formato));
    }

}