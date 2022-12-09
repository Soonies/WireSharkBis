# Projet WiresharkBis - Tutoriel 
## JOACHIM  Julien
## SOARES Robin

Tutoriel d'utilisatin du projet WiresharkBis, visualisateur de flot de traffic en lignes de commandes.  
Regardez Cette video ----> https://youtu.be/hOIrhRnbJSw pour une presentation du projet !!

Ce document donne :
    - Les instructions d'utilisation de WiresharkBis
    - Les instructions de formatage du fichier trace d'entrée
    - Les instructions de formatage du fichier filre d'entrée
Consulter le fichier `README.md` pour la description du projet et des fonctionnalités qu'il propose.

## Instructions d'utilisation
WiresharkBis est un visualisateur de flot de traffic intenet s'executant en ligne de commande.

Voici la procedure a suivre pour l'utiliser:
    
- 1 - Telecharger le projet WiresharkBis_JOACHIM_SOARES.zip et le decompresser.
- 2 - Recuperer l'archive `Start.jar` à l'emplacement `./ProjectRoot/`, avec `.` etant la racine du fichier decompressé, et la placer dans le dossier de votre choix. Elle peut aussi rester dans le dossier original.
- 3 - Se deplacer à l'emplacement du fichier `Start.jar` sur le terminal. 
- 4 - Enfin, executer la commande :

        java -jar Start.jar /chemin/de/la/trace/foo.txt /chemin/du/filtre/bar.txt

Le graphe de flux s'affiche alors sur le terminal.

Vous pourrez recuperer le fichier de trace d'execution `"FlowGraph_WireSharkBis.txt"` dans le meme dossier que celui de `Start.jar`.

## Formatage de la trace de traffic

Le fichier trace fourni par l'utilisateur, contenant les octets capturés préalablement sur un réseau Ethernet, doit respecter un certain formatage.

*WiresharkBIS* utilise un formatage different de celui decrit dans la consigne du projet. En effet, notre professeur referent ( Monsieur SPATHIS ) nous a **tres exactement** donné la marche à suivre que nous avons decrite plus bas pour obtenir des fichiers traces utilisables. Nous l'avons donc suivie. *( Merci de nous contacter at : robin.soares@etu.sorbonne-universite.fr, ou at: julien.joachim@etu.sorbonne-universite.fr pour toute information complementaire sur ce detail. )*

En particulier, le fichier de trace doit etre generé par le logiciel Wireshark, en faisant les actions suivantes :
- File > Export Packet Dissections > As Plain Text
- Dans la fenetre "Packet Range" ( En bas a gauche ), selectionner All Packets
- Dans la fenetre "Packet Format", cocher UNIQUEMENT la case "Packet Bytes" et deselectionner toutes les autres
- Enfin enregistrez votre fichier trace avec le nom de votre choix en cliquant sur "Save" 


Cependant, les deux seules differences entre le formatage fournit par Wireshark et celui decrit dans la consigne du projet sont:
- que WiresharkBIS attend *2* octets entre l'offset des données et le premier octet, tandis que la consigne en donne *3*.
- WiresharkBIS attend que chaque trame soit separée par une ligne vide.


Ci-dessous une description precise du format attendu:

 - Une trame est composée d'un bloc de plusieures lignes contigues.

 - Chaque ligne commence par 4 characteres d'offset, suivis de *2* espaces (et non 3), et de 16 octets de donees codes en hexadecimal. Cette ligne peut etre suivie (ou non) de characteres ASCII.

 - Les blocs représentant des trames sont separes l'un de l'autre par une ligne vide. 

Des exemples de fichiers traces sont fournis dans le dossier 
`WiresharkBIS_JOACHIM_SOARES/WireSharkBis/ProjectRoot/data/`

Encore une fois, l'utilisateur pourra génerer ses propres fichiers traces graces aux instructions données plus haut.

## Formatage du fichier Filtre

WiresharkBIS permet l'utilisation d'un filtre sous forme de formule booleenne. Ce filtre doit être contenu dans un fichier texte. Voici comment en écrire un:

Si il n'y a pas de filtre à appliquer sur le visualisateur, il suffit de donner un fichier .txt vide.  
Si un filtre est nécessaire, il suffit d'ecrire son expression sur une ligne dans le fichier .txt qui sera donné en argument.  

L'expression de celui ci est une formule booléenne, dont les opérateurs à utiliser sont listes plus bas.  
Tout expression booleenne legale, utilisant tous les champs listes tout en bas de ce fichier fonctionnera!  
Cependant, l'opérateur NOT n'est pas implémenté. *(Utiliser la loi de De Morgan!)*

### Symboles utilises
Opérateur   | Opérateur sur WireSharkBis
:----------:|:----------------------:
&&          | &
\|\|        | \|
!           | ∅ ( pas d'implementation )

Comparaison de Champ a une valeur   | Comparaison sur WireSharkBis
:----------:|:----------------------:
champ == val         | champ=val 
champ != val         | champ!val



<u>/!\ Attention /!\ </u>  
Il <u> FAUT METTRE</u> un espace avant et après chaque **opérateur** *( & et | )* et **parenthese**.  
Il  <u>NE FAUT PAS METTRE</u> d'espace avant et après chaque **champ** *( = et ! )*. 

 
 
Exemple d'utilisation de filtre :  

Expression  | Expression WireSharkBis
:--------:  |:----------------------:
tcp.srcPort == 80 | tcp.portSrc=80
tcp && http | tcp & http
eth.dst == 00:00:5e:00:53:00 | ethernet.macDst=00:00:5e:00:53:00
(ip.ttl == 16 \|\| ip.dst != 255.255.255.255) && ( http \|\| tcp) | ( ipv4.ttl=16 \| ipv4.ipDst!255.255.255.255 ) & ( http \| tcp )


Voici toute la liste des champs que vous pourrez éxaminer. Simplement faire suivre le protocole concerné d'un point, puis du nom du champ à observer. Exemple :  `tcp.CWR=1`  pour tester si le flag `CWR` de l'entete TCP est levé.
```       
├── ethernet.
│   ├── macDst
│   ├── macSrc
│   └── next
├── ipv4.
│   ├── version
│   ├── lengthHeader
│   ├── ToS
│   ├── lengthTota
│   ├── id
│   ├── flag
│   ├── offset
│   ├── ttl
│   ├── protocol
│   ├── checksum
│   ├── ipSrc
│   └── ipDst
├── tcp.
│   ├── portSrc
│   ├── portDst
│   ├── sequenceNumber
│   ├── ackNumber
│   ├── offset
│   ├── reserved
│   ├── NS
│   ├── CWR
│   ├── ECE
│   ├── URG
│   ├── ACK
│   ├── PSH
│   ├── RST
│   ├── SYN
│   ├── FIN
│   └── window
└── http.
    └── messageEnClair
```
