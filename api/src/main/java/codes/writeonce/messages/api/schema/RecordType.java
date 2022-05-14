package codes.writeonce.messages.api.schema;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class RecordType implements Type {

    private final int ordinal;

    @Nonnull
    private final List<RecordType> parentTypes;

    @Nonnull
    private final List<Field> fields;

    private final boolean instantiable;

    @Nullable
    private final String documentation;

    public RecordType(int ordinal, @Nonnull List<RecordType> parentTypes, @Nonnull List<Field> fields,
            boolean instantiable, @Nullable String documentation) {
        this.ordinal = ordinal;
        this.parentTypes = parentTypes;
        this.fields = fields;
        this.instantiable = instantiable;
        this.documentation = documentation;
    }

    public int getOrdinal() {
        return ordinal;
    }

    @Nonnull
    public List<RecordType> getParentTypes() {
        return parentTypes;
    }

    @Nonnull
    public List<Field> getFields() {
        return fields;
    }

    public boolean isInstantiable() {
        return instantiable;
    }

    @Nullable
    public String getDocumentation() {
        return documentation;
    }

    @Override
    public <V, E extends Throwable> V accept(@Nonnull Visitor<V, E> visitor) throws E {
        return visitor.visit(this);
    }
}
