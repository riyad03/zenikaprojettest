package com.example.distributeurautomatique.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarteServiceTest {

    @Autowired
    private CarteService carteService;



    @Test
    void testAjoutPieceValid() {

        assertTrue(carteService.ajoutPiece(1.0));
        assertEquals(1.0, carteService.getSolde());
    }

    @Test
    void testAjoutPieceInvalid() {

        assertFalse(carteService.ajoutPiece(-1));
        assertFalse(carteService.ajoutPiece(3.5));
        assertEquals(0.0, carteService.getSolde());
    }

    @Test
    void testMultipleCoins() {

        assertTrue(carteService.ajoutPiece(0.5));
        assertTrue(carteService.ajoutPiece(2.0));
        assertTrue(carteService.ajoutPiece(5.0));
        assertEquals(7.5, carteService.getSolde());
    }


    @Test
    void testCalculeAchat() {

        carteService.ajoutPiece(5.0);
        double prixProduit = 3.5;
        double soldeRestant = carteService.calculeAchat(prixProduit);
        assertEquals(1.5, soldeRestant);
    }
}
