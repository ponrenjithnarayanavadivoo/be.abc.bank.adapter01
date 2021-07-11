/**
 * 
 */
package be.abc.bank.adapter.model;

import java.io.Serializable;

/**
 * Transaction Model to display the transaction details
 * 
 * @author Renjith
 */

public class TransactionModel implements Serializable {

	private static final long serialVersionUID = -7999852259617491511L;
	
	private String tranNumber;
	private String amount;
	private String transDate;
	
	/**
	 * Transaction Model 
	 * @param tranNumber
	 * @param amount
	 * @param transDate
	 */
	public TransactionModel(String tranNumber, String amount, String transDate) {
		super();
		this.tranNumber = tranNumber;
		this.amount = amount;
		this.transDate = transDate;
	}
	public TransactionModel() {
	}
	/**
	 * @return the tranNumber
	 */
	public String getTranNumber() {
		return tranNumber;
	}
	/**
	 * @param tranNumber the tranNumber to set
	 */
	public void setTranNumber(String tranNumber) {
		this.tranNumber = tranNumber;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the transDate
	 */
	public String getTransDate() {
		return transDate;
	}
	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((tranNumber == null) ? 0 : tranNumber.hashCode());
		result = prime * result + ((transDate == null) ? 0 : transDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionModel other = (TransactionModel) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (tranNumber == null) {
			if (other.tranNumber != null)
				return false;
		} else if (!tranNumber.equals(other.tranNumber))
			return false;
		if (transDate == null) {
			if (other.transDate != null)
				return false;
		} else if (!transDate.equals(other.transDate))
			return false;
		return true;
	}
}
