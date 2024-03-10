# Ce référentiel contient une API Rest avec 4 méthodes pour gérer une équipe de football:

### Méthode 1 : Permet de créer une équipe avec ou sans joueurs.
### Méthode 2 : Permet de récupérer une équipe via sons id.
### Méthode 3 : Permet de récupérer la liste d’équipes avec la liste de joueurs paginée et pourra être triée par nom d’équipe ou(et) acronyme ou(et) budget. 
### Méthode 4 : Permet de récupérer la liste d’équipes avec la liste de joueurs paginée et triée sur nom d’équipe, acronyme et budget par défaut un tri ascendant.

## L'environnement technique :
- Spring boot.
- Hibernate.
- Base de données : PostgreSQL.
- Flyway : la gestion des changements de schéma de base de données (Création des tables dans la base de données).
- Dockerfile : pour la construction de l'image Docker.
- Docker compose : contient des services ainsi que leur configuration qui composent notre application.

## Ci-dessous la liste des méthodes :
![image](https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/198146b8-489b-4367-bf55-95ea811af7de)


## démarrer les conteneurs de l'application
Pour démarrer les conteneurs de l'application footballTeamManagement, suivez ces étapes :

### Prérequis:

1- Assurez-vous d'avoir Docker installé sur votre machine.

2- Téléchargez le code source de l'application footballTeamManagement. Vous pouvez le faire en clonant le dépôt Git:

		https://github.com/achraf-fandouli/GestionEquipeFootball.git

3- Démarrage des conteneurs:

- Ouvrez une fenêtre de terminal et naviguez vers le répertoire contenant le fichier docker-compose.yml (sous le repertoire racine de ce projet).
- Exécutez la commande suivante :

		docker-compose up -d

Cette commande lance les services définis dans le fichier docker-compose.yaml.

4- Vérification du démarrage:
- Exécutez la commande suivante pour vérifier que les conteneurs sont en cours d'exécution :

		docker ps

Cette commande affiche une liste de tous les conteneurs en cours d'exécution sur votre machine:
### footballteammanage-container, postgres-container et pgadmin-container

5- Accès à l'application:
- L'application est accessible sur le port 8081 de votre machine locale.

6- Accès à pgAdmin(l'interface de la base de données PostgreSQL): pgAdmin est une interface web pour gérer les bases de données PostgreSQL.

Vous pouvez y accéder à l'adresse http://localhost:9090 en utilisant les informations d'identification spécifiées dans le fichier docker-compose.yaml:

email:

		test@gmail.com

Mot de passe:

		root

![image](https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/2447251e-31ab-4fbc-b108-050855c94a71)


8- Via l'inetface PgAdmin on va créer la base de données avec le nom "footballteamdb" (mentionné dans le fichier docker-compose.yaml)

7- La documentation swagger est accessible via L'URL suivante :

		http://localhost:8081/swagger-ui/index.html#/

9- Une fois que tout est en ordre(Les conteneurs sont bien démarrés.) , on peut tester l'application avec l'outil de test des API Postman en se basant sur la documentation Swagger des différentes méthodes.

![image](https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/5bd624c6-fe8c-4477-b418-22c45c546f01)


10- exemples de test: Ci-dessous des exemples de tests:

### PostMapping  http://localhost:8081/equipes - création d'une équipe:

Request Body:

		{
    "nom":"Olympique Gymnaste Club de Nice",
    "acronyme":"OGC",
    "budget":100,
    "joueurs":[
        {"nom":"A fandouli",
        "position":"Défenseur"},
        {"nom":"M Bulka",
        "position":"Milieu de terrain"}
    ]}

<img width="636" alt="image" src="https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/a3b4f2a3-4574-4f64-863b-8ac937df6dd8">

### GetMapping  http://localhost:8081/equipes/46 - Récupérer une équipe via sons id:

<img width="632" alt="image" src="https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/c7b6b613-da94-4f6a-8170-6ebb93fcd1b6">

### GetMapping  http://localhost:8081/equipes/genericSort?acronyme=ASC&budget=DESC&nom=DESC - Récupérer la liste d’équipes pagi:
Récupération de la liste d’équipes avec la liste de joueurs paginée et pourra être triée par nom d’équipe ou(et) acronyme ou(et) budget

<img width="634" alt="image" src="https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/5a50f012-c114-461a-8067-429abe646ff0">

### GetMapping http://localhost:8081/equipes/simpleSort - Récupérer la liste d’équipes avec un tri ascendant par défaut:

<img width="626" alt="image" src="https://github.com/achraf-fandouli/GestionEquipeFootball/assets/55927202/fbea063f-b1a5-4fcd-abfe-bc3fa7d1af4f">


