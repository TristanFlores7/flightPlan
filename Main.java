/**
 * Tristan Flores
 * This file reads the input files, writes to the output files,
 * and contains the algorithms used to find the shortest path of cost/time
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.io.FileWriter;
public class Main {
    public static HashMap<String, City> nameToCity = new HashMap<>(); //name to city hash
    public static HashMap<City, String> cityToName = new HashMap<>(); //city to name hash
    static ArrayList<City> uniqueCity = new ArrayList<>(); //tracks whether city name exists in graph
    static Stack<City> searchTree = new Stack<>(); //stack for path algorithms
    static ArrayList<City> visitedCities = new ArrayList<>(); //tracks whether algorithm has 'visited' a city
    static ArrayList<City> knownCities = new ArrayList<>(); //tracks whether algorithm has checked all edges of node
    static ArrayList<City> nullList1 = new ArrayList<>(); //List/Path declarations
    static ArrayList<City> nullList2 = new ArrayList<>();
    static ArrayList<City> nullList3 = new ArrayList<>();
    static Path path1 = new Path(nullList1);
    static Path path2 = new Path(nullList2);
    static Path path3 = new Path(nullList3);

    static ArrayList<City> pathStack = new ArrayList<>();
    public static City origin;

    /**
     * Method called when lowest cost path is found
     */
    public static void destinationFoundCost(){
        Path testPath = new Path((ArrayList<City>) pathStack.clone());
        if (path1.path.isEmpty()) {
            path1 = testPath;
        }else if (testPath.getCostCounter() < path1.getCostCounter()) {
            path3 = path2;
            path2 = path1;
            path1 = testPath;
        } else if ((testPath.getCostCounter() < path2.getCostCounter()) || path2.path.isEmpty()) {
            path3 = path2;
            path2 = testPath;
        } else if ((testPath.getCostCounter() < path3.getCostCounter()) || path3.path.isEmpty()){
            path3 = testPath;
        }
        pathStack.clear();
        searchTree.pop();
    }

    /**
     * Method called when shortest time path is found
     */
    public static void destinationFoundTime(){
        Path testPath = new Path((ArrayList<City>) pathStack.clone());
        if (path1.path.isEmpty()) {
            path1 = testPath;
        }else if (testPath.getTimeCounter() < path1.getTimeCounter()) {
            path3 = path2;
            path2 = path1;
            path1 = testPath;
        } else if ((testPath.getTimeCounter() < path2.getTimeCounter()) || path2.path.isEmpty()) {
            path3 = path2;
            path2 = testPath;
        } else if ((testPath.getTimeCounter() < path3.getTimeCounter()) || path3.path.isEmpty()){
            path3 = testPath;
        }
        pathStack.clear();
        searchTree.pop();
    }

    /**
     * Recursive Algorithm for finding the lowest cost paths
     */

    public static void recursiveSearchCost(City c, City destination) {
        if (searchTree.isEmpty()) {
            searchTree.add(c);
            visitedCities.add(c);
            origin = c;
        }
        for (int i = 0; i < c.edgesByCost.size(); i++) {
            searchTree.push(nameToCity.get((c.edgesByCost.get(i).getConnection())));

            if (searchTree.peek().equals(destination)) {
                pathStack.addAll(searchTree);
                destinationFoundCost();
            } else if (visitedCities.contains(searchTree.peek())) {
                searchTree.pop();
            } else {
                visitedCities.add(searchTree.peek());
                recursiveSearchCost(searchTree.peek(), destination);
            }
        }
        knownCities.add(c);
        searchTree.pop();
    }

    /**
     * Recursive Algorithm to find the shortest time paths
     */

    public static void recursiveSearchTime(City c, City destination) {
        if (searchTree.isEmpty()) {
            searchTree.add(c);
            visitedCities.add(c);
            origin = c;
        }
        for (int i = 0; i < c.edgesByTime.size(); i++) {
            searchTree.push(nameToCity.get((c.edgesByTime.get(i).getConnection())));

            if (searchTree.peek().equals(destination)) {
                pathStack.addAll(searchTree);
                destinationFoundTime();
            } else if (visitedCities.contains(searchTree.peek())) {
                searchTree.pop();
            } else {
                visitedCities.add(searchTree.peek());
                recursiveSearchTime(searchTree.peek(), destination);
            }
        }
        knownCities.add(c);
        searchTree.pop();
    }

    /**
     * Main method used for taking input and building graph
     */
    public static void main(String[] args) throws IOException {
        FileReader fr = null;
        /**
         * try catch exception block
         */
        try {
            fr = new FileReader("FlightDataSampleFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        FileWriter myWriter;

        {
            try {
                myWriter = new FileWriter("FlightPathResultsSampleFile.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int numPathFlights = Character.getNumericValue((char) br.read());
        br.readLine();

        /**
         * input file reader and graph builder
         */

        for (int i = 0; i < numPathFlights; i++) {

            ArrayList<Character> builder1 = new ArrayList<>();
            int length1 = 0;
            StringBuilder cityNameBuilder1 = new StringBuilder();
            char tester1 = (char) br.read();
            while (tester1 != '|') {
                length1++;
                builder1.add(tester1);
                tester1 = (char) br.read();
            }
            for (int j = 0; j < length1; j++) {
                cityNameBuilder1.append(builder1.get(j));
            }

            String cityName1 = String.valueOf(cityNameBuilder1);

            if (!nameToCity.containsKey(cityName1)) {
                City city1 = new City(cityName1);
                nameToCity.put(cityName1, city1);
                cityToName.put(city1, cityName1);
                uniqueCity.add(city1);
            }

            ArrayList<Character> builder2 = new ArrayList<>();
            int length2 = 0;
            StringBuilder cityNameBuilder2 = new StringBuilder();
            char tester2 = (char) br.read();
            while (tester2 != '|') {
                length2++;
                builder2.add(tester2);
                tester2 = (char) br.read();
            }
            for (int j = 0; j < length2; j++) {
                cityNameBuilder2.append(builder2.get(j));
            }

            String cityName2 = String.valueOf(cityNameBuilder2);

            if (!nameToCity.containsKey(cityName2)) {
                City city2 = new City(cityName2);
                nameToCity.put(cityName2, city2);
                cityToName.put(city2, cityName2);
                uniqueCity.add(city2);
            }

            if (!nameToCity.get(cityName1).nameToEdge.containsKey(cityName2)) {
                ArrayList<Character> builder3 = new ArrayList<>();
                int length3 = 0;
                StringBuilder costBuilder = new StringBuilder();
                char tester3 = (char) br.read();
                while (tester3 != '|') {
                    length3++;
                    builder3.add(tester3);
                    tester3 = (char) br.read();
                }
                for (int j = 0; j < length3; j++) {
                    costBuilder.append(builder3.get(j));
                }
                int cost = Integer.parseInt(String.valueOf(costBuilder));
                int time = Integer.parseInt(String.valueOf(br.readLine()));

                nameToCity.get(cityName1).addEdge(cityName2, cost, time);
                nameToCity.get(cityName2).addEdge(cityName1, cost, time);
            } else {
                br.readLine();
            }
        }
        /**
         * Reads in the flight request data
         */

        FileReader fr1 = null;
        try {
            fr1 = new FileReader("FlightPathsToCalculateSampleFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br1 = new BufferedReader(fr1);
        int numRequestFlights = Character.getNumericValue((char) br1.read());
        br1.readLine();

        for (int i = 0; i < numRequestFlights; i++) {

            ArrayList<Character> builder1 = new ArrayList<>();
            int length1 = 0;
            StringBuilder cityNameBuilder1 = new StringBuilder();
            char tester1 = (char) br1.read();
            while (tester1 != '|') {
                length1++;
                builder1.add(tester1);
                tester1 = (char) br1.read();
            }
            for (int j = 0; j < length1; j++) {
                cityNameBuilder1.append(builder1.get(j));
            }

            String cityName1 = String.valueOf(cityNameBuilder1);

            ArrayList<Character> builder2 = new ArrayList<>();
            int length2 = 0;
            StringBuilder cityNameBuilder2 = new StringBuilder();
            char tester2 = (char) br1.read();
            while (tester2 != '|') {
                length2++;
                builder2.add(tester2);
                tester2 = (char) br1.read();
            }
            for (int j = 0; j < length2; j++) {
                cityNameBuilder2.append(builder2.get(j));
            }

            String cityName2 = String.valueOf(cityNameBuilder2);

            String costOrTime = String.valueOf(br1.readLine());

            /**
             * Calls recursive function for lowest cost and builds output
             */

            if (costOrTime.equals("C")) {
                recursiveSearchCost(nameToCity.get(cityName1), nameToCity.get(cityName2));
                if (!path1.path.isEmpty()) {
                    myWriter.write("Flight" + (i + 1) + ": " + cityName1 + ", " + cityName2 + " (Cost)" + "\n");
                    myWriter.write("Path1: " + cityToName.get(path1.path.get(0)));
                    for (int j = 1; j < path1.path.size(); j++) {
                        myWriter.write(" ---> " + cityToName.get((path1).path.get(j)));
                    }
                    myWriter.write(". Cost: " + path1.getCostCounter() + " Time: " + path1.getTimeCounter() + "\n");
                } else {
                    myWriter.write("Error: No possible path from origin to destination" + "\n");
                }
                if (!path2.path.isEmpty()) {
                    myWriter.write("Path2: " + cityToName.get(path2.path.get(0)));
                    for (int j = 1; j < path2.path.size(); j++) {
                        myWriter.write(" ---> " + cityToName.get((path2).path.get(j)));
                    }
                    myWriter.write(". Cost: " + path2.getCostCounter() + " Time: " + path2.getTimeCounter() + "\n");
                }
                if (!path3.path.isEmpty()) {
                    myWriter.write("Path3: " + cityToName.get(path3.path.get(0)));
                    for (int j = 1; j < path3.path.size(); j++) {
                        myWriter.write(" ---> " + cityToName.get((path3).path.get(j)));
                    }
                    myWriter.write(". Cost: " + path3.getCostCounter() + " Time: " + path3.getTimeCounter() + "\n");
                }
                myWriter.write("\n");

                searchTree.clear();
                visitedCities.clear();
                path1.path.clear();
                path2.path.clear();
                path3.path.clear();
            }

            /**
             * Calls recursive function for shortest time and builds output
             */

            if(costOrTime.equals("T")){
                recursiveSearchTime(nameToCity.get(cityName1), nameToCity.get(cityName2));
                if(!path1.path.isEmpty()) {
                    myWriter.write("Flight" + (i + 1) + ": " + cityName1 + ", " + cityName2 + " (Time)" + "\n");
                    myWriter.write("Path1: " + cityToName.get(path1.path.get(0)));
                    for (int j = 1; j < path1.path.size(); j++) {
                        myWriter.write(" ---> " + cityToName.get((path1).path.get(j)));
                    }
                    myWriter.write(". Time: " + path1.getTimeCounter() + " Cost: " + path1.getCostCounter() + "\n");
                }else{
                    myWriter.write("Error: No possible path from origin to destination" + "\n");
                }
                if(!path2.path.isEmpty()) {
                    myWriter.write("Path2: " + cityToName.get(path2.path.get(0)));
                    for (int j = 1; j < path2.path.size(); j++) {
                        myWriter.write(" ---> " + cityToName.get((path2).path.get(j)));
                    }
                    myWriter.write(". Time: " + path2.getTimeCounter() + " Cost: " + path2.getCostCounter() + "\n");
                }
                if(!path3.path.isEmpty()) {
                    myWriter.write("Path3: " + cityToName.get(path3.path.get(0)));
                    for (int j = 1; j < path3.path.size(); j++) {
                        myWriter.write(" ---> " + cityToName.get((path3).path.get(j)));
                    }
                    myWriter.write(". Time: " + path3.getTimeCounter() + " Cost: " + path3.getCostCounter() + "\n");
                }
                myWriter.write("\n");

                searchTree.clear();
                visitedCities.clear();
                path1.path.clear();
                path2.path.clear();
                path3.path.clear();
            }
        }
            myWriter.close();
    }
}