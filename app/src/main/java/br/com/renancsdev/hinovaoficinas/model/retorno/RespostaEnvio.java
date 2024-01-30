package br.com.renancsdev.hinovaoficinas.model.retorno;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespostaEnvio {

    @JsonProperty("RetornoErro")
    public RetornoErro RetornoErro;
    @JsonProperty("Sucesso")
    public String Sucesso;


    public br.com.renancsdev.hinovaoficinas.model.retorno.RetornoErro getRetornoErro() {
        return RetornoErro;
    }

    public void setRetornoErro(br.com.renancsdev.hinovaoficinas.model.retorno.RetornoErro retornoErro) {
        RetornoErro = retornoErro;
    }

    public String getSucesso() {
        return Sucesso;
    }

    public void setSucesso(String sucesso) {
        Sucesso = sucesso;
    }
}
