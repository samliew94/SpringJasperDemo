package app.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.models.Orders;
import app.services.OrderService;
import app.services.PrintOrderService;
import app.util.Console;

@RestController
public class OrderController {

	@Autowired
	private ApplicationContext context;
	
	private final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@RequestMapping("/print")
	private void print() {
		try 
		{
			PrintOrderService printOrderService = getPrintOrderService();
			printOrderService.printOrders();
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			log.error("error at " + getClass().getName() + " " + e);
		}
	}
	
	@RequestMapping("/order")
	private ResponseEntity<List<Orders>> get() {
		
		try {
			OrderService orderService = context.getBean(OrderService.class);
			List<Orders> orders = orderService.getAll();
			
			return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error at " + getClass().getName() + " " + e);
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/order/delete")
	private void delete() {
		
		try {
			OrderService orderService = context.getBean(OrderService.class);
			orderService.dropOrderTable();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error at " + getClass().getName() + " " + e);
		}
		
	}
	
	@RequestMapping("/order/populate")
	private ResponseEntity<List<Orders>> populate() {
		try {
			OrderService orderService = context.getBean(OrderService.class);
			
			orderService.dropOrderTable();
			
			return orderService.populate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error at " + getClass().getName() + " " + e);
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private PrintOrderService getPrintOrderService() {return context.getBean(PrintOrderService.class);}
}
