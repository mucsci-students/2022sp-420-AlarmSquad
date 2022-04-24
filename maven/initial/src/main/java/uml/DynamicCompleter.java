
package uml;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.reader.Candidate;
import java.util.List;


public class DynamicCompleter implements Completer {

    Completer delegate;

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        delegate.complete(reader, line, candidates);
    }

    public void setCompleter(Completer delegate) {
        this.delegate = delegate;
    }
}