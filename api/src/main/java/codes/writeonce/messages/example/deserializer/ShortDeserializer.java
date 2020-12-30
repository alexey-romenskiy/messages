package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class ShortDeserializer extends AbstractShortDeserializer<Short> {

    public short shortValue() {
        return value;
    }

    @Nonnull
    @Override
    public Short value() {
        return shortValue();
    }
}
