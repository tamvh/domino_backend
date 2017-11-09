/* global theApp, Highcharts */

(function () {
    'use strict';
    theApp.controller('ConfigEmailController', ConfigEmailController);
    ConfigEmailController.$inject = ['$scope', 'ConfigEmailService', 'LoginService', '$rootScope', '$uibModal', '$timeout', '$location', '$confirm'];
    function ConfigEmailController($scope, ConfigEmailService, LoginService, $rootScope, $uibModal, $timeout, $location, $confirm) {
        if (!LoginService.isLogined()) {
            $location.path("/login");
            return;
        }
        $rootScope.listemail = [];
        $scope.init = function () {
            console.log('init');
            ConfigEmailService.getlistemail()
                    .then(function (response) {
                        if (response.err === 0) {
                            $rootScope.listemail = response.dt;
                        } else {
                            console.log("error get list email");
                        }
                    });
        };
        $scope.init();

        $scope.deleteEmail = function (item) {
            var id = item.id;
            ConfigEmailService.deleteemail(id)
                    .then(function (response) {
                        if (response.err === 0) {
                            $scope.init();
                        } else {
                            console.log("error when delete email");
                        }

                    });
        };

        $scope.openPopupAddEmail = function () {
            $uibModal.open({
                animation: true,
                templateUrl: 'PopupAddItem.html',
                controller: 'AddItemController',
                resolve: {
                }
            });
        };
    }
    
    theApp.controller('AddItemController', AddItemController);
    AddItemController.$inject = ['$rootScope', '$scope', '$uibModalInstance', 'ConfigEmailService'];
    function AddItemController($rootScope, $scope, $uibModalInstance, ConfigEmailService) {
        $scope.newItem = {};
        $scope.errNameIsNull = false;
        $scope.errEmailIsNull = false;
        $scope.ok = function () {
            if (String($scope.newItem.email).trim() === "" || String($scope.newItem.email).trim() === "undefined") {
                $scope.errEmailIsNull = true;
                return;
            }
            if (String($scope.newItem.name).trim() === "" || String($scope.newItem.name).trim() === "undefined") {
                $scope.errNameIsNull = true;
                return;
            }

            ConfigEmailService.insertemail($scope.newItem)
                    .then(function (response) {
                        if (response.err === 0) {
                            $scope.newItem = response.dt;
                            $rootScope.listemail.push($scope.newItem);
                        } else {
                            console.log("error when insert email");
                        }
                    });
            $uibModalInstance.close();
        };

        $scope.cancel = function () {
            $uibModalInstance.close();
        };
    }
})();