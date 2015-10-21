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
    
    GameService.receive().then(null, null, function(message) {
      $scope.messages.push(message);
      if (typeof message.lastCard != undefined && message.lastCard) {
    	  $scope.lastCard = message.lastCard;
      }
      if (typeof message.hand != undefined && message.hand) {
    	  $scope.hand = message.hand;
      }
    });
  });
})(angular);