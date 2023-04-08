# Documentation de l'API (Back-end)

## Généralités
Pour toutes les requetes, il existe au minimum trois réponse possible du serveur :
- **HttpStatus.OK**

    C'est le fonctionnement normale de la requète, elle renvoie donc la valeur demandé accompagné du code HttpStatus.OK

- **HttpStatus.INTERNAL_SERVER_ERROR**

    Cela signifie que le serveur à rencontré un problème interne lors de la requète (idéalement ce message devrait ne jamais s'afficher en prod).
    Cela va concerné toutes les erreurs lié à la connection entre la base de donnée et le serveur par exemple.

- **HttpStatus.I_AM_A_TEAPOT**

    Il s'agit d'un flag qui nous permet de faire remonter une exception que nous n'avons pas anticipé.

Dans la suite de ce document, nous allons documenter le format des réponses nominal et lister les réponses Alternatives autre que INTERNAL_SERVER_ERROR et I_AM_A_TEAPOT.

# Utilisateurs
---
## Récupèrer la liste des utilisateurs
Requète : `GET /api/users`
### Réponse nominal :

HTTP Status = **OK**
```json
[
    {
        "id": 1,
        "firstname": "Jean",
        "lastname": "Dupont",
        "login": "jdupont",
        "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
        "email": "jean.dupont@reseau.eseo.fr",
        "speciality": "LD",
        "roleEntities": [
            {
                "idRole": 9,
                "role": "TEAM_MEMBER_ROLE"
            }
        ]
    },
    
    ...
]
```
---
## Récupèrer un user spécifique
Requète : `GET /api/users/:ID`

Paramètres :
- ID : id de l'utilisateur souhaité 

### Réponse nominal :
HTTP Status = **OK**
```json
{
    "id": 1,
    "firstname": "Jean",
    "lastname": "Dupont",
    "login": "jdupont",
    "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
    "email": "jean.dupont@reseau.eseo.fr",
    "speciality": "LD",
    "roleEntities": [
        {
            "idRole": 9,
            "role": "TEAM_MEMBER_ROLE"
        }
    ]
}
```

### Réponse alternative 3 (User not Found)
HTTP Status = **NOT_FOUND**

Cette erreur signifie que l'ID rentré en paramètre ne correspond à aucun utilisateur connu

---
## Ajouter un nouvel utilisateur
Requète : PUT /api/users
Body : 
```json
{
    "id": 1,
    "firstname": "Jean",
    "lastname": "Dupont",
    "login": "jdupont",
    "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
    "email": "jean.dupont@reseau.eseo.fr",
    "speciality": "LD",
    "roleEntities": [
        {
            "idRole": 9,
            "role": "TEAM_MEMBER_ROLE"
        }
    ]
}
```
### Réponse nominal
HTTP STATUS = **CREATED**

### Réponse alternative 3 : (User Already Exists)
HTTP STATUS = **CONFLICT**

---
## Mettre à jour un utilisateur
Requète : POST /api/users
Body : 
```json
{
    "id": 1,
    "firstname": "Jean",
    "lastname": "Dupont",
    "login": "jdupont",
    "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
    "email": "jean.dupont@reseau.eseo.fr",
    "speciality": "LD",
    "roleEntities": [
        {
            "idRole": 9,
            "role": "TEAM_MEMBER_ROLE"
        }
    ]
}
```

### Réponse nominale
HTTP STATUS = **OK**

### Réponse alternative 3 : (User Not Found)
HTTP STATUS = **NOT FOUND**

# Equipes

## Récupèrer toutes les équipes
Requète : GET /api/teams

### Réponse nominale
HTTP STATUS = **OK**
```json
[
    {
        "idTeam": 1,
        "name": "Equipe 1",
        "teamWorkMark": null,
        "teamValidationMark": null,
        "testBookLink": null,
        "filePathScopeStatement": null,
        "filePathFinalScopeStatement": null,
        "filePathScopeStatementAnalysis": null,
        "filePathReport": null,
        "idProjectDev": 1,
        "idProjectValidation": 2,
        "teamMembers": [
            {
                "user": {
                    "id": 1,
                    "firstname": "Jean",
                    "lastname": "Dupont",
                    "login": "jdupont",
                    "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
                    "email": "jean.dupont@reseau.eseo.fr",
                    "speciality": "LD",
                    "roleEntities": [
                        {
                            "idRole": 9,
                            "role": "TEAM_MEMBER_ROLE"
                        }
                    ]
                },
                "idTeam": 1,
                "individualMark": null,
                "bonusPenalty": null
            }
        ]
    },
    ...
]
```
---
## Récupèrer une équipe spécifique
Requète : GET /api/teams/:ID

Paramètres :
- ID : Id de la team souhaité

### Réponse nominale
HTTP STATUS = **OK**
```json
{
    "idTeam": 1,
    "name": "Equipe 1",
    "teamWorkMark": null,
    "teamValidationMark": null,
    "testBookLink": null,
    "filePathScopeStatement": null,
    "filePathFinalScopeStatement": null,
    "filePathScopeStatementAnalysis": null,
    "filePathReport": null,
    "idProjectDev": 1,
    "idProjectValidation": 2,
    "teamMembers": [
        {
            "user": {
                "id": 1,
                "firstname": "Jean",
                "lastname": "Dupont",
                "login": "jdupont",
                "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
                "email": "jean.dupont@reseau.eseo.fr",
                "speciality": "LD",
                "roleEntities": [
                    {
                        "idRole": 9,
                        "role": "TEAM_MEMBER_ROLE"
                    }
                ]
            },
            "idTeam": 1,
            "individualMark": null,
            "bonusPenalty": null
        }
    ]
}
```

### Réponse alternative 3 (Team not Found) :
HTTP STATUS : **NOT_FOUND**

---
## Récupèrer la liste des membres d'une équipe
Requète : GET /api/teams/:ID/teamMembers

Paramètres :
- ID : Id de la team souhaité

### Réponse nominale
HTTP STATUS : **OK**
```json
[
    {
        "user": {
            "id": 1,
            "firstname": "Jean",
            "lastname": "Dupont",
            "login": "jdupont",
            "password": "$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q",
            "email": "jean.dupont@reseau.eseo.fr",
            "speciality": "LD",
            "roleEntities": [
                {
                    "idRole": 9,
                    "role": "TEAM_MEMBER_ROLE"
                }
            ]
        },
        "idTeam": 1,
        "individualMark": null,
        "bonusPenalty": null
    }
]
```
### Réponse alternative 3 (Team not Found) :
HTTP STATUS : **NOT_FOUND**

---
## S'inscrire dans une équipe
Requète : PUT /api/teams/:IDTeam/:IDUser

Paramètres :
- IDTeam : Id de la team souhaité
- IDUser : Id de l'utilisateur souhaité

### Réponse nominale
HTTP STATUS : **OK**

### Réponse alternative 3 (Team not Found) :
HTTP STATUS : **NOT_FOUND**

### Réponse alternative 4 (User not Found) :
HTTP STATUS : **NOT_FOUND**

### Réponse alternative 5 (User already in team) :
HTTP STATUS : **CONFLICT**

### Réponse alternative 5 (Current user is not Request User) :
HTTP STATUS : **FORBIDDEN**

---