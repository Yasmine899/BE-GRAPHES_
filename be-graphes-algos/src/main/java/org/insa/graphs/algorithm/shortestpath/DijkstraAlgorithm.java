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

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() { 
       /* 
       boolean fini=false;
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
       // Retrieve the graph.
        final int nbNodes = graph.size();
        Label label_success = null;

        // Initialize array of distances.
        // Double[] distances = new Double[nbNodes];

       // ArrayList<Label> label_array= new ArrayList<Label>();
        
        for (int i=0; i<nbNodes; i++){
            if (graph.getNodes().get(i)==data.getOrigin()){ 
                label_array.set(i,new Label(graph.getNodes().get(i), 0.0));
                continue;
            }
            label_array.set(i, new Label(graph.getNodes().get(i),Double.POSITIVE_INFINITY) );
        }

        
        BinaryHeap<Label> labels_heap = new BinaryHeap<Label>();
        
        // Arrays.fill(labels, Double.POSITIVE_INFINITY);
        // labels[data.getOrigin().getId()] = ;  
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());      

        labels_heap.insert(label_array.get(0));

        while (!labels_heap.isEmpty() && !fini ){
            Label label_noeud_actuel =labels_heap.deleteMin();
            label_noeud_actuel.setMarque(true);

            if (label_noeud_actuel.getSommet_courant() == data.getDestination()){
               fini=true;
            }

            for (Arc sucess_arc : label_noeud_actuel.getSommet_courant().getSuccessors() ){
                label_success = label_array.get( graph.getNodes().indexOf(sucess_arc.getDestination()) );
                if (!label_success.getmarque()){
                    if (label_success.getCout_realise()>label_noeud_actuel.getCout_realise()+sucess_arc.getLength()){
                        label_success.setCout_realise(label_noeud_actuel.getCout_realise()+sucess_arc.getLength());
                        if (labels_heap.exist(label_success)){
                            labels_heap.remove(label_success);
                            labels_heap.insert(label_success);
                        } else {
                            labels_heap.insert(label_success);
                        }
                        label_success.setPere(sucess_arc);
                        label_success.setLabel_pere(label_noeud_actuel);
                    }
                }
            }
            
       }
       if (labels_heap.isEmpty() && !fini){
            return new ShortestPathSolution(data, Status.INFEASIBLE);
       }

        ArrayList<Arc> arc_array = new ArrayList<Arc>();
        Label Aux= label_array.get(graph.getNodes().indexOf(data.getDestination()));
        while (Aux.getSommet_courant()!=data.getOrigin()){
            arc_array.add(Aux.getPere());
            Aux= Aux.getLabel_pere();
        }
        Collections.reverse(arc_array);
            
        return new ShortestPathSolution( data,  Status.FEASIBLE , new Path(graph, arc_array) ) ;
    }*/
    //les variables
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
    for 𝑦 ∈ Successeurs(𝑥) loop
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

    
    boolean fini=false;

    final ShortestPathData data = getInputData();

    Graph graph = data.getGraph();

    ShortestPathSolution solutionfinal = null; 

    final int nbNodes = graph.size();

    Label [] Labels =new Label[nbNodes]; 

    ///initialisation du tableau de labels ,elle m'a saoulée avant de trouver prq l' algo marchait pas
    int i = 0;
    for (Node l : graph.getNodes()){
        Labels[i] = new Label(l, false, null);
        i++;
    }


    BinaryHeap<Label> labels_heap = new BinaryHeap<Label>();

    //Initialisation du tableau de labels en mettant au sommet d'orgine le cout 0
    Labels[data.getOrigin().getId()].setCout_realise(0);

    //ok insertion faite dans le tas
    labels_heap.insert(Labels[data.getOrigin().getId()]);

    // l algo se temine une fois arrivé à la destination ou tas vide 
    while (!labels_heap.isEmpty() && !fini ){

        //on retire le min du tas et on le marue;
        Label label_noeud_actuel =labels_heap.deleteMin();
        label_noeud_actuel.setMarque(true);
        //on notifie quand le sommet est marqué
        notifyNodeReached(label_noeud_actuel.getSommet_courant());

        //initialisation de la condition d'arret de la boucle
        if (label_noeud_actuel.getSommet_courant() == data.getDestination()){
           fini=true;
        }

        for (Arc les_succeseurs : label_noeud_actuel.getSommet_courant().getSuccessors() ){
            //on remplit le tab labels
            Label label_les_succeseurs=Labels[les_succeseurs.getDestination().getId()];
            if (!label_les_succeseurs.getmarque() && data.isAllowed(les_succeseurs)){
                //si le cout du succes est sup au cout du predecess+cout de l arc
                if (label_les_succeseurs.getCout_realise()>label_noeud_actuel.getCout_realise()+les_succeseurs.getLength()){
                   if (Labels[label_les_succeseurs.getSommet_courant().getId()].getCout_realise()!=Double.POSITIVE_INFINITY){
                        labels_heap.remove(label_les_succeseurs);
                    }
                    //on met à jour le cout
                    label_les_succeseurs.setCout_realise(label_noeud_actuel.getCout_realise()+les_succeseurs.getLength());
                    label_les_succeseurs.setPere(les_succeseurs);
                    labels_heap.insert(label_les_succeseurs);
                }
            }

        }
    }


 if (Labels[ data.getDestination().getId() ]==null || !fini ){
    solutionfinal= new ShortestPathSolution (data, AbstractSolution.Status.INFEASIBLE);
}
else{

    // The destination has been found, notify the observers.
    notifyDestinationReached(data.getDestination());

    ArrayList<Arc> liste_arcs = new ArrayList< >();

    Arc arc=Labels[data.getDestination().getId()].getPere();

    while (arc!=null){
        liste_arcs.add(arc);
        arc= Labels[arc.getOrigin().getId()].getPere();
    }

    Collections.reverse(liste_arcs);
    
    solutionfinal = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, liste_arcs));

}
return solutionfinal;

}
}
