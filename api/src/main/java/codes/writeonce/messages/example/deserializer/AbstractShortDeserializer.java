package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public abstract class AbstractShortDeserializer<T> implements Deserializer<T> {

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
                value |= 0xff & byteBuffer.get();
                state = false;
                return remaining - 1;
            } else {
                return -1;
            }
        } else {
            if (remaining > 1) {
                value = byteBuffer.getShort();
                return remaining - 2;
            } else if (remaining == 1) {
                value = (short) (byteBuffer.get() << 8);
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
                value |= 0xff & bytes[start];
                state = false;
                return start + 1;
            } else {
                return -1;
            }
        } else {
            final int next = start + 1;
            if (next < end) {
                value = (short) (bytes[start] << 8 | 0xff & bytes[next]);
                return start + 2;
            } else if (next == end) {
                value = (short) (bytes[start] << 8);
                state = true;
                return -1;
            } else {
                return -1;
            }
        }
    }
}
