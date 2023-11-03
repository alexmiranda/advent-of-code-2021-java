package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Day18Test {
    private static final String INPUT = "/2021/Day/18/input";

    @ParameterizedTest
    @ValueSource(strings = {
        "[1,2]",
        "[[1,2],3]",
        "[9,[8,7]]",
        "[[1,9],[8,5]]",
        "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]",
        "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]",
        "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]",
    })
    public void testParseExamples(String s) throws Exception {
        var number = Day18.SnailfishNumber.parse(s);
        assertNotNull(number);
        assertEquals(s, number.toString());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/2021/day/18/examples.csv")
    public void testExamplePart1(String s1, String s2, String expectedResult) throws Exception {
        var number1 = Day18.SnailfishNumber.parse(s1);
        var number2 = Day18.SnailfishNumber.parse(s2);
        var actual = number1.add(number2);
        assertEquals(expectedResult, actual.toString());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/2021/day/18/magnitudes.csv")
    public void testExamplesMagnitude(String s, int expectedResult) throws Exception {
        var number = Day18.SnailfishNumber.parse(s);
        assertEquals(expectedResult, number.magnitude());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/2021/day/18/final_sum.csv")
    public void testFinalSumPart1(String input, String expectedResult) throws Exception {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day18Test.class.getResourceAsStream("/2021/day/18/" + input)))) {
            var a = Day18.SnailfishNumber.parse(reader);
            var b = Day18.SnailfishNumber.parse(reader);
            while (b != null) {
                a = a.add(b);
                b = Day18.SnailfishNumber.parse(reader);
            }
            assertEquals(expectedResult, a.toString());
        }
    }

    @Test
    public void testPuzzleInputPart1() throws Exception {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day18Test.class.getResourceAsStream(INPUT)))) {
            var a = Day18.SnailfishNumber.parse(reader);
            var b = Day18.SnailfishNumber.parse(reader);
            while (b != null) {
                a = a.add(b);
                b = Day18.SnailfishNumber.parse(reader);
            }
            assertEquals("[[[[7,7],[7,7]],[[7,0],[7,7]]],[[[8,8],[8,7]],[[7,8],[7,8]]]]", a.toString());
            assertEquals(4289, a.magnitude());
        }
    }
}
