# ChaTop BackEnd API

## Installations

### WampServer
Télécharger et installer Laragon
https://laragon.org/download/

### Apache Maven
Télécharger et installer Apache Maven
https://maven.apache.org/download.cgi

### Java Developpment Kit
Télécharger et installer Java Developpment Kit
https://www.oracle.com/java/technologies/downloads/

### Database Configuration

#### MySQL Configuration:

Rendez vous sur l'adresse suivante : localhost
L'utilisateur par default est root, il n'y a pas de mot de passe.

#### Création Base de données in PhpMyAdmin

Connectez vous.
Créez votre base de données
Depuis l'onglet import, importer le script pour la création de la database qui se trouve :
P3_ApiChaTop\src\main\resources\script.sql

#### Création clé secrete
Pour créer votre clé secrète, vous pouvez en générer une à partir d'un site
générer une clé 56-bit.

#### Configuration de la base de données dans Spring Boot:

```Mise a jour de application properties P3_ApiChaTop\src\main\resources\application.properties :

spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=root
spring.datasource.password=
jwt.secret=votre_clé_secrète
```
## Lancement du projet

Pour lancer le serveur Java, dans un terminal placez vous dans le dossier /Back-End/SpringSecurityConfig et entrez la commande mvn spring-boot:run.
Documentation Swagger de l'API

Pour acceder à la documentation , rendez-vous sur : http://localhost:3001/swagger-ui/index.html#/