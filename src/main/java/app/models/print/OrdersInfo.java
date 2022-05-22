package app.models.print;

import app.models.Orders;
import lombok.Data;

@Data
public class OrdersInfo {

	private Integer orderId;
	private String productCode;
	private String productName;
	private Integer quantity;
	
	public OrdersInfo(Orders order) {
		
		orderId = order.getId();
		productCode = order.getProduct().getProductCode();
		productName = order.getProduct().getProductName();
		quantity = order.getQuantity();
		
	}
	
}
