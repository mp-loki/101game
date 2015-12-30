var app = angular.module("gameApp", [
  "gameApp.home_controller",
  "gameApp.controllers",
  "gameApp.services"
]);

angular.module("gameApp.home_controller", []);
angular.module("gameApp.controllers", []);
angular.module("gameApp.services", []);

app.directive('dynamic', function ($compile) {
  return {
    restrict: 'A',
    replace: true,
    link: function (scope, ele, attrs) {
      scope.$watch(attrs.dynamic, function(html) {
        ele.html(html);
        $compile(ele.contents())(scope);
      });
    }
  };
});
app.filter('range', function() {
  return function(input, total) {
    total = parseInt(total);
    for (var i=0; i<total; i++)
      input.push(i);
    return input;
  };
});