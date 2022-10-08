<?php
session_start();

if (isset($_SESSION['history'])) {
    $_SESSION['history'] = array();
}
?>
<table id="result_table" align="center" border="1" width="80%">
    <tr>
        <td colspan="6" align="center">
            Сделайте запрос
        </td>
    </tr>
</table>

