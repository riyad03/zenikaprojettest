package com.example.distributeurautomatique.controller;

import com.example.distributeurautomatique.dao.model.Panier;
import com.example.distributeurautomatique.dao.model.Produit;
import com.example.distributeurautomatique.service.CarteService;
import com.example.distributeurautomatique.service.PanierService;
import com.example.distributeurautomatique.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class VenteController {



    @Autowired
    private ProduitService produitService;
    
    @Autowired
    private CarteService carteService;

    @Autowired
    private PanierService panierService;



    // Keep the original endpoint for listing all products without filtering
    @GetMapping("/produits")
    public List<Produit> listerTousProduits() {

        return produitService.findAll(carteService.getSolde(),panierService.getSommePrixPanier());
    }

    @GetMapping("/prixtotale")
    public ResponseEntity<Double> getTotal(){
        return ResponseEntity.ok(panierService.getSommePrixPanier());
    }

    @GetMapping("/panier")
    public List<Produit> listerProduitPanier(){
        return panierService.getPanier();
    }
    @DeleteMapping("/retirer/{id}")
    public ResponseEntity<Boolean> retirerProduitPanier(@PathVariable Long id){
        try {
            panierService.removePanier(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/ajoutpanier/{id}")
    public ResponseEntity<Boolean> addToBasket(@PathVariable Long id){
        Produit p=produitService.findById(id);
        return ResponseEntity.ok( panierService.addToBasket(p,carteService.getSolde()));

    }
    @PostMapping("/ajoutpiece/{valeur}")
    public ResponseEntity<Boolean> ajoutPiece(@PathVariable double valeur){
        try {
            System.err.println(carteService.getSolde());
           return ResponseEntity.ok(carteService.ajoutPiece(valeur));
        }catch (Exception e){
            System.err.println("Erreur d ajout: " + valeur+" exception: "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }


    }
    @GetMapping("/recupiece")
    public  ResponseEntity<Double> recuperer(){

        return ResponseEntity.ok(carteService.getSolde());
    }

    @PostMapping("/acheter")
    public  ResponseEntity<Boolean> acheter(){
        try {
            panierService.acheter(carteService);
            return ResponseEntity.ok(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @DeleteMapping("/annuler")
    public ResponseEntity<Boolean> delete(){
        try {
            panierService.annuler(carteService);
            return ResponseEntity.ok(true);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return ResponseEntity.ok(false);
        }

    }


    @GetMapping("/test")
    public String test(){
        return "test " +panierService.getPanier().size()+" "+panierService.getSommePrixPanier();
    }
}
