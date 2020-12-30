package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class LongSerializer extends AbstractLongSerializer<Long> {

    public void value(long value) {
        this.value = value;
    }

    public void value(long value, int state) {

        this.value = value;
        this.state = state;
    }

    @Override
    public void value(@Nonnull Long value) {
        value((long) value);
    }
}
