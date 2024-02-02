package br.com.renancsdev.hinovaoficinas.ui.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.com.renancsdev.hinovaoficinas.R;
import br.com.renancsdev.hinovaoficinas.databinding.ActivityLoginBinding;
import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseEntradaIndicacao;
import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseIndicacao;
import br.com.renancsdev.hinovaoficinas.model.pessoa.Usuario;
import br.com.renancsdev.hinovaoficinas.ui.activity.main.MainActivity;
import br.com.renancsdev.hinovaoficinas.util.Mask;
import br.com.renancsdev.hinovaoficinas.util.ValidadorCPF;
import br.com.renancsdev.hinovaoficinas.util.Validar;

public class Login extends AppCompatActivity {

    // cpf gerado de teste = 723.204.450-05
    ActivityLoginBinding binding;
    private static final  Integer LAYOUT = R.layout.activity_login;

    // Storing data into SharedPreferences
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor myEdit;

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setarConfig();
        configurarMascaras();
        esconderComponentes();
        sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //binding.editLoginLogin.setText("78885983073");
        //binding.editLoginSenha.setText("Aaxvt5qweasd");
        binding.button.setOnClickListener(v ->validarAcesso());

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

    // esconder campos
    private void esconderComponentes(){
        binding.cardLoginErroLogin.setVisibility(View.GONE);
        binding.cardLoginErroSenha.setVisibility(View.GONE);
    }

    private Usuario gerarUsuario(){
        Usuario user = new Usuario();
        user.setCodigoAssociacao(601);
        user.setCpfAssociado("78885983073");
        user.setEmailAssociado("marcelo.teste@hinovamobile.com.br");
        user.setNomeAssociado("Marcelo Teste");
        user.setTelefoneAssociado("(31) 3462-9020");
        user.setPlacaVeiculoAssociado("AAB-9B99");
        user.setSenha("Aa1qweasd");
        return user;
    }

    // validar campos
    // Logar com login = 78885983073 e senha = Aaxvt5qweasd
    private void validarAcesso(){
        if(Boolean.TRUE.equals(seCamposNaoSaoBrancosOuNulos())){
            if(Boolean.TRUE.equals(seLoginCPFValido())){
                if(Boolean.TRUE.equals(seSenhaComCPFValida())){ }
                else{
                    if(Boolean.TRUE.equals(seSenhaSemCPFValida(binding.editLoginSenha.getText().toString()))){

                        redirecionarParaMain();
                        salvarDadosUsuario();

                    }
                }
            }
        }
    }

    private void  salvarDadosUsuario(){
        user = gerarUsuario();
        myEdit = sharedPreferences.edit();
        myEdit.putInt("codigoAssociacao", user.getCodigoAssociacao());
        myEdit.putString("cpfAssociado", user.getCpfAssociado());
        myEdit.putString("emailAssociado", user.getEmailAssociado());
        myEdit.putString("nomeAssociado", user.getNomeAssociado());
        myEdit.putString("telefoneAssociado", user.getTelefoneAssociado());
        myEdit.putString("placaVeiculoAssociado", user.getPlacaVeiculoAssociado());
        myEdit.putString("senha", user.getSenha());
        myEdit.commit();
    }

    private Boolean logarComDadosSalvos(String login , String senha){
        return login.equals("78885983073") && senha.equals("78885983073");
    }

    private Boolean seLoginCPFValido(){

        Boolean check = false;
        if(checarCPFDigitadoValido(binding.editLoginLogin.getText().toString())){
            check = true;
        }else{
            mostrarMensgensErro(binding.cardLoginErroLogin);
            setarMensgensErro(binding.tvErroLogin , "Login CPF inválido!");
        }
        return check;
    }

    private Boolean seSenhaComCPFValida(){

        Boolean check = false;
        if(checarCPFDigitadoValido(binding.editLoginSenha.getText().toString())){
            check = true;
        }
        return check;
    }

    private Boolean seSenhaSemCPFValida(String senha){

        Integer caracterMinusculo = 0;
        Integer caracterMaiusculo = 0;
        Integer caracterDigito = 0;
        Boolean check = false;
        for (char c : senha.toCharArray()) {

            if(Character.isLowerCase(c)){
                caracterMinusculo += 1;
            }
            if(Character.isUpperCase(c)){
                caracterMaiusculo += 1;
            }
            if(Character.isDigit(c)){
                caracterDigito += 1;
            }

        }
        if(caracterMinusculo >= 1 && caracterMaiusculo >= 1 && caracterDigito >= 1){
            check = true;
        }else{
            limparCampoSenha();
            mostrarMensgensErro(binding.cardLoginErroSenha);
            setarMensgensErro(binding.tvErroSenha , "Senha inválida.\nDeve conter: 1 - Letra Maiúscula\nDeve conter: 1 - Letra Minúscula\nDeve conter: 1 - Digito Numérico");
        }

        return check;
    }

    private boolean seCamposNaoSaoBrancosOuNulos(){
        Validar validar = new Validar();
        Boolean check = false;
        if(Boolean.FALSE.equals(validar.seCampEdtitTextVazio(binding.editLoginLogin)) && Boolean.FALSE.equals(validar.seCampEdtitTextVazio(binding.editLoginSenha))){
            check = true;
        }else{
            if(Boolean.TRUE.equals(validar.seCampEdtitTextVazio(binding.editLoginLogin))){
                mostrarMensgensErro(binding.cardLoginErroLogin);
                setarMensgensErro(binding.tvErroLogin , "Login em branco !");
            }
            else if(Boolean.TRUE.equals(validar.seCampEdtitTextVazio(binding.editLoginSenha))){
                mostrarMensgensErro(binding.cardLoginErroSenha);
                setarMensgensErro(binding.tvErroSenha , "Senha em branco !");
            }
        }
        return check;
    }

    // cpf na senha
    private boolean checarCPFDigitadoValido(String senha){
        ValidadorCPF validar = new ValidadorCPF();
        return seCampoTemTamanhoCPF(senha) && validar.seCPFValido(senha);
    }
    private boolean seCampoTemTamanhoCPF(String senha){
        return senha.length() == 11;
    }

    // exibir mensagens
    private void mostrarMensgensErro(CardView card){
        card.setVisibility(View.VISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(() -> card.setVisibility(View.GONE), 2500);
    }
    private void setarMensgensErro(TextView text , String mensagem){
        text.setText(mensagem);
    }

    private void limparCampoSenha(){
        binding.editLoginSenha.setText(null);
    }

    // Evento de adicionar Mascara
    void configurarMascaras(){
        // CPF
        adicionarMascara(binding.editLoginLogin , Mask.FORMAT_CPF);
    }

    // Mascara
    void adicionarMascara(EditText editText, String formato){
        editText.addTextChangedListener(Mask.mask(editText, formato));
    }

    private void redirecionarParaMain(){
        startActivity(new Intent(Login.this , MainActivity.class));
    }

}