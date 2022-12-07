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

    - un fichier `trace` au format texte contenant des octets capturés sur un réseau Ethernet.
    - et un fichier `filtre` au format text contenant un filtre de trame,

le visualisateur affiche un "graphe de flot" representatif du traffic filtre sur le terminal. 

Il generera dans la foulee un fichier de sortie `"FlowGraph_WireSharkBis.txt"`.

Meme si le visualisateur peut accepter n'importe quelle trame Ethernet en entree, 
## Startegie 

WiresharkBis separe la tache en 3 phases:
    - (1) Parsing :
        C'est l'etape de lecture du fichier trace fourni en entree. Elle permet d'extraire l'information des octets bruts et de la stocker dans une structure de donnee appellee `Trame`. 

        Cette etape s'effectue en O(n) # avec  n = .lobgueur de la trame brute en entree.
        Elle permet l'acces a n'importe quel champs d'une trame en O(1) grace a l'usage extensif de Tables de Hachage. 

    - (2) Filtrage : 
        A partir d'une d'une expression booleenne fournie dans un fichier texte, le visualisateur cree l'arbre syntaxique correspondant. Il est ensuite capable de selectionner les ``Trame``   
        

    - (3) Affichage : 



