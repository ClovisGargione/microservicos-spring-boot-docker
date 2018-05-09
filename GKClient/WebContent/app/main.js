/**
 * 
 */
requirejs.config({
    baseUrl: './',
    waitSeconds: 0,
    urlArgs: "bust=" + (new Date()).getTime(),
    paths: {
        'jquery': 'assets/libs/jquery/dist/jquery.min',
        'popper': 'assets/libs/popper.js/dist/umd/popper.min',
        'bootstrap': 'assets/libs/bootstrap/dist/js/bootstrap.bundle.min',
        'angular': 'assets/libs/angular/angular',
        'angular-ui-router': 'assets/libs/angular-ui-router/release/angular-ui-router.min',
        'ocLazyLoad': 'assets/libs/oclazyload/dist/ocLazyLoad.min',
        'satellizer': 'assets/libs/satellizer/dist/satellizer.min',
        'angular-i18n': 'assets/libs/angular-i18n/angular-locale_pt-br',
        'angular-animate':'assets/libs/angular-animate/angular-animate.min',
        'angular-aria': 'assets/libs/angular-aria/angular-aria.min',
        'angular-messages': 'assets/libs/angular-messages/angular-messages.min',
        'angular-cookies': 'assets/libs/angular-cookies/angular-cookies.min',
        'home-module': 'modules/home/module',
        'usuarios-module': 'modules/usuarios/module'
    },
    shim: {
        jquery: {
            exports: 'jquery',
        },
        'popper': {
            deps: ['jquery']
        },
        'bootstrap': {
            deps: ['jquery']
        },
        angular: {
            exports: 'angular'
        },
        'angular-ui-router': {
            deps: ['angular']
        },
        'ocLazyLoad': {
            deps: ['angular']
        },
        'satellizer': {
            deps: ['angular']
        },
        
        'angular-animate':{
            deps: ['angular']  
        },
        'angular-i18n': {
        	deps: ['angular']  
        },
        'angular-aria': {
        	deps: ['angular']  
        },
        'angular-messages': {
        	deps: ['angular']  
        },
        'angular-cookies' : {
        	deps: ['angular']
        },
        'home-module': {
            exports: 'home-module'
        },
        'usuarios-module': {
            exports: 'usuarios-module'
        }
        	
    }
});

require(['app/app'], function (app) {
    app.init();
});