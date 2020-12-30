package codes.writeonce.messages.example;

import codes.writeonce.messages.example.deserializer.Deserializer;
import codes.writeonce.messages.example.deserializer.DeserializerContext;
import codes.writeonce.messages.example.deserializer.Deserializers;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public class ExampleMessageDeserializer extends Deserializers implements Deserializer<ExampleMessageImpl> {

    @Nonnull
    private final DeserializerContext context;

    private int state;

    private ExampleMessageImpl value;

    public ExampleMessageDeserializer(
            @Nonnull DeserializerContext context,
            @Nonnull Deserializers deserializers
    ) {
        super(deserializers);
        this.context = context;
    }

    @Override
    public void reset() {
        // TODO:
    }

    @Override
    public int consume(@Nonnull ByteBuffer byteBuffer, int remaining) {

        switch (state) {
            case 0:
                value = new ExampleMessageImpl();
                remaining = booleanDeserializer.consume(byteBuffer, remaining);
                if (remaining == -1) {
                    context.push(booleanDeserializer);
                    state = 1;
                    return remaining;
                }
            case 1:
                value.booleanValue = booleanDeserializer.booleanValue();
                remaining = byteDeserializer.consume(byteBuffer, remaining);
                if (remaining == -1) {
                    context.push(byteDeserializer);
                    state = 2;
                    return remaining;
                }
            case 2:
                value.byteValue = byteDeserializer.byteValue();
                state = 0;
                return remaining;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public int consume(@Nonnull byte[] bytes, int start, int end) {
        return 0; // TODO:
    }

    @Nonnull
    @Override
    public ExampleMessageImpl value() {
        return value;
    }
}
