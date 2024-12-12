# Tp3Microservices

Résumé du TP : Implémentation de la résilience et de la surveillance des microservices avec Spring Boot, Resilience4j, et Micrometer

Ce TP consiste à améliorer la résilience des microservices product-microservice et user-microservice en utilisant Resilience4j pour ajouter des mécanismes de résilience (retry, rate limiting, et circuit breaking), et Micrometer pour surveiller les performances et exporter les métriques vers Prometheus. Grafana est ensuite utilisé pour visualiser ces données.

Objectifs :
Resilience4j :

Configuration : Dans chaque microservice, configurer Resilience4j dans le fichier YAML pour ajouter des stratégies de résilience comme le retry, le rate limiter, et le circuit breaker.
Annotations : Utiliser les annotations @Retry, @RateLimiter, et @CircuitBreaker pour appliquer les stratégies de résilience dans les méthodes du contrôleur. Par exemple, @Retry(name = "myRetry", fallbackMethod = "fallback"), @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallback"), et @CircuitBreaker(name = "productmicroService", fallbackMethod = "fallback").
Méthode fallback : Implémenter une méthode fallback pour gérer les erreurs, renvoyant un message personnalisé en cas d'échec des mécanismes de résilience.
Micrometer & Prometheus :

Dépendance : Ajouter la dépendance Micrometer à pom.xml pour exporter les métriques vers Prometheus.
Configuration : Activer l'exportation des métriques dans application.yml avec management.endpoints.web.exposure.include: "*" et metrics.export.prometheus.enabled: true.
Endpoint Prometheus : Lancer un endpoint /actuator/prometheus pour que Prometheus puisse collecter les données.
Visualisation avec Grafana : Configurer Grafana pour visualiser les métriques collectées par Prometheus, telles que le temps de réponse, le nombre de requêtes, l'utilisation de la mémoire, etc.
Résultat & Visualisation :

Endpoint Prometheus : Les métriques sont maintenant disponibles via l'endpoint /actuator/prometheus.
Prometheus : Collecte les données de métriques des microservices.
Grafana : Permet de créer des tableaux de bord pour visualiser les métriques, surveiller les performances en temps réel, et détecter les problèmes potentiels.
Cette configuration améliore la résilience des microservices product-microservice et user-microservice en limitant la propagation des erreurs et en surveillant les performances pour garantir une meilleure stabilité et une réactivité accrue.
