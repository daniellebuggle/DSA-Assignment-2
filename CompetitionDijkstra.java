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
import java.util.*;

public class CompetitionDijkstra {

    double[][] graph;
    private final int sA;
    private final int sB;
    private final int sC;
    private final String filename;
    private int numberOfVertices;
    List<Integer> shortestTime = new ArrayList<>();
    double[][] distance;


    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA,       sB, sC: speeds for 3 contestants
     */
    CompetitionDijkstra(String filename, int sA, int sB, int sC) {
        this.filename = filename;
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
            numberOfVertices = 0;
            int numberOfEdges = 0;
            while (myReader.hasNextInt()) {
                if (count == 0) {
                    numberOfVertices = myReader.nextInt();
                    graph = new double[numberOfVertices][numberOfVertices];
                    for (int i = 0; i < numberOfVertices; i++)
                        for (int j = 0; j < numberOfVertices; j++)
                            graph[i][j] = -3;
                    count++;
                } else if (count == 1) {
                    numberOfEdges = myReader.nextInt();
                    count++;
                } else {
                    if (numberOfVertices > 0) {
                        for (int i = 0; i < numberOfEdges; i++) {
                            int v1 = myReader.nextInt();
                            int v2 = myReader.nextInt();
                            double weight = myReader.nextDouble();
                            graph[v1][v2] = weight;
                        }
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
    int timeRequiredforCompetition() {
        if (dijkstra_GetMinDistances() == -1
                || filename == null) {
            return -1;
        }
        if (sA < 50 || sA > 100 ||
                sB < 50 || sB > 100 ||
                sC < 50 || sC > 100 ||
                numberOfVertices <= 0) {
            return -1;
        }
        int total;
        double distance1 = Double.MIN_VALUE;

        for (double[] doubles : distance) {
            for (double value : doubles) {
                if (value > distance1) {
                    distance1 = value;
                }
            }
        }
        shortestTime.add(sA);
        shortestTime.add(sB);
        shortestTime.add(sC);
        int longestSpeed = Collections.min(shortestTime);

        distance1 *= 1000;

        total = (int) Math.ceil(distance1 / longestSpeed);

        return total;
    }

    //get the vertex with minimum distance which is not included in SPT
    int getMinimumVertex(double[] mst, LinkedList<Integer> key) {
        double minDis = Integer.MAX_VALUE;
        int indx = -1;
        for (int i = 0; i < numberOfVertices; i++) {
            if (mst[i] < minDis && !key.contains(i)) {
                minDis = mst[i];
                indx = i;
            }
        }
        return indx;
    }

    public int dijkstra_GetMinDistances() {
        boolean[] spt = new boolean[numberOfVertices];
        distance = new double[numberOfVertices][numberOfVertices];

        //Initialize all the distance to infinity

        for (int row = 0; row < distance.length; row++) {
            for (int col = 0; col < distance[row].length; col++) {
                if (row != col) {
                    distance[row][col] = Integer.MAX_VALUE;
                } else {
                    distance[row][col] = 0;
                }
            }
        }

        LinkedList<Integer> q = new LinkedList<>();

        for (int i = 0; i < numberOfVertices; i++) {
            q.clear();

            while (q.size() != numberOfVertices) {
                int vertex = getMinimumVertex(distance[i], q);
                q.add(vertex);

                for (int j = 0; j < numberOfVertices; j++) {
                    if (vertex != -1) {
                        if (graph[vertex][j] != -3) {
                            if (distance[i][j] > graph[vertex][j] + distance[i][vertex]) {
                                distance[i][j] = graph[vertex][j] + distance[i][vertex];
                            }
                        }
                    } else {
                        return -1;
                    }
                }
            }
        }
        return 0;
    }

}


