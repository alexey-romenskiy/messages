package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class AbstractIntDeserializer<T> implements Deserializer<T> {

    protected int value;

    private int state = 4;

    @Override
    public void reset() {
        state = 4;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        final int state = this.state;

        if (state > remaining) {
            switch (remaining) {
                case 3:
                    this.value = this.value << 24 |
                                 (0xff & byteBuffer.get()) << 16 |
                                 0xffff & byteBuffer.getShort();
                    this.state = state - remaining;
                    return -1;
                case 2:
                    this.value = this.value << 16 |
                                 0xffff & byteBuffer.getShort();
                    this.state = state - remaining;
                    return -1;
                case 1:
                    this.value = this.value << 8 |
                                 0xff & byteBuffer.get();
                    this.state = state - remaining;
                    return -1;
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 4:
                    this.value = byteBuffer.getInt();
                    return remaining - state;
                case 3:
                    this.value = this.value << 24 |
                                 (0xff & byteBuffer.get()) << 16 |
                                 0xffff & byteBuffer.getShort();
                    this.state = 4;
                    return remaining - state;
                case 2:
                    this.value = this.value << 16 |
                                 0xffff & byteBuffer.getShort();
                    this.state = 4;
                    return remaining - state;
                case 1:
                    this.value = this.value << 8 |
                                 0xff & byteBuffer.get();
                    this.state = 4;
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
                case 3:
                    this.value = (0xff & bytes[start]) << 16 |
                                 (0xff & bytes[start + 1]) << 8 |
                                 0xff & bytes[start + 2];
                    this.state = state - remaining;
                    return -1;
                case 2:
                    this.value = this.value << 16 |
                                 (0xff & bytes[start]) << 8 |
                                 0xff & bytes[start + 1];
                    this.state = state - remaining;
                    return -1;
                case 1:
                    this.value = this.value << 8 |
                                 0xff & bytes[start];
                    this.state = state - remaining;
                    return -1;
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 4:
                    this.value = (0xff & bytes[start]) << 24 |
                                 (0xff & bytes[start + 1]) << 16 |
                                 (0xff & bytes[start + 2]) << 8 |
                                 0xff & bytes[start + 3];
                    return start + state;
                case 3:
                    this.value = this.value << 24 |
                                 (0xff & bytes[start]) << 16 |
                                 (0xff & bytes[start + 1]) << 8 |
                                 0xff & bytes[start + 2];
                    this.state = 4;
                    return start + state;
                case 2:
                    this.value = this.value << 16 |
                                 (0xff & bytes[start]) << 8 |
                                 0xff & bytes[start + 1];
                    this.state = 4;
                    return start + state;
                case 1:
                    this.value = this.value << 8 |
                                 0xff & bytes[start];
                    this.state = 4;
                    return start + state;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
