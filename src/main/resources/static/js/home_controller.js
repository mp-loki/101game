(function(angular) {
  angular.module("gameApp.home_controller").controller("HomeController", function($scope, $http, GameService) {
		$scope.messages = [];
		$scope.message = "";
		$scope.max = 140;
		
		$scope.players = [];
		
	    $scope.init = function() {
	    	 $http.get('/game/getUsers').
	         success(function(data) {
	        	 $scope.players = data;
	         });
	    }
	
		$scope.addMessage = function() {
			ChatService.send($scope.message);
			$scope.message = "";
		};
		
	    function isDefined(variable) {
	    	return variable !== undefined && variable != null;
	    }
		
		var processMessage = function(message) {
			if (isDefined(message)) {
				switch (true) {
					case(message.type == "USERS_UPDATE"):
						$scope.players = message.users;
						break;
				}
			}
		}
		
		GameService.receive().then(null, null, function(message) {
			processMessage(message);
		});
		$scope.init();
});
})(angular);