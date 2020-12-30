package codes.writeonce.messages.example.deserializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public interface Deserializer<T> {

    void reset();

    /**
     * @return <code>-1</code> if all bytes consumed but parsing not completed,
     * otherwise parsing is completed with non-negative number of bytes remaining
     */
    int consume(@Nonnull ByteBuffer byteBuffer, int remaining);

    /**
     * @return <code>-1</code> if all bytes consumed but parsing not completed,
     * otherwise parsing is completed and last array position returned
     */
    int consume(@Nonnull byte[] bytes, int start, int end);

    @Nonnull
    T value();
}
