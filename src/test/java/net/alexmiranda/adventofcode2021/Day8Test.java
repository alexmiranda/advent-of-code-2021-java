package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day8Test {
    private static final String EXAMPLE = """
            be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
            edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
            fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
            fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
            aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
            fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
            dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
            bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
            egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
            gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
            """;

    @Test
    public void testExamplePart1() throws IOException {
        try (var reader = new StringReader(EXAMPLE)) {
            assertEquals(26, Day8.countUniqueDigitsOccurrence(reader));
        }
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        assertEquals(392, Day8.countUniqueDigitsOccurrence());
    }

    @Test
    public void testExamplePart2() throws IOException {
        try (var reader = new StringReader(EXAMPLE)) {
            assertEquals(61229, Day8.sumOutputValues(reader));
        }
    }

    @Test
    public void testPuzzleInputPart2() throws IOException {
        assertEquals(1004688, Day8.sumOutputValues());
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                'acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf', 5353
                'be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe', 8394
            """)
    public void testDecodeOutput(String s, int displayNumber) {
        assertEquals(displayNumber, Day8.decodeOutput(s));
    }
}
