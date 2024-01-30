package br.com.renancsdev.hinovaoficinas.model.indicacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ClasseIndicacao {
    @SerializedName("CodigoAssociacao")
    public int codigoAssociacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @SerializedName("DataCriacao")
    public Date dataCriacao;
    @SerializedName("CpfAssociado")
    public String cpfAssociado;
    @SerializedName("EmailAssociado")
    public String emailAssociado;
    @SerializedName("NomeAssociado")
    public String nomeAssociado;
    @SerializedName("TelefoneAssociado")
    public String telefoneAssociado;
    @SerializedName("PlacaVeiculoAssociado")
    public String placaVeiculoAssociado;
    @SerializedName("NomeAmigo")
    public String nomeAmigo;
    @SerializedName("TelefoneAmigo")
    public String telefoneAmigo;
    @SerializedName("EmailAmigo")
    public String emailAmigo;
    @SerializedName("Observacao")
    public String observacao;


    public int getCodigoAssociacao() {
        return codigoAssociacao;
    }

    public void setCodigoAssociacao(int codigoAssociacao) {
        this.codigoAssociacao = codigoAssociacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getCpfAssociado() {
        return cpfAssociado;
    }

    public void setCpfAssociado(String cpfAssociado) {
        this.cpfAssociado = cpfAssociado;
    }

    public String getEmailAssociado() {
        return emailAssociado;
    }

    public void setEmailAssociado(String emailAssociado) {
        this.emailAssociado = emailAssociado;
    }

    public String getNomeAssociado() {
        return nomeAssociado;
    }

    public void setNomeAssociado(String nomeAssociado) {
        this.nomeAssociado = nomeAssociado;
    }

    public String getTelefoneAssociado() {
        return telefoneAssociado;
    }

    public void setTelefoneAssociado(String telefoneAssociado) {
        this.telefoneAssociado = telefoneAssociado;
    }

    public String getPlacaVeiculoAssociado() {
        return placaVeiculoAssociado;
    }

    public void setPlacaVeiculoAssociado(String placaVeiculoAssociado) {
        this.placaVeiculoAssociado = placaVeiculoAssociado;
    }

    public String getNomeAmigo() {
        return nomeAmigo;
    }

    public void setNomeAmigo(String nomeAmigo) {
        this.nomeAmigo = nomeAmigo;
    }

    public String getTelefoneAmigo() {
        return telefoneAmigo;
    }

    public void setTelefoneAmigo(String telefoneAmigo) {
        this.telefoneAmigo = telefoneAmigo;
    }

    public String getEmailAmigo() {
        return emailAmigo;
    }

    public void setEmailAmigo(String emailAmigo) {
        this.emailAmigo = emailAmigo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
