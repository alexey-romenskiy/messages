package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class CharSerializer extends AbstractShortSerializer<Character> {

    public void value(char value) {
        this.value = (short) value;
    }

    @Override
    public void value(@Nonnull Character value) {
        value((char) value);
    }
}
