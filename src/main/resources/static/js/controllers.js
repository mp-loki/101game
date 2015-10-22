(function(angular) {
  angular.module("gameApp.controllers").controller("GameCtrl", function($scope, GameService) {
    $scope.messages = [];
    $scope.message = "";
    $scope.max = 140;
    
    $scope.addMessage = function() {
      GameService.send($scope.message);
      $scope.message = "";
    };
    
    $scope.startGame = function() {
    	GameService.send("start");
    	$scope.startGameRequested = true;
    }
    
    $scope.isDefined = function(variable) {
    	return variable !== undefined && variable
    }
    $scope.range = function(min, max, step){
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) input.push(i);
        return input;
    };
    
    GameService.receive().then(null, null, function(message) {
      $scope.messages.push(message);
      if (typeof message.lastCard !== undefined && message.lastCard) {
    	  $scope.lastCard = message.lastCard;
      }
      if (typeof message.hand !== undefined && message.hand) {
    	  $scope.hand = message.hand;
      }
      var players = message.players
      if (typeof players !== undefined && players && players.length > 0) {
    	  if (players.length == 1) {
    		  $scope.frontPlayer = players[0]
    	  } else if (players.length == 2) {
    		  $scope.leftPlayer = players[0]
    		  $scope.rightPlayer = players[1]
    	  }
	       else if (players.length == 3) {
	    	  $scope.leftPlayer = players[0]
	    	  $scope.frontPlayer = players[1]
	    	  $scope.rightPlayer = players[2]
	      }
      }
    });
  });
})(angular);