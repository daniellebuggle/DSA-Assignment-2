import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CompetitionTests {

    @Test
    public void testDijkstraConstructor() {
        CompetitionDijkstra competition = new CompetitionDijkstra("tinyEWD.txt", 5, 1, 9);
        double[][] test = { {0, 0, 0.26, 0, 0.38, 0, 0, 0},
                {0, 0, 0, 0.29, 0, 0, 0, 0 },
                {0, 0, 0, 0, 0, 0, 0, 0.34},
                {0, 0, 0, 0, 0, 0, 0.52, 0},
                {0, 0, 0, 0, 0, 0.35, 0, 0.37},
                {0, 0.32, 0, 0, 0.35, 0, 0, 0.28},
                {0.58, 0, 0.40, 0, 0.93, 0, 0, 0},
                {0, 0, 0, 0.39, 0, 0.28, 0, 0}
        };
        assertArrayEquals(test, competition.graph);
        competition = new CompetitionDijkstra(null,1,2,3);
        //TODO
    }



    @Test
    public void testDijkstraTime(){
        CompetitionDijkstra competition = new CompetitionDijkstra("input-K.txt", 51,70,88);
        System.out.println(competition.timeRequiredforCompetition());
    }

    @Test
    public void testFWConstructor() {
        //TODO
    }

    //TODO - more tests
    
}
