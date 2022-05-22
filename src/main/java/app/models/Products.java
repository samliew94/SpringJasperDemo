package app.models;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Data
public class Products implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

}