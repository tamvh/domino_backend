/* global theApp */

(function () {
    'use strict';

    theApp
        .factory('TestService', TestService);

    TestService.$inject = ['$rootScope', '$http', '$q', 'API_URL'];
    function TestService($rootScope, $http, $q, API_URL) {
        var service = {};
        var url = API_URL + "test"; 
        
        service.test = test;
        return service;

        function test() {
            var cmd = "test";
            var dtJSON = {cabinet: ''};
            var dt = JSON.stringify(dtJSON);
            var data = $.param({
                cm: cmd,
                dt: dt
            });
            return $http.post(url, data).then(handleSuccess, handleError('Error test'));
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
