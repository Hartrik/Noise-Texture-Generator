
// tato metoda je volána při exportu
function out(components) {
    for each (var component in components) {
        print(format(component));
    }
}

function format(component) {
    var color = component.getColor();
    var count = component.getCount();
    
    var r = color.getRed();
    var g = color.getGreen();
    var b = color.getBlue();
    
    return java.lang.String.format("%d, %d, %d | %d", r, g, b, count);
}