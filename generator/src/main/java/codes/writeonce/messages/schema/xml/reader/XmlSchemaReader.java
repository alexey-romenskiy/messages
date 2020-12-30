package codes.writeonce.messages.schema.xml.reader;

import org.xml.sax.InputSource;

public class XmlSchemaReader {

    public SchemaInfo read(InputSource inputSource, ClassLoader classLoader) throws ParsingException {
        return new SchemaInfo("");
    }
}
