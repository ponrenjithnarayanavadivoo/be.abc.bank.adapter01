package be.abc.bank.adapter.management;

import be.abc.bank.adapter.model.CustomerAllInfoModel;

public interface IAdapterManagementServiceImpl {

	/**
	 * Build a create account by following the sequence of operations
	 * @param customerId
	 * @param creditAmount
	 */
	void buildCreateAccount(String customerId,String creditAmount);
	
	/**
	 * Method to get the bank account details from the back end service.
	 * 
	 * @param customerId String
	 * @return AccountDetailsInfo
	 *
	 */
	CustomerAllInfoModel getAllDetail(String customerId);
}
