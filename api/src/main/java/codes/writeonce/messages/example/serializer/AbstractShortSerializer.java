package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class AbstractShortSerializer<T> implements Serializer<T> {

    protected short value;

    private boolean state;

    @Override
    public void reset() {
        state = false;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (state) {
            if (remaining > 0) {
                byteBuffer.put((byte) value);
                state = false;
                return remaining - 1;
            } else {
                return -1;
            }
        } else {
            if (remaining > 1) {
                byteBuffer.putShort(value);
                return remaining - 2;
            } else if (remaining == 1) {
                byteBuffer.put((byte) (value >> 8));
                state = true;
                return -1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        if (state) {
            if (start < end) {
                bytes[start] = (byte) value;
                state = false;
                return start + 1;
            } else {
                return -1;
            }
        } else {
            final int next = start + 1;
            if (next < end) {
                bytes[start] = (byte) (value >> 8);
                bytes[next] = (byte) value;
                return start + 2;
            } else if (next == end) {
                bytes[start] = (byte) (value >> 8);
                state = true;
                return -1;
            } else {
                return -1;
            }
        }
    }
}
