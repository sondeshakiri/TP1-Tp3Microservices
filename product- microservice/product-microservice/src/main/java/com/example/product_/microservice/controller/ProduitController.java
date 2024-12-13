package com.example.product_.microservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.product_.microservice.entities.Produit;
import com.example.product_.microservice.services.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    private static final Logger logger = LoggerFactory.getLogger(ProduitController.class);

    /**
     * Méthode de fallback utilisée par Resilience4j.
     * Elle doit respecter la signature attendue : le même nombre de paramètres que la méthode protégée, avec Throwable en dernier.
     */
    public String fallback(Exception e) {
        logger.warn("Fallback activé : {}", e.getMessage());
        return "Trop de requêtes, veuillez réessayer plus tard.";
    }

    /**
     * Récupère tous les produits avec mécanismes de résilience.
     */
    @GetMapping("/allProduct")
    @Retry(name = "myRetry", fallbackMethod = "fallback")
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback")
    @CircuitBreaker(name = "productmicroService", fallbackMethod = "fallback")
    public String getAllProducts() {
        try {
            return produitService.obtenirTousProduits().toString();
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des produits", e);
            return fallback(e); // Appel explicite à la méthode fallback du service
        }
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
