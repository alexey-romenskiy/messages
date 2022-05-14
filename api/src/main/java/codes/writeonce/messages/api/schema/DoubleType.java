package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class DoubleType extends SimpleType {

    public static final DoubleType INSTANCE = new DoubleType();

    private DoubleType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
