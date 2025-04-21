/**
 * Factory class for creating different types of {@link User} objects.
 * <p>
 * This class uses the Factory design pattern to centralize user creation logic
 * based on a provided user type string. Supported types are:
 * <ul>
 *   <li>"scientist"</li>
 *   <li>"space agency representative" (or "spaceagencyrepresentative")</li>
 *   <li>"policy maker" (or "policymaker")</li>
 * </ul>
 * </p>
 * 
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.0
 */
public class UserFactory {

    /**
     * Creates a {@link User} instance of the specified type, initialized with the given name.
     * 
     * @param userType a case-insensitive string identifying the type of user to create;
     *                 valid values are "scientist", "space agency representative" 
     *                 (or "spaceagencyrepresentative"), and "policy maker" (or "policymaker").
     * @param userName the display name for the new User instance
     * @return a new User object corresponding to the specified type
     * @throws IllegalArgumentException if the {@code userType} is not one of the supported types
     *                                  or if {@code userType} or {@code userName} are null
     */
    public static User createUser(String userType, String userName) {
        if (userType == null || userName == null) {
            throw new IllegalArgumentException("userType and userName must not be null");
        }
        switch (userType.toLowerCase()) {

            case "scientist":
                return new Scientist(userName);

            case "space agency representative":
            case "spaceagencyrepresentative":
                return new SpaceAgencyRepresentative(userName);

            case "policy maker":
            case "policymaker":
                return new PolicyMaker(userName);

            default:
                throw new IllegalArgumentException("Invalid User Type: " + userType);
        }
    }
}
