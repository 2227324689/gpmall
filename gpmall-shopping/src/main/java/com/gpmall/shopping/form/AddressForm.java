package com.gpmall.shopping.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by ly
 * date:2019/8/10
 */
@Data
@ApiModel
public class AddressForm {

	@ApiModelProperty(name = "addressId", value = "地址ID", example = "10000")
	private Long addressId;

	@ApiModelProperty(name = "userName", value = "姓名")
	private String userName;

	@ApiModelProperty(name = "tel", value = "电话")
	private String tel;

	@ApiModelProperty(name = "streetName", value = "街道")
	private String streetName;

	@ApiModelProperty(name = "_Default", value = "是否默认")
	private boolean _Default;
}
