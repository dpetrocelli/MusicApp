package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.repositorios.ArtistaRepositorio;
import com.backend.repositorios.BiografiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ArtistaServicio {

    @Autowired
    ArtistaRepositorio artistaRepositorio;

    @Autowired
    ZonaGeograficaServicio zonaServicio;
    @Autowired
    GeneroMusicalServicio generoMusicalServicio;

    @Autowired
    InstrumentoServicio instrumentoServicio;

    public Artista obtener (Long id){
        return this.artistaRepositorio.findById(id).get();
    }

    public Artista obtenerPorUsuario (Usuario usuario) {
        if (this.artistaRepositorio.existsByUsuario(usuario)){
            return this.artistaRepositorio.findByUsuario(usuario).get();

        }else{
            return new Artista();
        }

    }

    public Artista obtenerPorNombre (String nombre) {return this.artistaRepositorio.findByNombre(nombre);}

    public List<Artista> obtenerTodos (){
        return this.artistaRepositorio.findAll();
    }
    public boolean guardar (Artista artista) {
        this.artistaRepositorio.save(artista);
        return true;
    }


    public ArrayList<Artista> buscarLike(String busqueda, String zona, String instrumento, String genero) {

        System.out.println(" HOLA ");
        Zona zonaObjeto = null;
        GeneroMusical generoMusical = null;
        try{
            if (this.zonaServicio.existePorId(Long.parseLong(zona))){
                zonaObjeto = this.zonaServicio.obtenerPorId(Long.parseLong(zona)).get();
            }
        }catch (Exception e){

        }
        try{

            if (this.generoMusicalServicio.existePorId(Long.parseLong(genero))){
                generoMusical = this.generoMusicalServicio.obtenerPorId(Long.parseLong(genero)).get();
            }

        }catch (Exception e){

        }
        List<Artista> test = this.findByLikeCriteria(busqueda, generoMusical, zonaObjeto);
        ArrayList<Artista> resultado=new ArrayList<Artista>();
        try{
            if (this.instrumentoServicio.existePorId(Long.parseLong(instrumento))){
                Instrumento instrumentoObjeto = this.instrumentoServicio.obtenerPorId(Long.parseLong(instrumento)).get();


                for (Artista art: test
                ) {
                    Set<Instrumento> instrumentos = art.getInstrumento();
                    for (Instrumento ins: instrumentos
                    ) {
                        if (ins.getId().equals(instrumentoObjeto.getId())){
                            resultado.add(art);
                        }

                    }

                }

            }
        }catch (Exception e){
            resultado = new ArrayList<Artista>(test);
        }

        return resultado;






    }

    public List<Artista> findByLikeCriteria(String busqueda, GeneroMusical generoMusical, Zona zona){
        return this.artistaRepositorio.findAll(new Specification<Artista>() {
            @Override
            public Predicate toPredicate(Root<Artista> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(busqueda.length()>0) {
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.like(root.get("nombre"), "%" + busqueda + "%"))

                    );
                }

                if(generoMusical!=null) {
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("generoMusical"), generoMusical))

                    );
                }

                if(zona!=null) {

                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("zona"), zona))

                    );
                }


                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }


}
