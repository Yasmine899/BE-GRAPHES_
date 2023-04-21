package org.insa.graphs.algorithm.shortestpath;
import java.lang.reflect.Constructor;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;


public class LabelStar extends Label {

    private double cout;
    //constructeur

    public LabelStar(Node sommet_courant,boolean marque,Arc sommet_pere){
        super(sommet_courant, marque,sommet_pere);
        this.cout=cout;

}
//l'équivalente de la methode get cout realise dans Djikstra
    public double getTotalCost(){
        return this.cout+this.getCout_realise();
    }   
}
