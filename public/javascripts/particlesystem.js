console.log("script loaded");
let canvas = document.getElementById("particle-canvas");
let context = canvas.getContext("2d");
let width = 600;
let height = 600;
var paused = false; // for debugging; not a game feature

context.beginPath();
context.moveTo(0,0);
context.lineTo(width,0);
context.lineTo(width,height);
context.lineTo(0,height);
context.lineTo(0,0);
context.stroke();

let particleWidth = 5;
var sandColor = "tan";
var pixelFills = []; //600 by 600 array of boolean - indicates whether a pixel is occupied w/ a particle or not
for(var x=0; x<width; x++) {
  pixelFills[x] = [];
  for(var y=0; y<height; y++) {
    pixelFills[x][y] = false;
  }
}

var currentMaterial = "sand";

let gravity = 1/30;
let terminal
var particles = [[300, 300, 0, 0, "sand"]]; // particle: [x, y, vx, vy, type]
var mouseDown = false;
let timeStepsPerSecond = 30;

setInterval(timeStep, 1000/timeStepsPerSecond);


canvas.addEventListener('mousedown', function(evt) {
  var mousePos = getMousePos(evt);
  addParticle(mousePos.x, mousePos.y);
  //console.log("drawing particle");
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
    //console.log("drawing particle");
    //console.log("x = "+mousePos.x+ " y = " +mousePos.y);
    addParticle(mousePos.x, mousePos.y);
  }
}, false);

function timeStep() {
  if(!paused) {
    clear();
    updatePixelFills();
    drawParticles();
    updateParticlePosition();
  }
}

function drawParticles() {
  for(var i=0; i<particles.length; i++) {
    if(particles[i][4]=="sand") {
      context.fillStyle = sandColor;
      context.fillRect(particles[i][0], particles[i][1], particleWidth, particleWidth);
    }
  }
}

function updateParticlePosition() {
  for(var i=0; i<particles.length; i++) {
    if(particles[i][1]+particles[i][3]+gravity+particleWidth > height) {
      //particle rests on the bottom of the canvas
      particles[i][3] = 0; // set y speed 0
      particles[i][1] = height-particleWidth; //set y height at bottom
    } else {
      var willCollide = false; //whether it will collide with a particle if it steps ahead
      var y=particles[i][1]+particleWidth+1;
      while(!willCollide && y<particles[i][1]+particles[i][3]+gravity+particleWidth) {
        if(pixelFills[particles[i][0]][y]) {
          //case where particle would fall into another
          particles[i][3] = 0;
          particles[i][1] = y-particleWidth-1;
          willCollide = true;
          console.log("particle would collide")
        }
        y++;
      }
      if(!willCollide) {
        //normal case - nothing below the particle
        //update speed
        particles[i][3]+=gravity;
      }
      //update position
      particles[i][0]+=particles[i][2];
      particles[i][1]+=particles[i][3];
    }
  }
}

function updatePixelFills() {
  for(var x=0; x<width; x++) {
    for(var y=0; y<height; y++) {
      pixelFills[x][y] = false;
    }
  }
  for(var i=0; i<particles.length; i++)
    for(var x=0; x<particleWidth; x++)
      for(var y=0; y<particleWidth; y++)
        pixelFills[x+particles[i][0]][y+particles[i][1]] = true;
}

function clear() {
  context.fillStyle = "white";
  context.fillRect(0,0,width,height);
  context.fillStyle = "black";
  context.beginPath();
  context.moveTo(0,0);
  context.lineTo(width,0);
  context.lineTo(width,height);
  context.lineTo(0,height);
  context.lineTo(0,0);
  context.stroke();
}

function addParticle(x, y) {
  particles.push([x, y, 0, 0, currentMaterial]);
}

function pause() {
  paused = !paused;
}
