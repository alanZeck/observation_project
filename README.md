lien pour utiliser swagger : http://localhost:8080/swagger-ui/index.html
exemple post json pour création d'observation :
{
  "type": "MAMMIFERE",
  "animalMarin": "CACHALOT",
  "ilot": "ff80818167a5556c0167a5559e030001",
  "distanceBord": 150,
  "dateObservation": "2024-12-23T00:39:00",
  "qualite": "VERIFIE",
  "tailleEstimee": 100,
  "nombreIndividus": 1
}

Pistes d'améliorations :
  - Une meilleur gestion des exceptions avec une gestion plus fine de validation des champs (créer une annotation spécifique ?) qui nous permettrait d'avoir une meilleur gestion des responseCodes.
  - Une meilleur couverture de code, ici je n'ai fait que très peu de TUs, il faudrait une couverture de code bien plus importante et paramétrer sonar pour nous forcer à avoir une couverture minimum de par exemple 70% du code.
  - Actuellement l'appel de l'api ilot est fait 1 fois à chaque démarrage de l'app, on pourrait plutôt penser à utiliser un schedule pour par exemple qu'il se lance 1 fois par jour à 5h du matin.

Pistes d'évolutions
  - Architecture micro-service : si l'app avait comme but d'évoluer et d'ajouter toutes sorte de services, on pourrait alors la découper, par exemple notre appel api vers ilo pourrait être un micro-service.
  - Créer une interface web ou mobile pour permettre aux utilisateurs de visualiser et gérer les observations.
  - Intégrer un module d'analyse pour fournir des statistiques sur les observations (par exemple : nombre moyen d’individus par banc, îlot le plus fréquenté, etc.).
  - Ajouter des filtres supplémentaires dans le GET /observations pour permettre des recherches par plage de dates, îlots spécifiques, distance, etc.
  - Ajouter un système d'authentification et d'autorisation :
          - Permettre à certains utilisateurs d'ajouter des observations, tandis que d'autres n'ont qu'un accès en lecture.
          - Implémenter OAuth2 ou JWT pour sécuriser l'API.
          - Enregistrer quelle personne a ajouté ou modifié une observation.
