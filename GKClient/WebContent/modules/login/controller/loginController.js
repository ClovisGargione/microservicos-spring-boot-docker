/**
 * 
 */
define([], function () {
    'use strict';

    var loginController = function (loginService, $state, $rootScope, $cookies) {
    	var UNAUTHORIZED = 401;
    	var iconDefault = "fa fa-sign-in";
    	var iconLoading = "fa fa-spinner fa-pulse";
    	var vm = this;
    	vm.obj = angular.isDefined($cookies.getObject('login')) ? $cookies.getObject('login') : {};
    	vm.objM = vm.obj;
    	vm.login = login;
    	vm.errorMessage = {message: "", show: false};
    	vm.icon = iconDefault;
    	
    	$rootScope.isLoginPage = true;
    	
    	function defineIcon(icon){
    		vm.icon = icon;
    	}
    	
    	function hideMessage(){
    		vm.errorMessage.show = false;
    	}
    	
    	
    	function prepareFormToLogin(icon){
    		defineIcon(icon);
    		hideMessage();
    	}
    	
        function login(login_) {
        	prepareFormToLogin(iconLoading);
        	if(login_.remember){
                $cookies.putObject('login', login_);
            }else{
                $cookies.remove('login');
            }
            loginService.login(login_)
                    .then(function (response) {
                    	$rootScope.isLoginPage = false;
                        $state.go('home.dashboard');
                    })
                    .catch(function (error, status) {
                    	verifyErro(error.status);
                    })
            		.finally(function(){
            			defineIcon(iconDefault);
            		});
        }
    
    
	    function defineErrorMessage(text){
	    	vm.errorMessage.message = text;
	    	vm.errorMessage.show = true;
	    }
	    
	    
	    function verifyErro(status){
	    	switch(status){
	    	case UNAUTHORIZED: 
	    		defineErrorMessage("Não foi possível validar suas crendenciais.");
	    		break;
			default: 
				defineErrorMessage("Não foi possível realizar o login. Tente novamente mais tarde.");
	    	}
	    	
	    }
    
    };

    loginController.$inject = ['loginService', '$state', '$rootScope', '$cookies'];
    return loginController;

});