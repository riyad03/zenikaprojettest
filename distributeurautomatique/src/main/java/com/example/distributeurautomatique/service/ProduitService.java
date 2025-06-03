package com.example.distributeurautomatique.service;

import com.example.distributeurautomatique.dao.model.Produit;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProduitService {




    Produit findById(Long id);

    public List<Produit> findAll(double solde,double TotalPrix);
}
