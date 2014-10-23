<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script> -->
<script type="text/javascript" src="static/study/jquery.js"></script>
<script type="text/javascript" src="static/bootstrap/js/bootstrap-dropdown.js"></script>

<link href="static/study/bootstrap.css" rel="stylesheet"/>

<body>
	 <div class="well">
            <ul class="nav nav-pills">
              <li class="active"><a href="#">Regular link</a></li>
              <li class="dropdown">
                <a class="dropdown-toggle" id="drop4" role="button" data-toggle="dropdown" href="#">Dropdown <b class="caret"></b></a>
                <ul id="menu1" class="dropdown-menu" role="menu" aria-labelledby="drop4">
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
                  <li role="presentation" class="divider"></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
                </ul>
              </li>
              <li class="dropdown">
                <a class="dropdown-toggle" id="drop5" role="button" data-toggle="dropdown" href="#">Dropdown 2 <b class="caret"></b></a>
                <ul id="menu2" class="dropdown-menu" role="menu" aria-labelledby="drop5">
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
                  <li role="presentation" class="divider"></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
                </ul>
              </li>
              <li class="dropdown">
                <a class="dropdown-toggle" id="drop5" role="button" data-toggle="dropdown" href="#">Dropdown 3 <b class="caret"></b></a>
                <ul id="menu3" class="dropdown-menu" role="menu" aria-labelledby="drop5">
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
                  <li role="presentation" class="divider"></li>
                  <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
                </ul>
              </li>
            </ul> <!-- /tabs -->
          </div>
    <div class="btn-group">
  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
    Select Airport <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu">
    <li><a href="#">AMS</a></li>
    <li><a href="#">CDG</a></li>
    
  </ul>
</div>
</body>
</html>