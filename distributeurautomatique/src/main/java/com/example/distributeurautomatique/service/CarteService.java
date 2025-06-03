package com.example.distributeurautomatique.service;


public interface CarteService{
    double getSolde();
    boolean ajoutPiece(double val);
    void setSolde(double solde);
    double calculeAchat(double prixDemande);

}
