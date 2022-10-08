function test(e) {
        let y = document.getElementsByClassName("input");
        let x = document.getElementsByName('x');
        let r = document.getElementsByName('r');
        let xv, yv, rv;
        event.preventDefault();
        for(let i = 0; i < x.length; i++){
                if (x[i].checked){
                        xv = parseFloat(x[i].value);
                        break;
                }
        }
        let con = 0;
        for(let j = 0; j < r.length; j++) {
                if (r[j].checked) {
                        rv = parseFloat(r[j].value);
                        con += 1;
                }
        }
        yv = y[0].value;
        event.preventDefault();
        if(!isNaN(xv) && !isNaN(yv) && !isNaN(rv) && yv.length > 0) {
                if (yv > -5 && yv < 3) {
                        if(con === 1){
                                event.preventDefault();
                                console.log(xv + " " + yv + " " + rv);
                                send(xv, yv, rv, null);
                        }
                        else{
                                alert("Проверьте количесво выбранных r")
                        }
                }
                else{
                        alert("Проверьте введённый y")
                }
        }
        else{
                alert("Проверьте, все ли данные вы ввели")
        }
}

function reset() {
    $.ajax(({
        type: "POST",
        data: {reset: "1"},
        url: "../ControllerServlet",
        success: function (e) {
            if(e.toString() === "400"){
                alert("Вы пытаетесь обойти систему ай ай ай")
            }
            else {
                console.log("success");
                document.querySelector("#answers").innerHTML = e.toString();
            }

        },
        error: function (e) {
            alert("Сервер недоступен, попробуйте позже")

        },}));
    if (sessionStorage.getItem('points') !== null){
        sessionStorage.setItem('points', "");
    }
    let doc = document.getElementById("svg");
    for (let i = 0; i < doc.childNodes.length; i++) {
        if (doc.childNodes[i].nodeName === "circle") {
            doc.childNodes[i].remove();
        }
    }


}

function swipe(){
    let x = document.getElementsByName('x');
    let r = document.getElementsByName('r');
    let xv = document.getElementsByClassName('label x');
    let rv = document.getElementsByClassName('label r');
    console.log("Зашёл в фукцию swipe")
    for(let i = 0; i < x.length; i++){
        if (x[i].checked){
            xv[i].style.color = 'green';
        }
        else{
            xv[i].style.color = 'white'
        }
    }
    for(let i = 0; i < r.length; i++) {
        if (r[i].checked) {
            rv[i].style.color = 'green';
        }
        else{
            rv[i].style.color = 'white';
        }
    }
}

const pt = svg.createSVGPoint();
if (sessionStorage.getItem('points') === null)
    sessionStorage.setItem('points', "");

$('#svg').click(function(e){
    let target = this.getBoundingClientRect();
    let x = e.clientX - target.left;
    let y = e.clientY - target.top;
    console.log(x + " " + y);
    let r = document.getElementsByName('r');
    let con = 0;
    let rv;
    for(let j = 0; j < r.length; j++) {
        if (r[j].checked) {
            rv = parseFloat(r[j].value);
            con += 1;
        }
    }
    if(con !== 1){
        alert("Проверьте введённый R");
    }
    else{
        let x_value = (((x - 150.5)/(100)) * rv).toFixed(2);
        let y_value = -(((y - 150)/(100)) * rv).toFixed(2);
        let circle = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
        pt.x = event.clientX;
        pt.y = event.clientY;
        const cursorpt = pt.matrixTransform(svg.getScreenCTM().inverse());
        circle.style.r = "3";
        circle.style.cx = cursorpt.x;
        circle.style.cy = cursorpt.y;
        console.log(cursorpt.x);
        console.log(cursorpt.y);
        sessionStorage.setItem("points", sessionStorage.getItem("points").concat(String(cursorpt.x).concat(";").concat(String(cursorpt.y).concat(";"))));
        send(x_value, y_value, rv, circle);
    }
});

function send(xv, yv, rv, circle) {
    $.ajax(({
        type: "POST",
        data: {x: xv, y: yv, r: rv},
        url: "../ControllerServlet",
        success: function (e) {
            if(!e.toString().includes("ErrorType:")) {
                if(circle !== null){
                    if(e.toString().includes("green")){
                        circle.style.fill = "green";
                        sessionStorage.setItem("points", sessionStorage.getItem("points").concat("green;"));
                    }
                    else {
                        circle.style.fill = "red";
                        sessionStorage.setItem("points", sessionStorage.getItem("points").concat("red;"));
                    }
                    document.getElementById("svg").appendChild(circle);
                }
                // document.querySelector("#answers").innerHTML = e.toString();
                location.href = 'http://localhost:8080/WEB_2_Web_exploded/Client/Table.jsp'
                // location.href = 'http://localhost:41200/WEB_2_Web/Client/Table.jsp'
            }
            else{
                alert(e.toString())
            }

        },
        error: function (e) {
            alert("Сервер недоступен, попробуйте позже")

        },
    }));

}
if (performance) {
    ptn = sessionStorage.getItem("points").split(";");
    for (let i = 0; i < ptn.length; i+=3) {
        let circle = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
        circle.style.fill = ptn[i + 2];
        circle.style.r = "3";
        circle.style.cx = ptn[i];
        circle.style.cy = ptn[i + 1];
        if (ptn[i] !== "")
            document.querySelector("#svg").appendChild(circle);
    }
}