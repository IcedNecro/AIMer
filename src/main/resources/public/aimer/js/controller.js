var authorizationApp = angular.module("authorizationApp", ['PageController','ngRoute']);

authorizationApp.config(function($httpProvider) {
  $httpProvider.defaults.useXDomain = true;
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
});

authorizationApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/register', {
			templateUrl:'../html/Registration.html',
			controller: 'PageCtrl'
		}).
		when('/login', {
			templateUrl:"../html/Login.html",
			controller: null
		}).
		when('/home/:id',{
			templateUrl:"../html/home.html",
			controller: 'PageCtrl'			
		}).
		otherwise( {
			redirectTo:"/register"
		})
}])

var pageController = angular.module('PageController',[])

pageController.controller('PageCtrl',['$scope', '$routeParams', '$location', '$http', function($scope, $routeParams, $location, $http) {
	$scope.login = $routeParams.id;

	$scope.email = ""
	$scope.password = ""
	$scope.first_name = ""
	$scope.second_name = ""
	$scope.year = ""
	$scope.day = ""
	$scope.month = ""

	$scope.months = [
		{name : "January", value:1, numOfDays:31},
		{name : "February", value:2, numOfDays:28},
		{name : "March", value:3, numOfDays:31},
		{name : "April", value:4, numOfDays:30},
		{name : "May", value:5, numOfDays:31},
		{name : "June", value:6, numOfDays:30},
		{name : "July", value:7, numOfDays:31},
		{name : "Aughust", value:8, numOfDays:31},
		{name : "September", value:9, numOfDays:30},
		{name : "October", value:10, numOfDays:31},
		{name : "November", value:11, numOfDays:30},
		{name : "December", value:12, numOfDays:31},
	];

	var gen = function() {
		var arr = [];
		for(var i=1900, j=0; i<2000; j++,i++)
			arr[j]={value:i};
		return arr;
	}

	$scope.years = gen(); 

	$scope.days = [];


	$scope.generateDays = function genDay(month) {
		var arr = [];
		for(var i=1; i<=month.numOfDays; i++)
			arr[i-1]={value:i};
		$scope.days = arr;
		$scope.day = $scope.days[0];
	}

	$scope.confirm = function() {
		$location.path();
	}

	$scope.addStudentToDatabase = function() {
		var student_to_add = 
		{
			"name":$scope.first_name,
			"surname":$scope.second_name,
			/*"birthDay":new Date($scope.year.value,$scope.month.value,$scope.day.value),*/
			"login":$scope.login,
			"password":$scope.password,
			"email":$scope.email
		}

		$http.post("http://localhost:8080/rabota/student/add", student_to_add)
			.success(function(data, status,headers,config) {
				alert("sent");
			})
			.error(function(data, status,headers,config) {
				alert(headers);
			})
	}
}])

pageController.controller('LoginCtrl',['$scope', '$http', function($scope, $http) {
	$scope.login = ""
	$scope.password = ""

	$scope.confirm = function() {
		var login = {
			"login":$scope.login,
			"password":$scope.password
		}

		$http.post("http://localhost:8080/rabota/student/login", login)
			.success(function(data,status,headers,config){
				alert("success")
			})
			.error(function(data,status,headers,config){
				alert("fail")
			})
	}
}])
