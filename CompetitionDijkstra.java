/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Dijkstra's algorithm
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CompetitionDijkstra {

    private static double[][] graph;
    private final int sA;
    private final int sB;
    private final int sC;
    private static int numberOfVertices;
    private static int numberOfEdges;
    private static LinkedList<Double> shortestDistances = new LinkedList<>();


    LinkedList<Edge> listOfEdges = new LinkedList<>();


    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
    */
    CompetitionDijkstra (String filename, int sA, int sB, int sC){
        this.sA = sA; // walking speed of contestant A
        this.sB = sB; // walking speed of contestant B
        this.sC = sC; // walking speed of contestant C

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            numberOfVertices = 0;
            numberOfEdges = 0;
            while (myReader.hasNextLine()) {
                if(count == 0){
                    numberOfVertices = myReader.nextInt();
                    graph = new double[numberOfVertices][numberOfVertices];
                    count++;
                }else if(count == 1){
                    numberOfEdges = myReader.nextInt();
                    count++;
                }else{
                    for(int i = 0; i < numberOfEdges; i++){
                        int v1 = myReader.nextInt();
                        int v2 = myReader.nextInt();
                        double weight = myReader.nextDouble();
                        graph[v1][v2] = weight;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
    * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition(){
        
        //TO DO
        return -1;
    }

    //get the vertex with minimum distance which is not included in SPT
    static int getMinimumVertex(boolean[] mst, double[] key){
        double minKey = Integer.MAX_VALUE;
        int vertex = -1;
        for (int i = 0; i <numberOfVertices ; i++) {
            if(!mst[i] && minKey>key[i]){
                minKey = key[i];
                vertex = i;
            }
        }
        return vertex;
    }

    public static void dijkstra_GetMinDistances(int sourceVertex){
        boolean[] spt = new boolean[numberOfVertices];
        double [] distance = new double[numberOfVertices];
        int INFINITY = Integer.MAX_VALUE;

        //Initialize all the distance to infinity
        for (int i = 0; i <numberOfVertices ; i++) {
            distance[i] = INFINITY;
        }

        //start from the vertex 0
        distance[sourceVertex] = 0;

        //create SPT
        for (int i = 0; i <numberOfVertices ; i++) {

            //get the vertex with the minimum distance
            int vertex_U = getMinimumVertex(spt, distance);

            //include this vertex in SPT
            spt[vertex_U] = true;

            //iterate through all the adjacent numberOfVertices of above vertex and update the keys
            for (int vertex_V = 0; vertex_V <numberOfVertices ; vertex_V++) {
                //check of the edge between vertex_U and vertex_V
                if(graph[vertex_U][vertex_V]>0){
                    //check if this vertex 'vertex_V' already in spt and
                    // if distance[vertex_V]!=Infinity

                    if(!spt[vertex_V] && graph[vertex_U][vertex_V]!=INFINITY){
                        //check if distance needs an update or not
                        //means check total weight from source to vertex_V is less than
                        //the current distance value, if yes then update the distance

                        double newKey = graph[vertex_U][vertex_V] + distance[vertex_U];
                        if(newKey<distance[vertex_V])
                            distance[vertex_V] = newKey;

                        shortestDistances.add(newKey);
                    }
                }
            }
        }
        //shortestDistances.add(distance);
        //print shortest path tree
        printDijkstra(sourceVertex, distance);
    }

    public static void printDijkstra(int sourceVertex, double[] key){
        System.out.println("Dijkstra Algorithm: (Adjacency Matrix)");
        for (int i = 0; i <numberOfVertices ; i++) {
            System.out.println("Source Vertex: " + sourceVertex + " to vertex " + i +
                    " distance: " + key[i]);
        }
    }


    public static void main(String[] args) {
        CompetitionDijkstra test = new CompetitionDijkstra("tinyEWD.txt", 1,2,3);
        for(int i = 0; i < numberOfVertices - 1; i++){
            dijkstra_GetMinDistances(i);
            System.out.println();
        }
        for (Double shortestDistance : shortestDistances) {
            System.out.println(shortestDistance);
        }

    }

}
