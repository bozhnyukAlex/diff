import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.DiffUtils;



public class FileComparator {

    private final File original;
    private final File revised;

    public FileComparator(File original, File revised) {
        this.original = original;
        this.revised = revised;
    }

    public List<AbstractDelta<String>> getDeltas(boolean includeEqualParts) throws IOException {
        List<String> originalFileLines = getLinesFromFile(original);
        List<String> revisedFileLines = getLinesFromFile(revised);
        Patch<String> patch = DiffUtils.diff(originalFileLines, revisedFileLines, includeEqualParts);
        return patch.getDeltas();
    }

    private List<String> getLinesFromFile(File file) throws IOException {
        return  Files.readAllLines(file.toPath());
    }




}
