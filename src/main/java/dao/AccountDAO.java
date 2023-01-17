package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Account;
import util.GenerateHashedPw;
import util.GenerateSalt;

public class AccountDAO {

	private static Connection getConnection() throws URISyntaxException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
	public static int registerAccount(Account account) {
		String sql = "INSERT INTO account VALUES(default, ?, ?, ?, ?, current_timestamp)";
		int result = 0;
		
		// ランダムなソルトの取得(今回は32桁で実装)
		String salt = GenerateSalt.getSalt(32);
		
		// 取得したソルトを使って平文PWをハッシュ
		String hashedPw = GenerateHashedPw.getSafetyPassword(account.getPassword(), salt);
		
		System.out.println("ログイン時のソルト：" + salt);
		System.out.println("ログイン時のハッシュパスワード：" + hashedPw);
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, account.getName());
			pstmt.setString(2, account.getMail());
			pstmt.setString(3, salt);
			pstmt.setString(4, hashedPw);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	
	public static int registerAccount2(Account account) {
		String sql = "INSERT INTO account2 VALUES(default, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
		int result = 0;
		
		// ランダムなソルトの取得(今回は32桁で実装)
		String salt = GenerateSalt.getSalt(32);
		
		// 取得したソルトを使って平文PWをハッシュ
		String hashedPw = GenerateHashedPw.getSafetyPassword(account.getPassword(), salt);
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, account.getName());
			pstmt.setString(2, account.getMail());
			pstmt.setInt(3, account.getAge());
			pstmt.setString(4, account.getGender());
			pstmt.setInt(5, account.getTel());
			pstmt.setString(6, salt);
			pstmt.setString(7, hashedPw);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	
	// メールアドレスを元にソルトを取得
		public static String getSalt(String mail) {
			String sql = "SELECT salt FROM account WHERE mail = ?";
			
			try (
					Connection con = getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					){
				pstmt.setString(1, mail);

				try (ResultSet rs = pstmt.executeQuery()){
					
					if(rs.next()) {
						String salt = rs.getString("salt");
						return salt;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		// ログイン処理
		public static Account login(String mail, String hashedPw) {
			String sql = "SELECT * FROM account WHERE mail = ? AND password = ?";
			
			try (
					Connection con = getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					){
				pstmt.setString(1, mail);
				pstmt.setString(2, hashedPw);

				try (ResultSet rs = pstmt.executeQuery()){
					
					if(rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String salt = rs.getString("salt");
						String createdAt = rs.getString("created_at");
						
						return new Account(id, name, mail, salt, null, null);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
// 一覧表示
	public static List<Account> selectAllAccount2(){
		String sql = "SELECT id, name, mail, age, gender, tel FROM account2";
		List<Account> result = new ArrayList<>();
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			try (ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String mail = rs.getString("mail");
					int age = rs.getInt("age");
					String gender = rs.getString("gender");
					int tel = rs.getInt("tel");
					Account account = new Account(id, name, mail, age, gender, tel, null, null, null);
					result.add(account);
				}
			}
		} catch (SQLException e) {
		e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
		}
		
// アカウント削除 途中
	public static int deleteAccount(String mail) {
		String sql = "DELETE FROM account2 WHERE mail = ?";
		int result = 0;
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, mail);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}
	
	// メールアドレスを元にソルトを取得
			public static String getSalt2(String mail) {
				String sql = "SELECT salt FROM account2 WHERE mail = ?";
				
				try (
						Connection con = getConnection();
						PreparedStatement pstmt = con.prepareStatement(sql);
						){
					pstmt.setString(1, mail);

					try (ResultSet rs = pstmt.executeQuery()){
						
						if(rs.next()) {
							String salt = rs.getString("salt");
							return salt;
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			// ログイン処理
			public static Account login2(String mail, String hashedPw) {
				String sql = "SELECT * FROM account2 WHERE mail = ? AND password = ?";
				
				try (
						Connection con = getConnection();
						PreparedStatement pstmt = con.prepareStatement(sql);
						){
					pstmt.setString(1, mail);
					pstmt.setString(2, hashedPw);

					try (ResultSet rs = pstmt.executeQuery()){
						
						if(rs.next()) {
							int id = rs.getInt("id");
							String name = rs.getString("name");
							String salt = rs.getString("salt");
							String createdAt = rs.getString("created_at");
							
							return new Account(id, name, mail, salt, null, null);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				return null;
			}
}
