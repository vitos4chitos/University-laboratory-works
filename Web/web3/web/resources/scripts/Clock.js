function currentTime() {
    let currentTime = new Date();
    let minutes = currentTime.getMinutes();
    let seconds = currentTime.getSeconds();
    let day = currentTime.getDate();
    let month = currentTime.getMonth();
    if (minutes < 10) {
        minutes = '0' + minutes;
    }
    if (seconds < 10) {
        seconds = '0' + seconds;
    }
    if (day < 10) {
        day = '0' + day;
    }
    if (month < 10) {
        month = '0' + month;
    }
    document.getElementById('date').innerHTML = day + "." + month + "." + currentTime.getFullYear();
    document.getElementById('hours').innerHTML = currentTime.getHours() + ":";
    document.getElementById('minutes').innerHTML = minutes + ":";
    document.getElementById('seconds').innerHTML = seconds;
}

setInterval(currentTime, 7000);