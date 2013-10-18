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
        clearCtxBg();
        total = size;
        box.src = 'images/box.png';
        box.addEventListener('load', imageLoaded, false);
        
        kryss.src = 'images/kryss.png';
        kryss.addEventListener('load', imageLoaded, false);
        
        ring.src = 'images/ring.png';
        ring.addEventListener('load', imageLoaded, false);
        
        width = 400 / size;
        
        //Initializing board
        for(var i = 0; i < size; i++){
            board[i] = []; 
            for(var j = 0; j < size; j++){
                board[i][j] = 0;
            } 
        }
    }
    
    function publicFill (posX, posY, type) {
        board[posX][posY] = type;
        clearCtxBg();
        drawGameBoard();
    }

    function publicReset(){
        
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
    var width;
    var total;
    var board = [];
    
    // main functions
    function drawGameBoard() {
        for(var iw = 0; iw < total; iw++){
            for(var jw = 0; jw < total; jw++){
               ctxBg.drawImage(box, (iw*width), (jw*width), width, width);
            }
        } 
        for(var iw = 0; iw < total; iw++){
            for(var jw = 0; jw < total; jw++){
                if (board[iw][jw] === gameCanvas.fillType.cross){
                    ctxBg.drawImage(kryss, (iw*width), (jw*width), width, width);
                } else if (board[iw][jw] === gameCanvas.fillType.circle){
                    ctxBg.drawImage(ring, (iw*width), (jw*width), width, width); 
                }
            }
        } 
    }
    
    
    
    function clearCtxBg() {
        ctxBg.clearRect(0, 0, 800, 600);
    }
    // end of main functions
    
    function getPublicBoardSize () {
        return total;
    }
    
    return {
        init : publicInit,
        fill : publicFill,
        reset : publicReset,
        getBoardSize : getPublicBoardSize,
        fillType : {
            cross : -1,
            free : 0,
            circle : 1
        }
    };
}();