package il.ac.haifa.cs.sweng.cms.common.messages;

/**
 * Statuses of responses.
 *
 * @author Yuval Razieli
 */
public enum ResponseStatus {
    Acknowledged,
    Rejected,
    DeclinedUser,  // permission = -1
    DeclinedPass,  // permission = -1
    DeclinedMultConnections, // permission = -1
    Customer, // permission = 0
    CustomerService, // permission = 1
    ContentManager, // permission = 2
    BranchManager, // permission = 3
    Administrator  // permission = 4
}
