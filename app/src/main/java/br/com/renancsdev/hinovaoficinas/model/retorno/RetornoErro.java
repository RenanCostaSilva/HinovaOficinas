package br.com.renancsdev.hinovaoficinas.model.retorno;

import com.google.gson.annotations.SerializedName;

public class RetornoErro {

    @SerializedName("retornoErro")
    public Object retornoErro;

    public Object getRetornoErro() {
        return retornoErro;
    }

    public void setRetornoErro(Object retornoErro) {
        this.retornoErro = retornoErro;
    }
}
