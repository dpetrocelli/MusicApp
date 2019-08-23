package com.backend.wrappers;

import com.backend.entidades.Artista;
import com.backend.entidades.Comercio;
import com.backend.entidades.Promocion;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RegistrarUsuarioRequest {
	@JsonProperty("usuario") Usuario usuario;
	@JsonProperty("formulario") String formulario;
	Artista artista;
	Comercio comercio;
	
	public RegistrarUsuarioRequest(Usuario usuario, String formulario) {
		this.usuario = usuario;
		System.out.println("Formulario "+formulario);
		JsonObject form = new Gson().fromJson(formulario, JsonObject.class);
		try{
			System.out.println("probando json:"+form.get("instrumento")+" is artista: "+form.get("isArtista").getAsBoolean());
			if(form.get("isArtista").getAsBoolean()) {
				String instrumento = form.get("instrumento").getAsString();
				String genero = form.get("genero").getAsString();
				this.artista = new Artista();
				//usuario, instrumento, genero);
			}else {
				String direccion = form.get("direccion").getAsString();
				String razonsocial = form.get("razonsocial").getAsString();
				this.comercio = new Comercio(usuario, direccion, razonsocial);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
			
	}
	
	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public Comercio getComercio() {
		return comercio;
	}

	public void setComercio(Comercio comercio) {
		this.comercio = comercio;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}