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

    public static int test_temps(Graph graph,String algorithme,int origine,int destination){

        int resultat_test_temps=0;
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
       // if (pathAlgo==null) return 0;

        Path res_BellmanFord = TestPath(graph,  origine, destination, "BellmanFord");

        if (comparer_temps(pathAlgo, res_BellmanFord) == 1) {
            resultat_test_temps++;
        }

        return resultat_test_temps;
    }

    public static int test_distance(Graph graph,String algorithme,int origine,int destination){

        int resultat_test_distance=0;
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
        //if (pathAlgo==null) return 0;

        Path res_BellmanFord = TestPath(graph,  origine, destination, "BellmanFord");

        if (comparer_distance(pathAlgo, res_BellmanFord) == 1) {
            resultat_test_distance++;
        }

        return resultat_test_distance;
    }

    //fonction comparant les deux chemins en temps
    public static int comparer_temps(Path chemin1, Path chemin2){
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
            if (Double.compare(chemin1.getMinimumTravelTime(), chemin2.getMinimumTravelTime()) == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    //fonction comparant les deux chemins en distance
    public static int comparer_distance(Path chemin1, Path chemin2){
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
            if (Float.compare(chemin1.getLength(), chemin2.getLength()) == 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
    

    //la fonction qui cree les chemins pour comparer_temps et distance

    public static  Path TestPath(Graph graph,int idOrigine,int idDestination,String algorithme){

        Node origine = graph.get(idOrigine);
        Node destination = graph.get(idDestination);
        Path chemin = null;
        ArcInspector arcs= ArcInspectorFactory.getAllFilters().get(0);
        ShortestPathSolution res_algo;
        ShortestPathData data= new ShortestPathData(graph,origine,destination,arcs);

        if (algorithme.equals("Astar")){ 
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
            return chemin;
         
        }  

        //verification que le coût du chemin calculé par Dijkstra est bien le même que celui calculé par la classe Path
       
        
      /* */  //test de validité du chemin trouvé      
        /*if (chemin==null || chemin.isValid()==false){
            System.out.println("Chemin non valide");
            return null;
        }*/
        return chemin;

    }


    public static void main(String[] args) throws Exception {


        int resultatDjikstra_correct_en_temps=0;

        int resultatAStar_correct_en_temps=0;

        int resultatDjikstra_correct_en_distance=0;

        int resultatAStar_correct_en_distance=0;

        int origine,destination;
        
        // Visit these directory to see the list of available files on Commetud.
        
        
        final String[] mapNames = {"/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/fractal.mapgr"};
        int nb_test=2;
        int nb_cartes = 2;

        for (int j =0; j<nb_cartes; j++){

        
        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapNames[j]))));

        // TODO: Read the graph.
        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);



        System.out.println("commencons les tests avec la carte "+mapNames[j]);
        ///la boucle qui va renvoyer le nombre de resultats bons
        for (int i = 0; i < nb_test; i++){
            //avoir un nombre aléatoire entre 0 et la taille du graphe 
            origine= (int)Math.floor(Math.random()*graph.size());
            destination = (int) Math.floor(Math.random()*graph.size());

            resultatDjikstra_correct_en_temps=resultatDjikstra_correct_en_temps+test_temps(graph,"Djikstra",origine,destination);
            resultatAStar_correct_en_temps=resultatAStar_correct_en_temps+test_temps(graph,"Astar",origine,destination);
        
            resultatDjikstra_correct_en_distance=resultatDjikstra_correct_en_distance+test_distance(graph,"Djikstra",origine,destination);
            resultatAStar_correct_en_distance=resultatAStar_correct_en_distance+test_distance(graph,"Astar",origine,destination);
    }
    
        }

    System.out.println("le nombre de test en total c'est "+nb_test*nb_cartes);

    //veracité Djikstra en temps et distance
    int pourcentage_veracite_Djikstra_en_temps=(resultatDjikstra_correct_en_temps*100/(nb_test*nb_cartes));
    int pourcentage_veracite_Djikstra_en_distance=(resultatDjikstra_correct_en_distance*100/(nb_test*nb_cartes));
    System.out.println("le nombre de  resultats corrects de djikstra en temps est de "+resultatDjikstra_correct_en_temps);
    System.out.println("le nombre de  resultats corrects de djikstra en distance est de "+resultatDjikstra_correct_en_distance);
    System.out.println("le pourcentage de véracité de Djikstra en temps est de  "+pourcentage_veracite_Djikstra_en_temps);
    System.out.println("le pourcentage de véracité de Djikstra en distance est de  "+pourcentage_veracite_Djikstra_en_distance);

    //veracite AStar en temps et distance
    int pourcentage_veracite_AStar_en_temps=(resultatAStar_correct_en_temps*100/(nb_test*nb_cartes));
    int pourcentage_veracite_AStar_en_distance=(resultatAStar_correct_en_distance*100/(nb_test*nb_cartes));
    System.out.println("le nombre de  resultats corrects de AStar en temps est de "+resultatAStar_correct_en_temps);
    System.out.println("le nombre de  resultats corrects de AStar en distance est de "+resultatAStar_correct_en_distance);
    System.out.println("le pourcentage de véracité de AStar en temps est de  "+pourcentage_veracite_AStar_en_temps);
    System.out.println("le pourcentage de véracité de AStar en distance est de  "+pourcentage_veracite_AStar_en_distance);

    //veracite total Djikstra et AStar
    int nb_total_Djikstra=resultatDjikstra_correct_en_temps+resultatDjikstra_correct_en_distance;
    int nb_total_AStar=resultatAStar_correct_en_temps+resultatAStar_correct_en_distance;

    int pourcentage_veracite_Djikstra=(nb_total_Djikstra*100/(2*(nb_test*nb_cartes)));
    int pourcentage_veracite_Astar=(nb_total_AStar*100/(2*(nb_test*nb_cartes)));
    System.out.println("le pourcentage de veracité de djikstra en temps est distance "+pourcentage_veracite_Djikstra);
    System.out.println("le pourcentage de véracité de AStar en temps et distance est de  "+pourcentage_veracite_Astar);

}
}


