package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public class ByteSerializer implements Serializer<Byte> {

    private byte value;

    @Override
    public void reset() {
        // empty
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (remaining > 0) {
            byteBuffer.put(value);
            return remaining - 1;
        } else {
            return -1;
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        if (start < end) {
            bytes[start] = value;
            return start + 1;
        } else {
            return -1;
        }
    }

    public void value(byte value) {
        this.value = value;
    }

    @Override
    public void value(@Nonnull Byte value) {
        value((byte) value);
    }
}
