package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class BooleanType extends SimpleType {

    public static final BooleanType INSTANCE = new BooleanType();

    private BooleanType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
