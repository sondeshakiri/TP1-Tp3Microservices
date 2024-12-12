package com.example.product_.microservice.services;


import java.util.List;

import com.example.product_.microservice.entities.Produit;

public interface ProduitService {
    Produit creerProduit(Produit produit);
    List<Produit> obtenirTousProduits();
    Produit obtenirProduitParId(Long id);
    Produit modifierProduit(Long id, Produit produit);
    void supprimerProduit(Long id);
}
