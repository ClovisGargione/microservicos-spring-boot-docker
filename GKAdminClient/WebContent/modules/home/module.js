/**
 * 
 */
define(
        ['shared/namespace', 'angular', 'modules/home/services/homeService',
            'modules/home/controller/homeController', 'modules/home/controller/homeEditController'
        ],
        function (namespace, angular, homeService, homeController, homeEditController) {
            'use strict';

            angular
                    .module(
                            namespace + '.home', ['ui.router',
                                'satellizer',
                                'oc.lazyLoad'
                            ])
                    .controller('homeController', homeController)
                    .controller('homeEditController', homeEditController)
                    .service('homeService', homeService);
        });