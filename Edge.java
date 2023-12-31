/**
 * This class keeps track of features of edge objects such as the edges cost, time,
 * or what city the edge is connected to
 */

public class Edge {
    String connection;
    int cost;
    int time;

    /**
     * Edge constructor
     */
    Edge(String connection, int cost, int time){
        this.connection = connection;
        this.cost = cost;
        this.time = time;
    }

    public String getConnection(){
        return connection;
    }

    public int getCost(){
        return cost;
    }

    public int getTime(){
        return time;
    }
}