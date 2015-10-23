(function(angular, SockJS, Stomp, _, undefined) {
  angular.module("gameApp.services").service("GameService", function($q, $timeout) {
    
    var service = {}, listener = $q.defer(), socket = {
      client: null,
      stomp: null
    }
    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/game";
    service.GAME_TOPIC = "/topic/message";
    service.GAME_QUEUE = "/user/queue/messages";
    service.GAME_BROKER = "/app/game";
    
    service.receive = function() {
      return listener.promise;
    };
    
    service.send = function(message) {
      var id = Math.floor(Math.random() * 1000000);
      socket.stomp.send(service.GAME_BROKER, {
        priority: 9
      }, JSON.stringify({
        message: message
      }));
    };
    
    var reconnect = function() {
      $timeout(function() {
        initialize();
      }, this.RECONNECT_TIMEOUT);
    };
    
    var getMessage = function(data) {
      /*
      var message = JSON.parse(data), out = {};
      out.message = message.message;
      out.lastCard = message.lastCard
      out.hand = message.hand
      out.players = message.players
      out.
      return out;
      */
    	return JSON.parse(data);
    };
    
    var startListener = function() {
      socket.stomp.subscribe(service.GAME_TOPIC, function(data) {
          listener.notify(getMessage(data.body));
      });
      socket.stomp.subscribe(service.GAME_QUEUE, function(data) {
          listener.notify(getMessage(data.body));
       });
    };
    
    var initialize = function() {
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({}, startListener);
      socket.stomp.onclose = reconnect;
    };
    initialize();
    return service;
  });
})(angular, SockJS, Stomp, _);