package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class ShortSerializer extends AbstractShortSerializer<Short> {

    public void value(short value) {
        this.value = value;
    }

    @Override
    public void value(@Nonnull Short value) {
        value((short) value);
    }
}
