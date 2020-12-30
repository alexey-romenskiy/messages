package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public interface DeserializerContext {

    void push(@Nonnull Deserializer<?> deserializer);
}
