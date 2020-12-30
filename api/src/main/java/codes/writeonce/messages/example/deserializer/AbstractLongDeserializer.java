package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class AbstractLongDeserializer<T> implements Deserializer<T> {

    protected long value;

    private int state = 8;

    @Override
    public void reset() {
        state = 8;
    }

    public void init(long value, int state) {

        this.value = value;
        this.state = state;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        final int state = this.state;

        if (state > remaining) {
            switch (remaining) {
                case 7:
                    this.value = this.value << 56 |
                                 (0xffL & byteBuffer.get()) << 48 |
                                 (0xffffL & byteBuffer.getShort()) << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = state - remaining;
                    return -1;
                case 6:
                    this.value = this.value << 48 |
                                 (0xffffL & byteBuffer.getShort()) << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = state - remaining;
                    return -1;
                case 5:
                    this.value = this.value << 40 |
                                 (0xffL & byteBuffer.get()) << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = state - remaining;
                    return -1;
                case 4:
                    this.value = this.value << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = state - remaining;
                    return -1;
                case 3:
                    this.value = this.value << 24 |
                                 (0xffL & byteBuffer.get()) << 16 |
                                 0xffffL & byteBuffer.getShort();
                    this.state = state - remaining;
                    return -1;
                case 2:
                    this.value = this.value << 16 |
                                 0xffffL & byteBuffer.getShort();
                    this.state = state - remaining;
                    return -1;
                case 1:
                    this.value = this.value << 8 |
                                 0xffL & byteBuffer.get();
                    this.state = state - remaining;
                    return -1;
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 8:
                    this.value = byteBuffer.getLong();
                    return remaining - state;
                case 7:
                    this.value = this.value << 56 |
                                 (0xffL & byteBuffer.get()) << 48 |
                                 (0xffffL & byteBuffer.getShort()) << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = 8;
                    return remaining - state;
                case 6:
                    this.value = this.value << 48 |
                                 (0xffffL & byteBuffer.getShort()) << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = 8;
                    return remaining - state;
                case 5:
                    this.value = this.value << 40 |
                                 (0xffL & byteBuffer.get()) << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = 8;
                    return remaining - state;
                case 4:
                    this.value = this.value << 32 |
                                 0xffffffffL & byteBuffer.getInt();
                    this.state = 8;
                    return remaining - state;
                case 3:
                    this.value = this.value << 24 |
                                 (0xffL & byteBuffer.get()) << 16 |
                                 0xffffL & byteBuffer.getShort();
                    this.state = 8;
                    return remaining - state;
                case 2:
                    this.value = this.value << 16 |
                                 0xffffL & byteBuffer.getShort();
                    this.state = 8;
                    return remaining - state;
                case 1:
                    this.value = this.value << 8 |
                                 0xffL & byteBuffer.get();
                    this.state = 8;
                    return remaining - state;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        final int state = this.state;
        final int remaining = end - start;

        if (state > remaining) {
            switch (remaining) {
                case 7:
                    this.value = (0xffL & bytes[start]) << 48 |
                                 (0xffL & bytes[start + 1]) << 40 |
                                 (0xffL & bytes[start + 2]) << 32 |
                                 (0xffL & bytes[start + 3]) << 24 |
                                 (0xffL & bytes[start + 4]) << 16 |
                                 (0xffL & bytes[start + 5]) << 8 |
                                 0xffL & bytes[start + 6];
                    this.state = state - remaining;
                    return -1;
                case 6:
                    this.value = (0xffL & bytes[start]) << 40 |
                                 (0xffL & bytes[start + 1]) << 32 |
                                 (0xffL & bytes[start + 2]) << 24 |
                                 (0xffL & bytes[start + 3]) << 16 |
                                 (0xffL & bytes[start + 4]) << 8 |
                                 0xffL & bytes[start + 5];
                    this.state = state - remaining;
                    return -1;
                case 5:
                    this.value = (0xffL & bytes[start]) << 32 |
                                 (0xffL & bytes[start + 1]) << 24 |
                                 (0xffL & bytes[start + 2]) << 16 |
                                 (0xffL & bytes[start + 3]) << 8 |
                                 0xffL & bytes[start + 4];
                    this.state = state - remaining;
                    return -1;
                case 4:
                    this.value = (0xffL & bytes[start]) << 24 |
                                 (0xffL & bytes[start + 1]) << 16 |
                                 (0xffL & bytes[start + 2]) << 8 |
                                 0xffL & bytes[start + 3];
                    this.state = state - remaining;
                    return -1;
                case 3:
                    this.value = (0xffL & bytes[start]) << 16 |
                                 (0xffL & bytes[start + 1]) << 8 |
                                 0xffL & bytes[start + 2];
                    this.state = state - remaining;
                    return -1;
                case 2:
                    this.value = this.value << 16 |
                                 (0xffL & bytes[start]) << 8 |
                                 0xffL & bytes[start + 1];
                    this.state = state - remaining;
                    return -1;
                case 1:
                    this.value = this.value << 8 |
                                 0xffL & bytes[start];
                    this.state = state - remaining;
                    return -1;
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 8:
                    this.value = (0xffL & bytes[start]) << 56 |
                                 (0xffL & bytes[start + 1]) << 48 |
                                 (0xffL & bytes[start + 2]) << 40 |
                                 (0xffL & bytes[start + 3]) << 32 |
                                 (0xffL & bytes[start + 4]) << 24 |
                                 (0xffL & bytes[start + 5]) << 16 |
                                 (0xffL & bytes[start + 6]) << 8 |
                                 0xffL & bytes[start + 7];
                    return start + state;
                case 7:
                    this.value = this.value << 56 |
                                 (0xffL & bytes[start]) << 48 |
                                 (0xffL & bytes[start + 1]) << 40 |
                                 (0xffL & bytes[start + 2]) << 32 |
                                 (0xffL & bytes[start + 3]) << 24 |
                                 (0xffL & bytes[start + 4]) << 16 |
                                 (0xffL & bytes[start + 5]) << 8 |
                                 0xffL & bytes[start + 6];
                    this.state = 8;
                    return start + state;
                case 6:
                    this.value = this.value << 48 |
                                 (0xffL & bytes[start]) << 40 |
                                 (0xffL & bytes[start + 1]) << 32 |
                                 (0xffL & bytes[start + 2]) << 24 |
                                 (0xffL & bytes[start + 3]) << 16 |
                                 (0xffL & bytes[start + 4]) << 8 |
                                 0xffL & bytes[start + 5];
                    this.state = 8;
                    return start + state;
                case 5:
                    this.value = this.value << 40 |
                                 (0xffL & bytes[start]) << 32 |
                                 (0xffL & bytes[start + 1]) << 24 |
                                 (0xffL & bytes[start + 2]) << 16 |
                                 (0xffL & bytes[start + 3]) << 8 |
                                 0xffL & bytes[start + 4];
                    this.state = 8;
                    return start + state;
                case 4:
                    this.value = this.value << 32 |
                                 (0xffL & bytes[start]) << 24 |
                                 (0xffL & bytes[start + 1]) << 16 |
                                 (0xffL & bytes[start + 2]) << 8 |
                                 0xffL & bytes[start + 3];
                    this.state = 8;
                    return start + state;
                case 3:
                    this.value = this.value << 24 |
                                 (0xffL & bytes[start]) << 16 |
                                 (0xffL & bytes[start + 1]) << 8 |
                                 0xffL & bytes[start + 2];
                    this.state = 8;
                    return start + state;
                case 2:
                    this.value = this.value << 16 |
                                 (0xffL & bytes[start]) << 8 |
                                 0xffL & bytes[start + 1];
                    this.state = 8;
                    return start + state;
                case 1:
                    this.value = this.value << 8 |
                                 0xffL & bytes[start];
                    this.state = 8;
                    return start + state;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
