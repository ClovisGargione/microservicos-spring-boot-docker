/**
 * 
 */
package com.gk.tenant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import javax.persistence.NoResultException;


/**
 * @author clovis
 *
 */
public class TenantResolver implements  Callable<String> {
	
	private String username;
	
	private final String driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "postgres";
	
    public Connection getConnection() throws ClassNotFoundException{
        System.out.println("Conectando ao banco!");
        Class.forName(driver);
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
            return null;
        }
	

	@Override
	public String call() throws Exception {
		String tenantId = "";
		try {
			Connection connection = getConnection(); 
            System.out.println("Conexão aberta!");
            String sql = "SELECT tenant_id FROM users where username = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            
            stm.setString(1, getUsername());
			
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

            	tenantId = rs.getString("tenant_id");

				System.out.println("tenantId : " + tenantId);

			}
            if (stm != null) {
				stm.close();
			}

			if (connection != null) {
				connection.close();
			}
            System.out.println("Conexão Fechada!");
		
			
			
			return tenantId;
		}catch(NoResultException e) {
			return "public";
		}
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
