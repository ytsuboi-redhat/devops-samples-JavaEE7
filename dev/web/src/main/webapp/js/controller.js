(function () {

    var module = angular.module('app.controllers', []);

    module.controller('RootController', function ($scope, $rootScope, $http) {
        $rootScope.restUrl = 'http://192.168.56.101:8080/order-manage-api/rs';

        listAll($scope, $rootScope, $http);

        $scope.modify = function (entry) {
            $http.put($rootScope.restUrl + '/' + encodeURIComponent(entry['key']) + '/' +
                encodeURIComponent(entry['value'])).success(function (data, status, headers, config) {
                listAll($scope, $rootScope, $http);
            });
        }

        $scope.remove = function (key) {
            $http.delete($rootScope.restUrl + '/' +
                encodeURIComponent(key)).success(function (data, status, headers, config) {
                listAll($scope, $rootScope, $http);
            });
        }

        $scope.register = function () {
            $http.post($rootScope.restUrl + '/' + encodeURIComponent($scope.newKey) + '/' +
                encodeURIComponent($scope.newValue)).success(function (data, status, headers, config) {
                listAll($scope, $rootScope, $http);
            });
            $scope.newKey = '';
            $scope.newValue = '';
        }
    });

    var listAll = function ($scope, $rootScope, $http) {
        $http.get($rootScope.restUrl + '/orders?offset=0&count=10').success(function (data, status, headers, config) {
            $scope.records = data.value;
        });
    }

}());
