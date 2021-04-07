import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiffGeneratorHTML implements DiffGenerator {

    private static final String DELETION = "<span style=\"background-color: #C4C4C4\">${text}</span>";
    private static final String INSERTION = "<span style=\"background-color: #45EA85\">${text}</span>";
    private static final String CHANGING = "<span style=\"background-color: #3D9CFF\">${text}</span>";
    private static final String HTML_NEW_LINE = "<br/>";
    private static final String TEMPLATE_STR_PATH = "/src/main/resources/templates/diff_template.html";

    private String left = "";
    private String right = "";

    public void generate(Path path) throws IOException {
        Path templatePath = Paths.get(System.getProperty("user.dir") + TEMPLATE_STR_PATH);
        String template = Files.readString(templatePath, StandardCharsets.UTF_8);
        String output = template.replace("${left}", left)
                                .replace("${right}", right);
        File diff_file = path.toFile();
        Files.write(path, output.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void generateInsert(String line) {
        right += INSERTION.replace("${text}", line + HTML_NEW_LINE);
    }

    @Override
    public void generateDelete(String line) {
        left += DELETION.replace("${text}", line + HTML_NEW_LINE);
    }

    @Override
    public void generateKeep(String line) {
        left += (line + HTML_NEW_LINE);
        right += (line + HTML_NEW_LINE);
    }

    @Override
    public void generateChange(String line, boolean isSource) {
        if (isSource) {
            left += CHANGING.replace("${text}", line + HTML_NEW_LINE);
        }
        else {
            right += CHANGING.replace("${text}", line + HTML_NEW_LINE);
        }
    }
}
