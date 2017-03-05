<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<style media="all">

h3 {display: inline;}

</style>
<head>
	<title>JttWs</title>
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="">JttWs</a>
		</div>
	</div>
</nav>

<div class="jumbotron">
	<div class="container">
		<h2>Welcome to the Jenkins Test Tracker!</h2>
	</div>
</div>

<div class="container">
	<table class="table">
		<colgroup>
			<col span="1" style="width: 10%;">
		</colgroup>
		<thead>
			<tr>
				<th>Filter</th>
				<th>Sort</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><select class="selectpicker" id="jobFilter" multiple
					data-live-search="true" onchange="filterAndRefreshJobs()">
						<c:forEach items="${entries}" var="entry">
							<c:if test="${entry.active}">
								<option selected>${entry.name()}</option>
							</c:if>
							<c:if test="${not entry.active}">
								<option>${entry.name()}</option>
							</c:if>
						</c:forEach>
				</select></td>
				<td><select class="selectpicker" id="jobSort"
					data-live-search="true" onchange="sortAndRefreshJobs()">
						<c:forEach items="${sort_options}" var="entry">
							<c:if test="${entry.active}">
								<option selected>${entry.name()}</option>
							</c:if>
							<c:if test="${not entry.active}">
								<option>${entry.name()}</option>
							</c:if>
						</c:forEach>
				</select></td>
			</tr>
		</tbody>
	</table>
</div>
<div class="container">

	<jsp:include page="job-table.jsp" />
	<footer>
		<p>&copy; Dan Grew 2017</p>
	</footer>
</div>

<script type="text/javascript" src="resources/core/js/table-submit.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/webapp/resources/core/js/jquery-3.1.1.min/jx"></script>
<script	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/i18n/defaults-*.min.js"></script>

</body>
</html>