package org.insa.graphs.algorithm.shortestpath;

import java.lang.reflect.Constructor;

import javax.lang.model.util.ElementScanner6;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    private Node sommet_courant; 
    private boolean marque;
    protected double cout_realise; 
    private Arc pere;

    
    public Label(Node sommet_courant,boolean marque, Arc pere){
        this.sommet_courant=sommet_courant;
        this.cout_realise=Double.POSITIVE_INFINITY;
        this.marque=marque;
        this.pere=pere;
    }




    public Node getSommet_courant() {
        return sommet_courant;
    }

    //ancienne version de compare to pour djikstra,on la modifie pour A star
    /*public int compareTo(Label label){
        return (this.cout_realise>label.cout_realise)?1:
        (this.cout_realise==label.cout_realise)?0:-1 ;
        }*/
    //implémenté dans A star
    public double getTotalCost() {
            return this.cout_realise;
    }
        
    public int compareTo(Label label){
        return (this.getTotalCost()>label.getTotalCost())?1:
        (this.getTotalCost()==label.getTotalCost())?0:-1 ;
        }
    
        //on définit la méthode getTotalCost pour le djikstra
    public double getCout_realise() {
        return this.cout_realise;
    }




    public Arc getPere() {
        return pere;
    }
 
    public void setSommet_courant(Node sommet_courant) {
        this.sommet_courant = sommet_courant;
    }

    public boolean getmarque(){
        return marque;
    }

    public void setCout_realise(double cout_realise) {
        this.cout_realise = cout_realise;
    }

    public void setPere(Arc pere) {
        this.pere = pere;
    }

 
    public void setMarque(boolean marque) {
        this.marque = marque;
    }
    



}
