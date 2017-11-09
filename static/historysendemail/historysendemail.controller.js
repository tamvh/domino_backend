/* global theApp */

(function () {
    'use strict';
    theApp.controller('HistorySendEmailController', HistorySendEmailController);
    HistorySendEmailController.$inject = ['$scope', 'HistorySendEmailService', 'LoginService', '$rootScope', '$uibModal', '$timeout', '$location', '$confirm'];
    function HistorySendEmailController($scope, HistorySendEmailService, LoginService, $rootScope, $uibModal, $timeout, $location, $confirm) {
        if (!LoginService.isLogined()) {
            $location.path("/login");
            return;
        }
        $rootScope.listhistorysendemail = [];
        $scope.init = function () {
            console.log('init');
            HistorySendEmailService.get_list_history_send_email()
                    .then(function (response) {
                        if (response.err === 0) {
                            $rootScope.listhistorysendemail = response.dt;
                        } else {
                            console.log("error get list history send email");
                        }
                    });
        };
        $scope.init();
    }
})();