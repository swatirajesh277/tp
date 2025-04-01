package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalProfiler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Profiler;
import seedu.address.model.ReadOnlyProfiler;

public class JsonProfilerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonProfilerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readProfiler_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readProfiler(null));
    }

    private java.util.Optional<ReadOnlyProfiler> readProfiler(String filePath) throws Exception {
        return new JsonProfilerStorage(Paths.get(filePath)).readProfiler(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readProfiler("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readProfiler("notJsonFormatProfiler.json"));
    }

    @Test
    public void readProfiler_invalidPersonProfiler_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readProfiler("invalidPersonProfiler.json"));
    }

    @Test
    public void readProfiler_invalidAndValidPersonProfiler_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readProfiler("invalidAndValidPersonProfiler.json"));
    }

    @Test
    public void readAndSaveProfiler_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempProfiler.json");
        Profiler original = getTypicalProfiler();
        JsonProfilerStorage jsonProfilerStorage = new JsonProfilerStorage(filePath);

        // Save in new file and read back
        jsonProfilerStorage.saveProfiler(original, filePath);
        ReadOnlyProfiler readBack = jsonProfilerStorage.readProfiler(filePath).get();
        assertEquals(original, new Profiler(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonProfilerStorage.saveProfiler(original, filePath);
        readBack = jsonProfilerStorage.readProfiler(filePath).get();
        assertEquals(original, new Profiler(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonProfilerStorage.saveProfiler(original); // file path not specified
        readBack = jsonProfilerStorage.readProfiler().get(); // file path not specified
        assertEquals(original, new Profiler(readBack));

    }

    @Test
    public void saveProfiler_nullProfiler_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProfiler(null, "SomeFile.json"));
    }

    /**
     * Saves {@code profiler} at the specified {@code filePath}.
     */
    private void saveProfiler(ReadOnlyProfiler profiler, String filePath) {
        try {
            new JsonProfilerStorage(Paths.get(filePath))
                    .saveProfiler(profiler, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProfiler_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProfiler(new Profiler(), null));
    }
}
