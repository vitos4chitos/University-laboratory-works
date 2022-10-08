<table id="result_table" align="center" border="1" width="80%">
    <tr>
        <th width="15%">
            X
        </th>
        <th width="15%">
            Y
        </th>
        <th width="15%">
            R
        </th>
        <th width="20%">
            Current Time
        </th>
        <th width="20%">
            Execution Time
        </th>
        <th width="20%">
            Hit Result
        </th>
    </tr>
    <?php foreach ($_SESSION['history'] as $value) { ?>
        <tr align="center">
            <td  class = "values"><?php echo $value[0] ?></td>
            <td class = "values"><?php echo $value[1] ?></td>
            <td class = "values"><?php echo $value[2] ?></td>
            <td class = "values"><?php echo $value[3] ?></td>
            <td class = "values"><?php echo number_format($value[4], 10, ".", "") * 1000000 ?></td>
            <td class = "values"> <?php echo $value[5] ?></td>
        </tr>
    <?php }?>
    <tr>
        <td colspan="6" align="center">
            <input type="submit" value="Сброс" onclick="reset()">
        </td>
    </tr>
</table>