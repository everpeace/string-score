package org.everpeace;

import org.junit.Test;

import static org.everpeace.StringScore.score;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * behavioral test of {@link StringScore}.
 * <p/>
 * User: Shingo Omura <everpeace _at_ gmail _dot_ com>
 * Date: 11/03/23
 */
public class StringScoreBehavioralTest {

    @Test
    public void exactMatch() {
        // Exact matches should score 1.0
        assertThat(score("Hello World", "Hello World"), is(1.0d));
    }

    @Test
    public void notMatch() {
        // non-existint charactor in match should return 0
        assertThat(score("hello world", "hellx"), is(0d));
        assertThat(score("hello world", "hello_world"), is(0d));
    }

    @Test
    public void matchMustBeSequential() {
        // Matches out of order should return 0
        assertThat(score("Hello World", "WH"), is(0d));
    }

    @Test
    public void sameCaseShouldMatchBetterThenWrongCase() {
        // Same case should match better then wrong case
        assertTrue(score("Hello World", "hello") < score("Hello World", "Hello"));
    }

    @Test
    public void higherScoreForCloserMatches() {
        // "He" should match "Hello World" better then "H" does
        assertTrue(score("Hello World", "H") < score("Hello World", "He"));
    }

    @Test
    public void matchingWithWrongCase() {
        // should match first matchable letter regardless of case
        assertTrue(score("Hillsdale Michigan", "himi") > 0);
    }

    @Test
    public void shouldHaveProperRelativeWeighting() {
        assertTrue(score("hello world", "e") < score("hello world", "h"));
        assertTrue(score("hello world", "h") < score("hello world", "he"));
        assertTrue(score("hello world", "hel") < score("hello world", "hell"));
        assertTrue(score("hello world", "hell") < score("hello world", "hello"));
        assertTrue(score("hello world", "hello") < score("hello world", "helloworld"));
        assertTrue(score("hello world", "helloworl") < score("hello world", "hello worl"));
    }

    @Test
    public void consecutiveLetterBonus() {
        // "Hel" should match "Hello World" better then "Hld"
        assertTrue(score("Hello World", "Hel") > score("Hello World", "Hld"));
    }

    @Test
    public void acronymBonus() {
        // "HW" should score higher with "Hello World" then Ho'
        assertTrue(score("Hello World", "HW") > score("Hello World", "Ho"));
        assertTrue(score("yet another Hello World", "yaHW") > score("Hello World", "yet another"));
        // "HiMi" should match "Hillsdale Michigan" higher then "Hil"
        assertTrue(score("Hillsdale Michigan", "HiMi") > score("Hillsdale Michigan", "Hil"));
        assertTrue(score("Hillsdale Michigan", "HiMi") > score("Hillsdale Michigan", "illsda"));
        assertTrue(score("Hillsdale Michigan", "HiMi") > score("Hillsdale Michigan", "hills"));
        assertTrue(score("Hillsdale Michigan", "HiMi") < score("Hillsdale Michigan", "hillsd"));
    }

    @Test
    public void beginningOfStringBonus() {
        assertTrue(score("Hillsdale", "hi") > score("Chippewa", "hi"));
        // should have a bonus for matching first letter
        assertTrue(score("hello world", "h") > score("hello world", "w"));
    }

    @Test
    public void properStringWeights() {
        // "res" matches "Mary Conces" better then "Research Resources North"
        assertTrue(score("Research Resources North", "res") > score("Mary Conces", "res"));
        assertTrue(score("Research Resources North", "res") > score("Bonnie Strathern - Southwest Michigan Title Search", "res"));
    }

    @Test
    public void startOfStringBonus() {
        assertTrue(score("Mary Large", "mar") > score("Large Mary", "mar"));
        // ensure start of string bonus only on start of string
        assertTrue(score("Silly Mary Large", "mar") == score("Silly Large Mary", "mar"));
    }

    @Test
    public void shouldScoreMismatchedStrings() {
        // should score 0 without a specified fuzziness.
        assertThat(score("Hello World", "Hz"), is(0d));
        // fuzzy matches should be worse then good ones
        assertTrue(score("Hello World", "Hz", 0.5) < score("Hello World", "H", 0.5));
    }

    @Test
    public void shouldBeTunedWell() {
        //mismatch should always be worse
        assertTrue(score("hello world", "hello worl", 0.5) > score("hello world", "hello wor1", 0.5));
        //"Hello World" should match "jello" more then 0 with a fuzziness of 0.5'
        assertTrue(score("Hello World", "jello", 0.5) > 0);
    }

    @Test
    public void shouldHaveVaryingDegreesOfFuzziness() {
        // higher fuzziness should yield higher scores
        assertTrue(score("Hello World", "Hz", 0.9) > score("Hello World", "Hz", 0.5));
    }
}
