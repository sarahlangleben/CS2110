package diver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datastructures.PQueue;
import datastructures.SlowPQueue;
import game.Edge;
import game.Maze;
import game.Node;
import game.NodeStatus;
import game.ScramState;
import game.SeekState;
import graph.ShortestPaths;

/** This is the place for your implementation of the {@code SewerDiver}. */
public class McDiver implements SewerDiver {
    boolean foundRing= false;
    HashMap<Long, ScramState> visitMap= new HashMap<>();

    /** See {@code SewerDriver} for specification. */
    @Override
    public void seek(SeekState state) {
        // TODO : Look for the ring and return.
        // DO NOT WRITE ALL THE CODE HERE. DO NOT MAKE THIS METHOD RECURSIVE.
        // Instead, write your method (it may be recursive) elsewhere, with a
        // good specification, and call it from this one.
        //
        // Working this way provides you with flexibility. For example, write
        // one basic method, which always works. Then, make a method that is a
        // copy of the first one and try to optimize in that second one.
        // If you don't succeed, you can always use the first one.
        // Use this same process on the second method, scram.
        HashSet<Long> visited= new HashSet<>(); // you can make a hashMap instead to keep the
        dfsWalkopt1(state, visited);
    }

    /**Unoptimized depth-first-search traversal to get McDiver to the ring.
     * Parameters: SeekState, and a HashSet that keeps track of all the visited nodes
     * based on tile ids. This implementation will always find the ring, but not very efficiently.*/
    public void dfsWalk(SeekState s, HashSet<Long> visited) {
        if (s.distanceToRing() == 0) {
            return;
        }
        Long prevId= s.currentLocation();
        visited.add(s.currentLocation());

        for (NodeStatus neighbor : s.neighbors()) {
            if (!visited.contains(neighbor.getId())) {
                s.moveTo(neighbor.getId());
                dfsWalk(s, visited);
                if (s.distanceToRing() == 0) {
                    foundRing= true;
                    return;
                }
                s.moveTo(prevId);
            }
        }
    }
    /**Optimized version of the above implementation of a depth-first-search traversal through the graph.
     * Params: SeekState and a HashSet that keeps track of previously visited nodes using tile ids.
     * Implements a Collection and Priority Queue to keep track of neighbors and neighbor distance to
     * ring.**/
    public void dfsWalkopt1(SeekState s, HashSet<Long> visited) {
        if (s.distanceToRing() == 0) { return; }
        Long prevId= s.currentLocation();
        visited.add(s.currentLocation());
        Collection<NodeStatus> neighbors= s.neighbors();
        PQueue<NodeStatus> seek1NodeQueue= new SlowPQueue<>();
        for (NodeStatus n : neighbors) {
            int dtr= n.getDistanceToRing();
            seek1NodeQueue.add(n, dtr);
        }
        while (seek1NodeQueue.size() != 0) {
            NodeStatus min= seek1NodeQueue.extractMin();
            if (!visited.contains(min.getId())) {
                if (s.distanceToRing() != 0) s.moveTo(min.getId());
                dfsWalkopt1(s, visited);
                if (s.distanceToRing() != 0) s.moveTo(prevId);
            }
        }
    }

    /** See {@code SewerDriver} for specification. */
    @Override
    public void scram(ScramState state) {
        // TODO: Get out of the sewer system before the steps are used up.
        // DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
        tileScorenbr2(state);
    }

    /**Unoptimized scram method to get McDiver out of the sewer system as fast as possible. Does not
     * take nearby coins into account. Will always get McDiver out of the sewer system, but at the cost
     * of not collecting coins on the way back.**/
    public void scramHelp(ScramState s) {
        Maze allNodes= new Maze((Set<Node>) s.allNodes());
        ShortestPaths<Node, Edge> sP= new ShortestPaths<>(allNodes);
        sP.singleSourceDistances(s.currentNode());
        List<Edge> corrPath= sP.bestPath(s.exit());
        for (Edge e : corrPath) {
            s.moveTo(e.destination());
        }
    }

    /**Optimized scram method to get McDiver out of the sewer system while also taking number of steps
     * left into account. While there are neighboring tiles with coins, and enough steps left, McDiver
     * will go to the tiles with coins before getting to the exit.**/
    public void tileScorenbr2(ScramState s) {
        PQueue<Node> scramNodeQueue= new SlowPQueue<>();
        Maze allNodes= new Maze((Set<Node>) s.allNodes());
        ShortestPaths<Node, Edge> sP= new ShortestPaths<>(allNodes);
        sP.singleSourceDistances(s.currentNode());
        PQueue<Node> coinneigh= new SlowPQueue<>();
        HashMap<Integer, Node> coinneighmap= new HashMap<>();
        Node l= s.currentNode();
        Set<Node> nbr= l.getNeighbors();
        Collection<Node> ass= s.allNodes();
        ArrayList<Integer> cvals= new ArrayList<>();
        int maxCoin= 0;
        for (Node n : ass) {
            Set<Node> nbrr= n.getNeighbors();
            int coin= 0;
            coin+= n.getTile().originalCoinValue();
            for (Node nn : nbrr) {
                coin+= n.getTile().originalCoinValue();
            }
            if (coin > maxCoin) {
                maxCoin= coin;
            }
            cvals.add(coin);
            coinneigh.add(n, coin);
            coinneighmap.put(coin, n);
            Collections.sort(cvals, Collections.reverseOrder());
        }
        for (int i= 0; i < cvals.size(); i++ ) {
            Node nextMove= coinneighmap.get(cvals.get(i));
            sP.singleSourceDistances(s.currentNode());
            List<Edge> coinPath= sP.bestPath(nextMove);
            double ctn= sP.getDistance(nextMove);
            sP.singleSourceDistances(nextMove);
            List<Edge> lastStretch= sP.bestPath(s.exit());
            double ste= sP.getDistance(s.exit());
            sP.getDistance(s.currentNode());
            if (ctn + ste < s.stepsToGo()) {
                for (Edge e : coinPath) {
                    s.moveTo(e.destination());
                }
            }
        }
        sP.singleSourceDistances(s.currentNode());
        List<Edge> corrPath= sP.bestPath(s.exit());
        for (Edge e : corrPath) {
            s.moveTo(e.destination());
        }
    }
}
