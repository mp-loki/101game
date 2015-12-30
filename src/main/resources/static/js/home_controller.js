(function(angular) {
  angular.module("gameApp.home_controller", ['ngSanitize']).controller("HomeController", function($scope, $sce, $http, $compile, GameService) {
	  	var onlinePlayersTemplate = '<h4 class="padding_left_10px">Players online: </h4><ul class="list-group"><li ng-repeat="player in players" class="list-group-item {{ player.available ? \'color_blue\' : \'color_red\'}}">{{player.name}}</li></ul>';
	  	var pendingPlayersTemplate = '<h4 class="padding_left_10px">Players: </h4><ul class="list-group"><li ng-repeat="player in players" class="list-group-item color_blue">{{player.name}}</li></ul>';
	  	var startGameButtonTemplate = '<button type="button" class="btn btn-success btn-lg" ng-click="send(\'START\')">Start Game!</button>';
	  	var pendingContentTemplate = '<div class="alert alert-info fade in">Waiting for other players</div><div><button type="button" class="btn btn-warning btn-lg" ng-click="send(\'QUIT\')">Quit</button></div>'
	  	var threePlayersTemplate = '<div>this is a three-player game</div>';
	  	var fourPlayersTemplate = '<div>this is a four-player game</div>';
	  	var usersPointsTemplate = '<div><h4>Score</h4><table class="table table-striped"><thead><tr><th>Player</th><th>Points</th></tr></thead><tbody><tr><td>{{currentPlayer.name}}</td><td>{{isDefined(currentPlayer.points) ? currentPlayer.points : 0}}</td></tr><tr data-ng-repeat="player in players"><td>{{player.name}}</td><td>{{isDefined(player.points) ? player.points : 0}}</td></tr></tbody></table></div>'
	  	
	  	var messagesTemplate ='<ul ><li data-ng-repeat="message in messages track by $index">{{message}}</li></ul>';
	
	  		
	  	var state = null;
	  	
	  	var cardDeckTemplate = '<div class="row height_120px margin_bottom_30px">\
									<div class="col-sm-4 col-sm-offset-4 text-align-center height_100">\
									<div class="sprite {{cardDeck.lastCard}} display-inline-block" />\
									<div class="sprite coverv display-inline-block deck" data-ng-click="pick()"/></div>'
	  		
	  	var frontPlayerTemplate = '<div class="row text-center height_120px margin_bottom_30px">\
									<div class="col-sm-4 col-sm-offset-4">\
									<p>{{players[0].name}}</p>\
									<div data-ng-repeat="n in [] | range:players[0].cardNum" class="sprite coverv position-absolute front-player-card-{{n}}"/></div></div>'	
	  	var currentPlayerTemplate = '<div class="row col-sm-4 col-sm-offset-4 {{active}}">\
										<div class="width_100 height_96px">\
											<div data-ng-repeat="card in currentPlayer.hand" id="{{card}}" data-ng-click="action(card)"\
									                    class="sprite card {{card}} card_{{$index + 1}} position-absolute"/>\
										</div>\
										<p>{{currentPlayer.name}}</p>\
	  								<div data-ng-show="currentPlayer.active.passAllowed"><button type="button" class="btn btn-success btn-lg" ng-click="send(\'PASS\')">Pass</button></div>'
	  		
	  	var twoPlayersTemplate = '<div>' + frontPlayerTemplate + cardDeckTemplate + currentPlayerTemplate + '</div>'
	  	
	  	var initial = "INITIAL";
	  	var pending = "PENDING_START";
	  	var dealStart = "DEAL_START";
	  	var state = "STATE";
	  	$scope.rightContent = messagesTemplate;
	  	$scope.currentPlayer = null;
		$scope.players = [];
		$scope.messages = [];
		
	    $scope.init = function() {
	    	 $http.get('/game/getState').
	         success(function(data) {
	        	 renderState(data);
	         });
	    }
	
		/*
		$scope.start = function() {
			GameService.send("START");
		}
		$scope.quit = function() {
			GameService.send("QUIT");
		}
		*/
		$scope.send = function(code) {
			GameService.send(code);
		}
		$scope.pick = function() {
			if ($scope.currentPlayer.active) {
				if ($scope.currentPlayer.active.pickAllowed) {
					GameService.send("PICK");
				} else {
					$scope.messages.push("You cannot pick more cards");
				}
			}
		} 
		
	    $scope.action = function(card) {
	    	console.log(card);
	    	GameService.send("ACTION", $scope.currentPlayer.name, card);
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
		    	case (data.type == state):
		    		renderGameState(data);
		    	    break;
	    	}
	    }
		var renderInitialState = function(data) {
			$scope.players = data.players;
			$scope.leftContent = onlinePlayersTemplate;
			$scope.centerContent = startGameButtonTemplate;
		}
		
		var renderDealStart = function(data) {
			$scope.players = data.players;
			$scope.currentPlayer = data.currentPlayer;
			$scope.leftContent = usersPointsTemplate;
			$scope.cardDeck = data.cardDeck;
			$scope.centerContent = getcenterContent(data);
		}
		
		var renderPendingState = function(data) {
			$scope.players = data.players;
			$scope.currentPlayer = data.currentPlayer;
			$scope.leftContent = pendingPlayersTemplate;
			$scope.centerContent = pendingContentTemplate;
		}
		var renderGameState = function(data) {
			$scope.players = data.players;
			$scope.currentPlayer = data.currentPlayer;
			$scope.leftContent = usersPointsTemplate;
			$scope.cardDeck = data.cardDeck;
			$scope.centerContent = getcenterContent(data);
		}
		
		var renderHandUpdate = function(data) {
			if (isDefined($scope.currentPlayer)) {
				$scope.currentPlayer.hand = data.hand;
				if (isDefined(data.active)) {
					$scope.currentPlayer.active = data.active;
				}
			}
		}
		
		var renderCardDeckUpdate = function(data) {
			$scope.cardDeck = data;
		}
		
		var renderActivate = function(data) {
			if ($scope.currentPlayer.name == data.name) {
				$scope.currentPlayer.active = data.active;
				$scope.messages.push("Your turn");
			} else {
				$scope.messages.push(data.name + "'s turn");
			}
		}
		
		var renderDeactivate = function(data) {
			if ($scope.currentPlayer.name == data.name) {
				$scope.currentPlayer.active = null;
				$scope.messages.push("Your turn ended");
			} else {
				$scope.messages.push(data.name + " ends  turn");
			}
		}
		var renderHandInfo = function(data) {
			for (var i = 0; i < $scope.players.length; i++) {
				if ($scope.players[i].name == data.name) {
					$scope.players[i].cardNum = data.cardNum;
					break;
				}
			}
		}
		
		renderActiveStateUpdate = function(data) {
			if (isDefined($scope.currentPlayer.active)) {
				$scope.currentPlayer.active = data;
			}
		}
		
		var getcenterContent = function(data) {
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
					case (data.type == "HAND_INFO"):
						renderHandInfo(data);
						break;	
					case (data.type == "ACTIVATE"):
						renderActivate(data);
					break;	
					case (data.type == "DEACTIVATE"):
						renderDeactivate(data);
					case (data.type == "ACTIVE"):
						renderActiveStateUpdate(data);
					case (data.type == "CARD_DECK"):
						renderCardDeckUpdate(data);
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