package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyProfiler;

/**
 * A class to access Prof-iler data stored as a json file on the hard disk.
 */
public class JsonProfilerStorage implements ProfilerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonProfilerStorage.class);

    private Path filePath;

    public JsonProfilerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getProfilerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyProfiler> readProfiler() throws DataLoadingException {
        return readProfiler(filePath);
    }

    /**
     * Similar to {@link #readProfiler()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyProfiler> readProfiler(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableProfiler> jsonProfiler = JsonUtil.readJsonFile(
                filePath, JsonSerializableProfiler.class);
        if (!jsonProfiler.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonProfiler.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveProfiler(ReadOnlyProfiler profiler) throws IOException {
        saveProfiler(profiler, filePath);
    }

    /**
     * Similar to {@link #saveProfiler(ReadOnlyProfiler)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveProfiler(ReadOnlyProfiler profiler, Path filePath) throws IOException {
        requireNonNull(profiler);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableProfiler(profiler), filePath);
    }

}
