<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.BillGeneration"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bills</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/bills.js"></script>
</head>
<body>
<div class="container"><div class="row"><div class="col-6">
<h1>Bill Generation</h1>
<form id="formBill" name="formBill">
User Name:
<input id="username" name="username" type="text"
class="form-control form-control-sm">
<br> Account NO:
<input id="ano" name="ano" type="text"
class="form-control form-control-sm">
<br> Address:
<input id="address" name="address" type="text"
class="form-control form-control-sm">
<br> Units:
<input id="units" name="units" type="text"
class="form-control form-control-sm">
<br>Amount
<input id="amount" name="amount" type="text"
class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" 
class="btn btn-primary"> <input type="hidden" 
	id="hidBillNOSave" name="hidBillNOSave" value="">
</form>

<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divBillsGrid">
<%
BillGeneration billObj = new BillGeneration();
out.print(billObj.readBills());
%>
</div>
</div> </div> </div>
</body>
</html>