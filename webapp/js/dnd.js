window.onload = init;

function init() {
  alert("Drag and Drop");
  load();
}


/*
 * Drag & Drop
 * (http://tech.pro/tutorial/688/javascript-tutorial-drag-drop-lists)
 */

var List; // set to the div surrounding all the list elements (to avoid calling getElementById)
var PlaceHolder; // div to be used as a placeholder/insertion marker when an item is being dragged

function load()
{
  List = document.getElementById("list");  
  PlaceHolder = document.createElement("DIV");
  PlaceHolder.className = "list";
  PlaceHolder.style.backgroundColor = "rgb(225,225,225)";
  PlaceHolder.SourceI = null;

  // create drag objects for each of the elements in the list
  // (and let them do their thing in the background)
  // Callbacks:
  // itemDragBegin - for when a drag begins
  // itemMoved - for each move during a drag
  // itemDragEnd - for when the drag completes
  new dragObject("e1", null, null, null, itemDragBegin, itemMoved, itemDragEnd, false);
  new dragObject("e2", null, null, null, itemDragBegin, itemMoved, itemDragEnd, false);
  new dragObject("e3", null, null, null, itemDragBegin, itemMoved, itemDragEnd, false);
  new dragObject("e4", null, null, null, itemDragBegin, itemMoved, itemDragEnd, false);
  new dragObject("e5", null, null, null, itemDragBegin, itemMoved, itemDragEnd, false);
}

// called when the user clicks on an item in the list and initiates a drag
// arguments: the mouse down event object, and the element that was clicked on
function itemDragBegin(eventObj, element)
{ 
  element.style.top = element.offsetTop + 'px';
  element.style.left = element.offsetLeft + 'px';
  element.className = "drag";
  
  PlaceHolder.style.height = element.style.height;
  List.insertBefore(PlaceHolder, element);
  PlaceHolder.SourceI = element;
}

// called every time the user moves the mouse and (the drag element will be moved)
function itemMoved(newPos, element, eventObj)
{
  eventObj = eventObj ? eventObj : window.event;
  var yPos = newPos.Y + (eventObj.layerY ? eventObj.layerY : eventObj.offsetY);

  var temp;
  var bestItem = "end";
  for(var i=0; i<List.childNodes.length; i++)
  {
    if(List.childNodes[i].className == "list")
    {
      temp = parseInt(List.childNodes[i].style.height);
      if(temp/2 >= yPos)
      {
        bestItem = List.childNodes[i];
        break;
      }     
      yPos -= temp;
    }
  }

  if(bestItem == PlaceHolder || bestItem == PlaceHolder.SourceI)
    return;

  PlaceHolder.SourceI = bestItem;
  if(bestItem != "end")
    List.insertBefore(PlaceHolder, List.childNodes[i]);
  else
    List.appendChild(PlaceHolder);
  }
}

// when the user releases the mouse button and drops the element
function itemDragEnd(element)
{
  if(PlaceHolder.SourceI != null)
  {
    PlaceHolder.SourceI = null;  
    List.replaceChild(element, PlaceHolder);
  }

  element.className = 'list';
  element.style.top = '0px';
  element.style.left = '0px';
}
