package net.alexmiranda.adventofcode2021;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;

public class Day16 {
    public sealed interface Packet permits LiteralPacket, OperationPacket {
        int TYPE_SUM = 0;
        int TYPE_PROD = 1;
        int TYPE_MIN = 2;
        int TYPE_MAX = 3;
        int TYPE_LIT = 4;
        int TYPE_GT = 5;
        int TYPE_LT = 6;
        int TYPE_EQ = 7;

        long value();
    }

    public static final class LiteralPacket implements Packet {
        private final int version;
        private final long literalValue;

        public LiteralPacket(int version, long literalValue) {
            this.version = version;
            this.literalValue = literalValue;
        }

        @Override
        public long value() {
            return this.literalValue;
        }
    }

    public static sealed abstract class OperationPacket implements Packet {
        private final int version;
        protected final ArrayList<Packet> subpackets = new ArrayList<>(2);

        protected OperationPacket(int version) {
            this.version = version;
        }

        abstract void addSubpacket(Packet packet);
    }

    public static final class SumPacket extends OperationPacket {
        private long sum = 0;

        private SumPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.sum;
        }

        @Override
        void addSubpacket(Packet packet) {
            this.subpackets.add(packet);
            this.sum = Math.addExact(this.sum, packet.value());
        }
    }

    public static final class ProductPacket extends OperationPacket {
        private long product = 1;

        private ProductPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.product;
        }

        @Override
        void addSubpacket(Packet packet) {
            this.subpackets.add(packet);
            this.product = Math.multiplyExact(this.product, packet.value());
        }
    }

    public static final class MinPacket extends OperationPacket {
        private long min = 0;

        private MinPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.min;
        }

        @Override
        void addSubpacket(Packet packet) {
            this.subpackets.add(packet);
            long val = packet.value();
            if (this.subpackets.size() == 1 || val < this.min) {
                this.min = val;
            }
        }
    }

    public static final class MaxPacket extends OperationPacket {
        private long max = 0;

        private MaxPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.max;
        }

        @Override
        void addSubpacket(Packet packet) {
            this.subpackets.add(packet);
            long val = packet.value();
            if (this.subpackets.size() == 1 || val > this.max) {
                this.max = val;
            }
        }
    }

    public static final class GreaterThanPacket extends OperationPacket {
        private boolean gt = false;

        private GreaterThanPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.gt ? 1L : 0L;
        }

        @Override
        void addSubpacket(Packet packet) {
            assert this.subpackets.size() <= 1;
            this.subpackets.add(packet);
            if (this.subpackets.size() > 1) {
                long lhs = this.subpackets.get(0).value();
                long rhs = packet.value();
                this.gt = lhs > rhs;
            }
        }
    }

    public static final class LessThanPacket extends OperationPacket {
        private boolean lt = false;

        private LessThanPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.lt ? 1L : 0L;
        }

        @Override
        void addSubpacket(Packet packet) {
            assert this.subpackets.size() <= 1;
            this.subpackets.add(packet);
            if (this.subpackets.size() > 1) {
                long lhs = this.subpackets.get(0).value();
                long rhs = packet.value();
                this.lt = lhs < rhs;
            }
        }
    }

    public static final class EqualPacket extends OperationPacket {
        private boolean eq = false;

        private EqualPacket(int version) {
            super(version);
        }

        @Override
        public long value() {
            return this.eq ? 1L : 0L;
        }

        @Override
        void addSubpacket(Packet packet) {
            assert this.subpackets.size() <= 1;
            this.subpackets.add(packet);
            if (this.subpackets.size() > 1) {
                long lhs = this.subpackets.get(0).value();
                long rhs = packet.value();
                this.eq = lhs == rhs;
            }
        }
    }


    private static class PacketDecoder implements AutoCloseable {
        private final HexToBinaryTransformer reader;
        private int read;

        private PacketDecoder(Reader reader) {
            this.reader = new HexToBinaryTransformer(reader);
        }

        private Packet decode() throws IOException {
            char[] buf = new char[16];
            return decode(buf);
        }

        private Packet decode(char[] buf) throws IOException {
            int version = readInt(buf, 3);
            int type = readInt(buf, 3);

            // literal packet
            if (isLiteral(type)) {
                long literalValue = readLiteral(buf);
                return new LiteralPacket(version, literalValue);
            }

            // operator packet
            var operatorPacket = createOperatorPacket(version, type);
            boolean lengthTypeId = readBoolean(buf);
            if (lengthTypeId) {
                int subpacketCount = readInt(buf, 11);
                operatorPacket.subpackets.ensureCapacity(subpacketCount);
                for (int i = 0; i < subpacketCount; i++) {
                    operatorPacket.addSubpacket(this.decode(buf));
                }
            } else {
                int subpacketsLength = readInt(buf, 15);
                int start = this.read;
                while (this.read < start + subpacketsLength) {
                    operatorPacket.addSubpacket(this.decode(buf));
                }
            }

            return operatorPacket;
        }

        private boolean isLiteral(int type) {
            return type == Packet.TYPE_LIT;
        }

        private OperationPacket createOperatorPacket(int version, int type) {
            return switch (type) {
                case Packet.TYPE_SUM -> new SumPacket(version);
                case Packet.TYPE_PROD -> new ProductPacket(version);
                case Packet.TYPE_MIN -> new MinPacket(version);
                case Packet.TYPE_MAX -> new MaxPacket(version);
                case Packet.TYPE_GT -> new GreaterThanPacket(version);
                case Packet.TYPE_LT -> new LessThanPacket(version);
                case Packet.TYPE_EQ -> new EqualPacket(version);
                default -> throw new IllegalStateException("Unexpected packet type: " + type);
            };
        }

        private long readLiteral(char[] buf) throws IOException {
            var sb = new StringBuilder(32);
            boolean continueReading = true;
            do {
                int read = this.reader.read(buf, 0, 5);
                assert read == 5;
                this.read += 5;

                var s = String.copyValueOf(buf, 1, 4);
                sb.append(s);
                continueReading = buf[0] == '1';
            } while (continueReading);
            return Long.parseUnsignedLong(sb.toString(), 2);
        }

        private int readInt(char[] buf, int len) throws IOException {
            int read = this.reader.read(buf, 0, len);
            assert read == len;
            this.read += read;
            var s = String.copyValueOf(buf, 0, len);
            return Integer.parseUnsignedInt(s, 2);
        }

        private boolean readBoolean(char[] buf) throws IOException {
            int read = this.reader.read(buf, 0, 1);
            assert read == 1 && (buf[0] == '1' || buf[0] == '0');
            this.read += read;
            return buf[0] == '1';
        }

        @Override
        public void close() throws Exception {
            this.reader.close();
        }
    }

    public static class HexToBinaryTransformer extends Reader {
        private final Reader reader;

        // buffer contains the contents read from the file.
        // Because we want to read the file as efficiently as possible, we should size this buffer to be as large as a single
        // disk/file system read which fits into the system's L2 cache, and in so doing, avoid expensive IO operations. As we know
        // the largest input file is smaller, then we are sizing this buffer to be the nearest power of two larger than the input file.
        private final char[] buffer = new char[2048];
        private int bufferIndex;
        private int bufferCount;

        // transformedBuffer contains the input where each hex char has been converted into a four-character long binary string
        // and because the maximum amount of binary conversions at a time that we need to make is 4 hex characters, i.e. 16 binary digits,
        // we can keep this buffer pretty small.
        private final char[] transformedBuffer = new char[16];
        private int transformedBufferIndex;
        private int transformedBufferCount;

        private HexToBinaryTransformer(Reader reader) {
            this.reader = reader;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int read = 0;
            while (read < len) {
                if (transformedBufferIndex >= transformedBufferCount && !refillTransformedBuffer()) {
                    break; // no more data to read
                }

                if (transformedBufferIndex >= transformedBufferCount) {
                    transformedBufferIndex = 0; // wrap around to the beginning of the transformed buffer
                }

                cbuf[off + read] = transformedBuffer[transformedBufferIndex];
                transformedBufferIndex++;
                read++;
            }

            return read > 0 ? read : -1;
        }

        private boolean refillTransformedBuffer() throws IOException {
            transformedBufferCount = 0;

            while (transformedBufferCount < transformedBuffer.length) {
                if (bufferIndex >= bufferCount && !refillBuffer()) {
                    return transformedBufferCount > 0; // still some data remaining in the transformed buffer
                }

                while (bufferIndex < bufferCount && transformedBufferCount < transformedBuffer.length) {
                    int hex = Character.digit(buffer[bufferIndex], 16);
                    bufferIndex++;

                    // probably hit the end of the buffer and found a null character, so just break
                    if (hex < 0) {
                        break;
                    }

                    String bin = String.format("%4s", Integer.toBinaryString(hex)).replace(' ', '0');
                    for (int j = 0; j < bin.length(); j++) {
                        transformedBuffer[transformedBufferCount++] = bin.charAt(j);
                    }
                }
            }

            return true;
        }

        private boolean refillBuffer() throws IOException {
            bufferCount = reader.read(buffer);
            bufferIndex = 0;
            return bufferCount != -1;
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }

    public static Packet decode(Reader reader) throws Exception {
        try (var decoder = new PacketDecoder(reader)) {
            return Objects.requireNonNull(decoder.decode());
        }
    }

    public static int sumOfVersionNumbers(Packet packet) {
        return switch (packet) {
            case LiteralPacket lit -> lit.version;
            case OperationPacket op -> {
                int sum = op.version;
                for (var subpacket : op.subpackets) {
                    sum += sumOfVersionNumbers(subpacket);
                }
                yield sum;
            }
        };
    }
}
