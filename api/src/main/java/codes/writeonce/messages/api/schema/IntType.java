package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class IntType extends SimpleType {

    public static final IntType INSTANCE = new IntType();

    private IntType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
