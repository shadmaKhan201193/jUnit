package com.itl.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVO {
	private String branchCode;
	private Integer tenantId;
	private String loginId;
}
