package uml;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


import java.io.IOException;


public class CLIView {

    private TerminalBuilder builder;
    private Completer completer;
    private Terminal terminal;
    
    public CLIView() {
        this.builder = TerminalBuilder.builder();
        this.completer = new ArgumentCompleter(new StringsCompleter("help", "exit", "add", "delete", "rename", "change", "list", "save", "load", "clear"));
        this.terminal = null;
    }
    
    //a method to build the terminal using Jline linereader
    public LineReader buildConsole() {
        //build terminal as not dumb
        try {
            terminal = builder.system(true).dumb(false).build();
        } catch (IOException e) {
           e.printStackTrace();
        }

        //create a virtual terminal
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .build();

        //options for reader object
        reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
        reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
        reader.option(LineReader.Option.CASE_INSENSITIVE, true);
        return reader;
    }
}
