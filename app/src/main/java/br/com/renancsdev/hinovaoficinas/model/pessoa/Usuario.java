package br.com.renancsdev.hinovaoficinas.model.pessoa;

import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseIndicacao;

public class Usuario {

    Integer codigoAssociacao = 0;
    String cpfAssociado = "";
    String emailAssociado = "";
    String nomeAssociado = "";
    String telefoneAssociado = "";
    String placaVeiculoAssociado = "";
    String senha = "";


    public Integer getCodigoAssociacao() {
        return codigoAssociacao;
    }

    public void setCodigoAssociacao(Integer codigoAssociacao) {
        this.codigoAssociacao = codigoAssociacao;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
