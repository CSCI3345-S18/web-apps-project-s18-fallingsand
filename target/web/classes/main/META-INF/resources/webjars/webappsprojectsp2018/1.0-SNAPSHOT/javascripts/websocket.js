var socket;
var socketIsInitialized = false;
var socketTimer;

/**
 * the messageType can be one of four values:
 * 0: removeClient message
 * 1: updateClient message
 * 2: initSelf message
 * 3: nullMsg message
 * see the WebSocketActor.scala source file for more information
 */
const socketMsgType = {
		removeClient: 0,
		updateClient: 1,
		initSelf: 2,
		nullMsg: 3
};

function initializeSocket() {
	// Connection opened 
	socket.addEventListener('open', function (event) {
		socket.send(JSON.stringify({'messageType': socketMsgType.nullMsg, 'clientSprite': mySprite}));
	});
	
	//Listen for messages
	socket.addEventListener('message', function (event) {
	    const socketMsg = JSON.parse(event.data);
	    console.log("Got socket message: " + socketMsg);
	    const msgType = socketMsg.messageType;
	    const clientSprite = socketMsg.clientSprite;
	    if((typeof clientSprite.id !== undefined) && (typeof clientSprite.xPos !== undefined) && (typeof clientSprite.yPos !== undefined)) {
	    	if(msgType == socketMsgType.initSelf) {
	    		// initSelf => set this client's id to the id given by the server
	    		mySprite.id = clientSprite.id;
	    		// position data may be invalid for the "self" object sent by the websocket
	    		// so we ignore it
	    		socketIsInitialized = true;
	    	} else if(msgType == socketMsgType.updateClient) {
	    		// updateClient => update (or add) client to sprite room
	    		var clientIdStr = (clientSprite.id).toString();
	    		var tmpObj = {'xPos': clientSprite.xPos, 'yPos': clientSprite.yPos};
	    		spritesInRoom[clientIdStr] = tmpObj;
	    	} else if(msgType == socketMsgType.removeClient) {
	    		// removeClient => remove the sprite with the given id from the sprite room
	    		var clientIdStr = (clientSprite.id).toString();
	    		delete spritesInRoom[clientIdStr];
	    	}
	    } else {
	    	console.log("Invalid socket message");
	    }
	});
}

function keepAlive() { 
    var timeout = 15000;  
    if (socket.readyState == socket.OPEN) {  
    	socket.send(JSON.stringify({'messageType': socketMsgType.nullMsg, 'clientSprite': mySprite}));
    } else if(socket.readyState == socket.CLOSED || socket.readyState == socket.CLOSING) {
    	socketIsInitialized = false;
    	socket.close();
    	socket = new WebSocket('ws://'+window.location.hostname+':'+window.location.port+'/socket');
    	initializeSocket();
    }
    socketTimer = setTimeout(function() { keepAlive(); }, timeout);  
}  
function cancelKeepAlive() {  
    if (socketTimer) {  
        clearTimeout(socketTimer);  
        socketIsInitialized = false;
    }  
}

document.addEventListener('DOMContentLoaded', (function() {
	//Create WebSocket connection.
	socket = new WebSocket('ws://'+window.location.hostname+':'+window.location.port+'/socket');
	initializeSocket();
	keepAlive();
}));

document.body.addEventListener('keydown', (function(event) {
	if(event.keyCode == keycodes['leftarrow']) {
		if(socketIsInitialized) {
			socket.send(JSON.stringify({'messageType': socketMsgType.updateClient, 'clientSprite': mySprite}));
		}
	} else if(event.keyCode == keycodes['rightarrow']) {
		if(socketIsInitialized) { 
			socket.send(JSON.stringify({'messageType': socketMsgType.updateClient, 'clientSprite': mySprite}));
		}
	} else if(event.keyCode == keycodes['uparrow']) {
		if(socketIsInitialized) {
			socket.send(JSON.stringify({'messageType': socketMsgType.updateClient, 'clientSprite': mySprite}));
		}
	} else if(event.keyCode == keycodes['downarrow']) {
		if(socketIsInitialized) {
			socket.send(JSON.stringify({'messageType': socketMsgType.updateClient, 'clientSprite': mySprite}));
		}
	}
}));