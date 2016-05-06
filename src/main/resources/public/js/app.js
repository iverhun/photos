'use strict';

angular.module('example366', ['ngAnimate', 'ngTouch', 'cfp.hotkeys'])
    .controller('MainCtrl', function ($scope, $http, hotkeys) {

        // You can pass it an object. This hotkey will not be unbound unless manually removed
        // using the hotkeys.del() method
        hotkeys.add({
            combo: 'right',
            description: 'This one goes to 11',
            callback: function() {
                $scope.showNext()
            }
        });

        hotkeys.add({
            combo: 'left',
            description: 'This one goes to 11',
            callback: function() {
                $scope.showPrev()
            }
        });

        // Set of Photos
        $scope.photos = [];

        $http.get('http://localhost:8080/photos').then(function (response) {
            $scope.photos = response.data;
        });

        // initial image index
        $scope._Index = 0;

        // if a current image is the same as requested image
        $scope.isActive = function (index) {
            return $scope._Index === index;
        };

        $scope.isActive2 = function (index) {
            return $scope._Index === index - 1;
        };

        // show prev image
        $scope.showPrev = function () {
            $scope._Index = ($scope._Index > 0) ? --$scope._Index : $scope.photos.length - 1;
        };

        // show next image
        $scope.showNext = function () {
            $scope._Index = ($scope._Index < $scope.photos.length - 1) ? ++$scope._Index : 0;
        };

        // show a certain image
        $scope.showPhoto = function (index) {
            $scope._Index = index;
        };
    });
