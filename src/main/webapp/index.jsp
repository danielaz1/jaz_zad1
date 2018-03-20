<%@page language="java" contentType="text/html; ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form action="loan" method="post">
    <label>Wnioskowana kwota kredytu: <input type="number" step="0.01" id="amount" name="amount"/></label> <br/>
    <label>Ilosc rat: <input type="number" id="number" step="0.01" name="number"/></label> <br/>
    <label>Oprocentowanie: <input type="number" step="0.0001" id="interest" name="interest"/></label> <br/>
    <label>Oplata stala: <input type="number" step="0.01" id="fee" name="fee"/></label> <br/>
    <label>Rodzaj rat</label><br/>
    <label> <input type="radio" name="type" value="decreasing"/> Malejaca</label> <br/>
    <label> <input type="radio" name="type" value="constant" checked/> Stala</label> <br/> <br/>
    <label> <input type="checkbox" name="pdf" value="pdf"/> Wygeneruj pdf</label><br/> <br/>
    <input type="submit" value="wyslij"/>
</form>

</body>
</html>