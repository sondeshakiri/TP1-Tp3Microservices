package com.example.product_.microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_.microservice.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
