/* global theApp */

(function () {
    'use strict';

    theApp
            .factory('ConfigEmailService', ConfigEmailService);

    ConfigEmailService.$inject = ['$rootScope', '$http', '$q', 'API_URL'];
    function ConfigEmailService($rootScope, $http, $q, API_URL) {
        var service = {};
        var url = API_URL + "email";

        service.getlistemail = getlistemail;
        service.deleteemail = deleteemail;
        service.insertemail = insertemail;
        return service;

        function deleteemail(email_id) {
            var cmd = "delete";
            var dtJSON = {id: email_id};
            var dt = JSON.stringify(dtJSON);
            var data = $.param({
                cm: cmd,
                dt: dt
            });
            return $http.post(url, data).then(handleSuccess, handleError('Error delete email'));
        }

        function getlistemail() {
            var cmd = "getlist";
            var data = $.param({
                cm: cmd
            });
            return $http.post(url, data).then(handleSuccess, handleError('Error get list email'));
        }
        
        function insertemail(json_email) {
            var cmd = "insert";
            var dtJSON = {email: json_email};
            var dt = JSON.stringify(dtJSON);
            var data = $.param({
                cm: cmd,
                dt: dt
            });
            return $http.post(url, data).then(handleSuccess, handleError('Error insert email'));
        }

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return {err: -2, msg: error};
            };
        }
    }
})();
