package org.insa.graphs.gui.simple;


import java.sql.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

public class Launch {
    //les vérifications des tests automatiques later 

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    private static long Astar_time=0;
    private static long Djikstra_time=0;

    public static int test(Graph graph,String algorithme,int origine,int destination){

        int resultat_test=0;
        //test distance
        long temps_deb=0;
        long temps_fin=0;

        Path pathAlgo = null;

        if (algorithme.equals("Astar")){
            //Astar
            temps_deb = System.currentTimeMillis();
            pathAlgo = TestPath(graph,  origine, destination,  "Astar");
            temps_fin = System.currentTimeMillis();
            Astar_time += (temps_fin-temps_deb);
        } else {
            //Djikstra
            temps_deb = System.currentTimeMillis();
            pathAlgo = TestPath(graph,  origine, destination, "Djikstra");
            temps_fin = System.currentTimeMillis();
            Djikstra_time += (temps_fin-temps_deb);
        }

        Path res_BellmanFord = TestPath(graph,  origine, destination, "BellmanFord");


        // tests if the results are the same in terms of time and length
        if (comparer(pathAlgo, res_BellmanFord, true) == 1) {
            resultat_test++;
        }

        if (comparer(pathAlgo, res_BellmanFord,false) == 1) {
            resultat_test++;
        }

        return resultat_test;
    }

    //fonction comparant les deux chemins
    public static int comparer(Path chemin1, Path chemin2,boolean test_distance){
        if (chemin1 == null){
            if (chemin2 == null){
                return 1;
            } else {
                return 0;
            }
        } else {
            if (chemin2 == null){
                return 0;
            }
        }


        if(test_distance) {
            if (Float.compare(chemin1.getLength(), chemin2.getLength()) == 0) {
                return 1;
            } else {
                return 0;
            }
        }else{
            if (Double.compare(chemin1.getMinimumTravelTime(), chemin2.getMinimumTravelTime()) == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    //la fonction qui cree les chemins pour comparer

    public static Path TestPath(Graph graph,int idOrigine,int idDestination,String algorithme){

        Node origine = graph.get(idOrigine);
        Node destination = graph.get(idDestination);
        Path chemin = null;
        ArcInspector arcs= ArcInspectorFactory.getAllFilters().get(0);
        ShortestPathSolution res_algo;
        ShortestPathData data= new ShortestPathData(graph,origine,destination,arcs);

        if (algorithme.equals("Astar")){ // we chose the algorithme we want
                AStarAlgorithm AstarAlgo = new AStarAlgorithm(data);
                res_algo=  AstarAlgo.doRun();
                chemin = res_algo.getPath();
        }
        else if (algorithme.equals("Djikstra")){
                DijkstraAlgorithm DjikstraAlgo = new DijkstraAlgorithm(data);
                res_algo=  DjikstraAlgo.doRun();
                chemin = res_algo.getPath();        
        }
        else if (algorithme.equals("BellmanFord")){  
                BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
                res_algo=  BellmanFord.doRun();
                chemin = res_algo.getPath();
          
        }
        else{
            System.out.println("algorithme non valide");
         
        }        
        return chemin;

    }


    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);

        // TODO: Create a PathReader.
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(
            new BufferedInputStream(new FileInputStream(pathName))
        ));

        // TODO: Read the path.
        final Path path = pathReader.readPath(graph);

        // TODO: Draw the path. (Path path, Color color, boolean markers)
        drawing.drawPath(path, Color.CYAN, true );

        int nb_test=200;
        int resultatDjikstra=0;
        int resultatAStar=0;
        double origine,destination;
        System.out.println("commencons les tests");
        for (int i = 0; i < nb_test; i++){
            //avoir un nombre aléatoire entre 0 et la taille du graphe 
            origine= Math.floor(Math.random()*graph.size());
            destination = Math.floor(Math.random()*graph.size());
    }

}
}
