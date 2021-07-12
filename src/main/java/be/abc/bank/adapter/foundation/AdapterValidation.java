package be.abc.bank.adapter.foundation;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is used to validate the input request of details entered in the
 * web page.
 * 
 * @author Renjith
 *
 */

public class AdapterValidation {

	/**
	 * Validate the create Account request operation's input parameters
	 * 
	 * @param customerId   String
	 * @param creditAmount String
	 * @return boolean
	 */
	public static boolean validateCreateAccounInputRequest(String customerId) {
		if (StringUtils.isBlank(customerId)) {
			return true;
		}
		return false;

	}
}
