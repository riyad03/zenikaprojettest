package com.example.distributeurautomatique.service;

import com.example.distributeurautomatique.dao.model.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PanierServiceTest {



    @Autowired
    private CarteService carteService;

    @Autowired
    private PanierService panierService;

    @Test
    void testAddToBasket_AvecSolde() {
        Produit p=new Produit(0L,"produit 1",10,false);

        double solde=15;

        carteService.setSolde(solde);

        panierService.addToBasket(p,carteService.getSolde());

        assertEquals(panierService.getSommePrixPanier(),10.0);
        assertTrue(panierService.getPanier().contains(p));

    }
    @Test
    void testAddToBasket_SansSolde() {
        Produit p=new Produit(0L,"produit 1",10,false);

        double solde=5;

        carteService.setSolde(solde);

        panierService.addToBasket(p,carteService.getSolde());

        assertNotEquals(panierService.getSommePrixPanier(),10.0);
        assertFalse(panierService.getPanier().contains(p));

    }

    @Test
    void testAchat_AvecSolde() {
        Produit p=new Produit(0L,"produit 1",10,false);

        double solde=15;

        carteService.setSolde(solde);

        panierService.addToBasket(p,carteService.getSolde());

        panierService.acheter(carteService);

        assertEquals(carteService.getSolde(),5);
        assertFalse(panierService.getPanier().contains(p));


    }
    @Test
    void testAchat_SansSolde() {
        Produit p=new Produit(0L,"produit 1",10,false);

        double solde=5;
        carteService.setSolde(solde);
        panierService.getPanier().add(p);
        panierService.setSommePrixPanier(10.0);
        assertFalse(panierService.acheter(carteService));
        assertTrue(panierService.getPanier().contains(p));


    }
    @Test
    void testAnnulation() {
        Produit p=new Produit(0L,"produit 1",10,false);

        double solde=15;
        carteService.setSolde(solde);

        panierService.addToBasket(p,carteService.getSolde());
        panierService.annuler(carteService);
        assertEquals(carteService.getSolde(),15);
        assertEquals(panierService.getSommePrixPanier(),0);
        assertFalse(panierService.getPanier().contains(p));


    }


}
