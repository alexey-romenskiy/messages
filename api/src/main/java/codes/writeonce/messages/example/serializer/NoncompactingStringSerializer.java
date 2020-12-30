package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;

public class NoncompactingStringSerializer extends StringSerializer {

    public NoncompactingStringSerializer(@Nonnull IntSerializer intSerializer) {
        super(intSerializer);
    }

    @Override
    protected void initState() {

        intSerializer.value(length);
        state = 1;
    }
}
