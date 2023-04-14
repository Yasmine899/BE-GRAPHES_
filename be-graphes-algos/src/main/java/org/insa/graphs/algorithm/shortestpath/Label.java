package org.insa.graphs.algorithm.shortestpath;

import java.lang.reflect.Constructor;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    private Node sommet_courant; 
    private boolean marque;
    private Label label_pere;
    private Node sommet_pere; 
    private double cout_realise; 
    private Arc pere;

    
    public Label(Node sommet, double cout_realise){
        this.sommet_courant=sommet;
        this.sommet_pere=null;
        this.cout_realise=cout_realise;
        this.marque=false;
        this.pere=null;
        this.label_pere=null;
    }

    public Node getSommet_courant() {
        return sommet_courant;
    }
    public Label getLabel_pere() {
        return label_pere;
    }
    public void setLabel_pere(Label label_pere) {
        this.label_pere = label_pere;
    }
    
    public int compareTo(Label label){
        return (this.cout_realise>label.cout_realise)?1:
        (this.cout_realise==label.cout_realise)?0:-1 ;
        }

    public double getCout_realise() {
        return cout_realise;
    }

    public void setCout_realise(double cout_realise) {
        this.cout_realise = cout_realise;
    }

    public boolean getmarque(){
        return marque;
    }
    public Arc getPere() {
        return pere;
    }
 
    public void setSommet_courant(Node sommet_courant) {
        this.sommet_courant = sommet_courant;
    }
    public void setPere(Arc pere) {
        this.pere = pere;
    }

 
    public void setMarque(boolean marque) {
        this.marque = marque;
    }


}
