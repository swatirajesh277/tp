package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyProfiler;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Prof-iler data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProfilerStorage profilerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ProfilerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ProfilerStorage profilerStorage, UserPrefsStorage userPrefsStorage) {
        this.profilerStorage = profilerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Profiler methods ==============================

    @Override
    public Path getProfilerFilePath() {
        return profilerStorage.getProfilerFilePath();
    }

    @Override
    public Optional<ReadOnlyProfiler> readProfiler() throws DataLoadingException {
        return readProfiler(profilerStorage.getProfilerFilePath());
    }

    @Override
    public Optional<ReadOnlyProfiler> readProfiler(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return profilerStorage.readProfiler(filePath);
    }

    @Override
    public void saveProfiler(ReadOnlyProfiler profiler) throws IOException {
        saveProfiler(profiler, profilerStorage.getProfilerFilePath());
    }

    @Override
    public void saveProfiler(ReadOnlyProfiler profiler, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        profilerStorage.saveProfiler(profiler, filePath);
    }

}
