package com.backend.wrappers;

import com.backend.entidades.Promocion;
import com.backend.recursos.LoginDatos;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PromocionRequest {
	@JsonProperty("login")
	LoginDatos loginDatos;
	@JsonProperty("promocion")
	Promocion promocion;
	
	public PromocionRequest(LoginDatos loginDatos, Promocion promocion) {
		this.loginDatos = loginDatos;
		this.promocion = promocion;
	}
	
	public LoginDatos getLoginDatos() {
		return loginDatos;
	}
	public void setLoginDatos(LoginDatos loginDatos) {
		this.loginDatos = loginDatos;
	}
	public Promocion getPromocion() {
		return promocion;
	}
	public void setArticulo(Promocion articulo) {
		this.promocion = articulo;
	}
	@Override
	public String toString() {
		return "LoginDatos: "+this.loginDatos.getNombreUsuario()+"\nArticulo: "+this.promocion.getTitulo();
	}
}
