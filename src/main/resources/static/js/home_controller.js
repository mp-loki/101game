(function(angular) {
  angular.module("gameApp.home_controller", ['ngSanitize']).controller("HomeController", function($scope, $sce, $http, $compile, GameService) {
	  	var onlinePlayersTemplate = '<h4 class="padding_left_10px">Players online: </h4><ul class="list-group"><li ng-repeat="player in players" class="list-group-item {{ player.available ? \'color_blue\' : \'color_red\'}}">{{player.name}}</li></ul>';
	  	var pendingPlayersTemplate = '<h4 class="padding_left_10px">Players: </h4><ul class="list-group"><li ng-repeat="player in players" class="list-group-item color_blue">{{player.name}}</li></ul>';
	  	var startGameButtonTemplate = '<button type="button" class="btn btn-success btn-lg" ng-click="start()">Start Game!</button>';
	  	var pendingContentTemplate = '<div class="alert alert-info fade in">Waiting for other players</div><div><button type="button" class="btn btn-warning btn-lg" ng-click="quit()">Quit</button></div>'
	  	var state = null;
	  	
	  	var initial = "INITIAL";
	  	var pending = "PENDING_START";
		$scope.players = [];
		
	    $scope.init = function() {
	    	 $http.get('/game/getState').
	         success(function(data) {
	        	 renderState(data);
	         });
	    }
	
		$scope.addMessage = function() {
			ChatService.send($scope.message);
			$scope.message = "";
		};
		
		$scope.start = function() {
			GameService.send("START");
		}
		$scope.quit = function() {
			GameService.send("QUIT");
		}
		
	    function isDefined(variable) {
	    	return variable !== undefined && variable != null;
	    }
	    
	    var renderState = function(data) {
	    	this.state = data.state;
	    	if (data.state == "INITIAL") {
	    		renderInitialState(data);
	    	} else if (data.state == "PENDING_START") {
	    		renderPendingState(data);
	    	}
	    }
		var renderInitialState = function(data) {
			$scope.players = data.players;
			$scope.leftContent = onlinePlayersTemplate;
			$scope.rightContent = startGameButtonTemplate;
		}
		
		var renderPendingState = function(data) {
			$scope.players = data.players;
			$scope.leftContent = pendingPlayersTemplate;
			$scope.rightContent = pendingContentTemplate;
		}
		
		var renderGameState = function(data) {
			
		}
		
		var processMessage = function(message) {
			if (isDefined(message)) {
				switch (true) {
					case(message.type == "USERS_UPDATE"):
						if (this.state = initial)
							renderInitialState(message);
						break;
					case (message.type == "PENDING_START"):
						renderPendingState(message);
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