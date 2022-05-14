package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Field {

    @Nonnull
    private final Type type;

    @Nonnull
    private final String name;

    private final boolean nullable;

    @Nullable
    private final String documentation;

    public Field(@Nonnull Type type, @Nonnull String name, boolean nullable, @Nullable String documentation) {
        this.type = type;
        this.name = name;
        this.nullable = nullable;
        this.documentation = documentation;
    }

    @Nonnull
    public Type getType() {
        return type;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public boolean isNullable() {
        return nullable;
    }

    @Nullable
    public String getDocumentation() {
        return documentation;
    }
}
