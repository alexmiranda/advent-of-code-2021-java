package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day16Test {
    private static final String INPUT = "/2021/Day/16/input";

    @ParameterizedTest
    @CsvSource(textBlock = """
            '38006F45291200',9
            '8A004A801A8002F478',16
            '620080001611562C8802118E34',12
            'C0015000016115A2E0802F182340',23
            'A0016C880162017C3686B18A3D4780',31
            """)
    public void testExamplePart1(String s, int sum) throws Exception {
        var reader = new StringReader(s);
        assertEquals(sum, Day16.decode(reader).sumOfVersionNumbers());
    }

    @Test
    public void testPuzzleInputPart1() throws Exception {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day16Test.class.getResourceAsStream(INPUT)))) {
            assertEquals(854, Day16.decode(reader).sumOfVersionNumbers());
        }
    }

    @Test
    public void testExampleDecodePart1() throws Exception {
        var reader = new StringReader("D2FE28");
        var packet = Day16.decode(reader);
        assertEquals(6, packet.version());
        assertEquals(4, packet.type());
        assertTrue(packet.isLiteral());
        assertEquals(2021, packet.literalValue());
    }
}
