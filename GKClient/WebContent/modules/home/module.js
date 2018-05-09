/**
 * 
 */
define(
        ['shared/namespace', 'angular', 'modules/home/services/homeService',
            'modules/home/controller/homeController'
        ],
        function (namespace, angular, homeService, homeController) {
            'use strict';

            angular
                    .module(
                            namespace + '.home', ['ui.router',
                                'satellizer',
                                'oc.lazyLoad'
                            ])
                    .controller('homeController', homeController)
                    .service('homeService', homeService);
        });