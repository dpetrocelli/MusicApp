package com.backend.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String pwd;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotNull
    @ManyToMany
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    /*
    @OneToOne(mappedBy = "usuario")
    private Comercio comercio;
    */
    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public void  addRol(Rol rol) {
        this.roles.add(rol);
    }

    public void removeRol(Rol rol) {
        this.roles.remove(rol);
    }


    public Usuario() {
    }


    //Logger log = LoggerFactory.getLogger(Usuario.class);

    public String getUsername() {
        return username;
    }


    public boolean validatePassword (String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("ESTAMOS EN VALIDATE PASSWORD: Password plana "+password + " - //// password bend- "+ this.getpwd());
        if (encoder.matches(password, this.getpwd())) return true; else return false;

    }

    public Usuario(@NotBlank String username, @NotBlank String pwd, @NotBlank String email, String usertype) {
        this.username = username;
        this.pwd = pwd;
        this.email = email;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String pass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.pwd = passwordEncoder.encode(pass);
    }

    public void encodePassword (){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.pwd = passwordEncoder.encode(this.pwd);
    }

    /*
    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
*/
    public String getpwd() {
        return pwd;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString (){
        return "Usuario: "+this.getUsername()+"Mail: "+this.getEmail();
    }

	public String rolesToString() {
		String roles = "";
		for(Rol r : this.getRoles()) {
			roles +=r.getNombre()+' ';
		}
		return roles;
	}
}
