<?php
function checkAcceptableValues($x, $y, $r){
    if ($x >= 0 && $y >= 0)
        return FirstHalf($x, $y, $r);
    if ($x <= 0 && $y > 0)
        return SecondHalf($x, $y, $r);
    if ($x > 0 && $y < 0)
        return FourthHalf();
    if ($x <= 0 && $y <= 0)
        return ThirdHalf($x, $y, $r);
}


function FirstHalf($x, $y, $r){
    return ($x <= $r && $y <= $r);
}

function SecondHalf($x, $y, $r){
    return ($x + 2 * $y - $r/2 <= 0);
}

function ThirdHalf($x, $y, $r){
    return (($x * $x + $y * $y) <= $r * $r);
}

function FourthHalf(){
    return false;
}

function check($x, $y, $r){
    if(strlen($r) != 1){
        return false;
    }
    elseif (!($r >= 1 && $r <= 5)){
        return false;
    }
    if(!(strlen($y) == 1 || strlen($y) == 2)){
        return false;
    }
    elseif($y >= 5 || $y <= -5){
        return false;
    }
    $xx = (int) $x;
    if($xx >= 5 || $xx <= -5){
        return false;
    }
    return true;

}

session_start();
date_default_timezone_set('Europe/Moscow');
$currentTime = date("H:i:s");
$start = microtime(true);
$x = ($_POST['x']);
$y = ($_POST['y']);
$r = ($_POST['r']);
$xs = $x;


if(is_numeric($x) && is_numeric($y) && is_numeric($r)){
    if(check($x, $y, $r)) {
        $res = "FALSE";
        if (checkAcceptableValues($x, $y, $r)) {
            $res = "TRUE";
        }
        $time = microtime(true) - $start;
        $result_table = array($xs, (int)$y, (int)$r, $currentTime, $time, $res);
        if (!isset($_SESSION['history'])) {
            $_SESSION['history'] = array();

        }

        array_push($_SESSION['history'], $result_table);

        include "table.php";
    }
    else{
        http_response_code(400);
    }
}
else{
    http_response_code(400);
}