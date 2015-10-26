(function(angular) {
  angular.module("gameApp.controllers").controller("GameCtrl", function($scope, $http, GameService) {
	//$scope.info = [];
	var messages = [];
	$scope.skip = "Pass";
	$scope.pickAllowed = false;
    
    $scope.addMessage = function() {
      GameService.send($scope.message);
    };
    
    $scope.startGame = function() {
    	GameService.send("START", $scope.currentPlayer);
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
    	return $scope.range($scope.frontPlayer);
    };
    $scope.leftPlayerRange = function(){
    	return $scope.range($scope.leftPlayer);
    };
    $scope.rightPlayerRange = function(){
    	return $scope.range($scope.rightPlayer);
    };
    
    $scope.init = function() {
    	 $http.get('/game/getState').
         success(function(data) {
             setState(data);
         });
    }
    
    $scope.action = function(card) {
    	console.log(card);
    	GameService.send("ACTION", $scope.currentPlayer, card);
    }
    
    $scope.pickCard = function() {
    	if ($scope.active && $scope.pickAllowed) {
    		GameService.send("PICK", $scope.currentPlayer);
    	}
    }
    
    $scope.pass = function() {
    	if ($scope.active) {
    		GameService.send("PASS", $scope.currentPlayer);
    	}
    }
    
    $scope.getMessages = function() {
    	return messages
    }

    var setState = function(data) {
    	if (isDefined(data.currentPlayerName)) {
    		$scope.currentPlayer = data.currentPlayerName
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
    	if (isDefined(data.playerDetails)) {
    		messages = data.playerDetails.concat(messages);
    	}
        if (isDefined(data.messages)) {
        	//$scope.info.push.apply($scope.info, data.messages);
        	data.messages.reverse();
        	messages = data.messages.concat(messages);
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
    		updatePlayerCardCount(data.playerUpdate);
    	}
    	if (isDefined(data.type) && data.type == "GAME_OVER") {
    		messages = ["Game Over"].concat(messages);
    	}
    }
    
    function updatePlayerCardCount(playerUpdate) {
    	if ($scope.currentPlayer == playerUpdate.name) {
    		return
    	} 
    	if (checkAndSetCardCount($scope.frontPlayer, playerUpdate)) {
    		return;
    	}
    	if (checkAndSetCardCount($scope.leftPlayer, playerUpdate)) {
    		return;
    	}
    	if (checkAndSetCardCount($scope.rightPlayer, playerUpdate)) {
    		return;
    	}
    }
    
    function checkAndSetCardCount(player, playerUpdate) {
    	if (isDefined(player) && player.name == playerUpdate.name) {
    		player.cardCount = playerUpdate.cardCount
    		return true
    	}
    	return false
    }
    
    function isDefined(variable) {
    	return variable !== undefined && variable != null
    }
    
	function setPlayers(players) {
		if (isDefined(players)) {
			if (players.length == 1) {
				$scope.frontPlayer = players[0]
			} else if (players.length == 2) {
				$scope.leftPlayer = players[0]
				$scope.rightPlayer = players[1]
			} else if (players.length == 3) {
				$scope.leftPlayer = players[0]
				$scope.frontPlayer = players[1]
				$scope.rightPlayer = players[2]
			}
		}
	}
	
    GameService.receive().then(null, null, 
    		function(message) {
    			setState(message);
    		}
    );
  });
})(angular);