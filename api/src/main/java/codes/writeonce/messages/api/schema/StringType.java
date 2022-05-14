package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class StringType extends SimpleType {

    public static final StringType INSTANCE = new StringType();

    private StringType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
