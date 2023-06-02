package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.*;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

// for colouring
import java.time.Duration;
import java.util.Random;
 import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Launch {
    private static int verbose = 1;
    private static long timeA = 0;
    private static long timeD = 0;


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


    // generates a path with the chosen algorithm
    public static Path createTestPath(Graph graph,int idOrigin,int idDest,String algo){
        Node A = graph.get(idOrigin);
        Node B = graph.get(idDest);
        Path resu = null;
        ArcInspector ins = ArcInspectorFactory.getAllFilters().get(0);
        ShortestPathSolution sol;
        ShortestPathData data= new ShortestPathData(graph,A,B,ins);

        switch (algo){ // we chose the algo we want
            case "A*":
                AStarAlgorithm AAlgo = new AStarAlgorithm(data);
                sol =  AAlgo.doRun();
                resu = sol.getPath();

                break;
            case "D":
                DijkstraAlgorithm DAlgo = new DijkstraAlgorithm(data);
                sol =  DAlgo.doRun();
                resu = sol.getPath();

                break;

            case "BF":
                BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
                sol =  BF.doRun();
                resu = sol.getPath();

                break;
            default:
                if (verbose == 1) { System.out.println("no known algorithm");}
                break;
        }
        return resu;

    }
    public static int comparePath(Path p1, Path p2,boolean time){
        // true if both are null or if they have
        //    - the same time cost
        //    - the same length
        if (p1 == null){
            if (p2 == null){
                return 1;
            } else {
                return 0;
            }
        } else {
            if (p2 == null){
                return 0;
            }
        }


        if(time) {
            if (Double.compare(p1.getMinimumTravelTime(), p2.getMinimumTravelTime()) == 0) {
                return 1;
            } else {
                return 0;
            }
        }else{
            if (Float.compare(p1.getLength(), p2.getLength()) == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static void displayResult(String algo, int justeD, int nbTest, long time){
        if (verbose == 1) {
            System.out.println(
                    algo + " : number of good tests : " + Integer.toString(justeD) + "/"
                            + Integer.toString(nbTest * 2) + ". Total execution time : " + Long.toString(time) + "ms");
        }
    }




    private static int test(Graph graph, String testType,Drawing drawing, int id1, int id2){
        int resu = 0;
        long endTime;
        long startTime;
        if (verbose == 1) { System.out.print("testing " + testType +" with nodes " + id1 + " and " + id2);}

        Path pathAlgo = null;

        if (testType == "A*"){

            // times the run of the algorithm

            startTime = System.currentTimeMillis();
            pathAlgo = createTestPath(graph,  id1, id2, "A*");
            endTime = System.currentTimeMillis();

            if (verbose == 2){System.out.println(endTime-startTime);}
            timeA += (endTime-startTime);
        } else {

            // times the run of the algorithm

            startTime = System.currentTimeMillis();
            pathAlgo = createTestPath(graph,  id1, id2, "D");
            endTime = System.currentTimeMillis();
            timeD += (endTime-startTime);
            if (verbose == 2){if (pathAlgo != null) {System.out.print(pathAlgo.getLength()+","+(endTime-startTime) + ",");}}
        }

        Path pathBF = createTestPath(graph,  id1, id2, "BF");


        // tests if the results are the same in terms of time and length
        if (comparePath(pathAlgo, pathBF, true) == 1) {
            resu++;
        }

        if (comparePath(pathAlgo, pathBF,false) == 1) {
            resu++;
        }

        // draw the path created on the map (purely visual)
        Path drawPath = pathAlgo;
        if (drawPath != null){
            if (!drawPath.isEmpty()){
                try {
                    if (testType == "A*") {
                        drawing.drawPath(drawPath, new Color(150, 0, 150));
                    } else {
                        drawing.drawPath(drawPath, new Color(20, 200, 20));
                    }
                } catch (Exception e) {}
                if (verbose == 1) { System.out.println(" ... Done (length : " + Float.toString(drawPath.getLength()) + " found in " + Long.toString(endTime-startTime) + " ms)");}
            }
        }
        return resu;
    }





    public static void main(String[] args) throws Exception {

        // === these file are local the paths need to be adapted ===
        // Due to the size of the files we chose not to include these in the git repository

        // Visit these directory to see the list of available files on Commetud.
        //final String mapName = "/home/rlacroix/Bureau/3MIC/Be Graphe/mapinsa/insa.mapgr";
        //final String mapName = "/home/rlacroix/Bureau/3MIC/Be Graphe/mapinsa/carre-dense.mapgr";
        final String mapName = "/home/rlacroix/Bureau/3MIC/Be Graphe/mapinsa/california.mapgr";
        //final String pathName = "/home/rlacroix/Bureau/3MIC/Be Graphe/mapinsa/path_fr31insa_rangueil_r2.path";

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
        //final PathReader pathReader = new BinaryPathReader(
        //        new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

        // TODO: Read the path.
        //final Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
        //drawing.drawPath(path);



        int nbTest = 2000;
        int justeD = 0;
        int justeA = 0;

        if (verbose == 1) { System.out.println("== LAUNCHING " + nbTest + " TESTS ==");}

        for (int i = 0; i < nbTest; i++){
            // picks two nodes at random
            int id1 = (int) Math.floor(Math.random()*graph.size());
            int id2 = (int) Math.floor(Math.random()*graph.size());

            if (i%10 == 0){
                // cleans the board every 10 runs and display the comparative runtimes
                drawing.clearOverlays();
                displayResult("Dijkstra",justeD,i,timeD);
                displayResult("A*",justeA,i,timeA);
                if (verbose == 1) { System.out.println("A* takes "+ Long.toString(100*(timeA+1)/(timeD+1)) +"% of Dijkstra's execution time");}
            }
            if (verbose == 1) { System.out.println("Test number " + Integer.toString(i));}
            justeD += test(graph,"D",drawing, id1, id2);
            justeA += test(graph,"A*",drawing,id1, id2);
        }
        if (verbose == 1) { System.out.println("===== Final Results =====");}
        displayResult("Dijkstra",justeD,nbTest,timeD);
        displayResult("A*",justeA,nbTest,timeA);
        if (verbose == 1) { System.out.println("A* takes "+ Long.toString(100*timeA/timeD) +"% of Dijkstra's execution time");}


    }
}