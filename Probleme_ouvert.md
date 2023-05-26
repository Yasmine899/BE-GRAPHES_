# Plan
## titre 2
Proposition de l'algorithme 
Essayez de démontrer que votre algorithme donne une solution correcte, bonus : démontrer que la solution est optimale.
Calculez la complexité de votre algorithme

```
Initialisation : 
  For tous les sommets i de 1 a n :
    MArk(i) <- Faux
    Cost(i) <- $\infty$
    Father(i) <- 0
  end for
  
  Cost(s) <- 0
  Insert
```
for 𝑥 ∈ 𝑋 loop
    Label(x).Cost = +∞ ; 
    Label(x).Father =NULL; 
    Label(x).Mark =FALSE
end loop

Label(point d'origine).Cost = 0 ;
Insert(Tas, Label(𝑜))

// Itérations
while not IsEmpty(Tas) et on est pas arrivé à la destination=> donc on definit un booleen loop
    x=DeleteMin(Tas)
    for 𝑦 ∈ Successeurs(𝑥) loop}
        if not Label.Mark(𝑦) then
            if Label(𝑦).Cost >Label.Cost(𝑥) + CostArc(𝑥, 𝑦) then
// mise à jour
                if Label(𝑦).Cost = +∞ then
                    remove=true;
                else
                    remove=false
                end if
                Label(𝑦).Cost = Label.Cost(𝑥) + CostArc(𝑥, 𝑦)
                Label(𝑦).Father =x
                if Remove then
                    Remove(Tas, Label(𝑦))
                end if
                Insert(Tas, Label(𝑦))
            end if
        end if
    end loop
Label(𝑥).Mark =true
end loop
