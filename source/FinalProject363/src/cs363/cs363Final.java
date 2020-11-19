//@authors Zachary DeMaris, Andrew Koenen
package cs363;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class cs363Final {


	/**
	 * This function generically executes a provided query and returns the results. Commas separate different columns.
	 * 
	 * @param stmt - SQL to execute
	 * @throws SQLException
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
	 * @param conn - database connection
	 * @param year - year to look for
	 * @param numRows - number of rows to return
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
	 * @param conn - database connection
	 * @param hashtag - hashtag to look for
	 * @param state - state to look for
	 * @param month - month to look for
	 * @param year - year to look for
	 * @param numRows - number of rows to return
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
	 * @param conn - database connection
	 * @param category
	 * @param numRows - number of rows to return
	 * @throws SQLException
	 */
	private static void runQ9(Connection conn, String category, String numRows) throws SQLException {
			
			String sqlQuery = "SELECT u.screen_name, u.sub_category, u.num_followers numFollowers FROM users u\r\n" + 
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
	 * @param conn - database connection
	 * @param month - month to look for
	 * @param year - year to look for
	 * @param numRows - number of rows to return
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
	 * @param conn - database connection
	 * @param month - month to look for
	 * @param year - year to look for
	 * @param category
	 * @param numRows - number of rows to return
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
	 * @param conn - database connection
	 * @param months - comma separated list of months (e.g. 1,2,3)
	 * @param year - year to look for
	 * @param category
	 * @param numRows - number of rows to return
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
	

	
	/**
	 * Inserts a new user
	 * 
	 * @param conn - database connection
	 * @param screenName - screen name of user
	 * @param name - name of user
	 * @param numFollowers - number of followers
	 * @param numFollowing - number of users following
	 * @param category
	 * @param subCategory
	 * @param state
	 * @throws SQLException
	 */
	private static void insert(Connection conn, String screenName, String name, String numFollowers, String numFollowing, String category, String subCategory, String state) throws SQLException {

			conn.setAutoCommit(false);

			PreparedStatement inststmt = conn
					.prepareStatement("insert into users (screen_name,name,num_followers,num_following,category,sub_category,state) \r\n" + 
							"values(?, ?, ?, ?, ?, ?, ?)");
			
			inststmt.setString(1, screenName);
			inststmt.setString(2, name);
			inststmt.setInt(3, Integer.parseInt(numFollowers));
			inststmt.setInt(4, Integer.parseInt(numFollowing));
			inststmt.setString(5, category);
			inststmt.setString(6, subCategory);
			inststmt.setString(7, state);
			
			int rowcount = inststmt.executeUpdate();

			System.out.println("Number of rows updated:" + rowcount);
			inststmt.close();
			// confirm that these are the changes you want to make
			conn.commit();

			conn.setAutoCommit(true);


	}
	

	/**
	 * 
	 * Deletes a user with the specified screen name
	 * 
	 * @param conn - database connection
	 * @param screenName - screen name of user
	 * @throws SQLException
	 */
	private static void delete(Connection conn, String screenName) throws SQLException {

			conn.setAutoCommit(false);

			PreparedStatement userDelete = conn.prepareStatement("DELETE FROM users \r\n" + 
																	"WHERE screen_name = ?");

			userDelete.setString(1, screenName);
	

			int rowcount = userDelete.executeUpdate();

			System.out.println("Number of rows deleted:" + rowcount);
			userDelete.close();;

			conn.commit();

			conn.setAutoCommit(true);

	}
	
	/**
	 * @return returns an array of username, password
	 */
	public static String[] loginDialog() {
		String result[] = new String[2];
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		JLabel lbUsername = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername, cs);

		JTextField tfUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername, cs);

		JLabel lbPassword = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword, cs);

		JPasswordField pfPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		String[] options = new String[] { "OK", "Cancel" };
		int ioption = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.OK_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (ioption == 0) // pressing OK button
		{
			result[0] = tfUsername.getText();
			result[1] = new String(pfPassword.getPassword());
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String dbServer = "jdbc:mysql://127.0.0.1:3306/group5?useSSL=false";

		
		
		String userName = "";
		String password = "";

		String result[] = loginDialog();
		userName = result[0];
		password = result[1];
		

		
		if (result[0]==null || result[1]==null) {
			System.out.println("Terminating: No username nor password is given");
			return;
		}
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
								"Enter i: Insert new user" + "\n" +
								"Enter d: Delete a user" + "\n" +
								"Enter e: Quit Program";

			while (true) {
				//Continually loop until a menu option is chosen
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
		
				} else if (option.equals("i")) {
					
					String screenName = JOptionPane.showInputDialog("Please enter a screen_name:");
					String name = JOptionPane.showInputDialog("Please enter a name:");
					String numFollowers = JOptionPane.showInputDialog("Specify the number of followers for this user:");
					String numFollowing = JOptionPane.showInputDialog("Specify the number of users this user follows");
					String category = JOptionPane.showInputDialog("Please enter a category:");
					String subCategory = JOptionPane.showInputDialog("Please enter a sub-category:");
					String state = JOptionPane.showInputDialog("Please enter a state (e.g. IA):");

					insert(conn, screenName, name, numFollowers, numFollowing, category, subCategory, state);
		
				} else if (option.equals("d")) {

					String screenName = JOptionPane.showInputDialog("Please enter a screen_name:");
					
					delete(conn, screenName);
		
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
