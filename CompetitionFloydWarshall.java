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
import java.util.Scanner;

public class CompetitionFloydWarshall {
    private final int sA;
    private final int sB;
    private final int sC;

    private double[][] graph;

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */
    CompetitionFloydWarshall (String filename, int sA, int sB, int sC){
        this.sA = sA; // walking speed of contestant A
        this.sB = sB; // walking speed of contestant B
        this.sC = sC; // walking speed of contestant C

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int count = 0;
            int numberOfVertices;
            int numberOfEdges = 0;
            while (myReader.hasNextLine()) {
                if(count == 0){
                    numberOfVertices = myReader.nextInt();
                    graph = new double[numberOfVertices - 1][numberOfVertices - 1];
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

}