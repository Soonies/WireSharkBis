# Projet WiresharkBis - Description 
## JOACHIM  Julien
## SOARES Robin

Description du projet WiresharkBis, visualisateur de flot de traffic en lignes de commandes.

Ce document donne :

    - Une description des fonctionnalités offertes
    - une description de la strategie mise en place
    - Une description de la structure du code

Consulter le fichier `HowTo.md` pour les instructions d'utilisation.

## Fonctionnalités
WiresharkBis est un visualisateur de flot de traffic. En prennant en entrée les chemins vers:

    - un fichier `trace` au format texte contenant des octets capturés sur un réseau Ethernet.
    - et un fichier `filtre` au format text contenant un filtre de trame,

le visualisateur affiche un "graphe de flot" representatif du traffic filtre sur le terminal. 

Il generera dans la foulée un fichier de sortie `"FlowGraph_WireSharkBis.txt"`.

Meme si le visualisateur peut accepter n'importe quelle trame Ethernet en entree, impliquant n'importe quel protocole, il ne sera capable d'analyser et de reconnaitre que les protocoles suivants: Ethernet, Ipv4, TCP et Http. 
En regle generale, le visualisateur sera capable de traiter n'importe quelle entree, mais n'analysera correctement que les trames impliquants les protocoles precedents.

Ainsi, si par exemple une trame encapsule un message ARP, elle sera simplement rejetee silencieusement et non affichee; tandis qu'une trame encapsulant un message TLS (Transport Layer Security) sera traitee comme un simple message TCP. Le comportement n'est pas garanti pour d'autres protocoles.

## Stratégie 

WiresharkBis sépare la tache en 3 phases:

   - (1) Parsing :
        C'est l'étape de lecture du fichier trace fourni en entrée. Elle permet d'extraire l'information des octets bruts et de la stocker dans une structure de donnée appellée `Trame`. 

        Cette étape s'effectue en O(n) # avec n = longueur de la trame brute en entrée.
        Elle permet l'accès à n'importe quel champs d'une trame en O(1) grâce à l'usage extensif de tables de Hachage. 

   - (2) Filtrage : 
        A partir d'une d'une expression booleenne fournie dans un fichier texte, le visualisateur crée l'arbre syntaxique correspondant. Il est ensuite capable de selectionner les ``Trames`` pour lesquelles l'abre précèdant s'évalue a la valeur `true`.

        La construction s'effectue en O(n) # avec n = longueur de l'expression booleenne fournie.
        L'evaluation du filtre s'effectue en O(h) avec h la profondeur de l'expression passée.
        

   - (3) Affichage : 

        L'affichage repose sur un objet `Fleche` qui stocke l'information d'un echange, qui comprend : ses addresses sources et destinations, ses ports, et un résumé du contenu du protocole le plus récent de la trame. Une `fleche` correspond exactement à une trame à afficher.

        Plusieures methodes permettent de generer la chaine de caractère representant l'échange grâce a une flèche (`---->`) orientée de l'ip source vers l'ip destination, ainsi que les informations complémentaires.``Trame``

        Le code source est fortement fractionné et fait bon usage de l'encapsulation. Ce procédé permet de faciliter la refactorisation et les ajouts futurs.

## Structure du code

```

.
├── Backend
│   ├── Info -  Objet stockant les attributs d'un protocole particulier concernant une trame
│   ├── Protocols - Objet stockant les `Infos` de toutes les trames pour tous les protocoles reconnus
│   ├── Parser - Objet lisant le fichier du client. Extrait ses informations, cree les `Infos` correspondantes et initialise un objet `Trame`
│   └── Trame - Objet representant une trame. Muni de plusieurs methodes de manipulation.
├── Display _ Point d'entree du programme.
├── Filtre
│   ├── InfixToPostfix - Converti l'expression utilisateur en notation post-fixe, utilisee pour
│   ├── Expression - Representation en memoire de la formule booleenne sous forme d'arbre. Les noeuds sont eux meme des expressions (And, Or, Protocol, Equals, Different).
│   │   ├── And
│   │   ├── Or
│   │   ├── Protocol
│   │   ├── Equals
│   │   │   ├── Field
│   │   │   └── Value
│   │   └── Different
│   └── VisitorEval
└── Affichage
    ├── Panneau - Representation de l'ecran d'affichage sous forme de matrice
    └── Fleche -  Objet contenant les informations d'une trame a afficher
        ├── Queue - Pointe source d'une `Fleche`
        └── Tete - Pointe destination d'une `Fleche`

```


