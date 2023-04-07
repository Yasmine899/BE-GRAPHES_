package org.insa.graphs.algorithm.shortestpath;

import java.lang.reflect.Constructor;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label extends Comparable {
    private Node sommet_courant; 
    private boolean marque;
    private Node sommet_pere; 
    private double cout_realise;
    private Arc pere;

    
    public Label(Node sommet){
        
    }
    
    public int compareTo(){

    }

    public double getCout_realise() {
        return cout_realise;
    }

    public boolean getmarque(){
        return marque;
    }
    
    public void setSommet_courant(Node sommet_courant) {
        this.sommet_courant = sommet_courant;
    }
    public void setPere(Arc pere) {
        this.pere = pere;
    }
    
}
