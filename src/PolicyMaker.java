/**
 * Represents a Policy Maker user in the Space Object Info System.
 * Policy Makers are responsible for setting and reviewing regulations and guidelines.
 *
 * <p>
 * Example:
 * <pre>
 *     PolicyMaker pm = new PolicyMaker("Senator Alex Rivera");
 *     pm.displayRole(); // Outputs: "Policy Maker: Senator Alex Rivera"
 * </pre>
 * </p>
 *
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.0
 */
public class PolicyMaker extends User {

    /**
     * Constructs a PolicyMaker with the specified name.
     *
     * @param name the name of the policy maker
     */
    public PolicyMaker(String name) {
        super(name, "Policy Maker");
    }

    /**
     * Displays the role and name of the policy maker.
     */
    @Override
    public void displayRole() {
        System.out.println("Policy Maker: " + name);
    }
}
