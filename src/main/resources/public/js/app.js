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


        hotkeys.add({
            combo: '1',
            description: 'Remove 1st photo',
            callback: function() {
                console.log("1 pressed");
                $scope.delete($scope._Index1);
            }
        });

        hotkeys.add({
            combo: '2',
            description: 'Remove 2nd photo',
            callback: function() {
                console.log("2 pressed");
                $scope.delete($scope._Index2);
            }
        });

        // Set of Photos
        $scope.photos = [];

        // initial image index
        $scope._Index1 = 0;
        $scope._Index2 = 1;

        $scope.loadPhotos = function () {
            $http.get('http://localhost:8080/photos').then(function (response) {
                $scope.photos = response.data;
            });
        }

        $scope.loadPhotos();

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

        $scope.delete = function (index) {
            var data = {
                imgPath : $scope.photos[index].desc,
                action : "DELETE"
            };

            $http.put('http://localhost:8080/photos', data).then(function (response) {
                console.log("Deleted " + $scope.photos[index]);
            });

            $scope.loadPhotos();
        }

        var next = function (index) {
            return (index < $scope.photos.length - 1) ? ++index : 0;
        }

        var prev = function (index) {
            return (index > 0) ? --index : $scope.photos.length - 1;
        }
    });
