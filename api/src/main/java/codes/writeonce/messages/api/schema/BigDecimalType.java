package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;

public class BigDecimalType extends SimpleType {

    public static final BigDecimalType INSTANCE = new BigDecimalType();

    private BigDecimalType() {
        // empty
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
