package com.example.distributeurautomatique.service;

import com.example.distributeurautomatique.dao.model.Produit;
import com.example.distributeurautomatique.dao.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProduitServiceTest {

    @MockBean
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

    @Test
    void testTrouver() {
        Produit p = Produit.builder().id(1L).name("produit 1").price(10).build();
        when(produitRepository.findById(1L)).thenReturn(Optional.of(p));
        Produit foundProduct = produitService.findById(1L);

        assertEquals(p.getId(), foundProduct.getId());
        assertEquals(p.getName(), foundProduct.getName());
        assertEquals(p.getPrice(), foundProduct.getPrice());
    }

    @Test
    void testTrouverTout() {
        Produit p1 = Produit.builder().id(1L).name("produit 1").price(10).build();
        Produit p2 = Produit.builder().id(2L).name("produit 2").price(20).build();
        Produit p3 = Produit.builder().id(3L).name("produit 3").price(30).build();
        List<Produit> mockProduits = Arrays.asList(p1, p2, p3);
        when(produitRepository.findAll()).thenReturn(mockProduits);
        List<Produit> produits = produitService.findAll(25);
        assertEquals(3, produits.size());
        assertEquals(true, produits.get(0).isPossibleAchat());
        assertEquals(true, produits.get(1).isPossibleAchat());
        assertEquals(false, produits.get(2).isPossibleAchat());
    }
}
