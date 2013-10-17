/* Reveal Pattern
 * 
 */
var gameCanvas = function () {
    
    
    var canvas;
    var ctxBg;
    var box = new Image();
    var ring = new Image();
    var kryss = new Image();
    
    function publicInit (canvasElem, ctxElem, size) {
        canvas = canvasElem;
        ctxBg = ctxElem;
        ctxBg.canvas.height = 400;
        ctxBg.canvas.width = 400;
        total = size;
        box.src = 'images/box.png';
        box.addEventListener('load', imageLoaded, false);
        
        kryss.src = 'images/kryss.png';
        kryss.addEventListener('load', imageLoaded, false);
        
        ring.src = 'images/ring.png';
        ring.addEventListener('load', imageLoaded, false);
        
        width = 400 / size;
    }
    
    function publicFill (posX, posY, type) {
        
    }
    
    
    
    function imageLoaded()
    {
        --pendingImages;
        if (pendingImages === 0)
        {
            drawGameBoard();
        }
    }
    
    var pendingImages = 3;
    var boxX = 0;
    var boxY = 0;
    var width;
    var total = 5;
    var count = 1;
    var a = [];
    

    
    // main functions
    function drawGameBoard() {
        
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
    }
    
    
    
    function clearCtxBg() {
        ctxBg.clearRect(0, 0, 800, 600);
    }
    // end of main functions
    
    
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