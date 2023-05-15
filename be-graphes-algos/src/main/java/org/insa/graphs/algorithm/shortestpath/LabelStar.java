package org.insa.graphs.algorithm.shortestpath;
import java.lang.reflect.Constructor;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;


public class LabelStar extends Label  {

    private double cout;
    //constructeur


    private LabelStar label_pere;




    public LabelStar(Node sommet_courant,double cout_realise, Node destination){
        super(sommet_courant, cout_realise);
        this.cout=sommet_courant.getPoint().distanceTo(destination.getPoint());
}

    public LabelStar(Node sommet_courant,boolean marque, Arc pere, Node destination){
        super(sommet_courant,marque,pere);
        this.cout=sommet_courant.getPoint().distanceTo(destination.getPoint());
}
    public LabelStar getLabel_pere() {
        return label_pere;
    }
//l'équivalentethisa() realise dans Djikstra


    public double getTotalCost() {
        return this.getCout_realise()+this.cout;
}

     

}


