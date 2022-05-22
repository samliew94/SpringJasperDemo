package app.models;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Data
public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ordered_date")
	private Date orderedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Products product;

	private Integer quantity;
	
	public Map toMap() {
		Map map = new HashMap();
		map.put("id", id);
		map.put("productName", product.getProductName());
		return map;
	}
}