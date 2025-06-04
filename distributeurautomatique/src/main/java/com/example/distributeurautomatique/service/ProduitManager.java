package com.example.distributeurautomatique.service;

import com.example.distributeurautomatique.dao.model.Produit;
import com.example.distributeurautomatique.dao.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitManager implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;



    @Override
    public List<Produit> findAll(double solde,double TotalPrix) {
        List<Produit> produits= produitRepository.findAll();

        produits.forEach(p -> {
            if (solde >= TotalPrix+p.getPrice()) {
                p.setPossibleAchat(true);
            } else {
                p.setPossibleAchat(false);
            }
        });
        return produits;
    }


    @Override
    public Produit findById(Long id) {
        return produitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }
}
