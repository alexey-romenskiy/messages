package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class AbstractLongSerializer<T> implements Serializer<T> {

    protected long value;

    protected int state = 8;

    @Override
    public void reset() {
        state = 8;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        int state = this.state;

        if (state > remaining) {
            switch (remaining) {
                case 7: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    byteBuffer.put((byte) (value >> 48));
                    byteBuffer.putShort((short) (value >> 32));
                    byteBuffer.putInt((int) value);
                    this.state = state;
                    return -1;
                }
                case 6: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    byteBuffer.putShort((short) (value >> 32));
                    byteBuffer.putInt((int) value);
                    this.state = state;
                    return -1;
                }
                case 5: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    byteBuffer.put((byte) (value >> 32));
                    byteBuffer.putInt((int) value);
                    this.state = state;
                    return -1;
                }
                case 4:
                    state -= remaining;
                    byteBuffer.putInt((int) (this.value >> (state << 3)));
                    this.state = state;
                    return -1;
                case 3: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    byteBuffer.put((byte) (value >> 16));
                    byteBuffer.putShort((short) value);
                    this.state = state;
                    return -1;
                }
                case 2:
                    state -= remaining;
                    byteBuffer.putShort((short) (this.value >> (state << 3)));
                    this.state = state;
                    return -1;
                case 1:
                    state -= remaining;
                    byteBuffer.put((byte) (this.value >> (state << 3)));
                    this.state = state;
                    return -1;
                case 0:
                    return -1;
                default:
                    throw new IllegalStateException();
            }
        } else {
            switch (state) {
                case 8:
                    byteBuffer.putLong(value);
                    return remaining - state;
                case 7: {
                    final long value = this.value;
                    byteBuffer.put((byte) (value >> 48));
                    byteBuffer.putShort((short) (value >> 32));
                    byteBuffer.putInt((int) value);
                    this.state = 8;
                    return remaining - state;
                }
                case 6: {
                    final long value = this.value;
                    byteBuffer.putShort((short) (value >> 32));
                    byteBuffer.putInt((int) value);
                    this.state = 8;
                    return remaining - state;
                }
                case 5: {
                    final long value = this.value;
                    byteBuffer.put((byte) (value >> 32));
                    byteBuffer.putInt((int) value);
                    this.state = 8;
                    return remaining - state;
                }
                case 4:
                    byteBuffer.putInt((int) value);
                    this.state = 8;
                    return remaining - state;
                case 3: {
                    final long value = this.value;
                    byteBuffer.put((byte) (value >> 16));
                    byteBuffer.putShort((short) value);
                    this.state = 8;
                    return remaining - state;
                }
                case 2:
                    byteBuffer.putShort((short) value);
                    this.state = 8;
                    return remaining - state;
                case 1:
                    byteBuffer.put((byte) value);
                    this.state = 8;
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
                case 7: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 48);
                    bytes[start + 1] = (byte) (value >> 40);
                    bytes[start + 2] = (byte) (value >> 32);
                    bytes[start + 3] = (byte) (value >> 24);
                    bytes[start + 4] = (byte) (value >> 16);
                    bytes[start + 5] = (byte) (value >> 8);
                    bytes[start + 6] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 6: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 40);
                    bytes[start + 1] = (byte) (value >> 32);
                    bytes[start + 2] = (byte) (value >> 24);
                    bytes[start + 3] = (byte) (value >> 16);
                    bytes[start + 4] = (byte) (value >> 8);
                    bytes[start + 5] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 5: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 32);
                    bytes[start + 1] = (byte) (value >> 24);
                    bytes[start + 2] = (byte) (value >> 16);
                    bytes[start + 3] = (byte) (value >> 8);
                    bytes[start + 4] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 4: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 24);
                    bytes[start + 1] = (byte) (value >> 16);
                    bytes[start + 2] = (byte) (value >> 8);
                    bytes[start + 3] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 3: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
                    bytes[start] = (byte) (value >> 16);
                    bytes[start + 1] = (byte) (value >> 8);
                    bytes[start + 2] = (byte) value;
                    this.state = state;
                    return -1;
                }
                case 2: {
                    state -= remaining;
                    final long value = this.value >> (state << 3);
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
                case 8: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 56);
                    bytes[start + 1] = (byte) (value >> 48);
                    bytes[start + 2] = (byte) (value >> 40);
                    bytes[start + 3] = (byte) (value >> 32);
                    bytes[start + 4] = (byte) (value >> 24);
                    bytes[start + 5] = (byte) (value >> 16);
                    bytes[start + 6] = (byte) (value >> 8);
                    bytes[start + 7] = (byte) value;
                    return start + state;
                }
                case 7: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 48);
                    bytes[start + 1] = (byte) (value >> 40);
                    bytes[start + 2] = (byte) (value >> 32);
                    bytes[start + 3] = (byte) (value >> 24);
                    bytes[start + 4] = (byte) (value >> 16);
                    bytes[start + 5] = (byte) (value >> 8);
                    bytes[start + 6] = (byte) value;
                    this.state = 8;
                    return start + state;
                }
                case 6: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 40);
                    bytes[start + 1] = (byte) (value >> 32);
                    bytes[start + 2] = (byte) (value >> 24);
                    bytes[start + 3] = (byte) (value >> 16);
                    bytes[start + 4] = (byte) (value >> 8);
                    bytes[start + 5] = (byte) value;
                    this.state = 8;
                    return start + state;
                }
                case 5: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 32);
                    bytes[start + 1] = (byte) (value >> 24);
                    bytes[start + 2] = (byte) (value >> 16);
                    bytes[start + 3] = (byte) (value >> 8);
                    bytes[start + 4] = (byte) value;
                    this.state = 8;
                    return start + state;
                }
                case 4: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 24);
                    bytes[start + 1] = (byte) (value >> 16);
                    bytes[start + 2] = (byte) (value >> 8);
                    bytes[start + 3] = (byte) value;
                    this.state = 8;
                    return start + state;
                }
                case 3: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 16);
                    bytes[start + 1] = (byte) (value >> 8);
                    bytes[start + 2] = (byte) value;
                    this.state = 8;
                    return start + state;
                }
                case 2: {
                    final long value = this.value;
                    bytes[start] = (byte) (value >> 8);
                    bytes[start + 1] = (byte) value;
                    this.state = 8;
                    return start + state;
                }
                case 1:
                    bytes[start] = (byte) value;
                    this.state = 8;
                    return start + state;
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
