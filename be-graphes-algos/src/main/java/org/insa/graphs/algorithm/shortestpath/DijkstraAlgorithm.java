package org.insa.graphs.algorithm.shortestpath;
//import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {



    BinaryHeap<Label> labels_heap ;
    Label [] Labels ;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    //initialisation du tableau des labels 
    public void initialisation(){

        ShortestPathData data = getInputData();


        Graph graph = data.getGraph();

        final int nbNodes = graph.size();
    
        this.Labels =new Label[nbNodes]; 

        int i = 0;
        for (Node l : graph.getNodes()){
            this.Labels[i] = new Label(l, false, null);
            i++;
        }


        this.labels_heap = new BinaryHeap<Label>();

        //Initialisation du tableau de labels en mettant au sommet d'orgine le cout 0
        this.Labels[data.getOrigin().getId()].setCout_realise(0);

        //ok insertion faite dans le tas
        this.labels_heap.insert(this.Labels[data.getOrigin().getId()]);
    
    }

    @Override
    protected ShortestPathSolution doRun() { 

    //appel de la fonction initialisation
        initialisation();
    //les variables    
    boolean fini=false;

    final ShortestPathData data = getInputData();

    Graph graph = data.getGraph();

    ShortestPathSolution solutionfinal = null; 

    final int nbNodes = graph.size();

    // l algo se temine une fois arrivé à la destination ou tas vide 
    while (!this.labels_heap.isEmpty() && !fini ){

        //on retire le min du tas et on le marue;
        Label label_noeud_actuel =this.labels_heap.deleteMin();
        label_noeud_actuel.setMarque(true);
        //on notifie quand le sommet est marqué
        notifyNodeReached(label_noeud_actuel.getSommet_courant());

        //initialisation de la condition d'arret de la boucle
        if (label_noeud_actuel.getSommet_courant() == data.getDestination()){
           fini=true;
        }

        for (Arc les_succeseurs : label_noeud_actuel.getSommet_courant().getSuccessors() ){
            //on remplit le tab labels
            Label label_les_succeseurs=this.Labels[les_succeseurs.getDestination().getId()];
            if (!label_les_succeseurs.getmarque() && data.isAllowed(les_succeseurs)){
                //si le cout du succes est sup au cout du predecess+cout de l arc
                if (label_les_succeseurs.getTotalCost()>label_noeud_actuel.getTotalCost()+les_succeseurs.getLength()){
                   if (this.Labels[label_les_succeseurs.getSommet_courant().getId()].getTotalCost()!=Double.POSITIVE_INFINITY){
                        this.labels_heap.remove(label_les_succeseurs);
                    }
                    //on met à jour le cout
                    label_les_succeseurs.setCout_realise(label_noeud_actuel.getCout_realise()+les_succeseurs.getLength());
                    label_les_succeseurs.setPere(les_succeseurs);
                    this.labels_heap.insert(label_les_succeseurs);
                }
            }

        }
    }
    System.out.println("L'algo est fini ? :  " + fini);


 if (this.Labels[ data.getDestination().getId() ]==null || !fini ){
    solutionfinal= new ShortestPathSolution (data, AbstractSolution.Status.INFEASIBLE);
}
else{

    // The destination has been found, notify the observers.
    notifyDestinationReached(data.getDestination());

    ArrayList<Arc> liste_arcs = new ArrayList< >();

    Arc arc=this.Labels[data.getDestination().getId()].getPere();

    while (arc!=null){
        liste_arcs.add(arc);
        arc= this.Labels[arc.getOrigin().getId()].getPere();
    }

    Collections.reverse(liste_arcs);
    
    solutionfinal = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, liste_arcs));

}
return solutionfinal;

}
}
/*ALGORITHIIMIQUEMENT VOILA LE PROGRAMME
     // Initialisation

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
     */