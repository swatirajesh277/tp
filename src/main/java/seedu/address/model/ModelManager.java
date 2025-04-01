package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the prof-iler data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Profiler profiler;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;

    /**
     * Initializes a ModelManager with the given profiler and userPrefs.
     */
    public ModelManager(ReadOnlyProfiler profiler, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(profiler, userPrefs);

        logger.fine("Initializing with prof-iler: " + profiler + " and user prefs " + userPrefs);

        this.profiler = new Profiler(profiler);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.profiler.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
    }

    public ModelManager() {
        this(new Profiler(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getProfilerFilePath() {
        return userPrefs.getProfilerFilePath();
    }

    @Override
    public void setProfilerFilePath(Path profilerFilePath) {
        requireNonNull(profilerFilePath);
        userPrefs.setProfilerFilePath(profilerFilePath);
    }

    //=========== Profiler ================================================================================

    @Override
    public void setProfiler(ReadOnlyProfiler profiler) {
        this.profiler.resetData(profiler);
    }

    @Override
    public ReadOnlyProfiler getProfiler() {
        return profiler;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return profiler.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        profiler.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        profiler.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        profiler.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedProfiler}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns a sorted view of the list of {@code Person} based on progress percentage.
     */
    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        if (comparator == null) {
            logger.info("Resetting sorted list to default insertion order.");
            sortedPersons.setComparator(null);
        } else {
            logger.info("Sorting person list using comparator: " + comparator.getClass().getSimpleName());
            sortedPersons.setComparator(comparator);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return profiler.equals(otherModelManager.profiler)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && sortedPersons.equals(otherModelManager.sortedPersons);
    }
}
