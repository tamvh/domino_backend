/* global theApp, Highcharts */

(function () {
    'use strict';
    theApp.controller('DashboardController', DashboardController);
    DashboardController.$inject = ['$scope', 'DashboardService', 'LoginService', '$rootScope', '$uibModal', '$timeout', '$location', '$confirm'];
    function DashboardController($scope, DashboardService, LoginService, $rootScope, $uibModal, $timeout, $location, $confirm) {
        
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