# GitFlow

## Status

proposed

## Context

Dans le cadre de nos �tudes, nous souhaitons collaborer pour r�aliser des developpements.
Le choix du gestionnaires de source ne fait plus d�bat, Git etant la reference.
Concernant la plateforme de centralisation, deux choix sont possibles, Github.com ou Gitlab.com
Avec l'utilisation de GIT le choix de plateforme n"est pas d�finitif, il est int�ressant de pratiquer pour les tester.
A ce jour le projet principale est centralis� sur Github.com : https://github.com/Mphil78/ENI-Enchere

Git permet de faire beaucoup de choses, il convient donc de d�finir un mode de fonctionnement partag� entre d�veloppeur.
Voici quelques propositions de workflow : 
- A - Fork et Pull request : r�serv� pour collaborer avec des personnes ext�rieur à l'�quipe
- B - Mono branche de travail, proche d'un gestionnaire centrealis� comme SVN,
 ce qui n'empeche pas les developpeurs de dispos�s de branches locales, non synchronis�es sur le serveur :
   - 1 branche develop : push autors� si le code compile mais l'application peut être instable
   - 1 branche master : l'application est fonctionnelle
   - 1 branche par release ou it�ration pour r�aliser des fix.
- C - Feature branching : chaque fonctionnalit� est identifi�e, elle dispose de sa branche
  - br-fonctionnalite-authentification
  - br-fonctionnalite-encherir
  - br-fonctionnalite-page-erreur
           

## Decision

## Consequences
