# GitFlow

## Status

proposed

## Context

Dans le cadre de nos Ã©tudes, nous souhaitons collaborer pour rÃ©aliser des developpements.
Le choix du gestionnaires de source ne fait plus dÃ©bat, Git etant la reference.
Concernant la plateforme de centralisation, deux choix sont possibles, Github.com ou Gitlab.com
Avec l'utilisation de GIT le choix de plateforme n"est pas dÃ©finitif, il est intÃ©ressant de pratiquer pour les tester.
A ce jour le projet principale est centralisÃ© sur Github.com : https://github.com/Mphil78/ENI-Enchere

Git permet de faire beaucoup de choses, il convient donc de dÃ©finir un mode de fonctionnement partagÃ© entre dÃ©veloppeur.
Voici quelques propositions de workflow : 
- A - Fork et Pull request : rÃ©servÃ© pour collaborer avec des personnes extÃ©rieur Ã  l'Ã©quipe
- B - Mono branche de travail, proche d'un gestionnaire centrealisÃ© comme SVN,
 ce qui n'empeche pas les developpeurs de disposÃ©s de branches locales, non synchronisÃ©es sur le serveur :
   - 1 branche develop : push autorsÃ© si le code compile mais l'application peut Ãªtre instable
   - 1 branche master : l'application est fonctionnelle
   - 1 branche par release ou itÃ©ration pour rÃ©aliser des fix.
- C - Feature branching : chaque fonctionnalitÃ© est identifiÃ©e, elle dispose de sa branche
  - br-fonctionnalite-authentification
  - br-fonctionnalite-encherir
  - br-fonctionnalite-page-erreur
           

## Decision

## Consequences
