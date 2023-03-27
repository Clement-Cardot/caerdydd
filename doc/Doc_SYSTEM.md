# Gitlab et OpenProject
- Gitlab      -> http://172.24.0.69
- OpenProject -> http://172.24.1.1

# SonarQube
- Adresse -> http://172.24.1.1:9000/sonar
- login : DevTeam3
- Mdp : devuserpass

# IP des Serveurs

- Adresse Dev -> http://172.24.1.10
- Adresse Prod -> http://172.24.1.11

# Base de données

## Serveur de développement

Vous pouvez accéder à `phpMyAdmin` en utilisant ce lien : https://172.24.1.10/phpmyadmin

### Utilisateur pour les développeurs

Cet utilisateur doit être utiliser quand vous avez besoin de modifier la base manuellement dans l'interface phpmyadmin. Par exemple créer une nouvelle table, insérer les premières données...

Cet utilisateur détient tous les droits SQL.
- Nom d'utilisateur : `devuser`
- Mot de passe : `WAl_UPpmE27V4ixh`

### Utilisateur à utiliser pour l'application
Cet utilisateur doit être utiliser dans le script de l'application.

Cet utilisateur détient uniquement les droits nécéssaires pour des raisons de sécurité. Ainsi, il se peut que quand vous aller coder, certaines requêtes ne fonctoinnent pas car vous n'avez les droits. Dans ce cas, prévenez le responsable système pour qu'il ajoute les droits nécéssaires.
- Nom d'utilisateur : `webuser`
- Mot de passe : `mNifUKDq10MPD3pP`

## Serveur de production

Vous pouvez accéder à `phpMyAdmin` en utilisant ce lien : https://172.24.1.11/phpmyadmin

#### Utilisateur pour les développeurs

Cet utilisateur doit être utiliser quand vous avez besoin de modifier la base manuellement dans l'interface phpmyadmin. Par exemple créer une nouvelle table, insérer les premières données...

Cet utilisateur détient tous les droits SQL.

- Nom d'utilisateur : `devuser`
- Mot de passe : `fL6fk58PnpBfuCgc`

#### Utilisateur à utiliser pour l'application

Cet utilisateur doit être utiliser dans le script de l'application.

Cet utilisateur détient uniquement les droits nécéssaires pour des raisons de sécurité. Normallement, les droits seront les mêmes que sur le serveur dev mais il ce peut qu'il y ait des erreurs, dans ce cas prévenez le responsable système.

- Nom d'utilisateur : `webuser`
- Mot de passe : `LOPk2DWnqj2oRtRm`
