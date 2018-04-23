var spriteRadius = 10;
var mySprite = {
		id: 0, // temporary until socket tells client what its id is
		xPos: 0, // temporary until window dimensions are determined
		yPos: 0 //  temporary until window dimensions are determined
};

var spritesInRoom = {};

var canvas;

function clearCanvas(ctx) {
	ctx.clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);
	ctx.fillStyle = "#2F2F2F";
	ctx.fillRect(0,0, canvas.clientWidth, canvas.clientHeight);
}

function drawSprite(ctx, sprite, color) {
	ctx.fillStyle = color;
	ctx.strokeStyle = "#FFFFFF";
	ctx.lineWidth = 2;
	ctx.beginPath();
	ctx.arc(sprite.xPos, sprite.yPos, spriteRadius, 0, 2 * Math.PI);
	ctx.closePath();
	ctx.fill();
	ctx.stroke();
}

function render() {
	if(canvas.getContext) {
		var ctx = canvas.getContext('2d');
		clearCanvas(ctx);
		drawSprite(ctx,mySprite,"#00FF00");
		for (var spriteId in spritesInRoom) {
			if (spritesInRoom.hasOwnProperty(spriteId)) {
				var obj = spritesInRoom[spriteId];
			    drawSprite(ctx,obj,"#FF0000");
			}
		}
	}
}

document.addEventListener('DOMContentLoaded', (function() {
	// Set up canvas and draw new client's sprite
	canvas = document.getElementById("sprite-canvas");
	var canvasHeight = (document.body.clientHeight - document.getElementById("sprite-header").clientHeight);
	var canvasWidth = (document.body.clientWidth);
	canvas.setAttribute('height', canvasHeight.toString() + "px");
	canvas.setAttribute('width', canvasWidth.toString() + "px");
	
	mySprite.xPos = Math.floor(Math.random() * canvasWidth);
	mySprite.yPos = Math.floor(Math.random() * canvasHeight);
	spriteRadius = (canvasWidth / 100);
	if(spriteRadius <= 0) {
		spriteRadius = 1;
	}
	
	setInterval(render,100);
}), false);

document.body.addEventListener('keydown', (function(event) {
	if(event.keyCode == keycodes['leftarrow']) {
		mySprite.xPos -= 1;
		mySprite.xPos = (mySprite.xPos < 0) ? 0 : mySprite.xPos;
	} else if(event.keyCode == keycodes['rightarrow']) {
		mySprite.xPos += 1;
		mySprite.xPos = (mySprite.xPos > canvas.clientWidth) ? canvas.clientWidth : mySprite.xPos;
	} else if(event.keyCode == keycodes['uparrow']) {
		mySprite.yPos -= 1;
		mySprite.yPos = (mySprite.yPos < 0) ? 0 : mySprite.yPos;
	} else if(event.keyCode == keycodes['downarrow']) {
		mySprite.yPos += 1;
		mySprite.yPos = (mySprite.yPos > canvas.clientHeight) ? canvas.clientHeight : mySprite.yPos;
	}
}));

document.body.addEventListener('keyup', (function(event) {
	if(event.keyCode == keycodes['leftarrow']) {
		console.log("left arrow released");
	} else if(event.keyCode == keycodes['rightarrow']) {
		console.log("right arrow released");
	} else if(event.keyCode == keycodes['uparrow']) {
		console.log("up arrow released");
	} else if(event.keyCode == keycodes['downarrow']) {
		console.log("down arrow released");
	}
}));