public interface DiffGenerator {

    void generateInsert(String line);

    void generateDelete(String line);

    void generateKeep(String line);

    void generateChange(String line, boolean isSource);

}
