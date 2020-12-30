package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Set;

public class SetSerializer implements Serializer<Set<?>> {

    @Nonnull
    private final IntSerializer intSerializer;

    private Serializer<Object> serializer;

    private int state;

    private Set<?> value;

    private Iterator<?> iterator;

    public SetSerializer(@Nonnull IntSerializer intSerializer) {
        this.intSerializer = intSerializer;
    }

    @Override
    public void reset() {

        intSerializer.reset();
        serializer.reset();
        state = 0;
        value = null;
        iterator = null;
    }

    @SuppressWarnings("unchecked")
    public void init(@Nonnull Serializer<?> serializer) {
        this.serializer = (Serializer<Object>) serializer;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        while (true) {
            switch (state) {
                case 0:
                    remaining = intSerializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    if (value.isEmpty()) {
                        value = null;
                        return remaining;
                    }
                    state = 1;
                    iterator = value.iterator();
                    serializer.value(iterator.next());
                case 1:
                    remaining = serializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    if (!iterator.hasNext()) {
                        state = 0;
                        value = null;
                        iterator = null;
                        return remaining;
                    }
                    serializer.value(iterator.next());
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        while (true) {
            switch (state) {
                case 0:
                    start = intSerializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    if (value.isEmpty()) {
                        value = null;
                        return start;
                    }
                    state = 1;
                    iterator = value.iterator();
                    serializer.value(iterator.next());
                case 1:
                    start = serializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    if (!iterator.hasNext()) {
                        state = 0;
                        value = null;
                        iterator = null;
                        return start;
                    }
                    serializer.value(iterator.next());
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Override
    public void value(@Nonnull Set<?> value) {

        intSerializer.value(value.size());
        this.value = value;
    }
}
