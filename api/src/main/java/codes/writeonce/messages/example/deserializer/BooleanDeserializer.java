package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public class BooleanDeserializer implements Deserializer<Boolean> {

    private boolean value;

    @Override
    public void reset() {
        // empty
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (remaining > 0) {
            switch (byteBuffer.get()) {
                case 0:
                    value = false;
                    return remaining - 1;
                case 1:
                    value = true;
                    return remaining - 1;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return -1;
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        if (start < end) {
            switch (bytes[start]) {
                case 0:
                    value = false;
                    return start + 1;
                case 1:
                    value = true;
                    return start + 1;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return -1;
        }
    }

    public boolean booleanValue() {
        return value;
    }

    @Nonnull
    @Override
    public Boolean value() {
        return booleanValue();
    }
}
