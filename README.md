# TP1_Tp3Microservices
### TP1 : Mise en place des microservices avec Spring Boot et Eureka

Dans le cadre du **TP1**, l'objectif était de créer deux microservices simples à l'aide de **Spring Boot** et d'intégrer **Eureka** pour la découverte des microservices :

- **product-microservice** : Microservice fournissant des informations sur les produits.
- **user-microservice** : Microservice gérant les informations sur les utilisateurs.

#### Étapes :

1. **Création des microservices** :
   - Chaque microservice a été développé en utilisant Spring Boot, avec des contrôleurs REST exposant des endpoints pour fournir et manipuler les données des produits et des utilisateurs.
   - Les microservices ont été configurés pour interagir avec une base de données pour stocker les informations relatives aux produits et utilisateurs.

2. **Intégration d'Eureka pour la découverte des microservices** :
   - Mise en place d'un serveur **Eureka** pour la gestion de la découverte des microservices.
   - Chaque microservice a été configuré pour être un **client Eureka**, permettant à chaque service de s'enregistrer auprès du serveur Eureka.
   
   Exemple de configuration dans **`application.yml`** :
   - **Serveur Eureka** :
     ```yaml
     server:
       port: 8761
     eureka:
       client:
         registerWithEureka: false
         fetchRegistry: false
     ```
   - **Client Eureka (product-microservice ou user-microservice)** :
     ```yaml
     spring:
       application:
         name: product-microservice  # ou user-microservice selon le cas
     eureka:
       client:
         service-url:
           defaultZone: http://localhost:8761/eureka/
     ```
   
   Cette configuration permet aux microservices de se découvrir et de se communiquer entre eux en utilisant les noms d'application via Eureka.

---

### TP3 : Implémentation de la résilience et de la surveillance des microservices avec Spring Boot, Resilience4j et Micrometer

Le **TP3** a pour objectif d'améliorer la résilience des microservices en utilisant **Resilience4j** pour les stratégies de résilience (telles que le retry, le rate limiting, et le circuit breaking), et **Micrometer** pour surveiller les performances et exporter les métriques vers **Prometheus**. **Grafana** est ensuite utilisé pour visualiser ces données.

#### Objectifs :

1. **Resilience4j** :
   - **Configuration** : Dans chaque microservice, configurer Resilience4j pour ajouter des stratégies de résilience comme le retry, le rate limiter, et le circuit breaker dans les fichiers YAML.
   - **Annotations** : Utilisation des annotations comme `@Retry`, `@RateLimiter`, et `@CircuitBreaker` pour appliquer les stratégies de résilience aux méthodes du contrôleur.
   - **Méthodes fallback** : Implémentation de méthodes fallback pour gérer les erreurs et fournir des messages personnalisés en cas d'échec des mécanismes de résilience.
   
   Exemple d'annotation :
   ```java
   @Retry(name = "myRetry", fallbackMethod = "fallback")
   public ResponseEntity<String> getProduct() {
       // Logic here
   }
   ```

2. **Micrometer & Prometheus** :
   - **Dépendance Micrometer** : Ajout de la dépendance **Micrometer** dans le fichier **`pom.xml`** pour exporter les métriques vers **Prometheus**.
   - **Configuration** : Activation de l'exportation des métriques dans **`application.yml`** :
     ```yaml
     management.endpoints.web.exposure.include: "*"
     metrics.export.prometheus.enabled: true
     ```
   - **Endpoint Prometheus** : Création de l'endpoint `/actuator/prometheus` pour permettre à **Prometheus** de collecter les données de métriques des microservices.

3. **Visualisation avec Grafana** :
   - Utilisation de **Grafana** pour visualiser les métriques collectées par **Prometheus**.
   - Création de tableaux de bord permettant de surveiller des métriques telles que le temps de réponse, le nombre de requêtes, et l'utilisation de la mémoire.

#### Résultats attendus :

- **Endpoint Prometheus** : Les métriques sont accessibles via l'endpoint `/actuator/prometheus`.
- **Prometheus** : Collecte les données de métriques des microservices.
- **Grafana** : Permet de créer des tableaux de bord pour surveiller les performances des microservices et détecter les problèmes potentiels.

Cette configuration améliore la résilience des microservices **product-microservice** et **user-microservice** en limitant la propagation des erreurs et en surveillant les performances pour garantir une meilleure stabilité et réactivité.


