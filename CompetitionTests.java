import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CompetitionTests {

    @Test
    public void testDijkstraConstructor() {
        CompetitionDijkstra competition = new CompetitionDijkstra("tinyEWD.txt", 5, 1, 9);
    }

    @Test
    public void testDijkstraTime() {
        CompetitionDijkstra competition = new CompetitionDijkstra("input-K.txt", 51, 70, 88);
        assertEquals("Checking if returning correct time:", competition.timeRequiredforCompetition(), 314);

        competition = new CompetitionDijkstra("input-K.txt", 70, 51, 88);
        assertEquals("Checking if returning correct time:", competition.timeRequiredforCompetition(), 314);

        competition = new CompetitionDijkstra("input-K.txt", 88, 70, 51);
        assertEquals("Checking if returning correct time:", competition.timeRequiredforCompetition(), 314);

        competition = new CompetitionDijkstra("input-K.txt", 49, 70, 88);
        assertEquals("Checking returning -1 for speed less than 50", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-K.txt", 101, 70, 88);
        assertEquals("Checking returning -1 for speed greater than 100", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-K.txt", 51, 49, 88);
        assertEquals("Checking returning -1 for speed less than 50", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-K.txt", 101, 70, 88);
        assertEquals("Checking returning -1 for speed greater than 50", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-K.txt", 51, 70, 49);
        assertEquals("Checking returning -1 for speed less than 50", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-K.txt", 51, 70, 101);
        assertEquals("Checking returning -1 for speed greater than 50", competition.timeRequiredforCompetition(), -1);


        competition = new CompetitionDijkstra(null, 51, 70, 88);
        assertEquals("Checking returning -1 for filename null", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-J.txt", 51, 70, 88);
        assertEquals("Checking returning -1 for vertices at 0", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("input-C.txt", 51, 70, 88);
        assertEquals("Checking returning -1 for vertices at 0", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionDijkstra("inputC.txt", 51, 70, 88);
        assertEquals("Checking returning -1 for incorrect file", competition.timeRequiredforCompetition(), -1);
    }

    @Test
    public void testFWConstructor() {
        CompetitionFloydWarshall competition = new CompetitionFloydWarshall("input-B.txt", 51, 70, 88);
        System.out.println(competition.timeRequiredforCompetition());
    }

    @Test
    public void testFWTime() {
        CompetitionFloydWarshall competition = new CompetitionFloydWarshall("input-K.txt", 51, 70, 88);
        assertEquals("Checking if returning correct time:", competition.timeRequiredforCompetition(), 314);

        competition = new CompetitionFloydWarshall("input-K.txt", 70, 51, 88);
        assertEquals("Checking if returning correct time:", competition.timeRequiredforCompetition(), 314);

        competition = new CompetitionFloydWarshall("input-K.txt", 70, 88, 51);
        assertEquals("Checking if returning correct time:", competition.timeRequiredforCompetition(), 314);

        competition = new CompetitionFloydWarshall("input-K.txt", 49, 70, 88);
        assertEquals("Checking returning -1 for speed less than 50", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionFloydWarshall(null, 51, 70, 88);
        assertEquals("Checking returning -1 for filename null", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionFloydWarshall("input-J.txt", 51, 70, 88);
        assertEquals("Checking returning -1 for vertices at 0", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionFloydWarshall("input-C.txt", 51, 70, 88);
        assertEquals("Checking returning -1 for vertices at 0", competition.timeRequiredforCompetition(), -1);

        competition = new CompetitionFloydWarshall("inputC.txt", 51, 70, 88);
        assertEquals("Checking returning -1 for incorrect file name", competition.timeRequiredforCompetition(), -1);

    }
}
