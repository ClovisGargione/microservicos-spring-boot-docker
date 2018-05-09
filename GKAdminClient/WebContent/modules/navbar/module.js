/**
 * 
 */
define([ 'shared/namespace', 'angular',
		'modules/navbar/services/navbarService',
		'modules/navbar/controller/navbarController' ], function(namespace,
		angular, navbarService, navbarController) {
	'use strict';

	angular.module(namespace + '.navbar', [ "oc.lazyLoad" ]).service(
			'navbarService', navbarService).controller('navbarController',
			navbarController);

});