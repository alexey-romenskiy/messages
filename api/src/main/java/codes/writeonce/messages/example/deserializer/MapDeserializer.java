package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

public class MapDeserializer implements Deserializer<Map<?, ?>> {

    @Nonnull
    private final IntDeserializer intDeserializer;

    private Deserializer<?> keyDeserializer;

    private Deserializer<?> valueDeserializer;

    private int state;

    private int size;

    private Map<Object, Object> value;

    private Object key;

    public MapDeserializer(@Nonnull IntDeserializer intDeserializer) {
        this.intDeserializer = intDeserializer;
    }

    @Override
    public void reset() {

        intDeserializer.reset();
        keyDeserializer.reset();
        valueDeserializer.reset();
        state = 0;
        value = null;
        key = null;
    }

    public void init(@Nonnull Deserializer<?> keyDeserializer, @Nonnull Deserializer<?> valueDeserializer) {
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
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
                    value = new HashMap<>(size);
                    state = 1;
                case 1:
                    remaining = keyDeserializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    key = keyDeserializer.value();
                    state = 2;
                case 2:
                    remaining = valueDeserializer.consume(byteBuffer, remaining);
                    if (remaining == -1) {
                        return remaining;
                    }
                    value.put(key, valueDeserializer.value());
                    if (value.size() == size) {
                        state = 0;
                        return remaining;
                    }
                    state = 1;
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
                    value = new HashMap<>(size);
                    state = 1;
                case 1:
                    start = keyDeserializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    key = keyDeserializer.value();
                    state = 2;
                case 2:
                    start = valueDeserializer.consume(bytes, start, end);
                    if (start == -1) {
                        return start;
                    }
                    value.put(key, valueDeserializer.value());
                    if (value.size() == size) {
                        state = 0;
                        return start;
                    }
                    state = 1;
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    @Nonnull
    @Override
    public Map<?, ?> value() {

        if (size == 0) {
            return emptyMap();
        } else {
            return unmodifiableMap(value);
        }
    }
}
