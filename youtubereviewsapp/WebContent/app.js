var youtubereviews = angular.module('youtubereviews', ['ngRoute','youtubereviews.controllers'])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.
            when('/mobilelist', {templateUrl: 'mobilelist.html',   controller: 'mobilesListController'}).
            when('/searchreviews', {templateUrl: 'searchreviews.html',   controller: 'searchReviewController'}).
            when('/reviews/:mobilePhoneName', {templateUrl: 'mobilereviews.html',   controller: 'mobileReviewsController'}).
            otherwise({redirectTo: '/'});
    }]);