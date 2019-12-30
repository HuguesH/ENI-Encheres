CREATE TABLE CATEGORIES (
    no_categorie INTEGER IDENTITY(1, 1) NOT NULL,
    libelle VARCHAR(30) NOT NULL,
    PRIMARY KEY (no_categorie)
);


CREATE TABLE ENCHERES (
    no_utilisateur INTEGER NOT NULL,
    no_article INTEGER NOT NULL,
    date_enchere datetime NOT NULL,
    montant_enchere INTEGER NOT NULL,
    PRIMARY KEY (no_utilisateur, no_article)
);


CREATE TABLE RETRAITS (
    no_article INTEGER NOT NULL,
    rue VARCHAR(30) NOT NULL,
    code_postal VARCHAR(15) NOT NULL,
    ville VARCHAR(30) NOT NULL,
    PRIMARY KEY (no_article)
);


CREATE TABLE UTILISATEURS (
    no_utilisateur INTEGER IDENTITY(1, 1) NOT NULL,
    pseudo VARCHAR(30) NOT NULL,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    email VARCHAR(80) NOT NULL,
    telephone VARCHAR(15),
    rue VARCHAR(30) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    ville VARCHAR(30) NOT NULL,
    mot_de_passe VARCHAR(256) NOT NULL,
    credit INTEGER NOT NULL,
    administrateur BOOLEAN NOT NULL,
    PRIMARY KEY (no_utilisateur)
);
-- TODO : mettre un index unique sur le pseudo et l'adresse mail permettrait de gérer l'unicité d'ameliorer la recherche.


CREATE TABLE ARTICLES_VENDUS (
    no_article INTEGER IDENTITY(1, 1) NOT NULL,
    nom_article VARCHAR(30) NOT NULL,
    description VARCHAR(300) NOT NULL,
    date_debut_encheres DATE NOT NULL,
    date_fin_encheres DATE NOT NULL,
    prix_initial INTEGER,
    prix_vente INTEGER,
    no_vendeur INTEGER NOT NULL,
    no_acheteur INTEGER NOT NULL,
    no_categorie INTEGER NOT NULL,
    etat_vente INTEGER NOT NULL,
    PRIMARY KEY (no_article)
);


ALTER TABLE
    ARTICLES_VENDUS
ADD
    CONSTRAINT encheres_utilisateur_fk FOREIGN KEY (no_acheteur) REFERENCES UTILISATEURS (no_utilisateur) ON DELETE NO ACTION ON UPDATE no action;

ALTER TABLE
    ENCHERES
ADD
    CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article) ON DELETE NO ACTION ON UPDATE no action;

ALTER TABLE
    RETRAITS
ADD
    CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article) ON DELETE NO ACTION ON UPDATE no action;

ALTER TABLE
    ARTICLES_VENDUS
ADD
    CONSTRAINT articles_vendus_categories_fk FOREIGN KEY (no_categorie) REFERENCES categories (no_categorie) ON DELETE NO ACTION ON UPDATE no action;

ALTER TABLE
    ARTICLES_VENDUS
ADD
    CONSTRAINT ventes_utilisateur_fk FOREIGN KEY (no_vendeur) REFERENCES utilisateurs (no_utilisateur) ON DELETE NO ACTION ON UPDATE no action;
