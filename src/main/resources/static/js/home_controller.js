(function(angular) {
  angular.module("gameApp.home_controller", ['ngSanitize']).controller("HomeController", function($scope, $sce, $http, $compile, GameService) {
	  	var onlinePlayersTemplate = '<h4 class="padding_left_10px">Players online: </h4><ul class="list-group"><li ng-repeat="player in players" class="list-group-item {{ player.available ? \'color_blue\' : \'color_red\'}}">{{player.name}}</li></ul>';
	  	var pendingPlayersTemplate = '<h4 class="padding_left_10px">Players: </h4><ul class="list-group"><li ng-repeat="player in players" class="list-group-item color_blue">{{player.name}}</li></ul>';
	  	var startGameButtonTemplate = '<button type="button" class="btn btn-success btn-lg" ng-click="start()">Start Game!</button>';
	  	var pendingContentTemplate = '<div class="alert alert-info fade in">Waiting for other players</div><div><button type="button" class="btn btn-warning btn-lg" ng-click="quit()">Quit</button></div>'
	  	var twoPlayersTemplate = '<div><div>{{players[0].name}}</div><div>{{players[0].cardCount}}</div></div><div><div>{{currentPlayer.name}}</div><div>{{currentPlayer.hand }}</div></div>';
	  	var threePlayersTemplate = '<div>this is a three-player game</div>';
	  	var fourPlayersTemplate = '<div>this is a four-player game</div>';
	  	var usersPointsTemplate = '<div><h4>Score</h4><table class="table table-striped"><thead><tr><th>Player</th><th>Points</th></tr></thead><tbody><tr><td>{{currentPlayer.name}}</td><td>{{isDefined(currentPlayer.points) ? currentPlayer.points : 0}}</td></tr><tr data-ng-repeat="player in players"><td>{{player.name}}</td><td>{{isDefined(player.points) ? player.points : 0}}</td></tr></tbody></table></div>'
	  	var state = null;
	  	
	  	var initial = "INITIAL";
	  	var pending = "PENDING_START";
	  	var dealStart = "DEAL_START";
	  	$scope.currentPlayer = null;
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
		
	    var isDefined = function(variable) {
	    	return variable !== undefined && variable != null;
	    }
	    
	    $scope.isDefined = isDefined;
	    
	    var renderState = function(data) {
	    	this.state = data.state;
	    	switch(true) {
		    	case (data.state == initial):
		    		renderInitialState(data);
		    		break;
		    	case (data.state == pending):
		    		renderPendingState(data);
		    		break;
		    	case (data.state == dealStart):
		    		renderDealStart(data);
		    		break;
	    	}
	    }
		var renderInitialState = function(data) {
			$scope.players = data.players;
			$scope.leftContent = onlinePlayersTemplate;
			$scope.rightContent = startGameButtonTemplate;
		}
		
		var renderDealStart = function(data) {
			$scope.players = data.players;
			$scope.currentPlayer = data.currentPlayer;
			$scope.leftContent = usersPointsTemplate;
			$scope.rightContent = getRightContent(data);
		}
		
		var renderPendingState = function(data) {
			$scope.players = data.players;
			$scope.currentPlayer = data.currentPlayer;
			$scope.leftContent = pendingPlayersTemplate;
			$scope.rightContent = pendingContentTemplate;
		}
		
		var renderHandUpdate = function(data) {
			$scope.currentPlayer.hand = data.hand;
		}
		
		var getRightContent = function(data) {
			var size = data.players.length;
			var template = null;
			switch(true) {
				case (size == 1):
					template = twoPlayersTemplate;
					break;
				case (size == 2):
					template = threePlayersTemplate;
					break;
				case (size == 3):
					template = fourPlayersTemplate
					break;
			}
			return template;
		}
		
		var processdata = function(data) {
			if (isDefined(data)) {
				switch (true) {
					case(data.type == "USERS_UPDATE"):
						if (this.state = initial)
							renderInitialState(data);
						break;
					case (data.type == "PENDING_START"):
						renderPendingState(data);
						break;
					case (data.type == "DEAL_START"):
						renderDealStart(data);
						break;
					case (data.type == "HAND_UPDATE"):
						renderHandUpdate(data);
						break;
				}
			}
		}
		
		GameService.receive().then(null, null, function(message) {
			console.log("got a message: " + message);
			processdata(message);
		});
		$scope.init();
});
})(angular);