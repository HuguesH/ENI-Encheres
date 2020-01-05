# GitFlow

## Status

proposed

## Context

Dans le cadre de nos études, nous souhaitons collaborer pour réaliser des developpements.
Le choix du gestionnaires de source ne fait plus débat, Git etant la reference.
Concernant la plateforme de centralisation, deux choix sont possibles, Github.com ou Gitlab.com
Avec l'utilisation de GIT le choix de plateforme n"est pas définitif, il est intéressant de pratiquer pour les tester.
A ce jour le projet principale est centralisé sur Github.com : https://github.com/Mphil78/ENI-Enchere

Git permet de faire beaucoup de choses, il convient donc de définir un mode de fonctionnement partagé entre développeur.
Voici quelques propositions de workflow : 
 
- ** A - Fork et Pull request : ** <br>
réservé pour collaborer avec des personnes extérieur à  l'équipe
- ** B - Mono branche de travail ** <br>
proche d'un gestionnaire centralisé comme SVN, ce qui n'empeche pas les developpeurs de disposés de branches locales, non synchronisées sur le serveur : <br>
    * 1 branche develop : push autorisé si le code compile mais l'application peut être instable <br>
    * 1 branche master : l'application est fonctionnelle <br>
    * 1 branche par release ou itération pour réaliser des fix. <br>
   
- ** C - Feature branching : chaque fonctionnalité est identifiée, elle dispose de sa branche ** <br>
    * br-fonctionnalite-authentification </br>
    * br-fonctionnalite-encherir <br>
    * br-fonctionnalite-page-erreur
           

## Decision

## Consequences
