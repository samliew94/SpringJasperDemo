package app.models.print;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class CompanyInfo {

	private String companyName;
	private String companyAddress;
	private String companyAddress1;
	private String companyAddress2;

	public CompanyInfo(String companyName, String companyAddress, String companyAddress1, String companyAddress2) {
		super();
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyAddress1 = companyAddress1;
		this.companyAddress2 = companyAddress2;
	}
	
	public Map toMap() {
		Map map = new HashMap();
		map.put("companyInfo", this);
		return map;
	}
	
}
