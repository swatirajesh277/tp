package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Profiler;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableProfilerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableProfilerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsProfiler.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonProfiler.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonProfiler.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableProfiler dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableProfiler.class).get();
        Profiler profilerFromFile = dataFromFile.toModelType();
        Profiler typicalPersonsProfiler = TypicalPersons.getTypicalProfiler();
        assertEquals(profilerFromFile, typicalPersonsProfiler);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableProfiler dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableProfiler.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableProfiler dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableProfiler.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableProfiler.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
