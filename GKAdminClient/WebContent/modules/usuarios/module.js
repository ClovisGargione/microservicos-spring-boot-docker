/**
 * 
 */
define(
        ['shared/namespace', 'angular', 'modules/usuarios/services/usuariosService',
            'modules/usuarios/controller/usuariosController'
        ],
        function (namespace, angular, usuariosService, usuariosController) {
            'use strict';

            angular
                    .module(
                            namespace + '.usuarios', ['ui.router',
                                'satellizer',
                                'oc.lazyLoad'
                            ])
                    .controller('usuariosController', usuariosController)
                    .service('usuariosService', usuariosService);
        });