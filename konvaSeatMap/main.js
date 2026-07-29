require('./app.css');
// modern way
import Konva from 'konva';


// SeatMap Constructor
function SeatMap(){
    this.seatConfig = {
        colors : ["red", "orange", "yellow", "green", "blue", "purple"],
        seatMapPadding : 100,
        eachSeatMargin : 20,
        eachSeatSize : 60,
        cabinLabelHeight : 30
    };   
}

SeatMap.prototype.getSeatData = function(onData){
    fetch('seatmap_data.json')
    .then((resp) => resp.json()) // Transform the data into json
    .then(function(data) {
      console.log('seatmap_data',data);
      onData(data);
    });
}

SeatMap.prototype.initSeatMap = function(){

    this.getSeatData((data)=>{
        const cabins = data.messagepayload.sabreResponse.sabreSeatMapDetails;

        this.preInit(cabins);

        var width = (this.seatConfig.maxSeatCount * this.seatConfig.eachSeat)+ (this.seatConfig.seatMapPadding * 2);
        console.log('width', width);
        var height = 5000;
        
        var stage = new Konva.Stage({
            container: 'container',
            width: width,
            height: height
        });

        // Make it responsive {
        this.fitStageIntoParentContainer(stage,width,height);
        // adapt the stage on any window resize
        window.addEventListener('resize', ()=>{
            this.fitStageIntoParentContainer(stage,width,height);
        });
        // }
        
        var layer = new Konva.Layer();
    
        let yy = this.seatConfig.seatMapPadding;
        cabins.forEach((eachCabin, cabinIndex) => {

            this.cabinLabel(100, yy, width, layer);
            yy += this.seatConfig.cabinLabelHeight+this.seatConfig.eachSeatMargin;

            eachCabin.seatMapStructureList.forEach((eachRow,rowIndex)=>{
                let xx = this.seatConfig.seatMapPadding;
                eachRow.columnDetails.forEach((eachColumn,columnIndex) => {
                        console.log('seatNo', eachColumn.seatNo);
                        this.createSeat(xx, yy, eachColumn, layer);
                        xx += this.seatConfig.eachSeat;
                    });
                    yy += this.seatConfig.seatMapPadding;
                });
            });
    
            stage.add(layer);
    })    
}

SeatMap.prototype.createSeat = function(xx, yy, eachColumn, layer) {
    var complexText = new Konva.Text({
        x: xx + 2,
        y: yy + 15,
        text: eachColumn.seatNo,
        fontSize: 14,
        fontFamily: 'Calibri',
        fill: '#FFF',
        width: this.seatConfig.eachSeatSize,
        padding: 2,
        align: 'center'
    });
    var rect = new Konva.Rect({
        x: xx,
        y: yy,
        stroke: '#FFF',
        strokeWidth: 3,
        fill: '#FF2300',
        width: this.seatConfig.eachSeatSize,
        height: this.seatConfig.eachSeatSize,
        shadowColor: 'black',
        shadowBlur: 10,
        shadowOffset: [10, 10],
        shadowOpacity: 0.2,
        cornerRadius: 8
    });
    rect.on('click', function (e) {
        console.log('clicked on', e.target);
    });
    // add the shapes to the layer
    layer.add(rect);
    layer.add(complexText);
}
SeatMap.prototype.cabinLabel = function(xx,yy, width, layer) {
    const minusToWidth = this.seatConfig.seatMapPadding * 2;
    var complexText = new Konva.Text({
        x: xx + 2,
        y: yy + 7,
        text: 'Label',
        fontSize: 14,
        fontFamily: 'Calibri',
        fill: '#FFF',
        width: width - minusToWidth,
        padding: 2,
        align: 'center'
    });
    var rect = new Konva.Rect({
        x: xx,
        y: yy,
        stroke: '#FFF',
        strokeWidth: 3,
        fill: '#f58511',
        width: width - minusToWidth,
        height: this.seatConfig.cabinLabelHeight,
        shadowColor: 'black',
        shadowBlur: 10,
        shadowOffset: [10, 10],
        shadowOpacity: 0.2,
        cornerRadius: 8
    });

     // add the shapes to the layer
     layer.add(rect);
     layer.add(complexText);
}
SeatMap.prototype.preInit = function(cabins){
    let maxSeatCount = 0;
    cabins.forEach((eachCabin, cabinIndex) => {
        if(eachCabin.maxSeatCount > maxSeatCount){
            maxSeatCount = eachCabin.maxSeatCount;
        }
    });
    this.seatConfig.eachSeat =  (this.seatConfig.eachSeatSize + (this.seatConfig.eachSeatMargin*2));
    this.seatConfig.maxSeatCount = maxSeatCount;
}

SeatMap.prototype.fitStageIntoParentContainer = function(stage, stageWidth,stageHeight) {
    var container = document.querySelector('#stage-parent');

    // now we need to fit stage into parent
    var containerWidth = container.offsetWidth;
    // to do this we need to scale the stage
    var scale = containerWidth / stageWidth;


    stage.width(stageWidth * scale);
    stage.height(stageHeight * scale);
    stage.scale({ x: scale, y: scale });
    stage.draw();
}

const seatMap = new SeatMap();
seatMap.initSeatMap();




