import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        File original = new File(System.getProperty("user.dir") + "/src/main/resources/testFiles/t1_2.txt");
        File revised = new File(System.getProperty("user.dir") + "/src/main/resources/testFiles/t2_2.txt");
        FileDiff.generateHTMLDiff(original, revised);
    }
}
