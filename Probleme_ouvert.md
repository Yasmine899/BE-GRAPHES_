# Plan
## titre 2
Proposition de l'algorithme 
Essayez de démontrer que votre algorithme donne une solution correcte, bonus : démontrer que la solution est optimale.
Calculez la complexité de votre algorithme


Si on considere la distance plus importante que le cout de rechargement
```
# Label.compareTo(Label, autre_Label) {
Status 
if Label.Cost < autre_Label.Cost :
    return -1
else if Label.Cost == autre_Label.Cost :
    if Label.ArcParcourus < autre_Label.ArcParcourus :
        return -1
    else if Label.ArcParcourus == autre_Label.ArcParcourus :
        retourner 0
    else retourner 1
else :
    return 1
}
```




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
