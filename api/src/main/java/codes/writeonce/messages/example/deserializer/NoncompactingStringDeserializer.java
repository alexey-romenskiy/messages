package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

import static codes.writeonce.messages.example.StringInterner.intern;

public class NoncompactingStringDeserializer extends StringDeserializer {

    public NoncompactingStringDeserializer(
            @Nonnull DeserializerContext context,
            @Nonnull IntDeserializer intDeserializer
    ) {
        super(context, intDeserializer);
    }

    @Override
    protected int startLatin1(@Nonnull ByteBuffer byteBuffer, int remaining) {

        position = 0;
        hash = 0;

        if (chars == null || chars.length < size) {
            chars = new char[size];
        }

        return appendLatin1(byteBuffer, remaining);
    }

    @Override
    protected int startLatin1(@Nonnull byte[] source, int start, int end) {

        position = 0;
        hash = 0;

        if (chars == null || chars.length < size) {
            chars = new char[size];
        }

        return appendLatin1(source, start, end);
    }

    @Override
    protected void copyLatin1(@Nonnull ByteBuffer byteBuffer, int length) {

        if (byteBuffer.hasArray()) {
            final int position = byteBuffer.position();
            byteBuffer.position(position + length);
            copyLatin1(byteBuffer.array(), byteBuffer.arrayOffset() + position, length);
        } else {
            if (bytes == null || bytes.length < length) {
                bytes = new byte[length];
            }
            byteBuffer.get(bytes, position, length);
            copyLatin1(bytes, 0, length);
        }
    }

    @Override
    protected void copyLatin1(@Nonnull byte[] source, int start, int length) {

        int i = position;
        final int end = i + length;
        position = end;
        int h = hash;

        while (i < end) {
            final int c = 0xff & source[start++];
            chars[i++] = (char) c;
            h = h * 31 + c;
        }

        hash = h;
    }

    @Override
    @Nonnull
    protected String buildString() {
        return intern(chars, 0, size, hash);
    }
}
