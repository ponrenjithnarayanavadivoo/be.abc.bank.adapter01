/**
 * 
 */
package be.abc.bank.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import be.abc.bank.adapter.Exception.AccountNotFoundException;
import be.abc.bank.adapter.Exception.CustomerNotFoundException;

/**
 * Controller class to handle all the exceptions
 * 
 * @author Renjith
 *
 */
@ControllerAdvice
public class LibraryControllerAdvice {

	@ExceptionHandler(value = CustomerNotFoundException.class)
	public ResponseEntity<String> customerNotFoundException(CustomerNotFoundException e) {
		return new ResponseEntity<String>(message("CustomerId"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = AccountNotFoundException.class)
	public ResponseEntity<String> accountNotFoundException(AccountNotFoundException e) {
		return new ResponseEntity<String>(message("AccountId"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> accountNotFoundException(HttpClientErrorException e) {
			return new ResponseEntity<String>(messageWithException(e),
					HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public String message(String input) {
		String message = "<b><h2>" + input + " not Found </h2></b>";
		return message;
	}

	public String messageWithException (Exception e) {
		String message = "<b><h2>" + e.getMessage() + " </h2></b><br>" ;
		return message;
	}

}
