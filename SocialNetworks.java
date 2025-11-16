import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocialNetworks {

    record Edge<T> (T v1, T v2) { }

    // (nodevalue, ListOfEdges)
    

    private class Pair<T,U> {
        T fst;
        U snd;
        public Pair(T f, U s) {
            fst = f;
            snd = s;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object other) {
            if(other instanceof Pair) {
                Pair<T, U> o = (Pair<T, U>) other;
                return fst.equals(o.fst) && snd.equals(o.snd);
            }
            return false;
        }

    }

    // we're assuming the graph is connected
    class Graph<T> {
        // Agonizing
        ArrayList<Pair<T,ArrayList<Edge<T>>>> nodesAndEdges;

        Graph() {
            nodesAndEdges = new ArrayList<Pair<T,ArrayList<Edge<T>>>>();
        }

        // adds an edge between vertices v1 and v2
        void add(T v1, T v2) {
            if(!isNode(v1)) {
                ArrayList<Edge<T>> a = new ArrayList<>();
                Pair<T, ArrayList<Edge<T>>> p = new Pair<>(v1, a);
                nodesAndEdges.add(p);
            }
            if(!isNode(v2)) {
                ArrayList<Edge<T>> a = new ArrayList<>();
                Pair<T, ArrayList<Edge<T>>> p = new Pair<>(v2, a);
                nodesAndEdges.add(p);
            }

            Edge<T> newEdge = new Edge<T>(v1, v2);
            Edge<T> newEdge2 = new Edge<T>(v2, v1);

            for(int i=0; i<nodesAndEdges.size(); i++) {
                Pair<T, ArrayList<Edge<T>>> node = nodesAndEdges.get(i);
                if(node.fst.equals(v1)) {
                    boolean inList = false;
                    for(int j=0; j<node.snd.size(); j++) {
                        Edge<T> edge = node.snd.get(j);
                        if(edge.equals(newEdge)) {
                            inList = true;
                        }
                    }
                    if(!inList) {
                        node.snd.add(newEdge);
                    }
                }
                if(node.fst.equals(v2)) {
                    boolean inList = false;
                    for(int j=0; j<node.snd.size(); j++) {
                        Edge<T> edge = node.snd.get(j);
                        if(edge.equals(newEdge2)) {
                            inList = true;
                        }
                    }
                    if(!inList) {
                        node.snd.add(newEdge2);
                    }
                }
            }


            // ArrayList<Edge<T>>newEdgeList = new ArrayList<>();
            // newEdgeList.add(newEdge);

            // nodesAndEdges.add(new Pair<T,ArrayList<Edge<T>>>(v1,newEdgeList));
            
        }

        boolean isNode(T v) {
            for(int i=0; i<nodesAndEdges.size(); i++) {
                if(nodesAndEdges.get(0).fst.equals(v)) {
                    return true;
                }
            }
            return false;
            
        }

        List<T> getNodes() {
            ArrayList<T> l = new ArrayList<>();
            for(int i=0; i<nodesAndEdges.size(); i++) {
                l.add(nodesAndEdges.get(i).fst);
            }
            return l;
        }

        List<Edge<T>> getEdges() {
            ArrayList<Edge<T>> l = new ArrayList<>();
            for(int i=0; i<nodesAndEdges.size(); i++) {
                for(int j=0; j<nodesAndEdges.get(i).snd.size(); j++) {
                    l.add(nodesAndEdges.get(i).snd.get(j));
                }
            }
            return l;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        SocialNetworks soc = new SocialNetworks();
        Graph<Integer> g = soc.new Graph<>();
        String filePath = "facebook_combined.txt";
        Scanner s = new Scanner(new File(filePath));
        
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] row_data = line.split(" ");

            Edge<Integer> e = new Edge<>(Integer.parseInt(row_data[0]), Integer.parseInt(row_data[1]));
            g.add(null, null);

        }
    }

}
