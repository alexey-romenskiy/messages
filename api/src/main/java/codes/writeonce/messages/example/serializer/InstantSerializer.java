package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.time.Instant;

public class InstantSerializer implements Serializer<Instant> {

    @Nonnull
    private final IntSerializer intSerializer;

    @Nonnull
    private final LongSerializer longSerializer;

    private boolean state;

    public InstantSerializer(@Nonnull IntSerializer intSerializer, @Nonnull LongSerializer longSerializer) {
        this.intSerializer = intSerializer;
        this.longSerializer = longSerializer;
    }

    @Override
    public void reset() {

        intSerializer.reset();
        longSerializer.reset();
        state = false;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        if (state) {
            remaining = intSerializer.consume(byteBuffer, remaining);
            if (remaining != -1) {
                state = false;
            }
            return remaining;
        } else {
            remaining = longSerializer.consume(byteBuffer, remaining);
            if (remaining != -1) {
                remaining = intSerializer.consume(byteBuffer, remaining);
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
            start = intSerializer.consume(bytes, start, end);
            if (start != -1) {
                state = false;
            }
            return start;
        } else {
            start = longSerializer.consume(bytes, start, end);
            if (start != -1) {
                start = intSerializer.consume(bytes, start, end);
                if (start == -1) {
                    state = true;
                }
                return start;
            }
            return start;
        }
    }

    @Override
    public void value(@Nonnull Instant value) {

        longSerializer.value(value.getEpochSecond());
        intSerializer.value(value.getNano());
    }
}
