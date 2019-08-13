package com.gpmall.shopping.form;

import lombok.Data;

/**
 * create by ly
 * date:2019/8/10
 */
@Data
public class AddressForm {
	private Long addressId;
	private String userName;
	private String tel;
	private String streetName;
	private boolean _Default;
}
