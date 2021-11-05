// Sets the value from the first series on every refresh

const inputField = data.series[0].fields[0];
const qtyField = data.series[0].fields[1];

console.log("STARTED ----------------");
var maxQty = 0;

for(let y= 1; y<= 20; y++){
  setColor("S"+y, 0,0,1);
  setColor("D"+y, 0,0,1);
  setColor("T"+y, 0,0,1);
}
setColor("SBULL", 0,0,1);
setColor("DBULL", 0,0,1);
for(let i= 0; i <= inputField.values.length -1; i++){
  var qtyValue = qtyField.values.get(i);
  if(qtyValue > maxQty){
    maxQty = qtyValue;
  }
}

for(let i= 0; i <= inputField.values.length -1; i++){
  setColor(inputField.values.get(i), qtyField.values.get(i), maxQty, 0);
}

function getColor(maxQty, qtyValue){
  return heatMapColorforValue((qtyValue / maxQty).toFixed(2));
}

function heatMapColorforValue(value){
  var h = (1.0 - value) * 240
  return "hsl(" + h.toFixed(0) + ", 100%, 50%)";
}

function setColor(input, qty, maxQty, force){
  if(force == 1){
    var hslValue = "hsla(150, 100%, 0%, 1)";
  } else {
    var hslValue = getColor(maxQty, qty);
  }
  let multiplier = input.charAt(0);
  let value = input.substring(1);
  switch(multiplier){
    case "S":
      switch(value){
        case "1":
          htmlNode.getElementById("use213").style.fill = hslValue;
          htmlNode.getElementById("use217").style.fill = hslValue;
          break;
        case "2":
          htmlNode.getElementById("use143").style.fill = hslValue;
          htmlNode.getElementById("use147").style.fill = hslValue;
          break;
        case "3":
          htmlNode.getElementById("use123").style.fill = hslValue;
          htmlNode.getElementById("use127").style.fill = hslValue;
          break;
        case "4":
          htmlNode.getElementById("use193").style.fill = hslValue;
          htmlNode.getElementById("use197").style.fill = hslValue;
          break;
        case "5":
          htmlNode.getElementById("use33").style.fill = hslValue;
          htmlNode.getElementById("use37").style.fill = hslValue;
          break;
        case "6":
          htmlNode.getElementById("use173").style.fill = hslValue;
          htmlNode.getElementById("use177").style.fill = hslValue;
          break;
        case "7":
          htmlNode.getElementById("use103").style.fill = hslValue;
          htmlNode.getElementById("use107").style.fill = hslValue;
          break;
        case "8":
          htmlNode.getElementById("use83").style.fill = hslValue;
          htmlNode.getElementById("use87").style.fill = hslValue;
          break;
        case "9":
          htmlNode.getElementById("use53").style.fill = hslValue;
          htmlNode.getElementById("use57").style.fill = hslValue;
          break;
        case "10":
          htmlNode.getElementById("use163").style.fill = hslValue;
          htmlNode.getElementById("use167").style.fill = hslValue;
          break;
        case "11":
          htmlNode.getElementById("use73").style.fill = hslValue;
          htmlNode.getElementById("use77").style.fill = hslValue;
          break;
        case "12":
          htmlNode.getElementById("use43").style.fill = hslValue;
          htmlNode.getElementById("use47").style.fill = hslValue;
          break;
        case "13":
          htmlNode.getElementById("use183").style.fill = hslValue;
          htmlNode.getElementById("use187").style.fill = hslValue;
          break;
        case "14":
          htmlNode.getElementById("use63").style.fill = hslValue;
          htmlNode.getElementById("use67").style.fill = hslValue;
          break;
        case "15":
          htmlNode.getElementById("use153").style.fill = hslValue;
          htmlNode.getElementById("use157").style.fill = hslValue;
          break;
        case "16":
          htmlNode.getElementById("use93").style.fill = hslValue;
          htmlNode.getElementById("use97").style.fill = hslValue;
          break;
        case "17":
          htmlNode.getElementById("use133").style.fill = hslValue;
          htmlNode.getElementById("use137").style.fill = hslValue;
          break;
        case "18":
          htmlNode.getElementById("use203").style.fill = hslValue;
          htmlNode.getElementById("use207").style.fill = hslValue;
          break;
        case "19":
          htmlNode.getElementById("use113").style.fill = hslValue;
          htmlNode.getElementById("use117").style.fill = hslValue;
          break;
        case "20":
          htmlNode.getElementById("use23").style.fill = hslValue;
          htmlNode.getElementById("use27").style.fill = hslValue;
          break;
        case "BULL":
          htmlNode.getElementById("circle219").style.fill = hslValue;
          break;
      }
      break;
    case "D":
      switch(value){
        case "1":
          htmlNode.getElementById("use211").style.fill = hslValue;
          break;
        case "2":
          htmlNode.getElementById("use141").style.fill = hslValue;
          break;
        case "3":
          htmlNode.getElementById("use121").style.fill = hslValue;
          break;
        case "4":
          htmlNode.getElementById("use191").style.fill = hslValue;
          break;
        case "5":
          htmlNode.getElementById("use31").style.fill = hslValue;
          break;
        case "6":
          htmlNode.getElementById("use171").style.fill = hslValue;
          break;
        case "7":
          htmlNode.getElementById("use101").style.fill = hslValue;
          break;
        case "8":
          htmlNode.getElementById("use81").style.fill = hslValue;
          break;
        case "9":
          htmlNode.getElementById("use51").style.fill = hslValue;
          break;
        case "10":
          htmlNode.getElementById("use161").style.fill = hslValue;
          break;
        case "11":
          htmlNode.getElementById("use71").style.fill = hslValue;
          break;
        case "12":
          htmlNode.getElementById("use41").style.fill = hslValue;
          break;
        case "13":
          htmlNode.getElementById("use181").style.fill = hslValue;
          break;
        case "14":
          htmlNode.getElementById("use61").style.fill = hslValue;
          break;
        case "15":
          htmlNode.getElementById("use151").style.fill = hslValue;
          break;
        case "16":
          htmlNode.getElementById("use91").style.fill = hslValue;
          break;
        case "17":
          htmlNode.getElementById("use131").style.fill = hslValue;
          break;
        case "18":
          htmlNode.getElementById("use201").style.fill = hslValue;
          break;
        case "19":
          htmlNode.getElementById("use111").style.fill = hslValue;
          break;
        case "20":
          htmlNode.getElementById("use21").style.fill = hslValue;
          break;
        case "BULL":
          htmlNode.getElementById("circle221").style.fill = hslValue;
          break;
      }
      break;
    
    case "T":
      switch(value){
        case "1":
          htmlNode.getElementById("use215").style.fill = hslValue;
          break;
        case "2":
          htmlNode.getElementById("use145").style.fill = hslValue;
          break;
        case "3":
          htmlNode.getElementById("use125").style.fill = hslValue;
          break;
        case "4":
          htmlNode.getElementById("use195").style.fill = hslValue;
          break;
        case "5":
          htmlNode.getElementById("use35").style.fill = hslValue;
          break;
        case "6":
          htmlNode.getElementById("use175").style.fill = hslValue;
          break;
        case "7":
          htmlNode.getElementById("use105").style.fill = hslValue;
          break;
        case "8":
          htmlNode.getElementById("use85").style.fill = hslValue;
          break;
        case "9":
          htmlNode.getElementById("use55").style.fill = hslValue;
          break;
        case "10":
          htmlNode.getElementById("use165").style.fill = hslValue;
          break;
        case "11":
          htmlNode.getElementById("use75").style.fill = hslValue;
          break;
        case "12":
          htmlNode.getElementById("use45").style.fill = hslValue;
          break;
        case "13":
          htmlNode.getElementById("use185").style.fill = hslValue;
          break;
        case "14":
          htmlNode.getElementById("use65").style.fill = hslValue;
          break;
        case "15":
          htmlNode.getElementById("use155").style.fill = hslValue;
          break;
        case "16":
          htmlNode.getElementById("use95").style.fill = hslValue;
          break;
        case "17":
          htmlNode.getElementById("use135").style.fill = hslValue;
          break;
        case "18":
          htmlNode.getElementById("use205").style.fill = hslValue;
          break;
        case "19":
          htmlNode.getElementById("use115").style.fill = hslValue;
          break;
        case "20":
          htmlNode.getElementById("use25").style.fill = hslValue;
          break;
      }
      break;
  }
}