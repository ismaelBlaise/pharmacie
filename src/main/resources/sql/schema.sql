\c postgres;

DROP DATABASE pharmacie;

CREATE DATABASE pharmacie;

\c pharmacie;

CREATE TABLE sexes(
   Id_sexes SERIAL,
   sexe VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_sexes),
   UNIQUE(sexe)
);

CREATE TABLE roles(
   Id_roles SERIAL,
   role VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_roles),
   UNIQUE(role)
);

CREATE TABLE medicaments(
   Id_medicaments SERIAL,
   nom_commercial VARCHAR(150)  NOT NULL,
   nom_scientifique VARCHAR(150)  NOT NULL,
   PRIMARY KEY(Id_medicaments)
);

CREATE TABLE laboratoires(
   Id_laboratoires SERIAL,
   nom VARCHAR(150) ,
   PRIMARY KEY(Id_laboratoires)
);

CREATE TABLE types_medicament(
   Id_types_medicament SERIAL,
   type_medicament VARCHAR(150)  NOT NULL,
   PRIMARY KEY(Id_types_medicament),
   UNIQUE(type_medicament)
);

CREATE TABLE compositions(
   Id_compositions SERIAL,
   nom VARCHAR(150) ,
   description VARCHAR(250) ,
   PRIMARY KEY(Id_compositions)
);

CREATE TABLE maladies(
   Id_maladies SERIAL,
   maladie VARCHAR(150)  NOT NULL,
   description VARCHAR(250) ,
   PRIMARY KEY(Id_maladies),
   UNIQUE(maladie)
);

CREATE TABLE voies_administration(
   Id_voies_administration SERIAL,
   voie_administration VARCHAR(150)  NOT NULL,
   PRIMARY KEY(Id_voies_administration),
   UNIQUE(voie_administration)
);

CREATE TABLE statut_commande(
   Id_statut_commande SERIAL,
   statut VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_statut_commande),
   UNIQUE(statut)
);

CREATE TABLE symptomes(
   Id_symptomes SERIAL,
   symptome VARCHAR(150)  NOT NULL,
   PRIMARY KEY(Id_symptomes)
);

CREATE TABLE types_mouvement(
   Id_types_mouvement SERIAL,
   type_mouvement VARCHAR(110)  NOT NULL,
   PRIMARY KEY(Id_types_mouvement),
   UNIQUE(type_mouvement)
);

CREATE TABLE config(
   Id_config SERIAL,
   cle VARCHAR(250)  NOT NULL,
   val VARCHAR(250)  NOT NULL,
   PRIMARY KEY(Id_config),
   UNIQUE(cle)
);

CREATE TABLE utilisateurs(
   Id_utilisateurs SERIAL,
   nom VARCHAR(150)  NOT NULL,
   prenoms VARCHAR(150)  NOT NULL,
   email VARCHAR(150)  NOT NULL,
   adresse VARCHAR(150) ,
   mdp VARCHAR(150)  NOT NULL,
   poids INTEGER,
   date_naissance DATE NOT NULL,
   Id_roles INTEGER NOT NULL,
   Id_sexes INTEGER NOT NULL,
   PRIMARY KEY(Id_utilisateurs),
   UNIQUE(email),
   FOREIGN KEY(Id_roles) REFERENCES roles(Id_roles),
   FOREIGN KEY(Id_sexes) REFERENCES sexes(Id_sexes)
);

CREATE TABLE formes(
   Id_formes SERIAL,
   forme VARCHAR(150)  NOT NULL,
   Id_voies_administration INTEGER NOT NULL,
   PRIMARY KEY(Id_formes),
   UNIQUE(forme),
   FOREIGN KEY(Id_voies_administration) REFERENCES voies_administration(Id_voies_administration)
);

CREATE TABLE paniers(
   Id_paniers SERIAL,
   etat BOOLEAN NOT NULL,
   date_panier DATE NOT NULL,
   Id_utilisateurs INTEGER NOT NULL,
   PRIMARY KEY(Id_paniers),
   FOREIGN KEY(Id_utilisateurs) REFERENCES utilisateurs(Id_utilisateurs)
);

CREATE TABLE commandes(
   Id_commandes SERIAL,
   date_commande DATE NOT NULL,
   prix_total NUMERIC(15,3)   NOT NULL,
   commission NUMERIC(15,3)   NOT NULL,
   Id_utilisateurs INTEGER NOT NULL,
   Id_statut_commande INTEGER NOT NULL,
   Id_paniers INTEGER NOT NULL,
   Id_utilisateurs_1 INTEGER NOT NULL,
   PRIMARY KEY(Id_commandes),
   FOREIGN KEY(Id_utilisateurs) REFERENCES utilisateurs(Id_utilisateurs),
   FOREIGN KEY(Id_statut_commande) REFERENCES statut_commande(Id_statut_commande),
   FOREIGN KEY(Id_paniers) REFERENCES paniers(Id_paniers),
   FOREIGN KEY(Id_utilisateurs_1) REFERENCES utilisateurs(Id_utilisateurs)
);

CREATE TABLE medicament_fiches(
   Id_medicament_fiches SERIAL,
   photo VARCHAR(150)  NOT NULL,
   dosage NUMERIC(15,2)   NOT NULL,
   age_min INTEGER NOT NULL,
   age_max INTEGER NOT NULL,
   poid_min NUMERIC(15,3)   NOT NULL,
   poid_max NUMERIC(15,3)   NOT NULL,
   indication VARCHAR(250)  NOT NULL,
   Id_formes INTEGER NOT NULL,
   Id_types_medicament INTEGER NOT NULL,
   Id_medicaments INTEGER NOT NULL,
   PRIMARY KEY(Id_medicament_fiches),
   FOREIGN KEY(Id_formes) REFERENCES formes(Id_formes),
   FOREIGN KEY(Id_types_medicament) REFERENCES types_medicament(Id_types_medicament),
   FOREIGN KEY(Id_medicaments) REFERENCES medicaments(Id_medicaments)
);

CREATE TABLE traitements_maladie(
   Id_medicament_fiches INTEGER,
   Id_maladies INTEGER,
   dose NUMERIC(15,3)  ,
   frequence INTEGER NOT NULL,
   moment VARCHAR(250)  NOT NULL,
   duree NUMERIC(15,3)   NOT NULL,
   instruction VARCHAR(250)  NOT NULL,
   PRIMARY KEY(Id_medicament_fiches, Id_maladies),
   FOREIGN KEY(Id_medicament_fiches) REFERENCES medicament_fiches(Id_medicament_fiches),
   FOREIGN KEY(Id_maladies) REFERENCES maladies(Id_maladies)
);

CREATE TABLE recommandations(
   Id_recommandations SERIAL,
   raison VARCHAR(250)  NOT NULL,
   date_debut DATE NOT NULL,
   date_fin DATE NOT NULL,
   date_ajout DATE NOT NULL,
   Id_medicament_fiches INTEGER NOT NULL,
   PRIMARY KEY(Id_recommandations),
   FOREIGN KEY(Id_medicament_fiches) REFERENCES medicament_fiches(Id_medicament_fiches)
);




CREATE TABLE stocks(
   Id_stocks SERIAL,
   lot VARCHAR(150)  NOT NULL,
   quantite INTEGER NOT NULL,
   prix_achat NUMERIC(15,4)  ,
   prix NUMERIC(15,3)   NOT NULL,
   date_fabrication DATE,
   date_expiration DATE,
   date_stock DATE NOT NULL,
   Id_laboratoires INTEGER NOT NULL,
   Id_medicament_fiches INTEGER NOT NULL,
   PRIMARY KEY(Id_stocks),
   FOREIGN KEY(Id_laboratoires) REFERENCES laboratoires(Id_laboratoires),
   FOREIGN KEY(Id_medicament_fiches) REFERENCES medicament_fiches(Id_medicament_fiches)
);

CREATE TABLE details_panier(
   Id_details_panier SERIAL,
   quantite INTEGER NOT NULL,
   Id_medicament_fiches INTEGER NOT NULL,
   Id_paniers INTEGER NOT NULL,
   PRIMARY KEY(Id_details_panier),
   FOREIGN KEY(Id_medicament_fiches) REFERENCES medicament_fiches(Id_medicament_fiches),
   FOREIGN KEY(Id_paniers) REFERENCES paniers(Id_paniers)
);

CREATE TABLE mouvements_stock(
   Id_mouvements_stock SERIAL,
   quantite INTEGER NOT NULL,
   date_mouvement DATE NOT NULL,
   Id_types_mouvement INTEGER NOT NULL,
   Id_stocks INTEGER NOT NULL,
   PRIMARY KEY(Id_mouvements_stock),
   FOREIGN KEY(Id_types_mouvement) REFERENCES types_mouvement(Id_types_mouvement),
   FOREIGN KEY(Id_stocks) REFERENCES stocks(Id_stocks)
);

CREATE TABLE medicaments_compositions(
   Id_compositions INTEGER,
   Id_medicament_fiches INTEGER,
   poid NUMERIC(15,3)   NOT NULL,
   PRIMARY KEY(Id_compositions, Id_medicament_fiches),
   FOREIGN KEY(Id_compositions) REFERENCES compositions(Id_compositions),
   FOREIGN KEY(Id_medicament_fiches) REFERENCES medicament_fiches(Id_medicament_fiches)
);

CREATE TABLE maladies_symptomes(
   Id_maladies INTEGER,
   Id_symptomes INTEGER,
   gravite INTEGER,
   frequence INTEGER,
   PRIMARY KEY(Id_maladies, Id_symptomes),
   FOREIGN KEY(Id_maladies) REFERENCES maladies(Id_maladies),
   FOREIGN KEY(Id_symptomes) REFERENCES symptomes(Id_symptomes)
);



CREATE TABLE prix_medicament(
   Id_prix_medicament SERIAL,
   montant NUMERIC(15,3)   NOT NULL,
   date_changement DATE NOT NULL,
   Id_medicament_fiches INTEGER NOT NULL,
   PRIMARY KEY(Id_prix_medicament),
   FOREIGN KEY(Id_medicament_fiches) REFERENCES medicament_fiches(Id_medicament_fiches)
);
