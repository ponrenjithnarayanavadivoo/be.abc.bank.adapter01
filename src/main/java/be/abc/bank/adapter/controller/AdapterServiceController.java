package be.abc.bank.adapter.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.abc.bank.adapter.foundation.AdapterConstants;
import be.abc.bank.adapter.foundation.AdapterValidation;
import be.abc.bank.adapter.management.IAdapterManagementServiceImpl;
import be.abc.bank.adapter.model.CustomerAllInfoModel;

/**
 * Adapter class which acts as a controller for all bank related services.
 * 
 * @author Renjith
 *
 */
@Controller
public class AdapterServiceController {

	private static final Logger LOGGER = Logger.getLogger(AdapterServiceController.class);

	@Autowired
	private IAdapterManagementServiceImpl myManagerService;

	/**
	 * This operation is used to create current accounts for the existing customer.
	 * 
	 * @param customerId     String
	 * @param initCrdtAmount String
	 * @return ResponseEntity
	 */
	@PostMapping(path = "/createaccount")
	public ResponseEntity<String> createAccount(@RequestParam("customerId") String customerId,
			@RequestParam("initCrdtAmount") String initCrdtAmount) {

		LOGGER.debug("createAccount() operation is called..");

		if (AdapterValidation.validateCreateAccounInputRequest(customerId)) {
			return ResponseEntity.badRequest().body(AdapterConstants.INVALID_INPUT);
		}
		myManagerService.buildCreateAccount(customerId, initCrdtAmount);

		return ResponseEntity.ok().body(AdapterConstants.CREATE_ACCOUNT_SUCCESS);
	}

	/**
	 * This Operation is used to get the customer details and transaction of the
	 * accounts.
	 * 
	 * @return
	 */
	@GetMapping(path = "/userandtransctInformation")
	public String getUserInformation(@RequestParam("customerId") String customerId, Model model) {

		if (AdapterValidation.validateCreateAccounInputRequest(customerId)) {
			model.addAttribute("error", AdapterConstants.INVALID_INPUT);
			return "error";
		}
			CustomerAllInfoModel theModel = myManagerService.getAllDetail(customerId);
			if (theModel != null) {
				model.addAttribute("thymName", theModel.getName());
				model.addAttribute("thymSurName", theModel.getSurName());
				model.addAttribute("thymTranInfoMap", theModel.getTranInfoMap());
			} else {
				model.addAttribute("error", AdapterConstants.INVALID_INPUT);
				return "error";
			}
		return "result";
	}
}
