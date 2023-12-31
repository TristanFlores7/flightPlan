/**
 * When a new city name is detected, a new city object is created
 * The addEdge method help build the graph
 * The array lists sort edges by lowest cost/time
 */

import java.util.ArrayList;
import java.util.HashMap;

public class City {

    public String name;
    ArrayList<Edge> edgesByCost = new ArrayList<>();
    ArrayList<Edge> edgesByTime = new ArrayList<>();
    HashMap<String, Edge> nameToEdge = new HashMap<>();
    HashMap<Edge, Integer> edgeToCost = new HashMap<>();

    HashMap<Edge, Integer> edgeToTime = new HashMap<>();

    City(String name){
        this.name = name;
    }

    /**
     * Creates an edge object associated with city of origin
     */

    public void addEdge(String cityConnection, int cost, int time){
        Edge e = new Edge(cityConnection, cost, time);
        nameToEdge.put(cityConnection, e);
        edgeToCost.put(e, cost);
        edgeToTime.put(e, time);

        if(!edgesByCost.isEmpty()) {
            if(e.getCost() > (edgesByCost.get((edgesByCost.size() - 1)).getCost())){
                edgesByCost.add(e);
            }else{
                for (int i = 0; i < edgesByCost.size(); i++) {
                    if (e.getCost() <= edgesByCost.get(i).getCost()) {
                        edgesByCost.add(i, e);
                        break;
                    }
                }
            }
            if(e.getTime() > (edgesByTime.get((edgesByTime.size() - 1)).getTime())) {
                edgesByTime.add(e);
            }else{
                for (int i = 0; i < edgesByTime.size(); i++) {
                    if (e.getTime() <= edgesByTime.get(i).getTime()) {
                        edgesByTime.add(i, e);
                        break;
                    }
                }
            }
        }else{
            edgesByCost.add(e);
            edgesByTime.add(e);
        }
    }
}