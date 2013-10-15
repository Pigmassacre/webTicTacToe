/* Reveal Pattern
 * 
 */
var gameCanvas = function () {
    
    
    var canvas;
    
    var ctxBg;
    
    function publicInit (canvasElem, ctxElem) {
        canvas = canvasElem;
        ctxBg = ctxElem;
        ctxBg.canvas.height = 420;
        ctxBg.canvas.width = 420;
        
    }
    
    function publicFill (posX, posY, type) {
        
    }
    
    
    var pendingImages = 3;
    
    var newScript = window.document.createElement("script");
    
    var requestAnimFrame =  window.requestAnimationFrame ||
            window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            window.msRequestAnimationFrame ||
            window.oRequestAnimationFrame;
    
    var music = new Audio("images/medieval.ogg");
    music.loop = true;
    //music.play();
    
    var klick = 0;
    var boxX = 0;
    var boxY = 0;
    var width;
    var total = 5;
    var player = 1;
    var count = 1;
    var a = [];
    //canvas[0].width = 400;  
    if(total == 3){
        width = 140;
        
    }
    else if(total == 4){
        width = 105;
    }
    else if(total == 5){
        width = 70;
    }
    
    
    var box = new Image();
    box.src = 'images/box.png';
    box.addEventListener('load', imageLoaded, false);
    
    var kryss = new Image();
    kryss.src = 'images/kryss.png';
    kryss.addEventListener('load', imageLoaded, false);
    
    var ring = new Image();
    ring.src = 'images/ring.png';
    ring.addEventListener('load', imageLoaded, false);
    
    // main functions
    function init() {
        loop();
        
        for(var i = 0; i < total; i++){ 
            for(var j = 0; j < total; j++){
                a[i] = []; 
                a[i][j] = count;
                count++;
            } 
        }
        
        
        for(var iw = 0; iw < total; iw++){
            for(var jw = 0; jw < total; jw++){
                ctxBg.drawImage(box, boxX+(iw*width), boxY+(jw*width), width, width);
                
            }
        } 
        document.addEventListener('keydown', checkKeyDown, false);
        
    }
    
    
    
    
    function loop() {  
        
        
        
        updateGame();  
        requestAnimFrame(loop);  
        
    }
    
    function imageLoaded()
    {
        --pendingImages;
        if (pendingImages === 0)
        {
            init();
        }
    }
    
    function drawText(){
        
        // ctxBg.fillStyle="#000000";
        // ctxBg.font="28px sans-serif";
        // ctxBg.fillText("Clear ", 570, 385);  
    }
    
    
    
    
    function updateGame(){
        
        drawText();
    }
    
    function clearCtxBg() {
        ctxBg.clearRect(0, 0, 800, 600);
    }
    // end of main functions
    
    
    window.onmousedown = mousedown;
    
    function mousedown()
    {
        var x=event.clientX;
        var y=event.clientY;
    }
    
    window.onmouseup = mouseup;
    
    function mouseup()
    {
        var x=event.clientX;
        var y=event.clientY;
        klick = 0;
        if(x > 550 && x < 730 && y > 350 && y < 400){ 
            //  clearCtxBg();  
        }
    }
    
    window.onclick = mouseclick;
    
    function mouseclick()
    {
        var x=event.clientX;
        var y=event.clientY;
        
        for(var i = 0; i < width*total; i+=width){
            for(var j = 0; j < width*total; j+=width){
                if(x >= i && x <= i+width && y >= j && y <= j+width){  
                    if(player > 0 && a[i/width][j/width] !== 66 ){
                        ctxBg.drawImage(kryss, i, j);
                        
                        a[i/width][j/width] = 66;
                        player*= -1;
                    }
                    else if(player < 0 && a[i/width][j/width] !== 66 ){
                        ctxBg.drawImage(ring, i, j);
                        a[i/width][j/width] = 66;
                        player*= -1;
                    }       
                }
            }
        } 
    }
    
    // event functions
    function checkKeyDown(e) {
        var keyID = e.keyCode || e.which;
        if (keyID === 13 ) { //enter
            e.preventDefault();
            
        }
        
    }
    
    return {
        init : publicInit,
                fill : publicFill,
                fillType : {
            cross : -1,
                    free : 0,
                    circle : 1
        }
    };
}();



// end of event functions