package graph;

import cms.util.maybe.Maybe;
import cms.util.maybe.NoMaybeValue;
import datastructures.PQueue;
import datastructures.SlowPQueue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** This object computes and remembers shortest paths through a
 *  weighted, directed graph. Once shortest paths are computed from
 *  a specified source vertex, it allows querying the distance to
 *  arbitrary vertices and the best paths to arbitrary destination
 *  vertices.
 *<p>
 *  Types Vertex and Edge are parameters, so their operations are
 *  supplied by a model object supplied to the constructor.
 */
public class ShortestPaths<Vertex, Edge> {

    /** The model for treating types Vertex and Edge as forming
     * a weighted directed graph.
     */
    private final WeightedDigraph<Vertex, Edge> graph;

    /** The distance to each vertex from the source.
     */
    private Maybe<Map<Vertex, Double>> distances;

    /** The incoming edge for the best path to each vertex from the
     *  source vertex.
     */
    private Maybe<Map<Vertex, Edge>> bestEdges;

    /** Creates: a single-source shortest-path finder for a weighted graph.
     *
     * @param graph The model that supplies all graph operations.
     */
    public ShortestPaths(WeightedDigraph<Vertex, Edge> graph) {
        this.graph = graph;
    }

    /** Effect: Computes the best paths from a given source vertex, which
     *  can then be queried using bestPath().
     */
    public void singleSourceDistances(Vertex source) {
        // Implementation: uses Dijkstra's single-source shortest paths
        //   algorithm.
        PQueue<Vertex> frontier = new SlowPQueue<>();
        Map<Vertex, Double> distances = new HashMap<>();
        Map<Vertex, Edge> bestEdges = new HashMap<>();
           // TODO: Complete computation of distances and best-path edges
        this.bestEdges = Maybe.some(bestEdges);
        this.distances = Maybe.some(distances);
        // code you wrote starts here and below.
        frontier.add(source, 0.0);
        distances.put(source, 0.0);
        while(!frontier.isEmpty()){
            Vertex g = frontier.extractMin();
            double val = distances.get(g);
            for(Edge x : graph.outgoingEdges(g)){
                Vertex destination = graph.dest(x);
                double weight_of_dest =  graph.weight(x);
                double updated_weight = weight_of_dest+val;
                if(distances.get(destination) == null){
                    frontier.add(destination, updated_weight);
                    distances.put(destination, updated_weight);
                    bestEdges.put(destination, x);
                }
                else {
                    if(distances.get(destination) > updated_weight) {
                        frontier.changePriority(destination, updated_weight);
                        bestEdges.put(destination, x);
                        distances.put(destination, updated_weight);
                    }
                }
            }
        }
    }

    /** Returns: the distance from the source vertex to the given vertex.
     *  Checks: distances have been computed from a source vertex,
     *    and vertex v is reachable from that vertex.
     */
    public double getDistance(Vertex v) {
        try {
            Double d = distances.get().get(v);
            assert d != null : "Implementation incomplete";
            return d;
        } catch (NoMaybeValue exc) {
            throw new Error("Distances not computed yet");
        }
    }

    /**
     * Returns: the best path from the source vertex to a given target
     * vertex. The path is represented as a list of edges.
     * Requires: singleSourceDistances() has already been used to compute
     * best paths.
     */
    public List<Edge> bestPath(Vertex target) {
        LinkedList<Edge> path = new LinkedList<>();
        Map<Vertex, Edge> bestEdges = this.bestEdges.orElseGet(() -> {
            throw new Error("best distances not computed yet");
        });
        Vertex v = target;
        while (true) {
            Edge e = bestEdges.get(v);
            if (e == null) break; // must be the source vertex
            path.addFirst(e);
            v = graph.source(e);
        }
        return path;
    }
}
