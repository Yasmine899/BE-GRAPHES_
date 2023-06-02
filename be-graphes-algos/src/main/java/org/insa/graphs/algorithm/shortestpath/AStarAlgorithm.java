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
//astar herite de djikstra ce qui change c est le tableau de label et le calcul du cout(cf vol d'oiseau)
    public void initialisation(){
        int i = 0;
        for (Node noeud : graph.getNodes()){
            this.Labels[i] = new LabelStar( noeud, false, null ,data.getDestination());
            i++;
        }
    
    }
    

}


