package net.alexmiranda.adventofcode2021;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;

public class Day16 {
    public static class Packet {
        private static final int TYPE_LITERAL = 4;

        private int version;
        private int type;
        private boolean lengthTypeId;
        private long literalValue;
        private final ArrayList<Packet> subpackets = new ArrayList<>(3);

        public int version() {
            return this.version;
        }

        public int type() {
            return this.type;
        }

        public long literalValue() {
            return this.literalValue;
        }

        public int sumOfVersionNumbers() {
            int sum = this.version;
            for (var subpacket : this.subpackets) {
                sum += subpacket.sumOfVersionNumbers();
            }
            return sum;
        }

        public boolean isLiteral() {
            return this.type == TYPE_LITERAL;
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
            var packet = new Packet();
            packet.version = readInt(buf, 3);
            packet.type = readInt(buf, 3);

            // literal packet
            if (packet.isLiteral()) {
                packet.literalValue = readLiteral(buf);
                return packet;
            }

            // operator packet
            packet.lengthTypeId = readBoolean(buf);
            if (packet.lengthTypeId) {
                int subpacketCount = readInt(buf, 11);
                packet.subpackets.ensureCapacity(subpacketCount);
                for (int i = 0; i < subpacketCount; i++) {
                    packet.subpackets.add(this.decode(buf));
                }
            } else {
                int subpacketsLength = readInt(buf, 15);
                int start = this.read;
                while (this.read < start + subpacketsLength) {
                    packet.subpackets.add(this.decode(buf));
                }
            }

            return packet;
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

}
