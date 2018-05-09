/**
 * 
 */
var dependencies = ['shared/namespace', 
					'angular', 
					'shared/vendor', 
					'init/authenticationConfig', 
					'init/routesConfig',
					'init/constantsConfig',
					'init/authProviderConfig',
					'modules/login/module', 
					'modules/navbar/module'];

function modules(namespace){
	return ['ui.router','ngCookies', 'satellizer', 'oc.lazyLoad', 'ngAnimate', '720kb.datepicker' ,namespace + '.login', namespace + '.navbar']
};

function angularConfig(namespace, angular){
	  'use strict';
    var app = angular.module(namespace, modules(namespace));


    app.config(routesConfigDependencies);
    app.config(authProvider);
    app.constant('CONSTANT', (constant)());

    /**
	 * Inits the app.
	 */
    app.init = function () {
        angular.element(document).ready(function () {
            angular.bootstrap(document, [namespace]);
        });
    };

    return app;
}

define(dependencies, angularConfig);