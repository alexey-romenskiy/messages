package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class AbstractIntSerializer<T> implements Serializer<T> {

    protected int value;

    private int state = 4;

    @Override
    public void reset() {
        state = 4;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        int state = this.state;

        if (state > remaining) {
            switch (remaining) {
                case 3: {
                    state -= remaining;
                    final int value = this.value >> (state << 3);
                    byteBuffer.put((byte) (value >> 16));
                    byteBuffer.putShort((short) value);
                    this.state = state;
                    return -1;
                }
                case 2: {
                    state -= remaining;
                    byteBuffer.putShort((short) (this.value >> (state << 3)));
                    this.state = state;
                    return -1;
                }
                case 1: {
                    state -= remaining;
                    byteBuffer.put((byte) (this.value >> (state << 3)));
                    this.state = state;
                    return -1;
                }
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 4:
                    byteBuffer.putInt(value);
                    return remaining - state;
                case 3: {
                    final int value = this.value;
                    byteBuffer.put((byte) (value >> 16));
                    byteBuffer.putShort((short) value);
                    this.state = 4;
                    return remaining - state;
                }
                case 2:
                    byteBuffer.putShort((short) value);
                    this.state = 4;
                    return remaining - state;
                case 1:
                    byteBuffer.put((byte) value);
                    this.state = 4;
                    return remaining - state;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        int state = this.state;
        final int remaining = end - start;

        if (state > remaining) {
            switch (remaining) {
                case 3: {
                    state -= remaining;
                    final int value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 16);
                    bytes[start + 1] = (byte) (value >> 8);
                    bytes[start + 2] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 2: {
                    state -= remaining;
                    final int value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 8);
                    bytes[start + 1] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 1: {
                    state -= remaining;
                    bytes[start] = (byte) (this.value >> (state << 3));
                    this.state = state;
                    return -1;
                }
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 4: {
                    final int value = this.value;
                    bytes[start] = (byte) (value >> 24);
                    bytes[start + 1] = (byte) (value >> 16);
                    bytes[start + 2] = (byte) (value >> 8);
                    bytes[start + 3] = (byte) value;
                    return start + state;
                }
                case 3: {
                    final int value = this.value;
                    bytes[start] = (byte) (value >> 16);
                    bytes[start + 1] = (byte) (value >> 8);
                    bytes[start + 2] = (byte) value;
                    this.state = 4;
                    return start + state;
                }
                case 2: {
                    final int value = this.value;
                    bytes[start] = (byte) (value >> 8);
                    bytes[start + 1] = (byte) value;
                    this.state = 4;
                    return start + state;
                }
                case 1:
                    bytes[start] = (byte) value;
                    this.state = 4;
                    return start + state;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
