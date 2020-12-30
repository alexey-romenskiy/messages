package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public class SetDeserializer implements Deserializer<Set<?>> {

    @Nonnull
    private final IntDeserializer intDeserializer;

    private Deserializer<?> deserializer;

    private int state;

    private int size;

    private Set<Object> value;

    public SetDeserializer(@Nonnull IntDeserializer intDeserializer) {
        this.intDeserializer = intDeserializer;
    }

    @Override
    public void reset() {

        intDeserializer.reset();
        deserializer.reset();
        state = 0;
        value = null;
    }

    public void init(@Nonnull Deserializer<?> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        while (true) {
            switch (state) {
                case 0:
                    remaining = intDeserializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    size = intDeserializer.intValue();
                    if (size < 0) {
                        throw new IllegalArgumentException();
                    }
                    if (size == 0) {
                        value = null;
                        return remaining;
                    }
                    value = new HashSet<>(size);
                    state = 1;
                case 1:
                    remaining = deserializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    value.add(deserializer.value());
                    if (value.size() == size) {
                        state = 0;
                        return remaining;
                    }
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
                    start = intDeserializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    size = intDeserializer.intValue();
                    if (size < 0) {
                        throw new IllegalArgumentException();
                    }
                    if (size == 0) {
                        value = null;
                        return start;
                    }
                    value = new HashSet<>(size);
                    state = 1;
                case 1:
                    start = deserializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    value.add(deserializer.value());
                    if (value.size() == size) {
                        state = 0;
                        return start;
                    }
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Nonnull
    @Override
    public Set<?> value() {

        if (size == 0) {
            return emptySet();
        } else {
            return unmodifiableSet(value);
        }
    }
}
