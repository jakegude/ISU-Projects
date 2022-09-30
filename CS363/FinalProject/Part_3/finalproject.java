/*
 * @author Jake Gudenkauf
 */

package cs363;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class finalproject {
	
	public static void main(String[] args) {
		String dbServer = "jdbc:mysql://localhost:3306/group31?useSSL=false";
		String userName = "cs363";
		String password = "363F2020";
		Connection conn;
		Statement stmt;
		Scanner s;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbServer, userName, password);
			stmt = conn.createStatement();
			String option = "";
			String instruction = "Enter A: Insert new user\n" + "Enter B: Delete user\n" + "Enter C: Run Query 3\n" 
					+ "Enter D: Run Query 7\n" + "Enter E: Run Query 9\n" + "Enter F: Run Query 16\n" 
					+ "Enter G: Run Query 18\n" + "Enter H: Run Query 23\n" + "Enter X: Exit The Program\n";
			s = new Scanner(System.in);
			
			while (true) {
				//print options for the user
				System.out.println(instruction);
				// determine what option user would like
				option = s.next();
				if (option.equals("A") || option.equals("a")) 
				{
					//ask/get input from user
					System.out.println("Please enter new user's screen name\n");
					String screenname = s.next();
					System.out.println("Please enter new user's name\n");
					String name = s.next();
					System.out.println("Please enter new user's sub category\n");
					String subcat = s.next();
					System.out.println("Please enter new user's category\n");
					String cat = s.next();
					System.out.println("Please enter new user's state (as abbreviation)\n");
					String ofstate = s.next();
					System.out.println("Please enter new user's number of followers\n");
					String numfollowers = s.next();
					System.out.println("Please enter new user's number of following\n");
					String numfollowing = s.next();
					String user[] = new String[7];
					user[0] = screenname;
					user[1] = name;
					user[2] = subcat;
					user[3] = cat;
					user[4] = ofstate;
					user[5] = numfollowers;
					user[6] = numfollowing;
					//insert the user
					insertUser(conn, user);
				} 
				else if (option.equals("B") || option.equals("b")) 
				{
					//ask/get input from user
					System.out.println("Please enter user screen name to delete\n");
					String screenname = s.next();
					//delete user
					deleteUser(conn, screenname);
				} 
				else if (option.equals("C") || option.equals("c")) 
				{
					//ask/get input from user
					System.out.println("Please enter value of k: ");
					int k = s.nextInt();
					//create query
					String query = "select count(distinct u.ofstate) as num_states, h.hastagname, group_concat(distinct u.ofstate) as states\n"
							+ "from user u, tagged h, tweets t\n"
							+ "where h.tid = t.tid and t.posting_user = u.screen_name and year(t.posted) = 2016\n"
							+ "group by h.hastagname order by num_states desc limit ?;";
					PreparedStatement q3 = conn.prepareStatement(query);
					q3.setInt(1, k);
					//run statement
					String ret = q3.toString().substring(43, q3.toString().length());
					runQuery(stmt, ret);
				} 
				else if (option.equals("D") || option.equals("d")) 
				{
					//get input from user
					System.out.println("Please enter value of k: ");
					int k = s.nextInt();
					System.out.println("Please enter value for hashtag: ");
					String tag = s.next();
					System.out.println("Please enter state abbreviation: ");
					String state = s.next();
					System.out.println("Please enter month (number): ");
					int month = s.nextInt();
					//create query
					String query = "select count(t.tid) as tweet_count, u.screen_name, u.category from tagged h, user u, tweets t\n"
							+ "where h.tid = t.tid and t.posting_user = u.screen_name and h.hastagname = ? and u.ofstate = ? and month(t.posted) = ? and year(t.posted) = 2016\n"
							+ "group by u.screen_name order by count(t.tid) desc limit ?; ";
					PreparedStatement q7 = conn.prepareStatement(query);
					q7.setString(1, tag);
					q7.setString(2, state);
					q7.setInt(3, month);
					q7.setInt(4, k);
					//run statement
					String ret = q7.toString().substring(43, q7.toString().length());
					runQuery(stmt, ret);
				} 
				else if (option.equals("E") || option.equals("e")) 
				{
					//get input from user
					System.out.println("Please enter value of k: ");
					int k = s.nextInt();
					System.out.println("Please enter value for sub category: ");
					String subcat = s.next();
					String query = "select u.screen_name, u.sub_category, u.numFollowers\n"
							+ "from user u where u.sub_category = ? order by u.numFollowers desc limit ?;";
					PreparedStatement q9 = conn.prepareStatement(query);
					q9.setString(1, subcat);
					q9.setInt(2, k);
					//run statement
					String ret = q9.toString().substring(43, q9.toString().length());
					runQuery(stmt, ret);
				} 
				else if (option.equals("F") || option.equals("f")) 
				{
					//get input from user
					System.out.println("Please enter value of k: ");
					int k = s.nextInt();
					System.out.println("Please enter value for month (number): ");
					int month = s.nextInt();
					String query = "select u.screen_name, u.category, t.textbody, t.retweet_count, url.url from user u, tweets t, urlused url\n"
							+ "where t.tid = url.tid and t.posting_user = u.screen_name and month(t.posted) = ? and year(t.posted) = 2016\n"
							+ "order by t.retweet_count desc limit ?;";
					PreparedStatement q16 = conn.prepareStatement(query);
					q16.setInt(1, month);
					q16.setInt(2, k);
					//run statement
					String ret = q16.toString().substring(43, q16.toString().length());
					runQuery(stmt, ret);
				} 
				else if (option.equals("G") || option.equals("g")) 
				{
					//get input from user
					System.out.println("Please enter value of k: ");
					int k = s.nextInt();
					System.out.println("Please enter value for sub category: ");
					String subcat = s.next();
					System.out.println("Please enter value for month (number): ");
					int month = s.nextInt();
					String query = "select m.screen_name as user_mentioned, u1.ofstate as user_mentioned_state, group_concat(distinct u2.screen_name) as posting_user\n"
							+ "from user u1, user u2, tweets t, mentioned m\n"
							+ "where u2.sub_category = ? and t.posting_user = u2.screen_name and u1.screen_name = m.screen_name and t.tid = m.tid and month(t.posted) = ? and year(t.posted) = 2016\n"
							+ "group by m.screen_name order by count(m.tid) desc limit ?;";
					PreparedStatement q18 = conn.prepareStatement(query);
					q18.setString(1, subcat);
					q18.setInt(2, month);
					q18.setInt(3, k);
					//run statement
					String ret = q18.toString().substring(43, q18.toString().length());
					runQuery(stmt, ret);
				} 
				else if (option.equals("H") || option.equals("h")) 
				{
					//get input from user
					System.out.println("Please enter value of k: ");
					int k = s.nextInt();
					System.out.println("Please enter value for sub category: ");
					String subcat = s.next();
					System.out.println("Please enter values of months: (separated by commas)");
					String months = s.next();
					String query = "select h.hastagname, count(distinct h.tid) from tagged h, user u, tweets t\n"
							+ "where t.posting_user = u.screen_name and u.sub_category = ? and h.tid = t.tid and "
							+ "find_in_set(month(t.posted), \"" +months +"\" ) and year(t.posted) = 2016\n"
							+ "group by h.hastagname order by count(h.tid) desc limit ?;";
					PreparedStatement q23 = conn.prepareStatement(query);
					q23.setString(1, subcat);
					q23.setInt(2, k);
					//run statement
					String ret = q23.toString().substring(43, q23.toString().length());
					runQuery(stmt, ret);
				} 
				else {
					//exiting program
					System.out.println("Exiting program\n");
					break;
				}
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			//error in connection or program found
			System.out.println("Program terminates due to errors");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param user, each index is a different attribute of the user
	 * @param sqlQuery
	 * @throws SQLException
	 */
	public static void insertUser(Connection connection, String[] user) {
		//if either parameter is null, throw null pointer exception
		if (connection == null || user == null) {
			throw new NullPointerException();
		} else if (user[0] == null || user[1] == null || user[2] == null || user[3] == null || user[4] == null || user[5] == null || user[6] == null) {
			throw new NullPointerException();
		} else {
			try {
				connection.setAutoCommit(false);
				Statement stmt = connection.createStatement();
				
				//prepare statement to insert actor
				PreparedStatement inststmt = 
						connection.prepareStatement("insert into user (screen_name, name, sub_category, category, ofstate, numFollowers, numFollowing) "
								+ "values(?,?,?,?,?,?,?)");
				
				//set values in statement
				inststmt.clearParameters();
				inststmt.setString(2, user[0]);
				inststmt.setString(3, user[1]);
				inststmt.setString(4, user[2]);
				inststmt.setString(4, user[3]);
				inststmt.setString(4, user[4]);
				inststmt.setString(4, user[5]);
				inststmt.setString(4, user[6]);


				//find and output number of rows updated
				int rowcount = inststmt.executeUpdate();
				System.out.println("Number of rows updated:" + rowcount);
				
				//close statement and commit changes
				inststmt.close();
				connection.commit();
			} catch(SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param cID, is the customer ID of which will be deleted
	 * @param sqlQuery
	 * @throws SQLException
	 */
	public static void deleteUser(Connection connection, String screenname) {
		//if either parameter is null, throw null pointer exception
		if (connection == null || screenname == null) {
			throw new NullPointerException();
		} else {
			try {
				connection.setAutoCommit(false);
				Scanner s = new Scanner(System.in);
				
				//prepare statements
				PreparedStatement userDelete = 
						connection.prepareStatement("delete from user where screen_name = ?");
				userDelete.setString(1, screenname);
				PreparedStatement tweetDelete = 
						connection.prepareStatement("delete from tweets where posting_user = ?");
				tweetDelete.setString(1, screenname);
				PreparedStatement mentionedDelete = 
						connection.prepareStatement("delete from mentioned where screen_name = ?");
				mentionedDelete.setString(1, screenname);
				
				System.out.println("Are you sure you want to delete user: " + screenname + "?");
				System.out.println("Enter 'y' for yes or 'n' for no");
				String option = s.next();
				
				if (option.equals("y")) {
					//find and output number of rows updated
					int countuser = userDelete.executeUpdate();
					int counttweet = tweetDelete.executeUpdate();
					int countmentioned = mentionedDelete.executeUpdate();
					System.out.println("Number of rows updated from user table:" + countuser);
					System.out.println("Number of rows updated from tweet table:" + counttweet);
					System.out.println("Number of rows updated from mentioned table:" + countmentioned);
				
					//close statement and commit changes
					userDelete.close();
					tweetDelete.close();
					mentionedDelete.close();
					connection.commit();		
				} else {
					System.out.println("User " + screenname + " not deleted");
				}
				s.close();
				
			} catch(SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

	/**
	 * @see  "JDBCTransactionTester.java" in class Module 8
	 * @param stmt  
	 *   @param sqlQuery
	 *   @throws SQLException  
	 */ 
	private static void runQuery(Statement stmt, String sqlQuery) throws SQLException { 
		String toShow = ""; 
		ResultSet rs = stmt.executeQuery(sqlQuery); 
		ResultSetMetaData rsMetaData = rs.getMetaData(); 
		//System.out.println(sqlQuery); 
		while (rs.next()) { 
			for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
				toShow += rs.getString(i + 1) + ", "; } toShow += "\n"; 
			} 
			System.out.println(toShow); 
		} 
	
}














