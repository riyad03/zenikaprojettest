# Distributeur Automatique API

Ce projet implémente une API REST pour un distributeur automatique permettant l'achat de produits avec gestion de la monnaie. Le projet est composé d'un backend Spring Boot et d'un frontend React.

## Architecture

```
distributeurautomatique/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/distributeurautomatique/
│   │           ├── controller/    # Endpoints REST
│   │           ├── service/       # Logique métier
│   │           ├── dao/          # Accès aux données
│   │           └── exception/     # Gestion des erreurs
│   └── test/
│       └── java/                 # Tests unitaires
└── front/
    └── frontapp/                 # Interface utilisateur React
```

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- Node.js 14 ou supérieur
- npm 6 ou supérieur

## Installation et lancement

### Backend (Spring Boot)

Vous avez deux options pour lancer le backend :

#### Option 1 : Via IDE
1. Installer IntelliJ Ultimate ou Eclipse
2. Ouvrir le dossier `distributeurautomatique`
3. Exécuter l'application via l'IDE (classe principale : `DistributeurautomatiqueApplication`)

Important: Il faut avoir activé Lumbok pour que ça fonctionne sans erreur: 
Dans IntelliJ: Settings->Build, Execution, Depoloyment->Compiler->Annotation Processors->Annotation Profile for Distributeurautomatique: changer du mode Processor path à obtain processors from project classpath

#### Option 2 : Via ligne de commande
```bash
cd distributeurautomatique
mvn clean install
mvn spring-boot:run
```

Le serveur démarre sur http://localhost:8080

### Frontend (React)

1. Installer les dépendances
```bash
cd front/frontapp
npm install
```

2. Lancer l'application
```bash
npm start
```

L'interface est accessible sur http://localhost:3000

## Démonstration de l'API

Voici les principaux endpoints de l'API avec des exemples d'utilisation via curl :

### 1. Lister tous les produits
```bash
curl http://localhost:8080/produits
```

### 2. Insérer une pièce
```bash
curl -X POST http://localhost:8080/ajoutpiece/5
```
Valeurs acceptées : 0.5, 1, 2, 5, 10 MAD

### 3. Voir le solde actuel
```bash
curl http://localhost:8080/recupiece
```

### 4. Ajouter un produit au panier
```bash
curl -X POST http://localhost:8080/ajoutpanier/1
```

### 5. Voir le contenu du panier
```bash
curl http://localhost:8080/panier
```

### 6. Finaliser l'achat
```bash
curl -X POST http://localhost:8080/acheter
```

### 7. Annuler la transaction
```bash
curl -X DELETE http://localhost:8080/annuler
```

## Choix techniques et hypothèses

### Architecture
- **Pattern MVC** : Séparation claire des responsabilités entre les contrôleurs, services et accès aux données
- **API REST** : Interface simple et standard pour les communications client-serveur
- **Stateless** : La session est gérée côté client pour plus de scalabilité

### Gestion de la monnaie
- Les pièces acceptées sont : 0.5, 1, 2, 5, 10 MAD
- La monnaie est rendue de manière optimisée en utilisant le minimum de pièces possible
- Le stock de monnaie est considéré comme illimité

### Gestion des produits
- Le stock de produits est considéré comme illimité
- Les prix sont en MAD avec une précision de 2 décimales
- Les produits ne peuvent pas être ajoutés au panier si le solde est insuffisant

### Sécurité
- CORS configuré pour permettre uniquement les requêtes depuis le frontend (localhost:3000)
- Validation des entrées pour les montants et les IDs de produits


## Tests

Les tests unitaires couvrent :
- La validation des pièces de monnaie
- Le calcul du rendu de monnaie
- La gestion du panier
- La logique d'achat

Pour exécuter les tests :
```bash
mvn test
``` 
