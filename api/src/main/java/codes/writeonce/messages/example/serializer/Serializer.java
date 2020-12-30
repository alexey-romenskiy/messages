package codes.writeonce.messages.example.serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public interface Serializer<T> {

    void reset();

    /**
     * @return <code>-1</code> if all bytes consumed but serialization not completed,
     * otherwise serialization is completed with non-negative number of bytes remaining
     */
    int consume(@Nonnull ByteBuffer byteBuffer, int remaining);

    /**
     * @return <code>-1</code> if all bytes consumed but serialization not completed,
     * otherwise serialization is completed and last array position returned
     */
    int consume(@Nonnull byte[] bytes, int start, int end);

    void value(@Nonnull T value);
}
