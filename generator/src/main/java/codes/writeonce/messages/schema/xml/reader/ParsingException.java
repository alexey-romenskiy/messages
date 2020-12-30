package codes.writeonce.messages.schema.xml.reader;

public class ParsingException extends Exception {

    private static final long serialVersionUID = 4216203109514014519L;

    public ParsingException() {
        // empty
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }
}
