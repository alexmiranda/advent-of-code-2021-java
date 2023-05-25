package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
    private static final String INPUT = "/2021/Day/12/input";

    private static final String EXAMPLE_1 = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
            """;

    private static final String EXAMPLE_2 = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
            """;

    private static final String EXAMPLE_3 = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
            """;

    @ParameterizedTest
    @MethodSource("examples")
    public void testExamplePart1(String example, int expected) {
        var reader = new StringReader(example);
        var start = Day12.readInput(reader);
        assertEquals(expected, start.discover());
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day12.class.getResourceAsStream(INPUT)))) {
            var start = Day12.readInput(reader);
            assertEquals(3887, start.discover());
        }
    }

    static Stream<Arguments> examples() {
        return Stream.of(
                Arguments.of(EXAMPLE_1, 10),
                Arguments.of(EXAMPLE_2, 19),
                Arguments.of(EXAMPLE_3, 226)
        );
    }
}
