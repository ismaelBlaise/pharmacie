# Gestion de Pharmacie

Ce projet est une application de gestion de pharmacie développée avec **Spring Boot** et une base de données PostgreSQL. L'application permet de gérer les utilisateurs, les commandes, les stocks, les médicaments et bien d'autres fonctionnalités nécessaires au bon fonctionnement d'une pharmacie.

## Fonctionnalités

- Gestion des utilisateurs (clients, pharmaciens) et des rôles.
- Gestion des médicaments (informations commerciales, compositions, types, formes, etc.).
- Suivi des stocks (ajout, retrait, mouvements de stock).
- Gestion des commandes et paniers.
- Suivi des maladies, symptômes et traitements.
- Gestion des laboratoires et des voies d'administration.

## Technologies utilisées

- **Backend** : Spring Boot
- **Base de données** : PostgreSQL
- **ORM** : JPA (Jakarta Persistence API)
- **Lombok** : Pour réduire le boilerplate (annotations @Getter, @Setter, etc.)
- **Tests** : JUnit, Mockito

## Pré-requis

Avant de commencer, assurez-vous d'avoir les outils suivants installés sur votre système :

- Java 17 ou supérieur
- Maven 3.8+ ou Gradle (au choix)
- PostgreSQL
- Un IDE comme IntelliJ IDEA, Eclipse ou VS Code

## Configuration du projet

### 1. Clonez le dépôt

```bash
git clone <url_du_projet>
cd <nom_du_projet>
