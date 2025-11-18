import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocialNetworks {

    record Edge<T>(T v1, T v2) {
    }

    // (nodevalue, ListOfEdges)

    private class Pair<T, U> {
        T fst;
        U snd;

        public Pair(T f, U s) {
            fst = f;
            snd = s;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object other) {
            if (other instanceof Pair) {
                Pair<T, U> o = (Pair<T, U>) other;
                return fst.equals(o.fst) && snd.equals(o.snd);
            }
            return false;
        }

    }

    // we're assuming the graph is connected
    class Graph<T> {
        // Agonizing
        ArrayList<Pair<T, ArrayList<Edge<T>>>> nodesAndEdges;

        Graph() {
            nodesAndEdges = new ArrayList<>();
        }

        // adds an edge between vertices v1 and v2
        void add(T v1, T v2) {
            if (!isNode(v1)) {
                ArrayList<Edge<T>> a = new ArrayList<>();
                Pair<T, ArrayList<Edge<T>>> p = new Pair<>(v1, a);
                nodesAndEdges.add(p);
            }
            if (!isNode(v2)) {
                ArrayList<Edge<T>> a = new ArrayList<>();
                Pair<T, ArrayList<Edge<T>>> p = new Pair<>(v2, a);
                nodesAndEdges.add(p);
            }

            Edge<T> newEdge = new Edge<>(v1, v2);
            Edge<T> newEdge2 = new Edge<>(v2, v1);

            for (int i = 0; i < nodesAndEdges.size(); i++) {
                Pair<T, ArrayList<Edge<T>>> node = nodesAndEdges.get(i);
                if (node.fst.equals(v1)) {
                    boolean inList = false;
                    for (int j = 0; j < node.snd.size(); j++) {
                        Edge<T> edge = node.snd.get(j);
                        if (edge.equals(newEdge)) {
                            inList = true;
                        }
                    }
                    if (!inList) {
                        node.snd.add(newEdge);
                    }
                }
                if (node.fst.equals(v2)) {
                    boolean inList = false;
                    for (int j = 0; j < node.snd.size(); j++) {
                        Edge<T> edge = node.snd.get(j);
                        if (edge.equals(newEdge2)) {
                            inList = true;
                        }
                    }
                    if (!inList) {
                        node.snd.add(newEdge2);
                    }
                }
            }
        }

        // true if there exist at most n edges connecting v1 and v2 (DFS of size n)
        boolean areConnected(T v1, T v2, int n) {
            if (!(isNode(v1) || isNode(v2))) {
                return false;
            }
            ArrayList<T> visited = new ArrayList<>();
            // iterate thru set of edges belonging to node for n-1 nodes
            return dfsHelper(v1, v2, n, 0, visited);
        }

        // v1 is cur node, v2 is goal node, lvl is cur dist. from initial v1
        boolean dfsHelper(T cur, T goal, int maxDepth, int curDepth, ArrayList<T> visited) {
            if (cur.equals(goal)) {
                return true;
            }
            if (curDepth == maxDepth) {
                return false;
            }

            // mark current node as visited
            visited.add(cur);

            // get list of NNs (nearest neighbors)
            ArrayList<Edge<T>> adj = null;

            for (int i = 0; i < nodesAndEdges.size(); i++) {
                Pair<T, ArrayList<Edge<T>>> node = nodesAndEdges.get(i);
                if (cur.equals(node.fst)) {
                    adj = node.snd;
                    break;
                }
            }

            for (int i = 0; i < adj.size(); i++) {
                Edge<T> e = adj.get(i);
                T neighbor = e.v2;
                if (!visited.contains(neighbor)) {
                    if (dfsHelper(neighbor, goal, maxDepth, curDepth + 1, visited)) {
                        return true;
                    }
                }
            }

            return false;
        }

        boolean isNode(T v) {
            for (int i = 0; i < nodesAndEdges.size(); i++) {
                if (nodesAndEdges.get(i).fst.equals(v)) {
                    return true;
                }
            }
            return false;

        }

        List<T> getNodes() {
            ArrayList<T> l = new ArrayList<>();
            for (int i = 0; i < nodesAndEdges.size(); i++) {
                l.add(nodesAndEdges.get(i).fst);
            }
            return l;
        }

        List<Edge<T>> getEdges() {
            ArrayList<Edge<T>> l = new ArrayList<>();
            for (int i = 0; i < nodesAndEdges.size(); i++) {
                for (int j = 0; j < nodesAndEdges.get(i).snd.size(); j++) {
                    l.add(nodesAndEdges.get(i).snd.get(j));
                }
            }
            return l;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        SocialNetworks soc = new SocialNetworks();
        Graph<Integer> g = soc.new Graph<>();
        String filePath = "0.edges";
        // String filePath = "graphtest.txt";
        Scanner s = new Scanner(new File(filePath));

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split(" ");

            // Edge<Integer> e = new Edge<>(Integer.parseInt(row_data[0]),
            // Integer.parseInt(row_data[1]));
            g.add(Integer.valueOf(row_data[0]), Integer.valueOf(row_data[1]));

        }
        ArrayList<Edge<Integer>> l = new ArrayList<>();
        l = (ArrayList<Edge<Integer>>) g.getEdges();
        System.out.println(l.toString());

        System.out.println(g.areConnected(211, 345, 3));
        System.out.println(g.areConnected(211, 188, 1));

        // System.out.println(g.areConnected(11, 6, 3));
        // System.out.println(g.areConnected(11, 22, 3));

        s.close();
    }

}
