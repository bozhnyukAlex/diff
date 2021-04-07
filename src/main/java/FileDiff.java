import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.DeltaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class FileDiff {

    public static void generateHTMLDiff(File original, File revised) throws IOException {
        FileComparator fileComparator = new FileComparator(original, revised);
        List<AbstractDelta<String>> deltas = fileComparator.getDeltas(true);
        DiffGeneratorHTML generator = new DiffGeneratorHTML();
        for (AbstractDelta<String> delta : deltas) {
            DeltaType deltaType = delta.getType();
            List<String> sourceLines = delta.getSource().getLines();
            List<String> targetLines = delta.getTarget().getLines();
            switch (deltaType) {
                case EQUAL:
                    sourceLines.forEach(generator::generateKeep);
                    break;
                case CHANGE:
                    sourceLines.forEach(line -> generator.generateChange(line, true));
                    targetLines.forEach(line -> generator.generateChange(line, false));
                    break;
                case DELETE:
                    sourceLines.forEach(generator::generateDelete);
                    break;
                case INSERT:
                    targetLines.forEach(generator::generateInsert);
                    break;
            }
        }
        generator.generate(Paths.get("diff.html"));

    }
}
