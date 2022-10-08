function test() {
    let x = document.getElementsByClassName("input");
    let y = document.getElementsByName('y');
    let r = document.getElementsByName('r');
    let xv, yv, rv;
    for(let i = 0; i < y.length; i++){
        if (y[i].checked){
            yv = parseInt(y[i].value);
            break;
        }
    }
    for(let j = 0; j < r.length; j++) {
        if (r[j].checked) {
            rv = parseInt(r[j].value);
            break;
        }
    }
    event.preventDefault();
    xv = x[0].value;
    console.log(Math.abs(parseInt(xv)));
    if(!isNaN(xv) && !isNaN(yv) && !isNaN(rv)){
        if(Math.abs(parseInt(xv)) < 5){
            let data = new FormData();
            data.append('x', xv);
            data.append('y', yv);
            data.append('r', rv);
            fetch("../Server/Server.php",{
                method: "POST",
                body: data,
            })
                .then(res => res.text()).then(table => document.querySelector("#answers").innerHTML = table);
                // .then(res => res.text()).then(res => console.log(res));
        }
        else {
            alert("Проверьте значение x");
        }
    }
    else{
        alert("Проверьте входные данные");
    }
}

function swipe(){
    let y = document.getElementsByName('y');
    let r = document.getElementsByName('r');
    let yv = document.getElementsByClassName('label y');
    let rv = document.getElementsByClassName('label r');
    for(let i = 0; i < y.length; i++){
        yv[i].style.color = 'white'
        if (y[i].checked){
            yv[i].style.color = 'green';
        }
    }
    for(let i = 0; i < r.length; i++) {
        rv[i].style.color = 'white';
        if (r[i].checked) {
            rv[i].style.color = 'green';
        }
    }
}
function reset() {
    event.preventDefault();
    fetch("../Server/Reset.php",{
        method: "POST",
        body: null,
    })
        .then(res => res.text()).then(table => document.querySelector("#answers").innerHTML = table);
    event.preventDefault();
}
function upload() {
    fetch("../Server/Upload.php",{
        method: "POST",
        body: null,
    })
        .then(res => res.text()).then(table => document.querySelector("#answers").innerHTML = table);

}