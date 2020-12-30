package codes.writeonce.messages.example;

import codes.writeonce.messages.example.deserializer.Deserializer;
import codes.writeonce.messages.example.deserializer.DeserializerContext;
import codes.writeonce.messages.example.deserializer.Deserializers;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class DeserializerFacade implements Deserializer<ExampleMessage> {

    private final ArrayList<Deserializer<?>> stack = new ArrayList<>();

    private final ExampleMessageDeserializer rootDeserializer;

    private Deserializer<?> deserializer;

    public DeserializerFacade() {
        final DeserializerContext context = this::push;
        final Deserializers deserializers = Deserializers.createDeserializers(context, true);
        rootDeserializer = new ExampleMessageDeserializer(context, deserializers);
        deserializer = rootDeserializer;
    }

    @Override
    public void reset() {
        // TODO:
    }

    /**
     * @return <code>-1</code> if all bytes consumed but parsing not completed,
     * otherwise parsing is completed with non-negative number of bytes remaining
     */
    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        while (true) {
            remaining = deserializer.consume(byteBuffer, remaining);
            if (remaining == -1 || stack.isEmpty()) {
                return remaining;
            }
            pop();
        }
    }

    /**
     * @return <code>-1</code> if all bytes consumed but parsing not completed,
     * otherwise parsing is completed and last array position returned
     */
    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {

        while (true) {
            start = deserializer.consume(bytes, start, end);
            if (start == -1 || stack.isEmpty()) {
                return start;
            }
            pop();
        }
    }

    @Nonnull
    @Override
    public ExampleMessage value() {
        return rootDeserializer.value();
    }

    private void push(@Nonnull Deserializer<?> deserializer) {

        requireNonNull(deserializer);
        stack.add(this.deserializer);
        this.deserializer = deserializer;
    }

    private void pop() {
        this.deserializer = stack.remove(stack.size() - 1);
    }
}
