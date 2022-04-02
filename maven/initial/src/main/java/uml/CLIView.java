package uml;


import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


import java.io.IOException;


public class CLIView {

    public static LineReader buildConsole() {
        TerminalBuilder builder = TerminalBuilder.builder();
        Completer completer = new EnumCompleter(Command.class);
        //Completer completer = new StringsCompleter("help");
        Terminal terminal = null;

        try {
            terminal = builder.build();
        } catch (IOException e) {
           e.printStackTrace();
        }

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .build();

        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);

        return reader;
    }
}
