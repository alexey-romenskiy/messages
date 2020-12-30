package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class IntSerializer extends AbstractIntSerializer<Integer> {

    public void value(int value) {
        this.value = value;
    }

    @Override
    public void value(@Nonnull Integer value) {
        value((int) value);
    }
}
