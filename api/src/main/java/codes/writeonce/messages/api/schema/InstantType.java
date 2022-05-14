package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class InstantType extends SimpleType {

    public static final InstantType INSTANCE = new InstantType();

    private InstantType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
