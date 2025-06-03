package com.example.distributeurautomatique.dao.repository;

import com.example.distributeurautomatique.dao.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit,Long>{
}
