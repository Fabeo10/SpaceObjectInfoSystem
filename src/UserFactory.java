public class UserFactory {
    public static User createUser(String userType) {
        switch (userType.toLowerCase()) {
            case "scientist":
                return new Scientist();
                
            case "space agency representative":
            case "spaceagencyrepresentative":
                return new SpaceAgencyRepresentative();
                

            case "policy maker":
            case "policymaker":
                return new PolicyMaker();
        
            default:
                throw new IllegalArgumentException("Invalid User Type: " + userType);
        }
    }
}
