package br.com.renancsdev.hinovaoficinas.model.oficina;

import com.google.gson.annotations.SerializedName;

public class ListaOficinasItem{

	@SerializedName("Email")
	private String email;

	@SerializedName("DescricaoCurta")
	private String descricaoCurta;

	@SerializedName("Foto")
	private String foto;

	@SerializedName("Ativo")
	private boolean ativo;

	@SerializedName("Latitude")
	private String latitude;

	@SerializedName("CodigoAssociacao")
	private int codigoAssociacao;

	@SerializedName("Longitude")
	private String longitude;

	@SerializedName("Endereco")
	private String endereco;

	@SerializedName("AvaliacaoUsuario")
	private int avaliacaoUsuario;

	@SerializedName("Telefone1")
	private String telefone1;

	@SerializedName("Descricao")
	private String descricao;

	@SerializedName("Id")
	private int id;

	@SerializedName("Nome")
	private String nome;

	@SerializedName("Telefone2")
	private Object telefone2;


	public String getEmail(){
		return email;
	}

	public String getDescricaoCurta(){
		return descricaoCurta;
	}

	public String getFoto(){
		return foto;
	}

	public boolean isAtivo(){
		return ativo;
	}

	public String getLatitude(){
		return latitude;
	}

	public int getCodigoAssociacao(){
		return codigoAssociacao;
	}

	public String getLongitude(){
		return longitude;
	}

	public String getEndereco(){
		return endereco;
	}

	public int getAvaliacaoUsuario(){
		return avaliacaoUsuario;
	}

	public String getTelefone1(){
		return telefone1;
	}

	public String getDescricao(){
		return descricao;
	}

	public int getId(){
		return id;
	}

	public String getNome(){
		return nome;
	}

	public Object getTelefone2(){
		return telefone2;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDescricaoCurta(String descricaoCurta) {
		this.descricaoCurta = descricaoCurta;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setCodigoAssociacao(int codigoAssociacao) {
		this.codigoAssociacao = codigoAssociacao;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setAvaliacaoUsuario(int avaliacaoUsuario) {
		this.avaliacaoUsuario = avaliacaoUsuario;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTelefone2(Object telefone2) {
		this.telefone2 = telefone2;
	}
}