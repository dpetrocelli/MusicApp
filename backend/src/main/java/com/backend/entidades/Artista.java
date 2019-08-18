package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    private String instrumento;
    
    private String genero;

    public Artista() {
    }

    public Artista(Usuario usuario, String instrumento, String genero) {
        this.usuario = usuario;
        this.genero = genero;
        this.instrumento = instrumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

}
