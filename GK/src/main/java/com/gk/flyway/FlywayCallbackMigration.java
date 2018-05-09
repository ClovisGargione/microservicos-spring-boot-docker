/**
 * 
 */
package com.gk.flyway;

import java.sql.Connection;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author clovis
 *
 */
public class FlywayCallbackMigration implements FlywayCallback {

	@Autowired
	Flyway flyway;
	
	
	
	 @Override
	    public void afterMigrate(Connection connection) {
		 		
	    }

	@Override
	public void afterBaseline(Connection arg0) {
	
		
	}

	@Override
	public void afterClean(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterEachMigrate(Connection arg0, MigrationInfo arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterEachUndo(Connection arg0, MigrationInfo arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInfo(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRepair(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUndo(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterValidate(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeBaseline(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeClean(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeEachMigrate(Connection arg0, MigrationInfo arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeEachUndo(Connection arg0, MigrationInfo arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInfo(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeMigrate(Connection arg0) {
		
 		
          System.out.println("callback");
		
	}

	@Override
	public void beforeRepair(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeUndo(Connection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeValidate(Connection arg0) {
		// TODO Auto-generated method stub
		
	}
}
