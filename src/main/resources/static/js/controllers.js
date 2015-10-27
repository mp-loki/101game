(function(angular) {
  angular.module("gameApp.controllers").controller("GameCtrl", function($scope, $http, GameService) {
	//$scope.info = [];
	var messages = [];
	$scope.skip = "Pass";
	$scope.pickAllowed = false;
	$scope.players = [];
    
    $scope.addMessage = function() {
      GameService.send($scope.message);
    };
    
    $scope.startGame = function() {
    	GameService.send("START", $scope.players['currentPlayer']);
    	$scope.startGameRequested = true;
    }
    
    $scope.range = function(player){
    	var range = [];
        if (isDefined(player)) {
        	for (var i = 0; i < player.cardCount; i++) range.push(i);
        }
        return range;
    };
    
    $scope.frontPlayerRange = function(){
    	return $scope.range($scope.players["frontPlayer"]);
    };
    $scope.leftPlayerRange = function(){
    	return $scope.range($scope.players["leftPlayer"]);
    };
    $scope.rightPlayerRange = function(){
    	return $scope.range($scope.players["rightPlayer"]);
    };
    
    $scope.init = function() {
    	 $http.get('/game/getState').
         success(function(data) {
             setState(data);
         });
    }
    
    $scope.action = function(card) {
    	console.log(card);
    	GameService.send("ACTION", $scope.players["currentPlayer"].name, card);
    }
    
    $scope.pickCard = function() {
    	if ($scope.active && $scope.pickAllowed) {
    		GameService.send("PICK", $scope.players["currentPlayer"].name);
    	}
    }
    
    $scope.pass = function() {
    	if ($scope.active) {
    		GameService.send("PASS", $scope.players["currentPlayer"].name);
    	}
    }
    
    $scope.getMessages = function() {
    	return messages
    }
    
    var setDealOverData = function(data) {
    	setMessages(data)
    	if (isDefined(data.playerDetails)) {
    		for (var i in data.playerDetails) {
    			updatePlayer(data.playerDetails[i], checkAndSetPoints);    			
    		}
    	}
    }
    
    var setMessages = function(data) {
    	if (isDefined(data.messages)) {
        	data.messages.reverse();
        	messages = data.messages.concat(messages);
        }
    }

	    var setState = function(data) {
		if (isDefined(data.type) && data.type == "DEAL_OVER") {
			setDealOverData(data)
		} else {
			setMessages(data)
			if (!isDefined($scope.players['currentPlayer']) && isDefined(data.currentPlayerName)) {
				$scope.players['currentPlayer'] = new Object({name:data.currentPlayerName, points:0})
			}
			if (isDefined(data.hand)) {
				$scope.hand = data.hand;
			}
			if (isDefined(data.lastCard)) {
				$scope.lastCard = data.lastCard;
			}
			if (isDefined(data.playerInfo)) {
				setPlayers(data.playerInfo);
			}
			if (isDefined(data.pickAllowed)) {
				$scope.pickAllowed = data.pickAllowed;
			}
			if (isDefined(data.endTurnAllowed)) {
				$scope.endTurnAllowed = data.endTurnAllowed;
			}
			if (isDefined(data.active)) {
				$scope.active = data.active;
			}
			if (isDefined(data.playerUpdate)) {
				updatePlayer(data.playerUpdate, checkAndSetCardCount);
			}
		}
	}
		
    function updatePlayer(playerUpdate, func) {
    	for (var player in $scope.players) {
    		func($scope.players[player], playerUpdate)
    	}
	}

	function checkAndSetCardCount(player, playerUpdate) {
		if (isDefined(player)) {
			if (playerUpdate.name == $scope.players["currentPlayer"].name) {
				// do nothing
				return true;
			} else if (player.name == playerUpdate.name) {
				player.cardCount = playerUpdate.cardCount
				return true
			}
			return false
		}
	}

	function checkAndSetPoints(player, playerUpdate) {
		if (isDefined(player) && player.name == playerUpdate.name) {
			player.points = playerUpdate.totalPoints
			return true
		}
		return false
	}

    function isDefined(variable) {
    	return variable !== undefined && variable != null
    }
    
	function setPlayers(players) {
		//if ($scope.players.length == 0) {
			if (players.length == 1) {
				$scope.players["frontPlayer"] = players[0]
			} else if (players.length == 2) {
				$scope.players["leftPlayer"] = players[0]
				$scope.players[rightPlayer] = players[1]
			} else if (players.length == 3) {
				$scope.players["leftPlayer"] = players[0]
				$scope.players["frontPlayer"] = players[1]
				$scope.players["rightPlayer"] = players[2]
			}
		//}
	}
	
    GameService.receive().then(null, null, 
    		function(message) {
    			setState(message);
    		}
    );
  });
})(angular);