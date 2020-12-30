package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.time.Instant;

public class InstantDeserializer implements Deserializer<Instant> {

    @Nonnull
    private final IntDeserializer intDeserializer;

    @Nonnull
    private final LongDeserializer longDeserializer;

    private boolean state;

    public InstantDeserializer(@Nonnull IntDeserializer intDeserializer, @Nonnull LongDeserializer longDeserializer) {
        this.intDeserializer = intDeserializer;
        this.longDeserializer = longDeserializer;
    }

    @Override
    public void reset() {

        intDeserializer.reset();
        longDeserializer.reset();
        state = false;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (state) {
            remaining = intDeserializer.consume(byteBuffer, remaining);
            if (remaining != -1) {
                state = false;
            }
            return remaining;
        } else {
            remaining = longDeserializer.consume(byteBuffer, remaining);
            if (remaining != -1) {
                remaining = intDeserializer.consume(byteBuffer, remaining);
                if (remaining == -1) {
                    state = true;
                }
                return remaining;
            }
            return remaining;
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        if (state) {
            start = intDeserializer.consume(bytes, start, end);
            if (start != -1) {
                state = false;
            }
            return start;
        } else {
            start = longDeserializer.consume(bytes, start, end);
            if (start != -1) {
                start = intDeserializer.consume(bytes, start, end);
                if (start == -1) {
                    state = true;
                }
                return start;
            }
            return start;
        }
    }

    @Nonnull
    @Override
    public Instant value() {
        return Instant.ofEpochSecond(longDeserializer.longValue(), intDeserializer.intValue());
    }
}
