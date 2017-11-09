/* global theApp */

(function () {
    'use strict';

    theApp
        .factory('HistorySendEmailService', HistorySendEmailService);

    HistorySendEmailService.$inject = ['$rootScope', '$http', '$q', 'API_URL'];
    function HistorySendEmailService($rootScope, $http, $q, API_URL) {
        var service = {};
        var url = API_URL + "history_send_email"; 
        
        service.get_list_history_send_email = get_list_history_send_email;
        return service;

        function get_list_history_send_email() {
            var cmd = "get_history";
            var dtJSON = {};
            var dt = JSON.stringify(dtJSON);
            var data = $.param({
                cm: cmd,
                dt: dt
            });
            return $http.post(url, data).then(handleSuccess, handleError('Error Test'));
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
