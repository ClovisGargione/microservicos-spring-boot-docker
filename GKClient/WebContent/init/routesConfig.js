/**
 * 
 */
var namespace = 'gk.app';

function routesConfig($stateProvider, $urlRouterProvider){
	
	 $stateProvider
     .state('login', {
         url: '/login',
         templateUrl: 'modules/login/views/login.html',
         controller: 'loginController',
         controllerAs:'vm',
         resolve: {
             authenticated: authenticated('home.dashboard')
         }
     })
     .state(
             'home', {
                 abstract: true,
                 url: '/home',
                 templateUrl: 'shared/views/index.html',
                 resolve: {
                     authenticated: authenticated('home.dashboard')
                 }
             })
     .state(
             'home.dashboard', {
                 url: '/dashboard',
                 templateUrl: 'modules/home/views/home.html',
                 controller: 'homeController',
                 controllerAs: 'vm',
                 resolve: {
                     loadModule: ['$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                             var deferred = $q.defer();
                             require(["home-module"], function () {
                                 $ocLazyLoad.inject(namespace + '.home');
                                 deferred.resolve();
                             });
                             return deferred.promise;
                         }]
                 }
             })
     .state(
         'usuarios', {
             abstract: true,
             url: '/usuarios',
             templateUrl: 'shared/views/index.html',
             resolve: {
                 authenticated: authenticated('usuarios.cadastro')
             }
         })
      .state(
             'usuarios.cadastro', {
                 url: '/cadastro',
                 templateUrl: 'modules/usuarios/views/usuarios.html',
                 controller: 'usuariosController',
                 controllerAs: 'vm',
                 resolve: {
                     loadModule: ['$ocLazyLoad', '$q', function ($ocLazyLoad, $q) {
                             var deferred = $q.defer();
                             require(["usuarios-module"], function () {
                                 $ocLazyLoad.inject(namespace + '.usuarios');
                                 deferred.resolve();
                             });
                             return deferred.promise;
                         }] 
                 }
             });
	 $urlRouterProvider.otherwise('/login');
}
var routesConfigDependencies = ['$stateProvider', '$urlRouterProvider', routesConfig];


