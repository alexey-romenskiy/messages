package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BigDecimalSerializer implements Serializer<BigDecimal> {

    @Nonnull
    protected final IntSerializer intSerializer;

    @Nonnull
    protected final LongSerializer longSerializer;

    private int state;

    private int scale;

    private int position;

    private byte[] magnitude;

    public BigDecimalSerializer(@Nonnull IntSerializer intSerializer, @Nonnull LongSerializer longSerializer) {
        this.intSerializer = intSerializer;
        this.longSerializer = longSerializer;
    }

    @Override
    public void reset() {

        intSerializer.reset();
        longSerializer.reset();
        magnitude = null;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        while (true) {
            switch (state) {
                case 0:
                case 1:
                case 2:
                    remaining = intSerializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    intSerializer.value(scale);
                    state += 3;
                    if (remaining == 0) {
                        return remaining;
                    }
                    break;
                case 3:
                    return intSerializer.consume(byteBuffer, remaining);
                case 4:
                    remaining = longSerializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    state = 3;
                    if (remaining == 0) {
                        return remaining;
                    }
                    break;
                case 5:
                    final int length = magnitude.length - position;
                    if (remaining >= length) {
                        remaining -= length;
                        byteBuffer.put(magnitude, position, length);
                        state = 3;
                        if (remaining == 0) {
                            return remaining;
                        }
                        break;
                    } else {
                        byteBuffer.put(magnitude, position, remaining);
                        position += remaining;
                        return -1;
                    }
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
                case 1:
                case 2:
                    start = intSerializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    intSerializer.value(scale);
                    state += 3;
                    if (start == end) {
                        return start;
                    }
                    break;
                case 3:
                    return intSerializer.consume(bytes, start, end);
                case 4:
                    start = longSerializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    state = 3;
                    if (start == end) {
                        return start;
                    }
                    break;
                case 5:
                    final int length = magnitude.length - position;
                    final int remaining = end - start;
                    if (remaining >= length) {
                        System.arraycopy(magnitude, position, bytes, start, length);
                        start += length;
                        state = 3;
                        if (start == end) {
                            return start;
                        }
                        break;
                    } else {
                        System.arraycopy(magnitude, position, bytes, start, remaining);
                        position += remaining;
                        return -1;
                    }
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public void value(@Nonnull BigDecimal value) {

        scale = value.scale();

        if (value.signum() == 0) {
            intSerializer.value(0);
            state = 0;
        } else if (scale == 0 && value.precision() < 19) {
            final long longValueExact = value.longValueExact();
            final int byteLength = getByteLength(longValueExact);
            intSerializer.value(byteLength);
            longSerializer.value(longValueExact, byteLength);
            state = 1;
        } else {
            final BigInteger unscaledValue = value.unscaledValue();
            final int byteLength = getByteLength(unscaledValue);
            intSerializer.value(byteLength);
            if (byteLength > 8) {
                magnitude = unscaledValue.toByteArray();
                position = 0;
                state = 2;
            } else {
                final long longValueExact = unscaledValue.longValueExact();
                longSerializer.value(longValueExact, byteLength);
                state = 1;
            }
        }
    }

    private static int getByteLength(@Nonnull BigInteger value) {
        return value.bitLength() + 8 >> 3;
    }

    private static int getByteLength(long longValueExact) {

        return longValueExact < 0 ?
                ((longValueExact | 0x7fffffffL) == -1 ? (
                        (longValueExact | 0x7fffL) == -1 ?
                                ((longValueExact | 0x7fL) == -1 ? 1 : 2) :
                                ((longValueExact | 0x7fffffL) == -1 ? 3 : 4)) :
                        ((longValueExact | 0x7fffffffffffL) == -1 ?
                                ((longValueExact | 0x7fffffffffL) == -1 ? 5 : 6) :
                                ((longValueExact | 0x7fffffffffffffL) == -1 ? 7 : 8))) :
                ((longValueExact & ~0x7fffffffL) == 0 ? (
                        (longValueExact & ~0x7fffL) == 0 ?
                                ((longValueExact & ~0x7fL) == 0 ? 1 : 2) :
                                ((longValueExact & ~0x7fffffL) == 0 ? 3 : 4)) :
                        ((longValueExact & ~0x7fffffffffffL) == 0 ?
                                ((longValueExact & ~0x7fffffffffL) == 0 ? 5 : 6) :
                                ((longValueExact & ~0x7fffffffffffffL) == 0 ? 7 : 8)));
    }
}
