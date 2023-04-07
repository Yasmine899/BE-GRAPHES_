package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
       // Retrieve the graph.
        final int nbNodes = graph.size();

        // Initialize array of distances.
        double[] labels = new double[nbNodes];

        BinaryHeap labels_heap = new BinaryHeap<Label>();
        
        Arrays.fill(labels, Double.POSITIVE_INFINITY);
        labels[data.getOrigin().getId()] = 0;  
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());              
        for (int i=0;i<graph.size();i++){
            insert(labels_heap, graph.get(i));
        }

        return solution;
    }

}
