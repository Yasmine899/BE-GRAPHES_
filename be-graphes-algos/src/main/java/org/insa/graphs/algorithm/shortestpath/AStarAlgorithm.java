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



public class AStarAlgorithm extends DijkstraAlgorithm {
    
    public AStarAlgorithm(ShortestPathData data){
        super(data);
    }

    public void initialisation(){

        ShortestPathData data = getInputData();
        
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        this.Labels =new LabelStar[nbNodes]; 

        int i = 0;
        for (Node l : graph.getNodes()){
            this.Labels[i] = new LabelStar( l, false, null ,data.getDestination());
            i++;
        }

        this.labels_heap = new BinaryHeap<>();

        //Initialisation du tableau de labels en mettant au sommet d'orgine le cout 0
        this.Labels[data.getOrigin().getId()].setCout_realise(0);

        //ok insertion faite dans le tas
        this.labels_heap.insert(this.Labels[data.getOrigin().getId()]);
        System.out.println(labels_heap);
    
    }

    

}


