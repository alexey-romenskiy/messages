package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public class BooleanSerializer implements Serializer<Boolean> {

    private boolean value;

    @Override
    public void reset() {
        // empty
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (remaining > 0) {
            byteBuffer.put(value ? (byte) 1 : (byte) 0);
            return remaining - 1;
        } else {
            return -1;
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        if (start < end) {
            bytes[start] = value ? (byte) 1 : (byte) 0;
            return start + 1;
        } else {
            return -1;
        }
    }

    public void value(boolean value) {
        this.value = value;
    }

    @Override
    public void value(@Nonnull Boolean value) {
        value((boolean) value);
    }
}
