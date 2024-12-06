package swagger;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseController {
    public static final String BENEFICIARY_TAG = "Beneficiary Management";
    public static final String BENEFICIARY_DESCRIPTION = "Operations for managing beneficiary information and records";

    public static final String VIREMENT_TAG = "Transfer Management";
    public static final String VIREMENT_DESCRIPTION = "Operations for handling financial transfers between accounts";

    // Not Implemented yet
    public static final String ACCOUNT_TAG = "Account Management";
    public static final String ACCOUNT_DESCRIPTION = "Operations for managing bank accounts";

    public static final String TRANSACTION_TAG = "Transaction Management";
    public static final String TRANSACTION_DESCRIPTION = "Operations for tracking and managing financial transactions";

}