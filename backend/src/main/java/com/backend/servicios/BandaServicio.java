package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.repositorios.BandaRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BandaServicio {

    @Autowired
    BandaRepositorio bandaRepositorio;
    @Autowired
    ZonaGeograficaServicio zonaServicio;
    @Autowired
    GeneroMusicalServicio generoMusicalServicio;

    public List<Banda> obtenerTodos() {
        List<Banda> lista = this.bandaRepositorio.findAll();
        return lista;
    }

    public ArrayList<Banda> obtenerBandasDeLasQueSoyAdmin (Artista artista){


            return this.bandaRepositorio.findAllByartistaLider(artista).get();


    }


    public boolean verificarSiSoyAdminDeBanda(Artista artista) {
        return this.bandaRepositorio.existsByArtistaLider(artista);
    }

    public Banda obtenerBandaPorNombre(String nombreDestino) {
        return this.bandaRepositorio.findByNombre(nombreDestino).get();
    }

    public ArrayList<Banda> buscarLike(String busqueda, String zona, String genero) {


        Zona zonaObjeto = null;
        GeneroMusical generoMusical = null;
        try{
            if (this.zonaServicio.existePorId(Long.parseLong(zona))){
                zonaObjeto = this.zonaServicio.obtenerPorId(Long.parseLong(zona)).get();
            }
        }catch (Exception e){

        }
        try {
            if (this.generoMusicalServicio.existePorId(Long.parseLong(genero))) {
                generoMusical = this.generoMusicalServicio.obtenerPorId(Long.parseLong(genero)).get();
            }

        }catch (Exception e){

        }
        List<Banda> test = this.findByLikeCriteria(busqueda, generoMusical, zonaObjeto);



        return new ArrayList<Banda>(test);






    }


    public List<Banda> findByLikeCriteria(String busqueda, GeneroMusical generoMusical, Zona zona){
        return this.bandaRepositorio.findAll(new Specification<Banda>() {
            @Override
            public Predicate toPredicate(Root<Banda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
    public List<Artista> obtenerTodosArtistasDeBanda(Set<Banda> banda) {
       return this.bandaRepositorio.obtenerTodosArtistasDeBanda(banda);
    }
}
