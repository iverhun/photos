'use strict';

angular.module('example366', ['ngAnimate', 'ngTouch', 'cfp.hotkeys', 'cp.ng.fix-image-orientation'])
    .controller('MainCtrl', function ($scope, $http, hotkeys) {

        // You can pass it an object. This hotkey will not be unbound unless manually removed
        // using the hotkeys.del() method
        hotkeys.add({
            combo: 'right',
            description: 'This one goes to 11',
            callback: function() {
                //console.log("right")
                $scope.moveToNext()
            }
        });

        hotkeys.add({
            combo: 'left',
            description: 'This one goes to 11',
            callback: function() {
                //console.log("left")
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
        $scope.leftImageData = "";
        $scope.rightImageData = "";

        var loadPhoto = function (url, myCallback) {
            console.log("Loading image from ", url, "; callback: ", myCallback)
            $http.get(url, {responseType: "blob"})
                .success(function (imageBlob, status, headers, config) {
                    var orientation = 1;

                    loadImage.parseMetaData(imageBlob, function (data) {
                        if (data.exif) {
                            orientation = data.exif.get('Orientation')
                        }

                        loadImage(imageBlob, function (img) {
                            myCallback(img);
                            //document.getElementById("leftPhotoCanvas").getContext("2d").drawImage(img, 0, 0, 100, 100)
                        }, {orientation: orientation, maxWidth: 1000})
                    });
                });
        }

        var xxx = function (context) {
            var leftUrl = $scope.photos[$scope._Index1].src
            var rightUrl = $scope.photos[$scope._Index2].src
            console.log(context, ": index 1: ", $scope._Index1, "; index 2: ", $scope._Index2, "; leftUrl: ", leftUrl, "; rightUrl: ", rightUrl)

            loadPhoto(leftUrl, function(img) {
                $scope.leftImageData = img.toDataURL("image/jpeg");
                $scope.$digest()
            });

            loadPhoto(rightUrl, function(img) {
                $scope.rightImageData = img.toDataURL("image/jpeg");
                $scope.$digest()
            });

        }

        $scope.loadPhotos = function () {
            $http.get('http://localhost:8080/photos').then(function (response) {
                $scope.photos = response.data;

                xxx("load")
            });
        }

        $scope.loadPhotos();

        // show prev image
        $scope.moveToPrev = function () {
            $scope._Index1 = prev($scope._Index1);
            $scope._Index2 = prev($scope._Index2);

            xxx("prev")
        };

        // show next image
        $scope.moveToNext = function () {
            $scope._Index1 = next($scope._Index1);
            $scope._Index2 = next($scope._Index2);

            xxx("next")
        };

        $scope.total = function () {
            return $scope.photos.length
        }

        $scope.delete = function (index) {
            var data = {
                imgPath : $scope.photos[index].description,
                action : "DELETE"
            };

            $http.put('http://localhost:8080/photos', data).then(function (response) {
                console.log("Deleted " + $scope.photos[index]);
            }).finally(function(){
                $scope.loadPhotos();
            })
        }

        var next = function (index) {
            return (index < $scope.photos.length - 1) ? ++index : 0;
        }

        var prev = function (index) {
            return (index > 0) ? --index : $scope.photos.length - 1;
        }
    });
