package be.abc.bank.adapter.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * CustomerAllInfoModel to display the customer Information details along with account and transaction
 * @author Renjith
 *
 */
public class CustomerAllInfoModel implements Serializable {

	private static final long serialVersionUID = -8052959880636931361L;

	private String name;
	private String surName;
	private Map<AccountModel,List<TransactionModel>> tranInfoMap;
	
	/**
	 * CustomerAllInfoModel
	 * 
	 * @param name String
	 * @param surName String
	 * @param tranInfoMap Map
	 */
	public CustomerAllInfoModel(String name, String surName, Map<AccountModel, List<TransactionModel>> tranInfoMap) {
		super();
		this.name = name;
		this.surName = surName;
		this.tranInfoMap = tranInfoMap;
	}

	public CustomerAllInfoModel() {
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surName
	 */
	public String getSurName() {
		return surName;
	}
	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * @return the tranInfoMap
	 */
	public Map<AccountModel, List<TransactionModel>> getTranInfoMap() {
		return tranInfoMap;
	}

	/**
	 * @param tranInfoMap the tranInfoMap to set
	 */
	public void setTranInfoMap(Map<AccountModel, List<TransactionModel>> tranInfoMap) {
		this.tranInfoMap = tranInfoMap;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surName == null) ? 0 : surName.hashCode());
		result = prime * result + ((tranInfoMap == null) ? 0 : tranInfoMap.hashCode());
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
		CustomerAllInfoModel other = (CustomerAllInfoModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surName == null) {
			if (other.surName != null)
				return false;
		} else if (!surName.equals(other.surName))
			return false;
		if (tranInfoMap == null) {
			if (other.tranInfoMap != null)
				return false;
		} else if (!tranInfoMap.equals(other.tranInfoMap))
			return false;
		return true;
	}

}
