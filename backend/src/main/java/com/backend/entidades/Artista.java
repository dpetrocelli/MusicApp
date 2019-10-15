package com.backend.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artista")
public class Artista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    private String nombre;
    private String apellido;
    private String nickname;
    private int documentoIdentidad;
    private Date fechaNacimiento;
    private String genero;


    @ManyToMany
    @JoinTable(name = "banda_artista", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "banda_id"))
    private Set<Banda> banda = new HashSet<>();

    private String generoMusical;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "instrumento_artista", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "instrumento_id"))
    private Set<Instrumento> instrumento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "zona_artista", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "zona_id"))
    private Set<Zona> zona = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puntuacion")
    private List<PuntuacionArtista> puntuacionesRealizadas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puntuacion")
    private List<PuntuacionArtista> puntuacionesRecibidas;


    public Artista() {
    }

    public Artista(Usuario usuario, String nombre, String apellido, String nickname, int documentoIdentidad, Date fechaNacimiento, String genero, Set<Banda> banda, String generoMusical, Set<Instrumento> instrumento, List<PuntuacionArtista> puntuacionesRealizadas, List<PuntuacionArtista> puntuacionesRecibidas) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.documentoIdentidad = documentoIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.banda = banda;
        this.generoMusical = generoMusical;
        this.instrumento = instrumento;
        this.puntuacionesRealizadas = puntuacionesRealizadas;
        this.puntuacionesRecibidas = puntuacionesRecibidas;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(int documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Set<Banda> getBanda() {
        return banda;
    }

    public void setBanda(Set<Banda> banda) {
        this.banda = banda;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public Set<Instrumento> getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Set<Instrumento> instrumento) {
        this.instrumento = instrumento;
    }

    public List<PuntuacionArtista> getPuntuacionesRealizadas() {
        return puntuacionesRealizadas;
    }

    public void setPuntuacionesRealizadas(List<PuntuacionArtista> puntuacionesRealizadas) {
        this.puntuacionesRealizadas = puntuacionesRealizadas;
    }

    public List<PuntuacionArtista> getPuntuacionesRecibidas() {
        return puntuacionesRecibidas;
    }

    public void setPuntuacionesRecibidas(List<PuntuacionArtista> puntuacionesRecibidas) {
        this.puntuacionesRecibidas = puntuacionesRecibidas;
    }
    public void addBanda (Banda banda){
        this.banda.add(banda);
    }

    public Set<Zona> getZona() {
        return zona;
    }

    public void addZona (Zona zona){
        this.zona.add(zona);
    }
    public void setZona(Set<Zona> zona) {
        this.zona = zona;
    }

    public void removeBanda (Banda banda){
        this.banda.remove(banda);
    }
}
