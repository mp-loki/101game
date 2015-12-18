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