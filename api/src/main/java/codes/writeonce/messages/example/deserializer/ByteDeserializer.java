package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public class ByteDeserializer implements Deserializer<Byte> {

    private byte value;

    @Override
    public void reset() {
        // empty
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (remaining > 0) {
            value = byteBuffer.get();
            return remaining - 1;
        } else {
            return -1;
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        if (start < end) {
            value = bytes[start];
            return start + 1;
        } else {
            return -1;
        }
    }

    public byte byteValue() {
        return value;
    }

    @Nonnull
    @Override
    public Byte value() {
        return byteValue();
    }
}
