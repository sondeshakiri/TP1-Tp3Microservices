package com.example.product_.microservice.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.product_.microservice.entities.Produit;
import com.example.product_.microservice.services.ProduitService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    public String fallback(Exception e) {
        return "Trop de requêtes, veuillez réessayer plus tard.";
    }


    @GetMapping("/all/Product")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "productmicroService", fallbackMethod = "fallback")
    public List<Produit> getAllProducts() {
        return produitService.obtenirTousProduits(); // Appel au service pour récupérer les produits
    }

    @PostMapping
    public Produit creerProduit(@RequestBody Produit produit) {
        return produitService.creerProduit(produit);
    }

    @GetMapping("/all")
    public List<Produit> obtenirTousProduits() {
        return produitService.obtenirTousProduits();
    }

    @GetMapping("/Get/{id}")
    public Produit obtenirProduitParId(@PathVariable Long id) {
        return produitService.obtenirProduitParId(id);
    }

    @PutMapping("/Update/{id}")
    public Produit modifierProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return produitService.modifierProduit(id, produit);
    }

    @DeleteMapping("/Delete/{id}")
    public void supprimerProduit(@PathVariable Long id) {
        produitService.supprimerProduit(id);
    }

}

