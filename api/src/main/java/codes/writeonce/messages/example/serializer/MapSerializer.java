package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

public class MapSerializer implements Serializer<Map<?, ?>> {

    @Nonnull
    private final IntSerializer intSerializer;

    private Serializer<Object> keySerializer;

    private Serializer<Object> valueSerializer;

    private int state;

    private Map<?, ?> value;

    private Iterator<? extends Map.Entry<?, ?>> iterator;

    public MapSerializer(@Nonnull IntSerializer intSerializer) {
        this.intSerializer = intSerializer;
    }

    @Override
    public void reset() {

        intSerializer.reset();
        keySerializer.reset();
        valueSerializer.reset();
        state = 0;
        value = null;
        iterator = null;
    }

    @SuppressWarnings("unchecked")
    public void init(@Nonnull Serializer<?> keyDeserializer, @Nonnull Serializer<?> valueDeserializer) {
        this.keySerializer = (Serializer<Object>) keyDeserializer;
        this.valueSerializer = (Serializer<Object>) valueDeserializer;
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
                    iterator = value.entrySet().iterator();
                    next();
                case 1:
                    remaining = keySerializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    state = 2;
                case 2:
                    remaining = valueSerializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    if (!iterator.hasNext()) {
                        state = 0;
                        value = null;
                        iterator = null;
                        return remaining;
                    }
                    state = 1;
                    next();
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
                    iterator = value.entrySet().iterator();
                    next();
                case 1:
                    start = keySerializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    state = 2;
                case 2:
                    start = valueSerializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    if (!iterator.hasNext()) {
                        state = 0;
                        value = null;
                        iterator = null;
                        return start;
                    }
                    state = 1;
                    next();
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private void next() {

        final Map.Entry<?, ?> entry = iterator.next();
        keySerializer.value(entry.getKey());
        valueSerializer.value(entry.getValue());
    }

    @Override
    public void value(@Nonnull Map<?, ?> value) {

        intSerializer.value(value.size());
        this.value = value;
    }
}
