package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BigDecimalDeserializer implements Deserializer<BigDecimal> {

    private static final int CACHE_SIZE = 250;

    private final byte[][] cache = new byte[CACHE_SIZE][];

    @Nonnull
    private final IntDeserializer intDeserializer;

    @Nonnull
    private final LongDeserializer longDeserializer;

    private int state;

    private int size;

    private int position;

    private byte[] magnitude;

    public BigDecimalDeserializer(
            @Nonnull IntDeserializer intDeserializer,
            @Nonnull LongDeserializer longDeserializer
    ) {
        this.intDeserializer = intDeserializer;
        this.longDeserializer = longDeserializer;
    }

    @Override
    public void reset() {

        intDeserializer.reset();
        longDeserializer.reset();
        state = 0;
        magnitude = null;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        while (true) {
            switch (state) {
                case 0:
                    remaining = intDeserializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    init();
                    break;
                case 1:
                    final int length = size - position;
                    if (remaining >= length) {
                        remaining -= length;
                        byteBuffer.get(magnitude, position, length);
                        state = 3;
                        break;
                    } else {
                        byteBuffer.get(magnitude, position, remaining);
                        position += remaining;
                        return -1;
                    }
                case 2:
                    remaining = longDeserializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    state = 3;
                    break;
                case 3:
                    remaining = intDeserializer.consume(byteBuffer, remaining);
                    if (remaining != -1) {
                        state = 0;
                    }
                    return remaining;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        while (true) {
            switch (state) {
                case 0:
                    start = intDeserializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    init();
                    break;
                case 1:
                    final int length = size - position;
                    final int remaining = end - start;
                    if (remaining >= length) {
                        System.arraycopy(bytes, start, magnitude, position, length);
                        start += length;
                        state = 3;
                        break;
                    } else {
                        System.arraycopy(bytes, start, magnitude, position, remaining);
                        position += remaining;
                        return -1;
                    }
                case 2:
                    start = longDeserializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    state = 3;
                    break;
                case 3:
                    start = intDeserializer.consume(bytes, start, end);
                    if (start != -1) {
                        state = 0;
                    }
                    return start;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private void init() {

        size = intDeserializer.intValue();

        if (size < 0) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            longDeserializer.init(0, 8);
            state = 3;
            magnitude = null;
        } else if (size > 8) {
            state = 1;
            magnitude = allocate(size);
            position = 0;
        } else {
            longDeserializer.init(0, size);
            state = 2;
            magnitude = null;
        }
    }

    @Nonnull
    @Override
    public BigDecimal value() {

        if (magnitude == null) {
            long value = longDeserializer.longValue();
            if ((size & 7) != 0) {
                final int bits = (8 - size) * 8;
                value = value << bits >> bits;
            }
            return BigDecimal.valueOf(value, intDeserializer.intValue());
        } else {
            final BigDecimal value = new BigDecimal(new BigInteger(magnitude), intDeserializer.intValue());
            magnitude = null;
            return value;
        }
    }

    @Nonnull
    private byte[] allocate(int size) {

        final int i = size - 9;

        if (i < CACHE_SIZE) {
            byte[] bytes = cache[i];
            if (bytes == null) {
                bytes = new byte[size];
                cache[i] = bytes;
            }
            return bytes;
        } else {
            return new byte[size];
        }
    }
}
