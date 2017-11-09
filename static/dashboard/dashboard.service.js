/* global theApp */

(function () {
    'use strict';

    theApp
        .factory('DashboardService', DashboardService);

    DashboardService.$inject = ['$rootScope', '$http', '$q', 'API_URL'];
    function DashboardService($rootScope, $http, $q, API_URL) {
        var service = {};
        var url = API_URL + "dashboard"; 
        
        service.test = test;
        return service;

        function test() {
            var cmd = "test";
            var dtJSON = {};
            var dt = JSON.stringify(dtJSON);
            var data = $.param({
                cm: cmd,
                dt: dt
            });
            return $http.post(url, data).then(handleSuccess, handleError('Error when test'));
        }
       
        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {            
            return function () {
                return { err: -2, msg: error };
            };
        }
       
    }
    
})();
