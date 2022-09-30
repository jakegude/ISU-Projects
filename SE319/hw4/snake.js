var right = document.getElementById("right");
var left = document.getElementById("left");
var start = document.getElementById("start");

start.onclick = function setup () {
    if (start.value == "Start"){
        start.value = "Stop";
        this.interval = setInterval(() => {
            Snake.update();
            Snake.draw();
        }, 30);
    } else if (start.value == "Stop") {
        start.value = "Start";
        clearInterval(this.interval);
    }
};

right.onclick = function () {
    if (Snake.curDir === 0) {
        Snake.xSpeed = 0;
        Snake.ySpeed = -1;
        Snake.curDir = 1;
    } else if (Snake.curDir === 1) {
        Snake.xSpeed = -1;
        Snake.ySpeed = 0;
        Snake.curDir = 2;
    } else if (Snake.curDir === 2) {
        Snake.xSpeed = 0;
        Snake.ySpeed = 1;
        Snake.curDir = 3;
    } else if (Snake.curDir === 3) {
        Snake.xSpeed = 1;
        Snake.ySpeed = 0;
        Snake.curDir = 0;
    }
}

left.onclick = function () {
    if (Snake.curDir === 0) {
        Snake.xSpeed = 0;
        Snake.ySpeed = 1;
        Snake.curDir = 3;
    } else if (Snake.curDir === 3) {
        Snake.xSpeed = -1;
        Snake.ySpeed = 0;
        Snake.curDir = 2;
    } else if (Snake.curDir === 2) {
        Snake.xSpeed = 0;
        Snake.ySpeed = -1;
        Snake.curDir = 1;
    } else if (Snake.curDir === 1) {
        Snake.xSpeed = 1;
        Snake.ySpeed = 0;
        Snake.curDir = 0;
    }
}

var Snake = {
    game : document.getElementById("game"),
    ctx : game.getContext("2d"),
    x : 0,
    y : 75,
    xSpeed : 1,
    ySpeed : 0,
    color : "#FF0000",
    width : 7,
    height : 7,
    curDir : 0,
    //0=right
    //1=down
    //2=left
    //3=up

    draw : function () {
        this.ctx.fillStyle = this.color;
        this.ctx.fillRect(this.x, this.y, this.width, this.height);
        this.ctx.fill(); 
    },

    update : function () {
        this.x += this.xSpeed;
        this.y += this.ySpeed;
        if (this.x < 0 || this.y < 0 || 
            this.x > this.game.width || this.y > this.game.height){
            this.xSpeed = 0;
            this.ySpeed = 0;
        }
        var idata;
        if (this.curDir === 0) {
            idata = this.ctx.getImageData(this.x + 1,this.y,this.width,this.height);
            if (idata.color == this.color) {
                this.xSpeed = 0;
                this.ySpeed = 0;
            }
        } else if (this.curDir === 3) {
            idata = this.ctx.getImageData(this.x, this.y + 1, this.width, this.height);
            if (idata.color == this.color) {
                this.xSpeed = 0;
                this.ySpeed = 0;
            }
        } else if (this.curDir === 2) {
            idata = this.ctx.getImageData(this.x - 1, this.y, this.width, this.height);
            if (idata.color == this.color) {
                this.xSpeed = 0;
                this.ySpeed = 0;
            }
        } else if (this.curDir === 1) {
            idata = this.ctx.getImageData(this.x, this.y - 1, this.width, this.height);
            if (idata.color == this.color) {
                this.xSpeed = 0;
                this.ySpeed = 0;
            }
        }
        console.log(this.color);
        console.log(idata.color);
        
    },

    clear : function() {
        this.game.clearRect(0, 0, this.width, this.height);
    },

    // turnRight : function () {
    //     if (right.onclick) {
    //         if (this.curDir === 0) {
    //             this.xSpeed = 0;
    //             this.ySpeed = -1;
    //             // this.y = this.x * -1;
    //             // this.x = 0;
    //             this.curDir = 1;
    //         } else if (this.curDir === 1) {
    //             this.xSpeed = -1;
    //             this.ySpeed = 0;
    //             // this.y = 0;
    //             // this.x = this.y;
    //             this.curDir = 2;
    //         } else if (this.urDir === 2) {
    //             this.xSpeed = 0;
    //             this.ySpeed = 1;
    //             // this.y = this.x * -1;
    //             // this.x = 0;
    //             this.curDir = 3;
    //         } else if (this.curDir === 3) {
    //             this.xSpeed = 1;
    //             this.ySpeed = 0;
    //             // this.y = this.x;
    //             // this.x = this.y * -1;
    //             this.curDir = 0;
    //         }
    //     }
    // },
    
    // turnLeft : function () {
    //     if (left.onclick) {
    //         if (this.curDir === 0) {
    //             this.xSpeed = 0;
    //             this.ySpeed = 1;
    //             // this.y = this.x;
    //             // this.x = 0;
    //             this.curDir = 3;
    //         } else if (this.curDir === 3) {
    //             this.xSpeed = -1;
    //             this.ySpeed = 0;
    //             // this.y = this.x;
    //             // this.x = 0;
    //             this.curDir = 2;
    //         } else if (this.curDir === 2) {
    //             this.xSpeed = 0;
    //             this.ySpeed = -1;
    //             // this.y = this.x;
    //             // this.x = 0;
    //             this.curDir = 1;
    //         } else if (this.curDir === 1) {
    //             this.xSpeed = 1;
    //             this.ySpeed = 0;
    //             // this.y = this.x;
    //             // this.x = 0;
    //             this.curDir = 0;
    //         }
    //     }
    // }
}