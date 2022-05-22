package app.services;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.models.Orders;
import app.models.Products;

@Service
@Scope("prototype")
public class OrderService {

	@Autowired
	private SessionFactory sessionFactory;
	
	public String dropOrderTable() throws Exception
	{
		Exception ex = null;
		
		Session session = null;
		
		try
		{
			session = sessionFactory.openSession();
			
			String query = "FROM Orders";
			List<Orders> orders = session.createQuery(query, Orders.class).getResultList();
			
			session.beginTransaction();
			
			for (Orders order : orders)
				session.delete(order);
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			ex = e;
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		
		if (ex != null)
			throw ex;
		
		return "Successfully Cleared Order Table";
	}
	
	public List<Orders> getAll() throws Exception
	{
		try (Session session = sessionFactory.openSession()){
			
			List<Orders> orders = session.createQuery("FROM Orders", Orders.class).getResultList();
			
//			orders.forEach(x->x.setProduct(null));
			
			return orders;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("error at " + getClass().getCanonicalName() + " " + e);
		}
		
	}

	public ResponseEntity<List<Orders>> populate() throws Exception
	{
		// TODO Auto-generated method stub
		
		Session session = null;
		Exception ex = null;
		
		try 
		{
			session = sessionFactory.openSession();
			
			String query = "FROM Products";
			List<Products> products = session.createQuery(query, Products.class).getResultList();
			
			// randomly insert X number of orders using random products
			List<Orders> orders = new LinkedList<Orders>();
			int counter = 50;
			while(counter > 0) {
				
				Orders order = new Orders();
				Products product = products.get(new Random().nextInt(products.size()));
				order.setOrderedDate(new Date());
				order.setProduct(product);
				order.setQuantity(new Random().nextInt(51) + 1);
				
				orders.add(order);
				
				counter--;
			}
			
			session.beginTransaction();
			for (Orders order : orders) 
				session.save(order);
			session.getTransaction().commit();
			
			return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			ex = e;
			session.getTransaction().rollback();
 		} finally {
			session.close();
		}
		
		throw ex;
	}
}
