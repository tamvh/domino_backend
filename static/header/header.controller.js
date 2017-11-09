

/* global theApp */

theApp.controller('HeaderController', function($rootScope,$scope, $http,$cookies, $location, LoginService, $window) {
    
    var path = $location.path();
    var titlePage = "Dominos - Admin";
    switch(path){
        case '/dashboard/':
            titlePage = "Tổng quan";
            break;
        case '/historysendemail/':
            titlePage = "Lịch sử gửi Email";
            break;
        case '/configemail/':
            titlePage = "Cấu hình Email";
            break;    
    }
    
    $scope.titlePage = titlePage;
    
    $scope.logout = function (){
        $rootScope.globals.currentUser.username = "";
        $cookies.remove('u');   
        $location.path("/login");  
    };
});