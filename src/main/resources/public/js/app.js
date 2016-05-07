'use strict';

angular.module('example366', ['ngAnimate', 'ngTouch', 'cfp.hotkeys'])
    .controller('MainCtrl', function ($scope, $http, hotkeys) {

        // You can pass it an object. This hotkey will not be unbound unless manually removed
        // using the hotkeys.del() method
        hotkeys.add({
            combo: 'right',
            description: 'This one goes to 11',
            callback: function() {
                console.log("right")
                $scope.moveToNext()
            }
        });

        hotkeys.add({
            combo: 'left',
            description: 'This one goes to 11',
            callback: function() {
                console.log("left")
                $scope.moveToPrev()
            }
        });

        // Set of Photos
        $scope.photos = [];

        $http.get('http://localhost:8080/photos').then(function (response) {
            $scope.photos = response.data;
        });

        // initial image index
        $scope._Index1 = 0;
        $scope._Index2 = 1;

        // show prev image
        $scope.moveToPrev = function () {
            $scope._Index1 = prev($scope._Index1);
            $scope._Index2 = prev($scope._Index2);
        };

        // show next image
        $scope.moveToNext = function () {
            $scope._Index1 = next($scope._Index1);
            $scope._Index2 = next($scope._Index2);
        };

        $scope.total = function () {
            return $scope.photos.length
        }

        var next = function (index) {
            return (index < $scope.photos.length - 1) ? ++index : 0;
        }

        var prev = function (index) {
            return (index > 0) ? --index : $scope.photos.length - 1;
        }
    });
