function behaviorController($scope) {

	$scope.todos = [
	{text: "learn Angularjs", done: false, favorite: false},
	{text: "learn html", done: false, favorite: false},
	{text: "learn css", done: false, favorite: false}

	];

	$scope.clearAll = function() {
		$scope.todos = [];
	}

	$scope.getTotal = function() {
		return $scope.todos.length - $scope.todos.filter(function(todo) {
			return todo.done;
		}).length;
	}

	$scope.sort = function() {
		$scope.todos.sort(function(firstTask, secondTask) {
			return (firstTask.favorite === secondTask.favorite) ? 0 : firstTask.favorite ? -1 : 1;
		});
	}

	$scope.percentage = function() {
		var length = $scope.todos.length == 0 ? 1 : $scope.todos.length;
		var percent = ((length - $scope.getTotal()) / length) * 100;
		return percent.toPrecision(3)
	}



	$scope.addTodo = function () {
		$scope.todos.push({text: $scope.textInput, done: false});
		$scope.textInput = '';
	}
	
	function wasDone(todo) {
  		return todo.done;
	}

	$scope.clearCompleted = function() {

		$scope.dones = $scope.todos.filter(function(todo) {
			return !todo.done;
		});


		$scope.todos = $scope.todos.filter(function(todo) {
			return !todo.done;
		});
	}



	// body...
}