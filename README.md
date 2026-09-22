# EcoAgriSens — Guide de mise en route

## 1. Base de donnees
1. Ouvre MySQL Workbench (ou la ligne de commande MySQL).
2. Execute le script `sql/agrisens_db.sql` fourni. Il cree la base `agrisens_db`, la table `capteurs`, et insere 3 capteurs de test.

## 2. Configuration IntelliJ (driver JDBC MySQL)
1. Telecharge le connecteur `mysql-connector-j` (fichier `.jar`) si ce n'est pas deja fait.
2. Dans IntelliJ : **File > Project Structure > Modules > Dependencies > + > JARs or Directories**, puis selectionne le `.jar` du connecteur.
3. Verifie que le module est bien coche en "Compile" dans la portee (scope).
4. Dans `DBConnection.java`, adapte si besoin :
   - `USER` (par defaut `root`)
   - `PASSWORD` (par defaut vide `""`)
   - le port dans l'URL si ton MySQL n'ecoute pas sur `3306`.

## 3. Structure du projet (conforme au sujet)
```
src/
 └─ fr.agrisens.
     ├─ config/DBConnection.java      → Connexion JDBC (Singleton)
     ├─ model/Capteur.java            → Entite POJO
     ├─ dao/ICapteurDao.java          → Contrat CRUD
     ├─ dao/CapteurDaoImpl.java       → Implementation JDBC (PreparedStatement)
     ├─ service/CapteurService.java   → Couche metier (filtres, moyennes)
     └─ main/App.java                 → Menu console (point d'entree)
```

## 4. Execution
Lance `App.java`. Le menu console propose les 7 options du sujet (affichage, ajout, mise a jour batterie, suppression, alerte batterie faible, moyenne par type, quitter).

## 5. Points a verifier avant le rendu
- Le driver JDBC est bien dans les dependances du module (pas juste dans le dossier du projet).
- La base `agrisens_db` existe et contient la table `capteurs` avant de lancer l'appli.
- Les dates saisies dans le menu suivent le format `jj/MM/aaaa`.
