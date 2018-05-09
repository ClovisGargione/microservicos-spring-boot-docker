/**
 * 
 */
define(['shared/namespace', 'angular', 'modules/login/services/loginService',
    'modules/login/controller/loginController'
], function (namespace, angular,
        loginService, loginController) {
    'use strict';

    angular.module(
            namespace + '.login', ["ui.router", "satellizer"])
            .controller('loginController',loginController)
            .service('loginService', loginService);
});