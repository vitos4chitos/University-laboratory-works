$('#reset').click(function(e){
    if (sessionStorage.getItem('points') !== null){
        sessionStorage.setItem('points', "");
    }
    document.querySelectorAll("circle").forEach((e) => e.remove());
    sessionStorage.setItem('points', "");
})

const pt = document.getElementById("svg").createSVGPoint();
if (sessionStorage.getItem('points') === null)
    sessionStorage.setItem('points', "");

$('#svg').click(function(e){
    let target = this.getBoundingClientRect();
    let x = e.clientX - target.left;
    let y = e.clientY - target.top;
    let r = document.getElementById('newDot:r_value');
    console.log(r.innerText)
    if(r.innerText !== "") {
        let rv = parseFloat(r.innerText);
        let con = 1;
        // for(let j = 0; j < r.length; j++) {
        //     if (r[j].checked) {
        //         rv = parseFloat(r[j].value);
        //         con += 1;
        //     }
        // }
        let x_value = (((x - 150.5) / (100)) * rv).toFixed(2);
        let y_value = -(((y - 150) / (100)) * rv).toFixed(2);
        let circle = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
        pt.x = event.clientX;
        pt.y = event.clientY;
        const cursorpt = pt.matrixTransform(document.getElementById("svg").getScreenCTM().inverse());
        circle.style.r = "3";
        circle.style.cx = cursorpt.x;
        circle.style.cy = cursorpt.y;
        sessionStorage.setItem("points", sessionStorage.getItem("points").concat(String(x_value).concat(";").concat(String(y_value).concat(";"))));
        sendJsf([{name: "x", value: x_value}, {name: "y", value: y_value}, {name: "r", value: rv}]);
        if(check(x_value, y_value, rv)){
            circle.style.fill = "green";
            sessionStorage.setItem("points", sessionStorage.getItem("points").concat("green;"));
        }
        else{
            circle.style.fill = "red";
            sessionStorage.setItem("points", sessionStorage.getItem("points").concat("red;"));
        }
        document.getElementById("svg").appendChild(circle);
    }
    else{
        alert("Введите значение R")
    }
});

function repaint(rr){
    let r = document.getElementById('newDot:r_value');
    let rv = parseFloat(r.innerText);
    let target = document.getElementById('svg').getBoundingClientRect();
    document.querySelectorAll("circle").forEach((e) => e.remove());
    ptn = sessionStorage.getItem("points").split(";");
    for (let i = 0; i < ptn.length; i+=3) {
        let circle = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
        circle.style.fill = ptn[i + 2];
        circle.style.r = "3";
        circle.style.cx = ((ptn[i] * 100) / parseFloat(rr)) + 150.5;
        circle.style.cy = ((-ptn[i + 1] * 100) / parseFloat(rr)) + 150;
        console.log((((parseFloat(ptn[i]) * 100) / parseFloat(rr)) + 150.5) + "  " + ((-(parseFloat(ptn[i + 1]) * 100) / parseFloat(rr)) + 150));
        console.log(ptn[i] + " " + ptn[i + 1]);
        console.log(rv);
        if (ptn[i] !== "")
            document.querySelector("#svg").appendChild(circle);
    }
}

if (performance) {
    repaint(3)
}

function sendJsf(){

}

function check(x, y, r){
    if (x >= 0 && y >= 0)
        return x <= r && y <= r;
    else if (x <= 0 && y <= 0)
        return (x * x + y * y <= r * r);
    else if (x >= 0 && y <= 0)
        return (2 * x + y >= -r);
    else
        return false;
}