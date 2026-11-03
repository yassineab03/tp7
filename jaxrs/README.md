# TP 7 — JAX-RS / Jersey

Projet généré avec Spring Initializr : group `ma.ws`, artifact `jaxrs`, package `ma.ws.jaxrs`.
Le ZIP original est conservé dans `../jaxrs-initializr.zip`. Le projet généré a été adapté à Spring Boot 3.5.7 pour suivre le TP avec Jersey 3 et JAXB 4 ; les versions JAXB/Jersey sont gérées par Spring Boot.

## Lancer

Java 21 ou supérieur. Ouvrir ce dossier comme projet Maven dans IntelliJ IDEA, choisir un JDK et lancer `JaxrsApplication`.

```powershell
.\mvnw.cmd spring-boot:run
```

API : http://localhost:8082/banque/comptes
Console H2 : http://localhost:8082/h2-console/
JDBC : `jdbc:h2:mem:banque` ; utilisateur `sa` ; mot de passe vide.
SQL : `SELECT * FROM COMPTE;`

Trois comptes aléatoires sont créés au démarrage. La base en mémoire est réinitialisée au redémarrage.

## Endpoints

| Méthode | URL | Résultat |
|---|---|---|
| GET | /banque/comptes | Liste des comptes |
| GET | /banque/comptes/{id} | Compte ou 404 |
| POST | /banque/comptes | Création, 201 et Location |
| PUT | /banque/comptes/{id} | Modification ou 404 |
| DELETE | /banque/comptes/{id} | Suppression, 204 ou 404 |

Les en-têtes `Accept` et `Content-Type` acceptent `application/json` et `application/xml`.
Voir `requetes.http` pour les exemples utilisables dans le client HTTP IntelliJ ou à reproduire dans SoapUI/Postman.

Jersey est monté sur `/banque` (application.properties) et la ressource sur `/` pour laisser la console H2 accessible.
L'entité utilise JAXB (`@XmlRootElement`, accès aux champs) pour le XML, et Lombok pour les accesseurs/constructeurs.

## Vérification

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

`CompteApiTests` lance un vrai serveur HTTP sur un port aléatoire et vérifie GET liste JSON/XML, POST, GET détail, PUT, DELETE dans les deux formats, les 404 et l'accès H2.
`JaxrsApplicationTests` vérifie le démarrage du contexte Spring.
