import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.DeltaType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FileComparatorTest {

    private File original;
    private File revised;
    private FileComparator fileComparator;

    @Before
    public void setUp() throws Exception {
        original = new File(System.getProperty("user.dir") + "/src/main/resources/testFiles/t1_2.txt");
        revised = new File(System.getProperty("user.dir") + "/src/main/resources/testFiles/t2_2.txt");
        fileComparator = new FileComparator(original, revised);
    }

    @Test
    public void deletesTest() {
        try {
            List<AbstractDelta<String>> deleteDeltas = fileComparator.getDeltas(false)
                    .stream()
                    .filter(d -> d.getType().equals(DeltaType.DELETE))
                    .collect(Collectors.toList());
            assertEquals(1, deleteDeltas.size());
            assertEquals(deleteDeltas.get(0).getSource().getLines().get(0), "Line 1");
        }
        catch (IOException ioe) {
            fail("Error while running test: " + ioe.toString());
        }
    }

    @Test
    public void insertTest() {
        try {
            List<AbstractDelta<String>> insertDeltas = fileComparator.getDeltas(false)
                    .stream()
                    .filter(d -> d.getType().equals(DeltaType.INSERT))
                    .collect(Collectors.toList());
            assertEquals(1, insertDeltas.size());
            assertEquals(insertDeltas.get(0).getTarget().getLines().get(0), "new line 6.1");
        }
        catch (IOException ioe) {
            fail("Error while running test: " + ioe);
        }
    }

    @Test
    public void changeTest() {
        try {
            List<AbstractDelta<String>> changesDeltas = fileComparator.getDeltas(false)
                    .stream()
                    .filter(d -> d.getType().equals(DeltaType.CHANGE))
                    .collect(Collectors.toList());
            assertEquals(3, changesDeltas.size());

            assertEquals(changesDeltas.get(0).getSource().getLines().get(0), "Line 3");
            assertEquals(changesDeltas.get(0).getTarget().getLines().get(0), "Line 3 with changes");

            assertEquals(changesDeltas.get(1).getSource().getLines().get(0), "Line 5");;
            assertEquals(changesDeltas.get(1).getTarget().getLines().get(0), "Line 5 with changes and");
            assertEquals(changesDeltas.get(1).getTarget().getLines().get(1), "a new line");

            assertEquals(changesDeltas.get(2).getSource().getLines().get(0), "Line 10");
            assertEquals(changesDeltas.get(2).getTarget().getLines().get(0), "Line 10 with changes");
        }
        catch (IOException ioe) {
            fail("Error while running test: " + ioe);
        }
    }

    @Test
    public void keepTest() {
        try {
            List<AbstractDelta<String>> keepDeltas = fileComparator.getDeltas(true)
                    .stream()
                    .filter(d -> d.getType().equals(DeltaType.EQUAL))
                    .collect(Collectors.toList());
            assertEquals(4, keepDeltas.size());

            assertEquals(keepDeltas.get(0).getSource().getLines().get(0), "Line 2");
            assertEquals(keepDeltas.get(0).getTarget().getLines().get(0), "Line 2");

            assertEquals(keepDeltas.get(1).getSource().getLines().get(0), "Line 4");
            assertEquals(keepDeltas.get(1).getTarget().getLines().get(0), "Line 4");

            assertEquals(keepDeltas.get(2).getSource().getLines().get(0), "Line 6");
            assertEquals(keepDeltas.get(2).getTarget().getLines().get(0), "Line 6");

            assertEquals(keepDeltas.get(3).getSource().getLines().get(0), "Line 7");
            assertEquals(keepDeltas.get(3).getTarget().getLines().get(0), "Line 7");

            assertEquals(keepDeltas.get(3).getSource().getLines().get(1), "Line 8");
            assertEquals(keepDeltas.get(3).getTarget().getLines().get(1), "Line 8");

            assertEquals(keepDeltas.get(3).getSource().getLines().get(2), "Line 9");
            assertEquals(keepDeltas.get(3).getTarget().getLines().get(2), "Line 9");
        }
        catch (IOException ioe) {
            fail("Error while running test: " + ioe);
        }
    }
}