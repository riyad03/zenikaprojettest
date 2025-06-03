package com.example.distributeurautomatique.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Data
public class CarteManager implements CarteService {

    double solde;

    List<Double> pieceValable = new ArrayList<>(Arrays.asList(0.5, 1.0, 2.0, 5.0, 10.0));



    public boolean ajoutPiece(double val){
        if (val <= 0) {
            System.err.println("La valeur doit être positive");
            return false;
        }

        if(pieceValable.contains(val)) {
            solde += val;
            return true;
        }else{
            System.err.println("La valeur doit appartenir à : 0,5 MAD, 1 MAD, 2 MAD, 5 MAD, 10 MAD ");
            return false;
            
        }
    }
    public double calculeAchat(double prixDemande){
        if(solde<prixDemande)
            throw new IllegalArgumentException("Le solde est insuffisant ");
        solde-=prixDemande;
        return solde;
    }



}
