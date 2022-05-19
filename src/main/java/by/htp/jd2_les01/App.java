//package by.htp.jd2_les01;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class App {
//	public static void main(String[] args) throws ClassNotFoundException {
//		Class.forName("com.mysql.cj.jdbc.Driver");
//
//		Connection con = null;
//		Statement st = null;
//		ResultSet rs = null;
//
//		try {
//			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/TEST1.1?useSSL=false", "root",
//					"Vfvf2183102");
//
//			System.out.println("OK");
//
//			st = con.createStatement();
//
//			//rs = st.executeQuery("SELECT * FROM users");
//			rs = st.executeQuery("SELECT * from shops");
//			while (rs.next()) {
//				//System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
//				System.out.println(rs.getString("name")+" "+rs.getString(3));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//			try {
//				if (st != null) {
//					st.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//			try {
//				if (con != null) {
//					con.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//}
