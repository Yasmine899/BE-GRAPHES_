package org.insa.graphs.algorithm.utils;
import org.insa.graphs.algorithm.shortestpath.*;

//import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.Assert;
import org.junit.Test;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Node;


import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractSolution;
import java.util.ArrayList;
import java.util.List;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.ArcInspector;
import org.junit.Assert.*;

import org.junit.Test;

//final String mapName2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
//final String pathName2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

public class DjikstraTest {
    
public static void Test_Basique() throws IOException{
    String mapName1 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/guadeloupe.mapgr";
    BinaryGraphReader reader1 = new BinaryGraphReader(
        new DataInputStream(new BufferedInputStream(new FileInputStream(mapName1))));

    Graph graph1 = reader1.read();

    //chemin nul
    Node origin1 = graph1.get(256);
    Node destination1 = graph1.get(897); 
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(new ShortestPathData(graph1,origin1,destination1,ArcInspectorFactory.getAllFilters().get(0)));
    ShortestPathSolution solDijkstra3 = dijkstra.run();

    //chemin inexistant sur la map
    Node origin2 = graph1.get(256);
    Node destination2= graph1.get(897); 
    DijkstraAlgorithm dijkstra2 = new DijkstraAlgorithm(new ShortestPathData(graph1,origin2,destination2,ArcInspectorFactory.getAllFilters().get(0)));
    ShortestPathSolution solDijkstra2 = dijkstra2.run();

    //chemin inexistant sur la map

}
public void Test_grand_scenario(String mapname,int origine, int destination) throws IOException {
    /*Apres avoir cherché comment nous pouvions verifier sur les grandes cartes nos algorithmes ,nous avions procédé à faire des tests de coherences pour verifier si les chemins les plus rapides sont inferieurs en temps des PCC et supérieurs en distance */

    
    double coutDjikstra_fastestsolution_distance=0;
    double coutDjikstra_shortestsolution_distance=0;
    double coutDjikstra_fastestsolution_temps=0;
    double coutDjikstra_shortestsolution_temps=0;
    

    //Mapname1 graph1=> paris
    final GraphReader reader1 = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapname))));

    final Graph graph1 = reader1.read();

    int nbNodes=graph1.size();

        //filtre mode temps => ArcInspectorFactory.getAllFilters().get(2) all roads allowed
        //filtre mode distance => ArcInspectorFactory.getAllFilters().get(0) all roads allowed  
        System.out.println("Commencons les tests");
        //temps=>fastest
        ShortestPathData data = new ShortestPathData(graph1, graph1.get(origine),graph1.get(destination), ArcInspectorFactory.getAllFilters().get(2));
        
        DijkstraAlgorithm Djikstra = new DijkstraAlgorithm(data);
        ShortestPathSolution Djikstrasolution = Djikstra.run();

        if (Djikstrasolution.getPath() == null) {
            System.out.println("on n a pas trouvé de solution et pas de cout");
        }
        //comparaison des couts
        else {

                coutDjikstra_fastestsolution_temps = Djikstrasolution.getPath().getMinimumTravelTime();
                coutDjikstra_fastestsolution_distance = Djikstrasolution.getPath().getLength();
    }
    //Mode distance=>shortest

        data = new ShortestPathData(graph1, graph1.get(origine),graph1.get(destination), ArcInspectorFactory.getAllFilters().get(0));
        
        Djikstra = new DijkstraAlgorithm(data);

        Djikstrasolution = Djikstra.run();

        if (Djikstrasolution.getPath() == null) {
            System.out.println("on n a pas trouvé de solution et pas de cout");
        }
        //comparaison des couts
        else {

            coutDjikstra_shortestsolution_temps = Djikstrasolution.getPath().getMinimumTravelTime();
            coutDjikstra_shortestsolution_distance = Djikstrasolution.getPath().getLength();
    }

        System.out.println("Cout en temps du chemin le plus fast : " + coutDjikstra_fastestsolution_temps);
        System.out.println("Cout en distance du chemin le plus fast : " + coutDjikstra_fastestsolution_distance);
        System.out.println("Cout en temps du chemin le plus short : " + coutDjikstra_shortestsolution_temps);
        System.out.println("Cout en distance du chemin le plus short  : " + coutDjikstra_shortestsolution_distance);
        assertTrue(coutDjikstra_fastestsolution_temps <= coutDjikstra_shortestsolution_temps);
        assertTrue(coutDjikstra_fastestsolution_distance >= coutDjikstra_shortestsolution_distance);
}


}




