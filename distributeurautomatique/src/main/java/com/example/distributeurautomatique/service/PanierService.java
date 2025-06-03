package com.example.distributeurautomatique.service;

import com.example.distributeurautomatique.dao.model.Panier;
import com.example.distributeurautomatique.dao.model.Produit;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PanierService {


    boolean addToBasket(Produit p,double solde);
    double getSommePrixPanier();
    boolean acheter(CarteService carteService);
    void annuler(CarteService carteService);
    List<Produit> getPanier();

    void setSommePrixPanier(double v);
    boolean removePanier(Long id);
}
