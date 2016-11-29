package backend;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ModelDBConnection {
	static String login;
	static String password;
	static String serverAddress;
	static String serverType;
	static String dbName;

	static Connection con = null;
	static CallableStatement cstmt = null;
	static ResultSet rset = null;
	static Statement stmt = null;

	public static void setConnectionParameters(String serverType, String serverAddress, String dbName, String login,
			String password) {
		ModelDBConnection.serverType = serverType;
		ModelDBConnection.serverAddress = serverAddress;
		ModelDBConnection.dbName = dbName;
		ModelDBConnection.login = login;
		ModelDBConnection.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public String getServerType() {
		return serverType;
	}

	public String getDBName() {
		return dbName;
	}

	public static boolean initConnection() {
		if (con == null) {
			try {
				String connectionUrl = null;
				switch (serverType) {
				case "AzureServer":
					connectionUrl = "jdbc:sqlserver://" + serverAddress + ":1433;database=" + dbName + ";user=" + login
							+ "@projectownserver;password=" + password
							+ ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
					break;
				case "MSServer":
					connectionUrl = "jdbc:sqlserver://" + serverAddress + ":1433;databaseName=" + dbName + ";user="
							+ login + ";password=" + password + ";";
					break;
				}

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static int getCountAbiturients() {
		if (initConnection()) {
			try {
				int countAbits = 0;
				String query = "select count(*) from Abiturient";
				stmt = con.createStatement();
				rset = stmt.executeQuery(query);

				while (rset.next()) {
					countAbits = rset.getInt(1);
				}

				stmt.close();
				rset.close();

				return countAbits;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}

	public static String[][] getAllAbiturients() {
		int countAbits = getCountAbiturients();

		String[][] data = null;

		if (countAbits > 0) {
			try {
				data = new String[countAbits][4];

				String query = "select aid, SName, Fname, MName from Abiturient order by aid";
				stmt = con.createStatement();
				rset = stmt.executeQuery(query);

				int curPos = 0;
				while (rset.next()) {
					data[curPos][0] = String.valueOf(rset.getInt(1));
					data[curPos][1] = rset.getString(2);
					data[curPos][2] = rset.getString(3);
					data[curPos][3] = rset.getString(4);
				}

				stmt.close();
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return data;
	}

	// TODO test
	public static void insertAbiturient(String[] info) {
		String aid = info[0];
		String SName = "'" + info[1] + "'";
		String FName = "'" + info[2] + "'";
		String MName = "'" + info[3] + "'";
		String birthday = "'" + info[4] + "'";
		String birthplace = "'" + info[5] + "'";
		String id_gender = info[6];
		String id_nationality = info[7];
		String email = "'" + info[8] + "'";
		String phoneNumbers = "'" + info[9] + "'";
		String needHostel = info[10];
		String registrationDate = "'" + info[11] + "'";
		String returnDate = "'" + info[12] + "'";
		String id_returnReason = info[13];
		String needSpecConditions = info[14];
		String is_enrolled = info[15];

		try {
			String query = "insert into Abiturient Values (" + aid + ", " + SName + ", " + FName + ", " + MName + ", "
					+ birthday + ", " + birthplace + ", " + id_gender + ", " + id_nationality + ", " + email + ", "
					+ phoneNumbers + ", " + needHostel + ", " + registrationDate + ", " + returnDate + ", "
					+ id_returnReason + ", " + needSpecConditions + ", " + is_enrolled + ");";
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			stmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO test
	public static void editAbiturient(String[] info) {
		String aid = info[0];
		String SName = "'" + info[1] + "'";
		String FName = "'" + info[2] + "'";
		String MName = "'" + info[3] + "'";
		String Birthday = "'" + info[4] + "'";
		String Birthplace = "'" + info[5] + "'";
		String id_gender = info[6];
		String id_nationality = info[7];
		String email = "'" + info[8] + "'";
		String phoneNumbers = "'" + info[9] + "'";
		String needHostel = info[10];
		String registrationDate = "'" + info[11] + "'";
		String returnDate = "'" + info[12] + "'";
		String id_returnReason = info[13];
		String needSpecConditions = info[14];
		String is_enrolled = info[15];

		try {
			String query = "update Abiturient set SName = " + SName + ", FName = " + FName + ", MName = " + MName
					+ ", Birthday = " + Birthday + ", Birthplace = " + Birthplace + ", id_gender = " + id_gender
					+ ", id_nationality = " + id_nationality + ", email = " + email + ", phoneNumbers = " + phoneNumbers
					+ ", needHostel = " + needHostel + ", registrationDate = " + registrationDate + ", returnDate = "
					+ returnDate + ", id_returnReason = " + id_returnReason + ", needSpecConditions = "
					+ needSpecConditions + ", is_enrolled = " + is_enrolled + " where aid = " + aid + ";";
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			stmt.close();
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
