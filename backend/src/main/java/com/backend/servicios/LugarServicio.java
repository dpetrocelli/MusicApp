package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.repositorios.LugarRepositorio;
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
public class LugarServicio {

    @Autowired
    LugarRepositorio LugarRepositorio;
    @Autowired
    ZonaGeograficaServicio zonaServicio;
    @Autowired
    LugarRepositorio lugarRepositorio;

    public List<Lugar> obtenerTodos() {
        List<Lugar> lista = LugarRepositorio.findAll();
        return lista;
    }

    public Optional<Lugar> obtenerPorId(Long id) {
        return LugarRepositorio.findById(id);
    }

    public Optional<Lugar> obtenerPorNombre(String ni) {
        return LugarRepositorio.findByNombre(ni);
    }

    public void guardar(Lugar Lugar) {
        LugarRepositorio.save(Lugar);
    }

    public void borrar(Long id) {
        LugarRepositorio.deleteById(id);
    }

    public boolean existePorNombre(String ni) {
        return LugarRepositorio.existsByNombre(ni);
    }

    public boolean existePorId(Long id) {
        return LugarRepositorio.existsById(id);
    }

    public List<Lugar> buscarLike(String busqueda, String zona) {
        System.out.println(" HOLA ");
        Zona zonaObjeto = null;
        GeneroMusical generoMusical = null;
        try{
            if (this.zonaServicio.existePorId(Long.parseLong(zona))){
                zonaObjeto = this.zonaServicio.obtenerPorId(Long.parseLong(zona)).get();
            }
        }catch (Exception e){

        }

        List<Lugar> test = this.findByLikeCriteria(busqueda, zonaObjeto);



        return test;
    }

    private List<Lugar> findByLikeCriteria(String busqueda, Zona zonaObjeto) {
        return this.lugarRepositorio.findAll(new Specification<Lugar>() {
            @Override
            public Predicate toPredicate(Root<Lugar> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(busqueda.length()>0) {
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.like(root.get("nombre"), "%" + busqueda + "%"))

                    );
                }



                if(zonaObjeto!=null) {

                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("zona"), zonaObjeto))

                    );
                }


                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

}
