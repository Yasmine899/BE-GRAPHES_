package org.insa.graphs.algorithm.shortestpath;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() { 
        boolean fini=false;
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
       // Retrieve the graph.
        final int nbNodes = graph.size();
        Label label_success = null;

        // Initialize array of distances.
        // Double[] distances = new Double[nbNodes];

        ArrayList<Label> label_array= new ArrayList<Label>();
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
    }

}
