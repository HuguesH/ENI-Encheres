# Remember Me

## Status

proposed

## Context

Créer une fonction "remember Me" pour que l'utilisateur
n'ai pas à reconnecter lors de chaque fin de session du serveur.
Par defaut Tomcat utilise un objet Session qui est nettoyé après 30 min d'inactivité.

L'utilisation des Cookie est tout à fait approprié pour ce type de fonctions, l'utilisation du local storage et des jettons JWT permettant des cinématique OpenId Connect OAuth necessitent de piloter l'application via du JavaScript. (SPA).

## Propositions 

La gestion et la sécurisation du Cookie peut se faire de 2 moyens :  
 - 1 objet serialisé puis chiffré : AES 256 portant les informations qui permettent de créer une session l'identifiant unique de l'utilisateur et potentiellement d'autres atttributs mais surtout pas le mot de passe.

 - 2 une table SQL, qui permet pas la suite d'apporter plus de contrôle à l'utilisateur.
 la chaine de caractère transmise comme valeur d'un Cookie sera un UUID unique , clés de recherche lors de l'authentification via ce Cookie. 
 La table permettra d'enregistrer la clés mais aussi la date de création, pour piloter proprement la date d'expiration, et cette table à le gros avantage d'être affichable à l'utilisateur, elle pourra être utilisée lors d'une attaque, par l'administrateur et l'utilisateur.

### User-Agent
Pour sécuriser cette fonction et identifier un potentiel vol de Cookie, des attributs concernant la connexion peuvent doivent être ajoutés soit à l'objet sérialisé soit à la table. 
Le header User-agent est particulierement intéressant pour réaliser un contrôle de cohérence entre le Cookie proposé par la requête et la requête d'identification initiale à qui nous faisions confiance.
https://developer.mozilla.org/fr/docs/Web/HTTP/Headers/User-Agent

Au global, que ce soit un objet chiffré ou une table qui contient les données il faudra rettrouver les attributs suivants : 

```javascript
 remember-token {
     uuid :  // identifiant unique en générale sur 36 positions
     user-agent : // dans notre cas extraire le contenu de la premiere parenthese est intéressant pour beneficier de l'OS
     creation-date : // permet de faire le contrôle de la durée de validité côté serveur.
 }
```


### Cookie attributs
Dans les deux cas de figure, le Cookie doit absolument être sécurisé avec les attributs : Secure; httpOnly; SameSite=Strict
cf msdn https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie
La durée de validité du Cookie va permettre de définir la durée de validité la fonctionnalitée.

Pour plus de sécurité l'idéal est ciblé le Cookie doit être ciblé sur une URI fine pour transiter sur le réseau http le moins souvent.



## Decision

Choisir le mode de stockage

## Consequences

Décrire une cinématique détailler avec les classes impactées par cette fonctionnalitée.