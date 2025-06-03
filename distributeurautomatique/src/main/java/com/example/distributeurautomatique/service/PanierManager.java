package com.example.distributeurautomatique.service;

import com.example.distributeurautomatique.dao.model.Panier;
import com.example.distributeurautomatique.dao.model.Produit;
import com.example.distributeurautomatique.dao.repository.PanierRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class PanierManager implements PanierService{


    List<Produit> panier=new ArrayList<>();
    double sommePrixPanier=0.0;

    public boolean addToBasket(Produit p,double solde){
        double somme=sommePrixPanier;
        somme+=p.getPrice();
        if(somme<=solde) {
            panier.add(p);
            sommePrixPanier=somme;
            return true;
        }else{
            System.err.println("Solde insuffisant");
            return false;
        }



    }
    public boolean acheter(CarteService carteService){
        if(sommePrixPanier<=carteService.getSolde()) {
            System.out.println("acheté");
            panier.clear();
            double reste = carteService.calculeAchat(sommePrixPanier);
            System.out.println("le reste: " + reste);
            sommePrixPanier=0;
            return true;
        }else{
            System.err.println("Solde insuffisant");
            return false;
        }

    }

    public boolean removePanier(Long id){
        for(Produit p:panier){
            if(p.getId().equals(id)){
                panier.remove(p);
                sommePrixPanier-=p.getPrice();
                return true;
            }
        }
        return false;
    }

    public void annuler(CarteService carteService){
        panier.clear();
        sommePrixPanier=0;
    }



}
