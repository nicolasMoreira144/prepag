package com.wex.prepag.dtos;

import java.time.LocalDate;

public class VendaDto {
	
	private Long id;
	private Long numero;
	private LocalDate validade;
	private String cvv;
	private String nomeEstabelecimento;
	private Double valor;
	private String senha;
	
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public LocalDate getValidade() {
		return validade;
	}
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getNomeEstabelecimento() {
		return nomeEstabelecimento;
	}
	public void setNomeEstabelecimento(String nomeEstabelecimento) {
		this.nomeEstabelecimento = nomeEstabelecimento;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VendaDto [id=" + id + ", numero=" + numero + ", validade=" + validade + ", cvv=" + cvv
				+ ", nomeEstabelecimento=" + nomeEstabelecimento + ", valor=" + valor + ", senha=" + senha + "]";
	}
}
