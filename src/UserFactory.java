public class UserFactory {
    public static User createUser(String userType, String userName) {
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
