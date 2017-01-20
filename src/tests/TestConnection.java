// @author BIUlitin
// modified by GoAlexander

package tests;

import java.sql.*;

public class TestConnection {

	public static void main(String[] args) {

		// Create a variable for the connection string.
		// TODO edit it!
		String connectionUrl = "jdbc:sqlserver://projectownserver.database.windows.net:1433;database=Project;user=Alexander@projectownserver;password={password here!};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

		// Declare the JDBC objects.
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			// Create and execute an SQL statement that returns some data.
			// String SQL = "SELECT * FROM Abiturient";
			String SQL = "CREATE TABLE Abiturient (aid int primary key, SName text, Fname text, MName text)";
			stmt = con.createStatement();
			// rs = stmt.executeQuery(SQL);
			stmt.executeUpdate(SQL);

			/*
			 * String[] info = { "1", "Smith", "John", "Vladimirovich",
			 * "25-04-1885", "Nevada", "1", "1", "xxx@mail.ru", "5-83-53", "0",
			 * "07-08-2015", "", "0", "0", "0" };
			 * ModelDBConnection.insertAbiturient(info);
			 */
			// Iterate through the data in the result set and display it.
			// while (rs.next()) {
			// System.out.println(rs.getString(1));
			// }
		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}
}
