package app.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import app.models.Orders;
import app.models.print.BillToInfo;
import app.models.print.CompanyInfo;
import app.models.print.OrdersInfo;
import app.util.Console;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
@Scope("prototype")
public class PrintOrderService {

	private static final Logger log = LoggerFactory.getLogger(PrintOrderService.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public void printOrders() throws Exception 
	{
		// TODO Auto-generated method stub
		
		Exception ex = null;
		
//		Session session = null;
		
		try (Session session = sessionFactory.openSession();)
		{
//			String query = "SELECT o FROM Orders o JOIN o.product p WHERE p.id = 2";
//			String query = "FROM Orders";
			String query = "FROM Orders";
					
			List<Orders> orders = session.createQuery(query, Orders.class).getResultList();
			List<OrdersInfo> ordersInfo = new LinkedList<OrdersInfo>(); 
			orders.forEach(x->ordersInfo.add(new OrdersInfo(x)));
			
			// generate json files
			CompanyInfo companyInfo = new CompanyInfo("Samsoft Solutions", "https://github.com/samliew94", "https://www.linkedin.com/in/sam-liew-2a995518b", "");
			BillToInfo billToInfo = new BillToInfo("Cahaya Bestari Sdn. Bhd.", "Jalan Abdul Kassim", "83250", "Selangor, Malaysia");
			
			Map map = new LinkedHashMap();
			map.put("companyInfo", companyInfo);
			map.put("billToInfo", billToInfo);
			map.put("orders", ordersInfo);
			
			String json = new ObjectMapper().writeValueAsString(map);
			
			Console.writeLine(json);
			
			InputStream stream = new ByteArrayInputStream(json.getBytes());
			
			JsonDataSource ds = new JsonDataSource(stream);

//			File file = ResourceUtils.getFile("classpath:jasperreports/testreport1.jasper");
			
			Resource resource = resourceLoader.getResource("classpath:jasperreports/testreport1.jasper");
            InputStream fileStream = resource.getInputStream();
			
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileStream);

//			File jsonFile = ResourceUtils.getFile("classpath:testjson.json");
			
			Map<String, Object> parameters = new HashMap<String, Object>(); 
			parameters.put("companyInfoDsExpr", "companyInfo");
			parameters.put("billToInfoDsExpr", "billToInfo");
			parameters.put("ordersDsExpr", "orders");
			parameters.put("REPORT_DIR", "jasperreports/");
			parameters.put("REPORT_DATA_SOURCE", ds);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds); 
			ds.subDataSource("companyInfo");
			ds.subDataSource("billToInfo");
			
			response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=testReport.pdf");
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
			ex = e;
		}
	
		if (ex != null)
			throw ex;
		
	}
	
	

	
}
