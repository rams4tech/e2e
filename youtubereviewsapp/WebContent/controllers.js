var youtubereviewsController = angular.module("youtubereviews.controllers",[]);


youtubereviewsController.controller("mobilesListController", function($scope,$http){

    $scope.title = 'Mobile Phones';
    $http({
        method: 'GET',
        url:  '/youtubereviewsapp/mobiles/reviews/mobileslist'
    }).success(function(data, status){
        $scope.mobilePhonesList = data;
    }).error(function(data, status){
        $scope.mobileFetchErrMsg = data;
        alert("Error Fetching the reviews" + data);
    });
});

youtubereviewsController.controller("searchReviewController", function($http, $scope,$routeParams, $location){

    
    $scope.fetchReviews = function() {
        var mobilePhoneName = $scope.mobilePhoneName;
        if (mobilePhoneName) {
	        $scope.title = mobilePhoneName + ' Reviews';
	        $http({
	            method: 'GET',
	            url:  '/youtubereviewsapp/mobiles/reviews/'+mobilePhoneName
	        }).success(function(data, status){
	        	$location.path('/reviews/'+mobilePhoneName);
	        }).error(function(data, status){
	            $scope.mobileReviewErrMsg = data;
	            alert("Error Fetching the reviews" + data);
	        });
        } else {
        	alert("Please enter the mobile name ");
        }
    };
});



youtubereviewsController.controller("mobileReviewsController", function($http, $scope,$routeParams, $location){

    var mobilePhoneName = $routeParams.mobilePhoneName;
    $scope.title = mobilePhoneName;
    $http({
        method: 'GET',
        url:  '/youtubereviewsapp/mobiles/reviews/'+mobilePhoneName
    }).success(function(data, status){
        $scope.reviewsList = data;
        //$('#reviewListTable').dataTable();
    }).error(function(data, status){
        $scope.mobileReviewErrMsg = data;
        alert("Error Fetching the reviews" + data);
    });
    
    $scope.deleteReview = function(videoId) {
    	$http({
            method: 'DELETE',
            url:  '/youtubereviewsapp/mobiles/reviews/delete/'+videoId
        }).success(function(data, status){
        	$location.path('/reviews/'+mobilePhoneName);
        }).error(function(data, status){
            $scope.mobileReviewErrMsg = data;
            alert("Error Deleting the review" + data);
        });
    }
});