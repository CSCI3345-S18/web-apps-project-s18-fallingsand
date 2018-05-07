console.log("js loaded");

let canvas = document.getElementById("particle-canvas");
let context = canvas.getContext("2d");
//canvas.requestFullscreen();
let width = canvas.offsetWidth;
let height = canvas.offsetHeight;
var paused = false; // for debugging; not a game feature

var timerID = 0;
function keepAlive(webSocket) {
	var timeout = 20000;
	if (!(webSocket == null) && webSocket.readyState == webSocket.OPEN) {
		makeMessage(0, 0, "erase");
	}
	timerID = setTimeout(keepAlive, timeout);
	console.log("keeping alive")
}

const socket = new WebSocket('ws://'+window.location.hostname+':'+window.location.port+'/socket');

socket.addEventListener('open', function (event) {
	
	keepAlive(socket);
	makeMessage(700,0,"erase");
});

socket.addEventListener('message', function (event) {
	var msg = JSON.parse(event.data);
	if (msg.x > 600) {
		console.log("socket opened");
	}
	var tempMat = currentMaterial;
	currentMaterial = msg.mat;
	dropParticles(msg.x, msg.y);
	currentMaterial = tempMat;
});

let particleWidth = 2;
var particles = []; // 2D array storing particle location and type

for(var x=0; x<width/particleWidth; x++) {
  particles[x] = [];
  for(var y=0; y<height/particleWidth; y++) {
    particles[x][y] = "empty";
  }
}

var heights = [];
for(var x=0; x<width; x++) {
  heights[x] = height;
}

var currentMaterial = "sand";
let allowedMaterials = ["sand", "stone", "water", "metal", "acid", "lava", "erase"];


//var particles = [[width/4, height/4, 0, 0, "sand"], [width/4, 3*height/4, 0, 0, "sand"], [3*width/4, height/4, 0, 0, "sand"], [3*width/4+1, 3*height/4, 0, 0, "sand"]]; // particle: [x, y, vx, vy, type]
var mouseDown = false;
let timeStepsPerSecond = 30;
//let fallVelocity = 1;
var numParticles = 0;

setInterval(timeStep, 1000/timeStepsPerSecond);


canvas.addEventListener('mousedown', function(evt) {
  var mousePos = getMousePos(evt);
  var x = mousePos.x;
  var y = mousePos.y;
  //dropParticles(x, y);
  makeMessage(x, y, currentMaterial);
  mouseDown = true;
}, false);

canvas.addEventListener('mouseup', function(evt) {
  mouseDown = false;
}, false);


function getMousePos(evt) {
  var rect = canvas.getBoundingClientRect();
  return {
    x: evt.clientX - rect.left,
    y: evt.clientY - rect.top
  };
}

canvas.addEventListener('mousemove', function(evt) {
  if(mouseDown) {
    var mousePos = getMousePos(evt);
    var x = mousePos.x;
    var y = mousePos.y;
    //dropParticles(x, y);
    makeMessage(x, y, currentMaterial);
  }
}, false);

function makeMessage(inX, inY, material) {
	var particleObj = {
			x: inX,
			y: inY,
			mat: material
	};
	var particleJSON = JSON.stringify(particleObj);
	socket.send(particleJSON);
	//console.log("JSON to be sent: " +particleJSON);
}

function timeStep() {
  if(!paused) {
    clear();
    drawParticles();
    updateParticlePositions();
  }
}

function drawParticles() {
  for(var i=0; i<particles.length; i++) {
    for(var j=0; j<particles[0].length; j++) {
      if(particles[i][j]!="empty") {
        context.fillStyle = color(particles[i][j]);
        context.fillRect(i*particleWidth, j*particleWidth, particleWidth, particleWidth);
      }
    }
  }
}

function updateParticlePositions() {
  for(var i=particles.length-1; i>=0; i--) {
    for(var j=particles[0].length-1; j>=0; j--) {
      //How stone moves - if it can fall it does; doesn't move horizontally
      if(particles[i][j]=="stone") {
        if(j!=particles[0].length-1) {
          if(particles[i][j+1]=="empty"){
            particles[i][j+1] = particles[i][j];
            particles[i][j] = "empty";
          }
          if(particles[i][j+1]=="water"){
            particles[i][j+1] = "stone";
            particles[i][j] = "water";
          }
        }
      }

      if(particles[i][j]=="sand") {
        if(j!=particles[0].length-1) {
          if(particles[i][j+1]=="empty"){
            particles[i][j+1] = particles[i][j];
            particles[i][j] = "empty";
          } else if(particles[i][j+1]=="water"){
            particles[i][j+1] = "sand";
            particles[i][j] = "water";
          } else {
            if(i+1<particles.length && particles[i+1][j]=="empty" && particles[i+1][j+1]=="empty") {
              particles[i+1][j] = particles[i][j];
              particles[i][j] = "empty";
            } else if(i-1>0 && particles[i-1][j]=="empty" && particles[i-1][j+1]=="empty") {
              particles[i-1][j] = particles[i][j];
              particles[i][j] = "empty";
            }
          }
        }
      }

      //water
      if(particles[i][j]=="water") {
        if(j!=particles[0].length-1) {
          if(particles[i][j+1]=="empty"){
            particles[i][j+1] = particles[i][j];
            particles[i][j] = "empty";
          } else {
            if(i+1<particles.length && particles[i+1][j]=="empty" && particles[i+1][j+1]=="empty") {
              particles[i+1][j] = particles[i][j];
              particles[i][j] = "empty";
            } else if(i-1>0 && particles[i-1][j]=="empty" && particles[i-1][j+1]=="empty") {
              particles[i-1][j] = particles[i][j];
              particles[i][j] = "empty";
            } else if(i+1<particles.length && particles[i+1][j]=="empty" && particles[i-1][j]=="water") {
              particles[i+1][j] = particles[i][j];
              particles[i][j] = "empty";
            } else if(i+1<particles.length && i-1>0 &&particles[i-1][j]=="empty" &&  (i+1 <= width) && particles[i+1][j]=="water") {
              particles[i-1][j] = particles[i][j];
              particles[i][j] = "empty";
            }

          }
        }
      }
      if(particles[i][j]=="acid") {
        if(j!=particles[0].length-1) {
          if(particles[i][j+1]=="empty"){
            particles[i][j+1] = particles[i][j];
            particles[i][j] = "empty";
          } else {
            //var melted = false;
            if(i+1<particles.length && particles[i+1][j]!="acid") {
              particles[i][j] = "empty";
              particles[i+1][j] = "empty";
            } if(i-1>0 && particles[i-1][j]!="acid") {
              particles[i][j] = "empty";
              particles[i-1][j] = "empty";
            } if(j+1<particles[0].length && particles[i][j+1]!="acid") {
              particles[i][j] = "empty";
              particles[i][j+1] = "empty";
            } if(j-1>0 && particles[i][j-1]!="acid") {
              particles[i][j] = "empty";
              particles[i][j-1] = "empty";
            }
          }
        }
      }

      if(particles[i][j]=="lava") {
        if(j!=particles[0].length-1) {
          if(particles[i][j+1]=="empty"){
            particles[i][j+1] = particles[i][j];
            particles[i][j] = "empty";
          } else {
            if(i+1<particles.length) {
              if(particles[i+1][j]=="stone") particles[i+1][j] = "lava";
              else if(particles[i+1][j]=="sand") particles[i+1][j] = "lava";
              else if(particles[i+1][j]=="water") particles[i+1][j] = "empty";
            } if(i-1>0) {
              if(particles[i-1][j]=="stone") particles[i+1][j] = "lava";
              else if(particles[i-1][j]=="sand") particles[i+1][j] = "lava";
              else if(particles[i-1][j]=="water") particles[i+1][j] = "empty";
            } if(j+1<particles[0].length) {
              if(particles[i][j+1]=="stone") particles[i+1][j] = "lava";
              else if(particles[i][j+1]=="sand") particles[i+1][j] = "lava";
              else if(particles[i][j+1]=="water") particles[i+1][j] = "empty";
            } if(j-1>0) {
              if(particles[i][j-1]=="stone") particles[i+1][j] = "lava";
              else if(particles[i][j-1]=="sand") particles[i+1][j] = "lava";
              else if(particles[i][j-1]=="water") particles[i+1][j] = "empty";
            }
          }
        }
      }

    }
  }
}

function clear() {
  context.fillStyle = "white";
  context.fillRect(0,0,width,height);
}

function addParticle(x, y) {
  if(x>=0 && y>=0 && x<width && y<height)
    if(particles[parseInt(x/particleWidth)][parseInt(y/particleWidth)] == "empty") {
      particles[parseInt(x/particleWidth)][parseInt(y/particleWidth)] = currentMaterial;
      numParticles++;
    }
}

function removeParticle(x, y) {
  if(x>=0 && y>=0 && x<width && y<height) {
    particles[parseInt(x/particleWidth)][parseInt(y/particleWidth)] = "empty";
    numParticles--;
  }
}

function isParticleLocationValid(x, y) {
  if(x>=0 && y>=0 && x<width && y<height)
    if(particles[parseInt(x/particleWidth)][parseInt(y/particleWidth)] == "empty") {
      return true;
    }
  return false;
}

function dropParticles(x, y) {
  if(currentMaterial!="erase") {
  addParticle(x-2*particleWidth, y-2*particleWidth);
  addParticle(x, y-2*particleWidth);
  addParticle(x+2*particleWidth, y-2*particleWidth);

  addParticle(x-2*particleWidth, y);
  addParticle(x, y);
  addParticle(x+2*particleWidth, y);

  addParticle(x-2*particleWidth, y+2*particleWidth);
  addParticle(x, y+2*particleWidth);
  addParticle(x+2*particleWidth, y+2*particleWidth);
} else {
    for(var i=x-2*particleWidth; i<x+2*particleWidth; i++)
      for(var j=y-2*particleWidth; i<y+2*particleWidth; i++)
        removeParticle(i, j);
  }
}

function setMaterial(material) {
  if(allowedMaterials.includes(material))
    currentMaterial = material;
  	console.log(currentMaterial);
}

//for debugging purposes
function pause() {
  paused = !paused;
}

function color(material) {
  if(material=="sand")
    return "tan";
  else if(material=="stone")
    return "grey";
  else if(material=="water")
    return "navy";
  else if(material=="metal")
    return "silver";
  else if(material=="acid")
    return "yellowgreen";
  else if(material=="lava")
    return "orangered";
  else if(material=="erase")
    return "white";
}

//button event listeners
document.getElementById('sandButton').onclick = function(){
	setMaterial("sand");
}
document.getElementById('waterButton').onclick = function(){
	setMaterial("water");
}
document.getElementById('stoneButton').onclick = function(){
	setMaterial("stone");
}
document.getElementById('metalButton').onclick = function(){
	setMaterial("metal");
}
document.getElementById('acidButton').onclick = function(){
	setMaterial("acid");
}
document.getElementById('eraseButton').onclick = function(){
	setMaterial("erase");
}
