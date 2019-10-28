package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comercio")
public class Comercio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;


    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String accessToken;

    @Column(unique = true)
    private Date fechaExpiracion;


    private String direccion;

    private String razonsocial;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lugares")
    private List<Lugar> lugares;
    
    public Comercio() {
    }

    public Comercio(Usuario usuario, String code, String accessToken, Date fechaExpiracion) {
        this.usuario = usuario;
        this.code = code;
        this.accessToken = accessToken;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Comercio(Usuario usuario, String direccion, String razonsocial) {
    	this.usuario = usuario;
		this.razonsocial = razonsocial;
		this.direccion = direccion;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }
}
