package com.loja.virtual.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Cupom de Desconto")
public class CupomDesconto {
	
private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	
	private long id;
	private String numeroCupom;
	private Double valorDesconto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNumeroCupom() {
		return numeroCupom;
	}
	public void setNumeroCupom(String numeroCupom) {
		this.numeroCupom = numeroCupom;
	}
	public Double getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}	

}
