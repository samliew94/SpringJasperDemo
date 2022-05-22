package app.models.print;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class BillToInfo  {

	private String billToName;
	private String billToAddress;
	private String billToAddress1;
	private String billToAddress2;
	
	public BillToInfo(String billToName, String billToAddress, String billToAddress1, String billToAddress2) {
		this.billToName = billToName;
		this.billToAddress = billToAddress;
		this.billToAddress1 = billToAddress1;
		this.billToAddress2 = billToAddress2;
	}
	
	public Map toMap() {
		Map map = new HashMap();
		map.put("billToName", billToName);
		map.put("billToAddress", billToAddress);
		map.put("billToAddress1", billToAddress1);
		map.put("billToAddress2", billToAddress2);
		
		return map;
	}
	
}
