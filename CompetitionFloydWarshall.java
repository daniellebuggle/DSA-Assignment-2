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
 * This class implements the competition using Floyd-Warshall algorithm
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CompetitionFloydWarshall {
    private String filename;
    private final int sA;
    private final int sB;
    private final int sC;
    private int numberOfVertices;
    private int numberOfEdges;

    private double[][] graph;

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA,       sB, sC: speeds for 3 contestants
     */
    CompetitionFloydWarshall(String filename, int sA, int sB, int sC) {
        this.sA = sA; // walking speed of contestant A
        this.sB = sB; // walking speed of contestant B
        this.sC = sC; // walking speed of contestant C

        try {
            if (filename == null) {
                return;
            }
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextInt()) {
                if (count == 0) {
                    numberOfVertices = myReader.nextInt();
                    graph = new double[numberOfVertices][numberOfVertices];
                    for (int i = 0; i < numberOfVertices; i++)
                        for (int j = 0; j < numberOfVertices; j++)
                            graph[i][j] = Integer.MAX_VALUE;
                    count++;
                } else if (count == 1) {
                    numberOfEdges = myReader.nextInt();
                    count++;
                } else {
                    for (int i = 0; i < numberOfEdges; i++) {
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


    double[][] floydWarshall(double[][] graph) {
        double[][] dist = new double[numberOfVertices][numberOfVertices];
        int i, j, k;

        /* Initialize the solution matrix
           same as input graph matrix.
           Or we can say the initial values
           of shortest distances
           are based on shortest paths
           considering no intermediate
           vertex. */
        for (i = 0; i < numberOfVertices; i++)
            for (j = 0; j < numberOfVertices; j++) {
                if (i == j)
                    dist[i][j] = 0;
                else dist[i][j] = graph[i][j];
            }

        /* Add all vertices one by one
           to the set of intermediate
           vertices.
          ---> Before start of an iteration,
               we have shortest
               distances between all pairs
               of vertices such that
               the shortest distances consider
               only the vertices in
               set {0, 1, 2, .. k-1} as
               intermediate vertices.
          ----> After the end of an iteration,
                vertex no. k is added
                to the set of intermediate
                vertices and the set
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < numberOfVertices; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < numberOfVertices; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < numberOfVertices; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        // Print the shortest distance matrix
        return dist;
    }

    /**
     * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition() {
        if (numberOfVertices > 0 && sA <= 100 && sA >= 50 && sB <= 100 && sB >= 50 && sC <= 100 && sC >= 50) {
            int slowestSpeed;
            if (sA <= sB && sA <= sC)
                slowestSpeed = sA;
            else if (sB <= sA && sB <= sC)
                slowestSpeed = sB;
            else slowestSpeed = sC;
            double[][] floydWarshall;
            ArrayList<Double> list = new ArrayList<>();
            floydWarshall = floydWarshall(graph);
            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    if (floydWarshall[i][j] == Integer.MAX_VALUE)
                        return -1;
                    else list.add(floydWarshall[i][j]);
                }
            }
            double topDistance = Collections.max(list);
            double slowestSpeedInKpm = (double) slowestSpeed / 1000;
            double timeInMinutes = topDistance / slowestSpeedInKpm;
            return (int) Math.ceil(timeInMinutes);
        } else return -1;
    }

}