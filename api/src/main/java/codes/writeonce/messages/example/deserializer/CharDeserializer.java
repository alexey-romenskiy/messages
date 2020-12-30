package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;

public class CharDeserializer extends AbstractShortDeserializer<Character> {

    public char charValue() {
        return (char) value;
    }

    @Nonnull
    @Override
    public Character value() {
        return charValue();
    }
}
