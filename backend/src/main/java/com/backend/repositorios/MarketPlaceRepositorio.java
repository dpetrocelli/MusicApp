package com.backend.repositorios;

import com.backend.entidades.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketPlaceRepositorio extends JpaRepository<MarketPlace, Long> {



}
