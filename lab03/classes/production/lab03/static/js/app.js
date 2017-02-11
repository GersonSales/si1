/**
 * Created by gersonsafj on 08/02/17.
 */
var app = angular.module("todoApp", []);


app.controller("appController", function($scope, $http) {
    $scope.tasks=[];
    
    function getAllTasks() {
        $http.get('/task/allTasks').success(function (response){
            $scope.tasks = response.getData();
        });
    }

});