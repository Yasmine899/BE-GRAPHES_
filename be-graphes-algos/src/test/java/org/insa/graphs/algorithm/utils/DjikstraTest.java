package org.insa.graphs.algorithm.utils;
import org.insa.graphs.algorithm.shortestpath.*;

//import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
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


public class DjikstraTest {
    @Test
    public void Test() throws IOException {
    List<ArcInspector> ListeInspector=ArcInspectorFactory.getAllFilters();
    final String mapName1 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/paris.mapgr";

    final String mapName2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
    final String pathName2 = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

    //Mapname1 graph1=> paris
    final GraphReader reader1 = new BinaryGraphReader(
            new DataInputStream(new BufferedInputStream(new FileInputStream(mapName1))));

    final Graph graph1 = reader1.read();



    //Mapname2 graph2=> bretagne

    final GraphReader reader2 = new BinaryGraphReader(
        new DataInputStream(new BufferedInputStream(new FileInputStream(mapName2))));

    final Graph graph2 = reader2.read();

    //chemin nul

    Node origine1 =graph1.get(500);
    Node destination1 =graph1.get(500);
    DijkstraAlgorithm test_djikstra1=new DijkstraAlgorithm(new ShortestPathData(graph1, origine1, destination1, ListeInspector.get(0)));
    ShortestPathSolution solution_Djikstra1=test_djikstra1.run();


    //chemin inexistant sur une ile et un moint inexistant

    Node origine2=graph2.get(546481);
    Node destination2 =graph2.get(126348);
    DijkstraAlgorithm test_djikstra2=new DijkstraAlgorithm(new ShortestPathData(graph2, origine2, destination2, ListeInspector.get(0)));
    ShortestPathSolution solution_Djikstra2=test_djikstra2.run();

    Assert.assertEquals(0.0,solution_Djikstra2.getPath().getLength(), 0.2);



    //test 

    }




}