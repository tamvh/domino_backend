/* global theApp, Highcharts */

(function () {
    'use strict';
    theApp.controller('TestController', TestController);
    TestController.$inject = ['$scope', 'TestService', 'LoginService', '$rootScope', '$uibModal', '$timeout', '$location', '$confirm'];
    function TestController($scope, TestService, LoginService, $rootScope, $uibModal, $timeout, $location, $confirm) {
        if (!LoginService.isLogined()) {
            $location.path("/login");
            return;
        }
        
        $scope.init = function () {
            console.log('init');
        };
        $scope.init();
    }
})();