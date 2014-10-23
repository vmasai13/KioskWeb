<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
  .highlight { 
  	background:red;
  }
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="static/study/jquery.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap-dropdown.js"></script>
<link href="static/css/chipnpin.css" rel="stylesheet"/>
<link rel="icon" href="static/images/favicon.gif" type="image/x-icon" />

<title>Welcome</title>
</head>

<body>
	<div class="page-header headerextra" >
		<h1 headerchip-pin>CHIP N PIN REPORTING TOOL!!!!</h1>
	</div>
	<nav class="navbar navbar-default" role="navigation">
		<form class="navbar-form navbar-left" action="perKiosk"  role="search">
		  <div class="form-group navbaruser">
		    <input type="text" name="name" class="form-control " placeholder="Search KAC">
		  </div>
		  <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Search</button>
		</form>
	</nav>
	<div id="content" class="content-style">
		<div id="details1">
			<form id="result" action="result" >
					<button type="submit" class="btn btn-success btn-lg divpos">Over All Status Of Payment</button>
			</form>
		</div>
		
		
		
		<div id="details4">	
			<form id="compareNGK" action="compareNGK" >
				<button type="submit" class="btn btn-success btn-lg divpos">Comparing NGK with BLS</button>
			</form>	
		</div>
		<div id="details3" >
			<form id="kioskIdsNGKSeries0" action="kioskIdsNGKSeries0" >
					<button type="submit" class="btn btn-success btn-lg divpos">NGK Kiosk Series0</button>
			</form>
		</div>
		<div id="details7" >
			<form id="kioskIdsNGKSeries1_AMS" action="kioskIdsNGKSeries1_AMS" >
					<button type="submit" class="btn btn-success btn-lg divpos">NGK Kiosk Series1 - AMS</button>
			</form>
		</div>
		<div id="details8" >
			<form id="kioskIdsNGKSeries1_CDG" action="kioskIdsNGKSeries1_CDG" >
					<button type="submit" class="btn btn-success btn-lg divpos">NGK Kiosk Series1 - CDG</button>
			</form>
		</div>
		<div id="details5">	
			<form id="errorCodes" action="errorCodes" >
				<button type="submit" class="btn btn-success btn-lg divpos">Error Codes</button>
			</form>	
		</div>
		<!-- <div id="details6">
			<form class="navbar-form navbar-left" action="StatisticsAMSorCDG"  role="search">
			  <div class="form-group navbaruser">
			    <input type="text" name="station" class="form-control " placeholder="Search kiosk Id">
			  </div>
			  <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Search</button>
			</form>
		</div> -->
		<!-- <div id="details7">
		<form id="StatisticsAMSorCDG" action="StatisticsAMSorCDG" >
			<div class="btn-group">
				  <button type="button" name="station"  class="btn btn-success btn-lg dropdown-toggle" data-toggle="dropdown">
				    Select Airport <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu">
				    <li><a href="#">AMS</a></li>
				    <li><a href="#">CDG</a></li>
				    
				  </ul>
			</div>
		</form> 
		</div>-->
	</div>
	<!-- <div id="details6">
	Single button
		<div class="btn-group">
		  <button type="button" class="btn btn-success btn-lg dropdown-toggle" data-toggle="dropdown">
		    Action <span class="caret"></span>
		  </button>
		  <ul class="dropdown-menu" role="menu">
		    <li>Action</a></li>
		    <li>Another action</a></li>
		    
		  </ul>
		</div>
	</div> -->
	<div id="details6">
		<form id="amsKiosks" action="amsKiosks" >
				<button type="submit" class="btn btn-success btn-lg divpos">IBM Kiosks AMS</button>
			</form>	
	</div>
</body>
</html>