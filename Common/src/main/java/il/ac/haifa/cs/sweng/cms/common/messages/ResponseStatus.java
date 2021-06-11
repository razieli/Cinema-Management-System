package il.ac.haifa.cs.sweng.cms.common.messages;

/**
 * Statuses of responses.
 *
 * @author Yuval Razieli
 */
public enum ResponseStatus {
    Acknowledged,
    Declined,  // permission = -1
    Customer, // permission = 0
    ContentManager, // permission = 1
    CustomerService, // permission = 2
    BranchManager, // permission = 3
    Administrator  // permission = 4
}
