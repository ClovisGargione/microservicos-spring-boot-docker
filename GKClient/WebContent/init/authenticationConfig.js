/**
 * 
 */
function isAuthenticated($state, $auth, state){
	if ($auth.isAuthenticated()) {
		$state.go(state);
    }else{
    	$state.go('login');
    }
}

function authenticated(state){ return ['$state', '$auth', isAuthenticated]};