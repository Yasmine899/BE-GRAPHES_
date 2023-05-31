package org.insa.graphs.algorithm.shortestpath;
import java.lang.reflect.Constructor;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label  {

    private double cout;

    public LabelStar(Node sommet_courant,boolean marque, Arc pere,Node destination){
        super(sommet_courant, marque,pere);
        this.cout=sommet_courant.getPoint().distanceTo(destination.getPoint());
}

//l'équivalentethisa() realise dans Djikstra


    public double getTotalCost() {
        return (this.getCout_realise()+this.cout);
}

     

}


