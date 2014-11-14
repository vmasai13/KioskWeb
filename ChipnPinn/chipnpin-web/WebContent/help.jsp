<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>jQuery UI Datepicker - Default functionality</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
  $(function() {
	    $( "#from, #froma" ).datepicker({
	      defaultDate: "+1w",
	      changeMonth: true,
	      numberOfMonths: 2,
	      onClose: function( selectedDate ) {
	        $( "#to, #toa" ).datepicker( "option", "minDate", selectedDate );
	      }
	    });
	    $( "#to, #toa").datepicker({
	      defaultDate: "+1w",
	      changeMonth: true,
	      numberOfMonths: 2,
	      onClose: function( selectedDate ) {
	        $( "#from, #froma").datepicker( "option", "maxDate", selectedDate );
	      }
	    });
	  });
  
	  </script>
</head>
<body>

	<label for="from">From</label>
	<input type="text" id="from" name="from">
	<label for="to">to</label>
	<input type="text" id="toa" name="to">

	<label for="froma">From</label>
	<input type="text" id="froma" name="from">
	<label for="toa">to</label>
	<input type="text" id="to" name="to">

</body>
</html>