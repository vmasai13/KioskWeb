<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>chipnpin</title>
		
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		<link href="static/css/chipnpin.css" rel="stylesheet"/>
		
		<script type='text/javascript' src='http://www.google.com/jsapi'></script>
		<script type="text/javascript"
			src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script type='text/javascript'>
		      google.load('visualization', '1', {'packages':['annotatedtimeline']});
		      google.setOnLoadCallback(drawChart);
		      function drawChart() {
		        var data = new google.visualization.DataTable();
		        data.addColumn('date', 'Date');
		        data.addColumn('number', 'ErrorCode0004');
		        data.addColumn('number', 'ErrorCode0014');
		        data.addColumn('number', 'ErrorCode0017');
		        data.addColumn('number', 'ErrorCode0018');
		        
		        var jsonResp = [];
		      
		        '<c:forEach var="reportTrend" items="${jsonObject}">';
		               
		        		jsonResp.push( [new Date(${reportTrend.key}), ${reportTrend.value[0]},${reportTrend.value[1]},${reportTrend.value[2]},${reportTrend.value[3]}]);
				'</c:forEach>';
				data.addRows(jsonResp);
		
		        var chart = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
		        chart.draw(data, {
					displayAnnotations : true,
					colors : [ 'green', '#DF7401', '#B40404', '#DF01D7'],
					displayExactValues : false
				});
		      }
		    </script>
	</head>
	<body>
		<form id="home" action="home" >
		<div id="details4">
			<button type="submit" class="btn btn-info home">Home</button>
		</div>
		</form>	
		<div class="page-header headerextra" >
			<h2  >CHIP AND PIN PAYMENT STATISTICS</h2>
		</div>
		<center>
			<div id='chart_div' style='width: 800px; height: 340px;'></div>	
		</center>
	</body>
</html>