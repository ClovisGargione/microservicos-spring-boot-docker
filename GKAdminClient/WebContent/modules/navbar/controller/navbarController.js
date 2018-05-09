/**
 * 
 */
define(['angular'], function (angular) {
    'use strict';

    function navbarController($auth, $state, $rootScope, $timeout) {
        $rootScope.isLoginPage = false;
        var payload = $auth.getPayload();
        var vm = this;
        vm.name = payload.sub;
        vm.logout = logout;
        vm.menuActive = 'menu-1';
        vm.isActive = isActive;
        vm.setActive = setActive;
        
        function logout() {
            $auth.logout().then(function () {
            	$state.go('login');
            });
        };
        
        function setActive(id){
        	vm.menuActive = id;
        }
        
        function isActive(id){
        	return vm.menuActive == id;
        }
      
        $timeout(function(){
        	/* off-canvas sidebar toggle */
            $('[data-toggle=offcanvas]').click(function() {
                $('.row-offcanvas').toggleClass('active');
                $('span.collapse').toggleClass('in');
            });

            $('[data-toggle=offcanvas-in]').click(function() {
                $('.row-offcanvas').addClass('active');
                $('span.collapse').addClass('in');
            });
        	
        }, 300);
    }
    navbarController.$inject = ['$auth', '$state', '$rootScope', '$timeout'];
    return navbarController;

});