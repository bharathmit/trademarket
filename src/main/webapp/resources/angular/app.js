angular.module('exampleApp', ['ngRoute', 'ngCookies', 'nvd3ChartDirectives'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			
			$routeProvider.when('/create', {
				templateUrl: 'enroll/create',
				controller: EnrollController
			});
			
			$routeProvider.when('/enroll', {
				templateUrl: 'enroll/layout',
				controller: EnrollController
			});
			
			$routeProvider.when('/codelist', {
				templateUrl: 'enroll/codelistlayout',
				controller: EnrollController
			});
			
			
			$routeProvider.when('/report', {
				templateUrl: 'dashboard/report/layout',
				controller: DashBoardController
			});

			$routeProvider.when('/dashboard', {
				templateUrl: 'dashboard/layout',
				controller: DashBoardController
			});

			$routeProvider.when('/login', {
				templateUrl: 'login/layout',
				controller: LoginController
			});
			
			$routeProvider.otherwise({redirectTo: '/login'});
			
			
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (exampleAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, $cookieStore) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			$cookieStore.remove('user');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('user');
		if (authToken !== undefined) {
			$rootScope.user = authToken;
			$location.path(originalPath);
		}
		
		$rootScope.initialized = true;
	});









function EnrollController($scope, $rootScope, $routeParams , $http, $location) {
	
	$scope.getCodeList = function(){
		
		if($rootScope.editCodeList != undefined){
			$scope.codeList=$rootScope.editCodeList;
		}
		
		 $http.get('enroll/codeList').success(function(resultCodeList){
			 $scope.country = resultCodeList;
	        }).error(function() {
				alert("drop down values fetch failed");
			});
	};
	
	$scope.getCodeList();
	
	
	$scope.create = function(codeList) {
		 
		 $http.post('enroll/create',codeList).success(function() {
			 			
					$scope.codeList.clLabel = '';
					$location.path("/codelist");
				}).error(function(){
					alert("create Failed");
				});	
	 };
	 
	 $scope.deleteEntry = function(Id){
		 $http.post('enroll/delete',Id).success(function(resultCodeList){
			 $scope.getCodeList();
	        }).error(function() {
				alert("Delete failed");
			});
	};
	
	$scope.editEntry = function(Id){
		
		 $http.post('enroll/codeid',Id).success(function(resultCodeList){
			 $rootScope.editCodeList=resultCodeList;
			 $location.path("/create");
	        }).error(function() {
				alert("Edit failed");
			});
	};
	
	$scope.pageRedirect = function(){
		$rootScope.editCodeList='';
		$location.path("/create");
	};
 
	
	$scope.register = function(enroll) {
		 
		
		 $scope.enroll.country=$scope.enroll.counrtyObject.codeId;
		 $scope.enroll.countryName=$scope.enroll.counrtyObject.clAlternate;
		 
		 $http.post('enroll/register',enroll).success(function(result) {
			 			
					$scope.enroll.name = '';
					$scope.enroll.orgName = '';
					$scope.enroll.counrtyObject = '';
					
					$scope.value=result;
					
					alert($scope.value);
				}).error(function(){
					alert("Register Failed");
				});	
	 };
};



function DashBoardController($scope, $http, $window) {
	
	$scope.chartType='pie';
	
	$scope.getCodeList = function(){
		
		 $http.get('enroll/codeList').success(function(resultCodeList){
			 $scope.country = resultCodeList;
	        }).error(function() {
				alert("drop down values fetch failed");
			});
	};
	
	$scope.getCodeList();
	
	$scope.getChartData = function(report) {
		 $http.post('dashboard/chart',report).success(function(chartresult){
			 $scope.pieChart = chartresult;
			 $scope.barChart=[{
				    		 "key": "Bar Chart",
				    		 "values": $scope.pieChart[0].values
			 				}];
			
	        }).error(function() {
			});
	
	};  
	
	
	
	//Pie Chart Event
	
	$scope.xFunction = function() {
		return function(d) {
			return d.fieldName;
		};
	};
	
	$scope.yFunction = function() {
		return function(d) {
			return d.fieldValue;
		};
	};

	$scope.descriptionFunction = function() {
		return function(d) {
			return d.fieldValue;
		};
	}; 
	
	//Bar Chart Function
	
	$scope.xAxisTickFormatFunction = function(){
	    return function(d){
	        return d;
	    };
	};
	
	$scope.yAxisTickFormatFunction = function(){
	    return function(d){
	        return d;
	    };
	};
	
	 
	 $scope.pdf = function(report) {
		 
		 var left = screen.width/2 - 200 , 
		 	 top = screen.height/2 - 250 ;
		 var x = $window.open('/popup', '', "top=" + top + ",left=" + left + ",width=400,height=500");
		 
		 var string=report;
		 
		 x.document.open();
		 x.document.write(string);
		 x.document.close();

		
	 };
	 
	 
	 $scope.print = function(report) {
		 $http.post('dashboard/report',report).success(function(result){
			 $scope.report = result;
			 $scope.pdf($scope.report);
	        }).error(function() {
			});
	
	};  
	
	
};



function LoginController($scope,$http,$rootScope, $location, $cookieStore) {
	
	$scope.authenticate = function(login) {
		$http.post('login/authenticate', login).success(function(authenticationResult) {
				$scope.login.username = '';
				$scope.login.password = '';
				$rootScope.user = authenticationResult;
				$cookieStore.put('user', authenticationResult);
				$location.path("/enroll");
			}).error(function() {
				alert("Login Not Work");
			});

	};
};


















