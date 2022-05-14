package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class ByteType extends SimpleType {

    public static final ByteType INSTANCE = new ByteType();

    private ByteType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
