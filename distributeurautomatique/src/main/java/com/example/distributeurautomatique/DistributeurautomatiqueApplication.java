package com.example.distributeurautomatique;

import com.example.distributeurautomatique.dao.model.Produit;
import com.example.distributeurautomatique.dao.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DistributeurautomatiqueApplication {

	public static void main(String[] args) {

		SpringApplication.run(DistributeurautomatiqueApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ProduitRepository produitRepository) {
		return args -> {

			Produit p=Produit.builder().name("produit 1").price(5).build();
			produitRepository.save(p);

			System.out.println("Produit ajoutée avec solde = " + p.getPrice());

			p=Produit.builder().name("produit 2").price(10).build();
			produitRepository.save(p);

			System.out.println("Produit ajoutée avec solde = " + p.getPrice());

			p=Produit.builder().name("produit 3").price(15).build();
			produitRepository.save(p);

			System.out.println("Produit ajoutée avec solde = " + p.getPrice());
		};
	}
}
