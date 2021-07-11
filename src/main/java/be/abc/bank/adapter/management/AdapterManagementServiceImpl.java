package be.abc.bank.adapter.management;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import be.abc.bank.accounts.accounts.v1.model.AccountDetailsInfo;
import be.abc.bank.accounts.accounts.v1.model.AccountInfo;
import be.abc.bank.accounts.accounts.v1.model.CustomerInfo;
import be.abc.bank.accounts.accounts.v1.model.CustomerRequestInfo;
import be.abc.bank.adapter.Exception.CustomerNotFoundException;
import be.abc.bank.adapter.model.AccountModel;
import be.abc.bank.adapter.model.CustomerAllInfoModel;
import be.abc.bank.adapter.model.TransactionModel;
import be.abc.bank.transaction.transaction.v1.model.TransactionDetail;
import be.abc.bank.transaction.transaction.v1.model.TransactionDetailsInfo;
import be.abc.bank.transaction.transaction.v1.model.TransactionRequestInfo;

/**
 * Manager service supervise the account service operations or communications.
 * It is used to create the business logic and communicating with other sub ordinate services.
 *
 * @author Renjith
 *
 */
@Component
public class AdapterManagementServiceImpl implements IAdapterManagementServiceImpl {

	@Value("${account.service.host.url}")
	private String theAccountServiceurl;
	
	@Value("${transaction.service.host.url}")
	private String theTransactionServiceurl;
	
	@Value("${account.type}")
	private String accountType;
	
	@Value("${api.version}")
	private String apiVersion;
	
	@Autowired
	RestTemplate myRestTamplate;

	/**
	 * create new current accounts for existing bank customers
	 * 
	 * @param customerId    String
	 * @param creditAccount String
	 *
	 */
	@Override
	public void buildCreateAccount(String customerId, String amount) {

		long creditAmount = StringUtils.isBlank(amount) ? 0 : Long.parseLong(amount);

		// Step :1 Find the customer id from customer service
		getCutomerInfo(customerId);

		// Step :2 create a new current account and without credit amount
		CustomerRequestInfo aCustomerRequestInfo = new CustomerRequestInfo();
		aCustomerRequestInfo.accountType(accountType);
		aCustomerRequestInfo.setCustomerId(Integer.parseInt(customerId));
		String accountId = createAccountWithoutCreditAmount(aCustomerRequestInfo, customerId);

		if (creditAmount > 0 && StringUtils.isNotBlank(accountId)) {
			
			//Step :2 create a transaction in transaction service
			createTransaction(accountId, creditAmount);
			
			//Step :3 update the credit amount in the update service
			aCustomerRequestInfo.setCreditAmount(creditAmount);
			long acctID = Long.parseLong(accountId);
			String theUpdateAccountUrl = theAccountServiceurl + "updateAccount/{accountId}";
			
			HttpEntity<CustomerRequestInfo> theAccountHttpEntity = new HttpEntity<>(aCustomerRequestInfo,
					getEntities());
			myRestTamplate.exchange(theUpdateAccountUrl, HttpMethod.PUT, theAccountHttpEntity, String.class, acctID);
		}
	}

	/**
	 * Method to get the bank account details from the back end service.
	 * 
	 * @param customerId String
	 * @return AccountDetailsInfo
	 *
	 */
	@Override
	public CustomerAllInfoModel getAllDetail(String customerId) {

		List<AccountInfo> theAccountDetails = null;

		// Step :1 Find the customer id from customer service
		CustomerInfo theCustomerInfo = getCutomerInfo(customerId);
		
		CustomerAllInfoModel aCustomerDetailsInfo = new CustomerAllInfoModel();
		aCustomerDetailsInfo.setName(theCustomerInfo.getCustomerName());
		aCustomerDetailsInfo.setSurName(theCustomerInfo.getCustomerSurName());

		//Step : 2 Find the account id using customer id
		String theAccountUrl = theAccountServiceurl + "getAccountDetail/{customerId}";
		HttpEntity<String> theHttpEntity = new HttpEntity<>(getEntities());

		ResponseEntity<AccountDetailsInfo> theAccountDetailsResponse = myRestTamplate.exchange(theAccountUrl,
				HttpMethod.GET, theHttpEntity, AccountDetailsInfo.class, customerId);

		// Step : Find the transactions by using the account id.
		if (!theAccountDetailsResponse.getBody().getAccountDetails().isEmpty()) {
			theAccountDetails = theAccountDetailsResponse.getBody().getAccountDetails();
			List<String> accountIds = theAccountDetails.stream().map(accountInfo -> accountInfo.getAccountNumber())
					.collect(Collectors.toList());
			String idsAsParam = accountIds.stream().map(Object::toString).collect(Collectors.joining(","));
			String theTransactionUrl = theTransactionServiceurl + "getTransactionDetail/" + idsAsParam;
			ResponseEntity<TransactionDetailsInfo> theTransactionDetailsResponse = myRestTamplate.exchange(
					theTransactionUrl, HttpMethod.GET, theHttpEntity, TransactionDetailsInfo.class, String.class);

			TransactionDetailsInfo theTransactionDetailsInfo = theTransactionDetailsResponse.getBody();

			Map<AccountModel, List<TransactionModel>> theTransactionMap = new HashMap<>();
			for (AccountInfo accountInfo : theAccountDetails) {
				List<TransactionModel> transList = new ArrayList<>();
				for (TransactionDetail theTransactionDetail : theTransactionDetailsInfo.getTransactionDetails()) {
					if (accountInfo.getAccountNumber().equals(theTransactionDetail.getAccountNumber())) {
						TransactionModel aAccountModel = new TransactionModel();
						aAccountModel.setAmount("" + theTransactionDetail.getAmount());
						aAccountModel.setTranNumber(theTransactionDetail.getTransactionId());
						aAccountModel.setTransDate(theTransactionDetail.getTransactionTimestamp());
						transList.add(aAccountModel);
					}
				}
				theTransactionMap.put(new AccountModel(accountInfo.getAccountNumber(), String.valueOf(accountInfo.getBalance())),
						transList);
			}
			aCustomerDetailsInfo.setTranInfoMap(theTransactionMap);
		}
		return aCustomerDetailsInfo;
	}


	/**
	 * get the customerInformation
	 * @param customerId
	 * @return
	 */
	private CustomerInfo getCutomerInfo(String customerId) {

		final String theCustomerUrl = theAccountServiceurl + "getCustomerDetail/{customerId}";

		// get customer Detail
		HttpEntity<String> theCustomerHttpEntity = new HttpEntity<>(getEntities());
		ResponseEntity<CustomerInfo> customerInfoResponse = myRestTamplate.exchange(theCustomerUrl, HttpMethod.GET,
				theCustomerHttpEntity, CustomerInfo.class, customerId);
		CustomerInfo CustomerInfo = customerInfoResponse.getBody();
		if (CustomerInfo != null) {
			return CustomerInfo;
		} else
			throw new CustomerNotFoundException("Customer detail not found");
	}

	/**
	 * Create Account Without credit amount value
	 * @param aCustomerRequestInfo
	 * @param customerId
	 * @return
	 */
	private String createAccountWithoutCreditAmount(CustomerRequestInfo aCustomerRequestInfo, String customerId) {
		String theAccounturl = theAccountServiceurl + "createAccount";
		HttpEntity<CustomerRequestInfo> theAccountHttpEntity = new HttpEntity<>(aCustomerRequestInfo, getEntities());
		ResponseEntity<String> theAccountPostResponseEntity = myRestTamplate.postForEntity(theAccounturl,
				theAccountHttpEntity, String.class);

		return theAccountPostResponseEntity.getBody();
	}
	
	/**
	 * create a new transaction by using the account id
	 * @param accountId
	 * @param creditAmount
	 */
	private void createTransaction(String accountId, long creditAmount) {
		String theTransactionUrl = theTransactionServiceurl + "createTransaction";
		TransactionRequestInfo aTransactionRequestInfo = new TransactionRequestInfo();
		aTransactionRequestInfo.setAccountNumber(Long.parseLong(accountId));
		aTransactionRequestInfo.setAmount(creditAmount);
		HttpEntity<TransactionRequestInfo> theTransactionEntity = new HttpEntity<>(aTransactionRequestInfo,
				getEntities());
		myRestTamplate.postForEntity(theTransactionUrl, theTransactionEntity, String.class);
	}

	/**
	 * This operation is used to create input headers for the rest consuming
	 * services.
	 * 
	 * @return HttpHeaders
	 */
	private HttpHeaders getEntities() {
		HttpHeaders theHttpHeaders = new HttpHeaders();
		theHttpHeaders.set("Request-ID", "1");
		theHttpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		theHttpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
		theHttpHeaders.set("Api-Version", apiVersion);

		return theHttpHeaders;

	}

}