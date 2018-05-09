/**
 * 
 */

function authProvider($authProvider){
	  $authProvider.logoutRedirect = '/login';
      $authProvider.loginUrl = 'http://localhost:8080/auth';
}


