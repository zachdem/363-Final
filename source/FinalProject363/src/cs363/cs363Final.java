//@author Zachary DeMaris
package cs363;

import java.sql.*;
import javax.swing.*;

public class cs363Final {

	/**
	 * Inserts record into actor table
	 * 
	 * @param conn Valid database connection firstName: First name of actor
	 *             lastName: Last name of actor
	 */
	private static void insertActor(Connection conn, String firstName, String lastName) {

		if (conn == null || firstName == null || lastName == null)
			throw new NullPointerException();
		try {
			// we want to make sure that all the query and update statements
			// are considered as one unit; both got done or none got done

			conn.setAutoCommit(false);

			PreparedStatement inststmt = conn
					.prepareStatement(" insert into actor (first_name,last_name) values(?,?) ");

			inststmt.setString(1, firstName);
			inststmt.setString(2, lastName);

			System.out.println("Adding actor " + firstName + " " + lastName);
			int rowcount = inststmt.executeUpdate();

			System.out.println("Number of rows updated:" + rowcount);
			inststmt.close();
			// confirm that these are the changes you want to make
			conn.commit();

			conn.setAutoCommit(true);
		} catch (SQLException e) {
		}

	}

	/**
	 * Deletes customer data from all tables
	 * 
	 * @param conn Valid database connection customerID: customer id to be deleted
	 */
	private static void deleteCustomer(Connection conn, String customerID) {

		if (conn == null || customerID == null)
			throw new NullPointerException();
		try {

			conn.setAutoCommit(false);

			// Must do deletes in order to not break constraints
			PreparedStatement paymentDelete = conn.prepareStatement(" delete from payment where customer_id =?");

			PreparedStatement rentalDelete = conn.prepareStatement(" delete from rental where customer_id =?");

			PreparedStatement customerDelete = conn.prepareStatement(" delete from customer where customer_id =?");

			paymentDelete.setString(1, customerID);
			rentalDelete.setString(1, customerID);
			customerDelete.setString(1, customerID);

			// Delete from payment
			System.out.println("Delete customer from payment table, customer_id: " + customerID);
			int rowcount = paymentDelete.executeUpdate();

			System.out.println("Number of rows deleted:" + rowcount);
			paymentDelete.close();

			// Delete from rental
			System.out.println("Delete customer from rental table, customer_id: " + customerID);
			rowcount = rentalDelete.executeUpdate();

			System.out.println("Number of rows deleted:" + rowcount);
			rentalDelete.close();

			// Delete from customer
			System.out.println("Delete customer from customer table, customer_id: " + customerID);
			rowcount = customerDelete.executeUpdate();

			System.out.println("Number of rows deleted:" + rowcount);
			customerDelete.close();

			// confirm that these are the changes you want to make
			conn.commit();

			conn.setAutoCommit(true);
		} catch (SQLException e) {
		}

	}
	
	/**
	 */
	private static void runQuery(PreparedStatement stmt) throws SQLException {
		
		ResultSet rs;
		ResultSetMetaData rsMetaData;
		String toShow;
		
				
		rs=stmt.executeQuery();
		rsMetaData = rs.getMetaData();
		
		toShow = "";
		
		rs=stmt.executeQuery();
		rsMetaData = rs.getMetaData();
		
		while (rs.next()) {
			for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
							
				toShow += rs.getString(i + 1) + ", ";
				
			}
			toShow += "\n";
		}
		JOptionPane.showMessageDialog(null, toShow);
		
	}
	
	
	/**
	 * @throws SQLException 
	 */
	private static void runQ3(Connection conn, String year, String numRows) throws SQLException {
			
			String sqlQuery = "select count(distinct(u.state)) as statenum, GROUP_CONCAT(distinct(u.state)) as states, ht.name \r\n" + 
			           "	from hastags ht, tweets t, users u \r\n" + 
			           "    where ht.tid = t.tid \r\n" + 
			           "    and t.postedUser = u.screen_name \r\n" + 
			           "    and u.state != 'na' \r\n" + 
			           "    and u.state != '' \r\n" + 
			           "    and year(t.createdTime) = ? \r\n" + 
			           "    group by ht.name order by statenum desc limit ? \r\n";
			
			System.out.println(sqlQuery);
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			
			stmt.clearParameters();
			
			stmt.setInt(1, Integer.parseInt(year));
			stmt.setInt(2, Integer.parseInt(numRows));
			
			runQuery(stmt);
	
	}
	
	
	
	/**
	 * @throws SQLException 
	 */
	private static void runQ7(Connection conn, String hashtag, String state, String month, String year, String numRows) throws SQLException {
			
			String sqlQuery = "SELECT count(*) tweet_count, u.screen_name, u.category FROM users u, tweets t, hasTags ht\r\n" + 
					"WHERE u.screen_name = t.postedUser\r\n" + 
					"AND t.tid = ht.tid\r\n" + 
					"AND ht.name = ?\r\n" + 
					"AND u.state = ?\r\n" + 
					"AND MONTH(t.createdTime) = ?\r\n" + 
					"AND YEAR(t.createdTime) = ?\r\n" + 
					"GROUP BY u.screen_name\r\n" + 
					"ORDER BY tweet_count desc\r\n" + 
					"LIMIT ?";
			
			System.out.println(sqlQuery);
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			
			stmt.clearParameters();
			
			stmt.setString(1, hashtag);
			stmt.setString(2, state);
			stmt.setInt(3, Integer.parseInt(month));
			stmt.setInt(4, Integer.parseInt(year));
			stmt.setInt(5, Integer.parseInt(numRows));
			
			runQuery(stmt);
	
	}
	
	
	/**
	 * @throws SQLException 
	 */
	private static void runQ9(Connection conn, String category, String numRows) throws SQLException {
			
			String sqlQuery = "SELECT u.screen_name, u.sub_category, u.numFollowers FROM users u\r\n" + 
					"WHERE u.sub_category = ? \r\n" + 
					"ORDER BY u.num_followers desc\r\n" + 
					"LIMIT ?";
			
			System.out.println(sqlQuery);
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			
			stmt.clearParameters();
			
			stmt.setString(1, category);
			stmt.setInt(2, Integer.parseInt(numRows));
			
			runQuery(stmt);
	
	}
	
	/**
	 * @throws SQLException 
	 */
	private static void runQ16(Connection conn, String month, String year, String numRows) throws SQLException {
			
			String sqlQuery = "select u.name as user_name, u.category, t.textbody as texts, t.retweet_count as retweetCt, url.address \r\n" + 
					"	from tweets t, users u, hasurls url \r\n" + 
					"    where t.postedUser = u.screen_name \r\n" + 
					"    and t.tid = url.tid \r\n" + 
					"    and month(t.createdTime) = ? \r\n" + 
					"    and year(t.createdTime) = ? \r\n" + 
					"    group by t.tid\r\n" + 
					"    order by t.retweet_count desc\r\n" + 
					"    limit ?;";
			
			System.out.println(sqlQuery);
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			
			stmt.clearParameters();
			
			stmt.setInt(1, Integer.parseInt(month));
			stmt.setInt(2, Integer.parseInt(year));
			stmt.setInt(3, Integer.parseInt(numRows));
			
			runQuery(stmt);
	
	}
	
	

	/**
	 * @throws SQLException 
	 */
	private static void runQ18(Connection conn, String month, String year, String category, String numRows) throws SQLException {
			
			String sqlQuery = "SELECT m.mentioned mentionedUser , u2.state mentionedUserState, group_concat(distinct(t.postedUser)) postingUsers\r\n" + 
					"FROM users u, tweets t, mentions m, users u2\r\n" + 
					"WHERE t.postedUser = u.screen_name\r\n" + 
					"AND t.tid = m.tid\r\n" + 
					"AND u2.screen_name = m.mentioned\r\n" + 
					"AND MONTH(t.createdTime) = ? \r\n" + 
					"AND YEAR(t.createdTime) = ? \r\n" + 
					"AND u.sub_category = ? \r\n" + 
					"GROUP BY m.mentioned\r\n" + 
					"ORDER BY count(*) desc\r\n" + 
					"LIMIT ?";
			
			System.out.println(sqlQuery);
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			
			stmt.clearParameters();
			
			stmt.setInt(1, Integer.parseInt(month));
			stmt.setInt(2, Integer.parseInt(year));
			stmt.setString(3, category);
			stmt.setInt(4, Integer.parseInt(numRows));
			
			runQuery(stmt);
	
	}
	
	/**
	 * @throws SQLException 
	 */
	private static void runQ23(Connection conn, String months, String year, String category, String numRows) throws SQLException {
			
			String sqlQuery = "select ht.name, count(ht.tid) as num_uses\r\n" + 
					"	from hastags ht, tweets t, users u \r\n" + 
					"    where ht.tid = t.tid \r\n" + 
					"    and t.postedUser = u.screen_name\r\n" + 
					"    and u.sub_category = ? \r\n" + 
					"    and month(t.createdTime) in ("+ months +") \r\n" + 
					"    and year(t.createdTime) = ? \r\n" + 
					"    group by ht.name order by num_uses desc limit ?;";
			
			System.out.println(sqlQuery);
				
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);

			stmt.clearParameters();

			stmt.setString(1, category);
			stmt.setInt(2, Integer.parseInt(year));
			stmt.setInt(3, Integer.parseInt(numRows));
			
			runQuery(stmt);
	
	}


	public static void main(String[] args) {
		
		
		String testString = "1,2,3";
		
		String[] result = testString.split(",");
		
		System.out.println(result[2]);
		
		String dbServer = "jdbc:mysql://127.0.0.1:3306/group5?useSSL=false";
		// For compliance with existing applications not using SSL the
		// verifyServerCertificate property is set to ‘false’,
		String userName = "root";
		String password = "root";

		Connection conn;
		Statement stmt;


		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbServer, userName, password);
			stmt = conn.createStatement();			

			//Menu options
			String option = "";
			String instruction = "Enter 3: Run Q3" + "\n" + 
								"Enter 7: Run Q7" + "\n" + 
								"Enter 9: Run Q9" + "\n" +
								"Enter 16: Run Q16" + "\n" +
								"Enter 18: Run Q18" + "\n" +
								"Enter 23: Run Q23" + "\n" +
								"Enter e: Quit Program";

			while (true) {
				option = JOptionPane.showInputDialog(instruction);
				if (option.equals("3")) {

					String year = JOptionPane.showInputDialog("Please enter a year (e.g. 2016):");
					String numRows = JOptionPane.showInputDialog("Please enter the number of rows you would like to return:");

					runQ3(conn, year, numRows);


				} else if (option.equalsIgnoreCase("7")) {

					String hashtag = JOptionPane.showInputDialog("Please enter a hashtag:");
					String state = JOptionPane.showInputDialog("Please enter a state (e.g. IA):");
					String month = JOptionPane.showInputDialog("Please enter a month (1,2,3..):");
					String year = JOptionPane.showInputDialog("Please enter a year (e.g. 2016):");
					String numRows = JOptionPane.showInputDialog("Please enter the number of rows you would like to return:");
					
					runQ7(conn, hashtag, state, month, year, numRows);
					
				} else if (option.equals("9")) {

					String category = JOptionPane.showInputDialog("Please enter a category (e.g. GOP):");
					String numRows = JOptionPane.showInputDialog("Please enter the number of rows you would like to return:");
					
					runQ9(conn, category, numRows);
		
				} else if (option.equals("16")) {

					String month = JOptionPane.showInputDialog("Please enter a month (1,2,3..):");
					String year = JOptionPane.showInputDialog("Please enter a year (e.g. 2016):");
					String numRows = JOptionPane.showInputDialog("Please enter the number of rows you would like to return:");
					
					runQ16(conn, month, year, numRows);
		
				} else if (option.equals("18")) {

					String month = JOptionPane.showInputDialog("Please enter a month (1,2,3..):");
					String year = JOptionPane.showInputDialog("Please enter a year (e.g. 2016):");
					String category = JOptionPane.showInputDialog("Please enter a category (e.g. GOP):");
					String numRows = JOptionPane.showInputDialog("Please enter the number of rows you would like to return:");
					
					runQ18(conn, month, year, category, numRows);
		
				} else if (option.equals("23")) {

					String category = JOptionPane.showInputDialog("Please enter a category (e.g. GOP):");
					String months = JOptionPane.showInputDialog("Please enter a comma seperated list of months (e.g. 1,2,3):");
					String year = JOptionPane.showInputDialog("Please enter a year (e.g. 2016):");
					String numRows = JOptionPane.showInputDialog("Please enter the number of rows you would like to return:");
					
					runQ23(conn, months, year, category, numRows);
		
				}
				
				
				else if (option.equals("e")) {
					break;
				}
			}

			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Program terminates due to errors");
			e.printStackTrace(); // for debugging
		}
	}

}
