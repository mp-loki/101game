var stompClient = null;

function setConnected(connected) {
	document.getElementById('connect').disabled = connected;
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('conversationDiv').style.visibility = connected ? 'visible'
			: 'hidden';
	document.getElementById('response').innerHTML = '';
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendName() {
	var name = document.getElementById('name').value;
	stompClient.send("/app/hello", {}, JSON.stringify({
		'name' : name
	}));
}

function showGreeting(message) {
	var response = document.getElementById('response');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(message));
	response.appendChild(p);
}