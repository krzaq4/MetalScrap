var moduleapp = angular.module('app', ['ngAnimate','ui.bootstrap','ngGrid']);


moduleapp.factory('auctionService', function($http){
	var aucs = [] ;
	
	return { getAuctions:function(category_id) {
		
			$http({
				url: 'http://localhost:8081/MetalScrap/app/auctions',
				method: 'GET',
				params: {'category_id': category_id, status: '', from: '', to: ''}
				}).success(function(data, status, headers, config){
				
					aucs = data ;
					return data ;
				
			}
			
				
			
				) ;
			
			
	
	}, 
		getAucs: function(){
			return aucs ;
		}
	};
	
}) ;

function AuctionDetails($scope, $http) {
	
	
	
	
		    
		    
		    
	
		$scope.getAuction = function(){
			
			$http.get('http://localhost:8081/MetalScrap/app/auction?id=1').success(function(data, status, headers, config){
				
				$scope.auction = data ;
				if(!$scope.val) {
					$scope.val = data.currentOffer ;
				}
				//$scope.getAuction() ;
				
			}) ;
		};
		
		$scope.getAuction() ;
		
		$scope.placeBid = function(val) {
			
			
			$http({
		        url: 'http://localhost:8081/MetalScrap/app/offer',
		        method: "POST",
		        data: JSON.stringify({auction_id:1, value: val}),
		        headers: {'Content-Type': 'application/json'}
		      }).success(function(data, status, headers, config){
				
			}) ;
			
		} ;
		
		$scope.showUserDetails = function() {
			
			
			
		} ;
		
		$scope.toggle = function() {
			 
			 $scope.isVisible = ! $scope.isVisible;
			  
			 };
			  
			
			 $scope.isVisible = false;
		 
		
		
			 
		$http.get('http://localhost:8081/MetalScrap/user/details.html').success(function(data, status, headers, config){
			$scope.userDetailsWindow = data ;
			
		}) ;
		
}

	 
	 
	 
function UserDetails($scope, $modalInstance, $http) {
	
	$scope.user = {"login":"krzaq4", "firstName":"MichaÅ‚"} ;
	
	$scope.ok = function () {
	    $modalInstance.close();
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	
}

var UserDetailsModal = function ($scope, $modal, $log) {

	

	  $scope.open = function () {

	    var modalInstance = $modal.open({
	      templateUrl: 'userDetails.html',
	      controller: UserDetails,
	      
	     
	    });

	    modalInstance.result.then(function (selectedItem) {
	      $scope.selected = selectedItem;
	    }, function () {
	      $log.info('Modal dismissed at: ' + new Date());
	    });
	  };
	};

function Categories($scope, $http, auctionService){
	
	$scope.subs=[];
	
	$scope.getSubCategories = function(parent_id){
		
		
		$http.get('http://localhost:8081/MetalScrap/app/categories/'+parent_id).success(function(data, status, headers, config){
			
			$scope.subs[parent_id] = data ;
			
		}) ;
		
	
} ;
	
	
	$http.get('http://localhost:8081/MetalScrap/app/categories/0').success(function(data, status, headers, config){
		
		$scope.cats = data ;
		
		angular.forEach($scope.cats, function(catt){
			
			$scope.getSubCategories(catt.category_id) ;
			
			
		}) ;
		
		
	}) ;
	
	
	
	//$scope.subs[38] = $scope.getSubCategories(38) ;
	
	$scope.auctions = [] ;
	$scope.currentCategory= 0 ;

	$scope.getAuctions = function(category_id) {
	$scope.currentCategory=category_id ;
	/*	$http({
			url: 'http://localhost:8081/MetalScrap/app/auctions',
			method: 'GET',
			params: {'category_id': category_id, status: '', from: '', to: ''}
			}).success(function(data, status, headers, config){
			
				
				$rootScope.auctions = data ;
				
					$rootScope.auct = data ;
				
				$rootScope.gridOptions = { data: data };
			
		}) ;*/
		
		auctionService.getAuctions(category_id);
		
		
	};
	
	$scope.getA = function() {
		var ret = [] ;
		var i=0;
		var auu =  auctionService.getAucs() ;
		angular.forEach(auu, function(au){
			ret[i]={'name': au.name, 'from': au.dateFrom, 'to':au.dateTo, 'price': au.price, 'end':au.timeLeft} ;
			i++;
		}) ;
		return ret ;
	};
	
	$scope.$watch('currentCategory', function(){
		$scope.auctions = $scope.getA() ;
	}) ;
	
	$scope.auctions = $scope.getA() ;
	//$scope.auct = [{'name':'dddd', 'code':1},{'name':'dddd', 'code':2},{'name':'dddd', 'code':3} ] ;
	//$scope.auctions=$scope.getAuctions(1) ;
	$scope.gridOptions = { data: 'getA()', columnDefs: [{field:'name', displayName:'Nazwa aukcji'}, {field:'from', displayName:'Data rozpoczêcia'}, {field:'to', displayName:'Data zakoñczenia'},{field:'price', displayName:'Aktualna cena'}, {field:'end', displayName:'Do koñca aukcji'}] };
}
