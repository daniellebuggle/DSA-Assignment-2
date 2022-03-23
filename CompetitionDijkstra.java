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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CompetitionDijkstra {

    private static double[][] graph;
    private static int sA = 0;
    private static int sB = 0;
    private static int sC = 0;
    private static int numberOfVertices;
    private static int numberOfEdges;


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
    public static int timeRequiredforCompetition(){
        double distance1 = 0;
        double distance2 = 0;
        double distance3 = 0;

        double max = 0;
        double temp = 0;

        double[] testArray = new double[3];
        double[] tempArray = new double[3];

        for(int j = 0; j < numberOfVertices; j++)
        {
            for(int i = 0; i < numberOfVertices; i++)
            {
                if(graph[i][j] > distance1)
                {
                    distance3 = distance2;
                    distance2 = distance1;
                    distance1 = graph[i][j];
                }
                else if(graph[i][j] > distance2)
                {
                    distance3 = distance2;
                    distance2 = graph[i][j];
                }
                else if(graph[i][j] > distance3)
                {
                    distance3 = graph[i][j];
                }
            }
            temp = distance1 + distance2 + distance3;
            tempArray[0] = distance1;
            tempArray[1] = distance2;
            tempArray[2] = distance3;

            if(temp > max)
            {
                max = temp;
                testArray = tempArray;
            }
        }
        distance1 = testArray[0] * 1000;
        distance2 = testArray[1] * 1000;
        distance3 = testArray[2] * 1000;

        // Insert y
        int x = sA;
        int y = sB;
        int z = sC;
        
        int temps;
        if (y < x)
        {
            temps = x;
            x = y;
            y = temps;
        }

        // Insert z
        if (z < y)
        {
            temps = y;
            y = z;
            z = temps;

            if (y < x)
            {
                temps = x;
                x = y;
                y = temps;
            }
        }

        System.out.println(x);
        System.out.println(y);
        System.out.println(z);

        double longestDistSpeed = z*distance1;
        double middleDistSpeed = y*distance2;
        double shortestDistSpeed = z*distance3;

        return (int) Math.ceil(longestDistSpeed + middleDistSpeed + shortestDistSpeed);
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
        for (int i = 0; i < numberOfVertices ; i++) {

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
            graph[sourceVertex][i] = key[i];
            System.out.println("Source Vertex: " + sourceVertex + " to vertex " + i +
                    " distance: " + key[i]);
        }
    }


    public static void main(String[] args) {
        CompetitionDijkstra test = new CompetitionDijkstra("tinyEWD.txt", 34,12,1);
        for(int i = 0; i < numberOfVertices; i++){
            dijkstra_GetMinDistances(i);
            System.out.println();
        }
        System.out.println(Arrays.deepToString(graph).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        timeRequiredforCompetition();
    }

}
