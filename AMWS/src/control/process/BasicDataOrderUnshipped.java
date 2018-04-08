package control.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import control.transmission.Message;
import control.transmission.MessageBuffer;
import helper.dao.ConnectionPool;
import model.basic.FileData;

public class BasicDataOrderUnshipped {

	private String readMessageToOSSClassName = "control.dataSynchronization.DataSynchronizationOperationsForReflection";
	private String readMessageToOSSMethodName = "updateOrderInformation";
	
	public String getOrderUnshipped() throws Exception{
		String filePath = "file/Reports/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "OrderUnshipped";
		Connection connection = ConnectionPool.getConnectionPool().getConnection();
		Statement stmt = connection.createStatement();
		String dateNow = new SimpleDateFormat("yyyyMMdd").format(new Date());
		ResultSet rs = stmt.executeQuery(""
					+ "select "
						+ "tmp.*, "
						+ "report_requested_or_scheduled_flat_file_order.item_promotion_id, "
						+ "order_FBM_transactionPrice.item_price "
					+ "from "
						+	"     (SELECT  "
						+	"         b.merchant, "
						+	"             b.order_id, "
						+	"             b.order_item_id, "
						+	"             b.purchase_date, "
						+	"             b.payments_date, "
						+	"             b.reporting_date, "
						+	"             b.promise_date, "
						+	"             b.days_past_promise, "
						+	"             b.buyer_email, "
						+	"             b.buyer_name, "
						+	"             b.buyer_phone_number, "
						+	"             b.sku, "
						+	"             b.product_name, "
						+	"             b.quantity_purchased, "
						+	"             b.quantity_shipped, "
						+	"             b.quantity_to_ship, "
						+	"             b.ship_service_level, "
						+	"             b.recipient_name, "
						+	"             b.ship_address_1, "
						+	"             b.ship_address_2, "
						+	"             b.ship_address_3, "
						+	"             b.ship_city, "
						+	"             b.ship_state, "
						+	"             b.ship_postal_code, "
						+	"             b.ship_country, "
						+	"             b.is_business_order, "
						+	"             b.purchase_order_number, "
						+	"             b.price_designation "
						+	"     FROM "
						+	"         (SELECT  "
						+	"         report_unshipped_orders.Store AS merchant, "
						+	"             report_unshipped_orders.order_id AS order_id, "
						+	"             report_unshipped_orders.order_item_id AS order_item_id, "
						+	"             report_unshipped_orders.purchase_date AS purchase_date, "
						+	"             report_unshipped_orders.payments_date AS payments_date, "
						+	"             report_unshipped_orders.reporting_date AS reporting_date, "
						+	"             report_unshipped_orders.promise_date AS promise_date, "
						+	"             report_unshipped_orders.days_past_promise AS days_past_promise, "
						+	"             report_unshipped_orders.buyer_email AS buyer_email, "
						+	"             report_unshipped_orders.buyer_name AS buyer_name, "
						+	"             report_unshipped_orders.buyer_phone_number AS buyer_phone_number, "
						+	"             report_unshipped_orders.sku AS sku, "
						+	"             report_unshipped_orders.product_name AS product_name, "
						+	"             report_unshipped_orders.quantity_purchased AS quantity_purchased, "
						+	"             report_unshipped_orders.quantity_shipped AS quantity_shipped, "
						+	"             report_unshipped_orders.quantity_to_ship AS quantity_to_ship, "
						+	"             report_unshipped_orders.ship_service_level AS ship_service_level, "
						+	"             report_unshipped_orders.recipient_name AS recipient_name, "
						+	"             report_unshipped_orders.ship_address_1 AS ship_address_1, "
						+	"             report_unshipped_orders.ship_address_2 AS ship_address_2, "
						+	"             report_unshipped_orders.ship_address_3 AS ship_address_3, "
						+	"             report_unshipped_orders.ship_city AS ship_city, "
						+	"             report_unshipped_orders.ship_state AS ship_state, "
						+	"             report_unshipped_orders.ship_postal_code AS ship_postal_code, "
						+	"             report_unshipped_orders.ship_country AS ship_country, "
						+	"             report_unshipped_orders.is_business_order AS is_business_order, "
						+	"             report_unshipped_orders.purchase_order_number AS purchase_order_number, "
						+	"             report_unshipped_orders.price_designation AS price_designation "
						+	"     FROM "
						+	"         report_unshipped_orders, (SELECT  "
						+	"         Store as merchant, MAX(insert_date) AS max_date "
						+	"     FROM "
						+	"         report_unshipped_orders "
						+	"       where order_id is not null and insert_date>" + dateNow
						+	"     GROUP BY merchant) AS a "
						+	"     WHERE "
						+	"         report_unshipped_orders.insert_date = a.max_date "
						+	"             AND report_unshipped_orders.Store = a.merchant "
						+	"             AND report_unshipped_orders.order_id IS NOT NULL) AS b "
						+	"     LEFT JOIN report_requested_or_scheduled_flat_file_order ON b.order_id = report_requested_or_scheduled_flat_file_order.order_id "
						+	"         AND b.order_item_id = report_requested_or_scheduled_flat_file_order.order_item_id "
						+	"         AND report_requested_or_scheduled_flat_file_order.item_price IS NOT NULL "
						+	"     GROUP BY b.order_id , b.order_item_id) AS tmp "
						+	"         LEFT JOIN "
						+	"     report_requested_or_scheduled_flat_file_order ON tmp.order_id = report_requested_or_scheduled_flat_file_order.order_id "
						+	"         AND tmp.order_item_id = report_requested_or_scheduled_flat_file_order.order_item_id "
						+	"         AND report_requested_or_scheduled_flat_file_order.item_promotion_id IS NOT NULL "
						+	"         LEFT JOIN "
						+	"     (SELECT  "
						+	"         `u`.`id` AS `id`, "
						+	"         `u`.`order_id` AS `order_id`, "
						+	"         `u`.`order_item_id` AS `order_item_id`, "
						+	"         SUM((((IFNULL(`u`.`item_price`, 0) + IFNULL(`u`.`shipping_price`, 0)) + IFNULL(`u`.`item_promotion_discount`, 0)) + IFNULL(`u`.`ship_promotion_discount`, 0))) AS `item_price` "
						+	"     FROM "
						+	"         ( SELECT  "
						+	"       * "
						+	"     FROM "
						+	"         `report_requested_or_scheduled_flat_file_order` "
						+	"     GROUP BY `report_requested_or_scheduled_flat_file_order`.`order_id` , `report_requested_or_scheduled_flat_file_order`.`order_item_id` , `report_requested_or_scheduled_flat_file_order`.`item_promotion_id` , `report_requested_or_scheduled_flat_file_order`.`ship_promotion_id`) as `u` "
						+	"     GROUP BY `u`.`order_id` , `u`.`order_item_id`)as order_FBM_transactionPrice ON tmp.order_id = order_FBM_transactionPrice.order_id "
						+	"         AND tmp.order_item_id = order_FBM_transactionPrice.order_item_id "
						+	" GROUP BY tmp.order_id , tmp.order_item_id");
		PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter(filePath) ) );
		pw.println("merchant	order-id	order-item-id	purchase-date	payments-date	reporting-date	promise-date	days-past-promise	buyer-email	buyer-name	buyer-phone-number	sku	product-name	quantity-purchased	quantity-shipped	quantity-to-ship	ship-service-level	recipient-name	ship-address-1	ship-address-2	ship-address-3	ship-city	ship-state	ship-postal-code	ship-country	is-business-order	purchase-order-number	price-designation	Promotion_ID	Principle_Price_Amount");

		while (rs.next()) {
			pw.print(rs.getString(1));
			for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) pw.print("\t" + rs.getString(i));
			pw.println();
		}
		pw.close();
		connection.close();
		return filePath;
	}
	
	
	public void process(String readMessageToOSSClassName, String readMessageToOSSMethodName) throws Exception{
		String filePath = getOrderUnshipped();
		MessageBuffer.getInstance().putMessage(new Message(readMessageToOSSClassName,readMessageToOSSMethodName,new FileData(filePath)));
	}
	
	public void process() throws Exception{
		process(readMessageToOSSClassName,readMessageToOSSMethodName);
	}
	
	public static void main(String[] aaa){
		try {
			System.out.println(new BasicDataOrderUnshipped().getOrderUnshipped());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
