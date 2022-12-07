# Projet WiresharkBis 
## JOACHIM  Julien
## SOARES Robin

Description du projet WiresharkBis, visualisateur de flot de traffic en lignes de commandes.

Ce document donne :

    - Une description des fonctionnalites offertes
    - une description de la strategie mise en place
    - Une description de la structure du code

Consulter le fichier `HowTo.md` pour les instructions d'utilisation.

## Fonctionnalites
WiresharkBis est un visualisateur de flot de traffic. En prennant en entree les chemins vers:

    - un fichier `trace` au format texte contenant des octets capturés sur un réseau Ethernet   
    - et un fichier `filtre` au format text contenant un filtre de trame,

le visualisateur affiche un "graphe de flot" representatif du traffic filtre sur le terminal. 

Il generera dans la foulee un fichier de sortie `"FlowGraph_WireSharkBis.txt"`.

## Startegie 

WiresharkBis peut 


