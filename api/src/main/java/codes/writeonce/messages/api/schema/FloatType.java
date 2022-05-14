package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class FloatType extends SimpleType {

    public static final FloatType INSTANCE = new FloatType();

    private FloatType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
