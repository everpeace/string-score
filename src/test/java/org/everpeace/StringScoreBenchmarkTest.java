package org.everpeace;

import org.junit.Test;

import static org.everpeace.StringScore.score;

/**
 * benchmark for {@link StringScore}.
 * <p/>
 * User: Shingo Omura <everpeace _at_ gmail _dot_ com>
 * Date: 11/03/24
 */
public class StringScoreBenchmarkTest {
    @Test
    public void expandToSeeTimeToScore() {
        int iterations = 4000;

        long start1 = System.currentTimeMillis();
        for (int i = iterations; i > 0; i--) {
            score("hello world", "h");
        }
        long end1 = System.currentTimeMillis();
        long t1 = end1 - start1;
        System.out.println(t1 + " milliseconds to do " + iterations + " iterations of score(\"hello world\", \"h\")");

        long start2 = System.currentTimeMillis();
        for (int i = iterations; i > 0; i--) {
            score("hello world", "hw");
        }
        long end2 = System.currentTimeMillis();
        long t2 = end2 - start2;
        System.out.println(t2 + " milliseconds to do " + iterations + " iterations of score(\"hello world\", \"hw\")");

        long start3 = System.currentTimeMillis();
        for (int i = iterations; i > 0; i--) {
            score("hello world", "hello world");
        }
        long end3 = System.currentTimeMillis();
        long t3 = end3 - start3;
        System.out.println(t3 + " milliseconds to do " + iterations + " iterations of score(\"hello world\", \"hello world\")");

        long start4 = System.currentTimeMillis();
        for (int i = iterations; i > 0; i--) {
            score("hello any world that will listen", "hlo wrdthlstn");
        }
        long end4 = System.currentTimeMillis();
        long t4 = end4 - start4;
        System.out.println(t4 + " milliseconds to do " + iterations +
                " iterations of score(\"hello any world that will listen\", \"hlo wrdthlstn\")");

        long start5 = System.currentTimeMillis();
        for (int i = iterations; i > 0; i--) {
            score("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                    , "Lorem i dor coecadipg et, Duis aute irure dole nulla. qui ofa mot am l");
        }
        long end5 = System.currentTimeMillis();
        long t5 = end5 - start5;
        System.out.println(t5 + " milliseconds to do " + iterations + " iterations of 446 character string scoring a 70 character match");

        System.out.println("Average Score... all benchmarks devided by the number of benchmarks (smaller is faster): "
                + ((t1 + t2 + t3 + t4 + t5) / (5.0d)) + "ms");
    }
}
