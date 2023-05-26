# Plan
## titre 2
Proposition de l'algorithme 
Essayez de démontrer que votre algorithme donne une solution correcte, bonus : démontrer que la solution est optimale.
Calculez la complexité de votre algorithme


## Probleme choisi : Les véhicules électriques (contraintes d'autonomie)

Nous avons choisis de nous pencher sur le probléme des voiture électriques. Ce dernier nous semblait pertinent car il nous rappelait une des question de l'examen de Graphe.

Le probléme peut être résumé ainsi :

Nous avons besoin d'un algorithme de recherche de plus court chemin. Ce dernier doit prendre en compte certaines contraites : 
- Le véhicule sensé empreinter le chemin a une autonomie limitée de 200km. "Son autonomie est limitée à 200km"
- Il faut cependant pouvoir realiser des trajets plus longs que 200km "Proposez une méthode de calcul d'itinéraires longs (supérieurs à 200km)"
- Le véhicule peut se recharger a des points de recharges. le temps de recharge est de 2minutes.
- Ces points de recharge sont présents sur certains sommets mais pas tous

- Optionellement on cherche a passer par le moins de stations possible

    Pour information, notez que l'autonomie décroît fortement avec la vitesse (comme pour les véhicules thermiques d'ailleurs) : estimation de l'autonomie électrique. 



Conceptuellement, ce dont on a besoin c'est un algorithme qui trouve si la destination est accessible pour le véhicule électrique. Ensuite si plusieurs trajets sonts possibles, il faut trouver lequel est le plus court.

Le premier probléme est celui de trouver un chemin.

Le cas simple est si la destination est à moins de 200 km de l'origine. Dans ce cas le trajet est toujours possible.
Si la destination est a plus de 200 km, à ce moment il faut passer inévitablement par une station de recharge.
Les stations de recharge et le point d'origine  forment des zones, de 200 km de rayon que le véhicule peut atteindre


```
# Initialisation:


for 𝑥 ∈ 𝑋 loop
    Label(x).Cost = +∞  
    Label(x).ArcParcourus
    Label(x).Father =NULL 
    Label(x).Mark =FALSE
end loop

Label(point d'origine).Cost = 0 ;
Insert(Tas, Label(𝑜))

# Itérations
while not IsEmpty(Tas) et Label(destination).Mark == False
    x=DeleteMin(Tas)
    for 𝑦 ∈ Successeurs(𝑥) et CostArc(x,y)<200km loop :
        if not Label.Mark(𝑦) then
            if Label(𝑦).Cost >Label.Cost(𝑥) + CostArc(𝑥, 𝑦) then
// mise à jour
                Label(𝑦).Cost = Label.Cost(𝑥) + CostArc(𝑥, 𝑦)
                Label(𝑦).Father =x
                Insert(Tas, Label(𝑦))
            end if
        end if
    end loop
Label(𝑥).Mark =true
end loop
```

Si on considere la distance plus importante que le cout de rechargement
```
#
Label.compareTo(Label, autre_Label) {
Status 
if Label.Cost < autre_Label.Cost :
    retourner -1
else if Label.Cost == autre_Label.Cost :
    if Label.ArcParcourus < autre_Label.ArcParcourus :
        retourner -1
    else if Label.ArcParcourus == autre_Label.ArcParcourus :
        retourner 0
    else retourner 1
else :
    retourner 1
}
```

