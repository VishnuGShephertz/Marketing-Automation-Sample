package com.shephertz.app42.ma.cart;

import java.util.regex.Pattern;

public class ValidateInput {
	private final String namePattern="^[a-zA-Z0-9 ]*$";
//	private final String zipPattern="^[a-zA-Z0-9-]*$";
	private final String addressPattern="^[a-zA-Z0-9 .-]*$";
	private final  String phonePattern = "^\\+?[0-9. ()-]{10,25}$";
	private final Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
	
	public  boolean isEmailValid(String email){
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
	
	public  boolean isAddressValid(String address){
		return isValid(address,addressPattern);
	}
	
	public  boolean isNameValid(String name){
		return isValid(name,namePattern);
	}
	
//	public  boolean isZipCodeValid(String zipCode){
//		return isValid(zipCode,zipPattern);
//	}
	
	public  boolean isPhoneNoValid(String phoneNo){
		return isValid(phoneNo,phonePattern);
	}
	
	private  boolean isValid(String text,String pattern) {
		return Pattern.matches(pattern, text);
	}

	
}
