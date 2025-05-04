import java.util.List;
/**
 * Required implementation of a method to filter the full list of space objects
 * by a field of the users choice or analytical need
 */
public interface FilterInterface {
public void filterByField(List<SpaceObject> filteredEntries, String criteria);
}
