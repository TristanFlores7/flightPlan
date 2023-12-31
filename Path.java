/**
 * This file gives path objects features such as cost and time calculators
 */

import java.util.ArrayList;

public class Path {
    int costCounter = 0;
    int timeCounter = 0;
    ArrayList<City> path;

    /**
     * Path class constructor which calculates paths associated total cost and time.
     */
    Path(ArrayList<City> path){
        this.path = path;
        for (int j = 0; j < (path.size() - 1); j++) {
            costCounter = costCounter + path.get(j).nameToEdge.get(Main.cityToName.get(path.get(j+1))).getCost();
        }
        for (int j = 0; j < (path.size() - 1); j++) {
            timeCounter = timeCounter + path.get(j).nameToEdge.get(Main.cityToName.get(path.get(j+1))).getTime();
        }
    }
    public int getCostCounter(){
        return costCounter;
    }
    public int getTimeCounter(){
        return timeCounter;
    }
}
