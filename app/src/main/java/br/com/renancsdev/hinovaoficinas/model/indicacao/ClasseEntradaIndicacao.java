package br.com.renancsdev.hinovaoficinas.model.indicacao;

import com.google.gson.annotations.SerializedName;

public class ClasseEntradaIndicacao {

    @SerializedName("Indicacao")
    public ClasseIndicacao indicacao;

    @SerializedName("Remetente")
    public String remetente;

    @SerializedName("Copias")
    public String[] copias;


    public ClasseIndicacao getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(ClasseIndicacao indicacao) {
        this.indicacao = indicacao;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String[] getCopias() {
        return copias;
    }

    public void setCopias(String[] copias) {
        this.copias = copias;
    }
}
