
package uml;

import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


import java.io.IOException;
import java.util.ArrayList;

public class CLIView {

    private TerminalBuilder builder;
    public Completer completer;

    public DynamicCompleter dc;

    private Terminal terminal;

    public CLIView() {

        this.dc = new DynamicCompleter();
        this.dc.setCompleter(new StringsCompleter(CLIView.commands));

        this.builder = TerminalBuilder.builder();
        this.completer = new ArgumentCompleter(this.dc);
        this.terminal = null;
    }

    public void editCompleter(ArrayList<UMLClass> cl) {
        var clObjsArray = new ArrayList<String>();

        for (UMLClass u: cl) {
            clObjsArray.add(u.getClassName());
            for (Field f: u.getFieldList())
                clObjsArray.add(f.getAttName());
            for (Method m : u.getMethodList()) {
                clObjsArray.add(m.getAttName());
                for (Parameter p : m.getParamList())
                    clObjsArray.add(p.getAttName());
            }
        }

        String[] clObjsList = clObjsArray.toArray(new String[clObjsArray.size()]);
        this.dc.setCompleter(new AggregateCompleter(
                new StringsCompleter(CLIView.commands),
                new StringsCompleter(clObjsList)
        ));
    }

    public static String[] commands = {"help", "exit", "add", "delete", "rename", "change", "list", "save", "load", "clear", "class",
            "classes", "att", "rel"};

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
