package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        var packet = Day16.decode(reader);
        assertEquals(sum, Day16.sumOfVersionNumbers(packet));
    }

    @Test
    public void testPuzzleInputPart1() throws Exception {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day16Test.class.getResourceAsStream(INPUT)))) {
            var packet = Day16.decode(reader);
            assertEquals(854, Day16.sumOfVersionNumbers(packet));
        }
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            'C200B40A82',3
            '04005AC33890',54
            '880086C3E88112',7
            'CE00C43D881120',9
            'D8005AC2A8F0',1
            'F600BC2D8F',0
            '9C005AC2F8F0',0
            '9C0141080250320F1802104A08',1
            """)
    public void testExamplePart2(String s, long value) throws Exception {
        var reader = new StringReader(s);
        var packet = Day16.decode(reader);
        assertEquals(value, packet.value());
    }

    @Test
    public void testPuzzleInputPart2() throws Exception {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day16Test.class.getResourceAsStream(INPUT)))) {
            var packet = Day16.decode(reader);
            assertEquals(186_189_840_660L, packet.value());
        }
    }
}
