package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Profiler;
import seedu.address.model.ReadOnlyProfiler;

/**
 * Represents a storage for {@link Profiler}.
 */
public interface ProfilerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProfilerFilePath();

    /**
     * Returns Prof-iler data as a {@link ReadOnlyProfiler}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyProfiler> readProfiler() throws DataLoadingException;

    /**
     * @see #getProfilerFilePath()
     */
    Optional<ReadOnlyProfiler> readProfiler(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyProfiler} to the storage.
     * @param profiler cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProfiler(ReadOnlyProfiler profiler) throws IOException;

    /**
     * @see #saveProfiler(ReadOnlyProfiler)
     */
    void saveProfiler(ReadOnlyProfiler profiler, Path filePath) throws IOException;

}
