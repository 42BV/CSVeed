package nl.tweeenveertig.csveed.api;

import java.io.Reader;
import java.util.List;

public interface BeanReader<T> {

    public List<T> read(Reader reader);

    public T readLine(Reader reader);

    public int getCurrentLine();

    public boolean isFinished();

}
