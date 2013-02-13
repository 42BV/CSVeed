package nl.tweeenveertig.csveed.parser;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class LineReaderTest {

    @Test
    public void roughRide() throws IOException {
        Reader reader = new StringReader("\"alpha\";\"\";;\"beta\";gamma;\"een \"\"echte\"\" test\";\"1\n2\n3\n\"\"regels\"\"\"");
        LineReader lineReader = new LineReader();
        List<String> cells = lineReader.readLine(reader);
        assertEquals(7, cells.size());
        assertEquals("alpha", cells.get(0));
        assertEquals("", cells.get(1));
        assertEquals("", cells.get(2));
        assertEquals("beta", cells.get(3));
        assertEquals("gamma", cells.get(4));
        assertEquals("een \"echte\" test", cells.get(5));
        assertEquals("1\n2\n3\n\"regels\"", cells.get(6));
    }

    @Test
    public void doubleQuotesAsEscape() throws IOException {
        Reader reader = new StringReader("\"\"\"very literal\"\"\";\"a\"\"b\"\"c\"\n\"abc\";\"first this, \"\"then that\"\"\"");
        LineReader lineReader = new LineReader();
        checkEscapedStrings(lineReader.readAllLines(reader));
    }

    @Test
    public void backSlashesAsEscape() throws IOException {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '\\');
        ParseStateMachine machine = new ParseStateMachine();
        machine.setSymbolMapping(mapping);
        LineReader lineReader = new LineReader();
        lineReader.setStateMachine(machine);

        Reader reader = new StringReader("\"\\\"very literal\\\"\";\"a\\\"b\\\"c\"\n\"abc\";\"first this, \\\"then that\\\"\"");
        checkEscapedStrings(lineReader.readAllLines(reader));
    }

    private void checkEscapedStrings(List<List<String>> lines) {
        List<String> cells = lines.get(0);
        assertEquals("\"very literal\"", cells.get(0));
        assertEquals("a\"b\"c", cells.get(1));
        cells = lines.get(1);
        assertEquals("abc", cells.get(0));
        assertEquals("first this, \"then that\"", cells.get(1));
    }

    @Test
    public void readAllLines() throws IOException {
        Reader reader = new StringReader(";;;\n;;;\n;;;\n");
        LineReader lineReader = new LineReader();
        List<List<String>> allLines = lineReader.readAllLines(reader);
        assertEquals(3, allLines.size());
    }

    @Test
    public void allNumbers() throws IOException {
        Reader reader = new StringReader("17.51;23.19;-100.23;");
        LineReader lineReader = new LineReader();
        List<String> cells = lineReader.readLine(reader);
        assertEquals(4, cells.size());
        assertEquals("17.51", cells.get(0));
        assertEquals("23.19", cells.get(1));
        assertEquals("-100.23", cells.get(2));
        assertEquals("", cells.get(3));
    }

}
