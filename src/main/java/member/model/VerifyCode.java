package member.model;

import java.io.Serializable;

public class VerifyCode implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
