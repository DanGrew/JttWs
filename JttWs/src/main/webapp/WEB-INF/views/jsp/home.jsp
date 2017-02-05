<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>JttWs</title>

<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">JttWs</a>
		</div>
	</div>
</nav>

<div class="jumbotron">
	<div class="container">
		<h2>Welcome to the Jenkins Test Tracker!</h2>
	</div>
</div>

<div class="container">

	<table class="table table-striped">
		<colgroup>
			<col span="1" style="width: 15%;">
			<col span="1" style="width: 30%;">
			<col span="1" style="width: 15%;">
			<col span="1" style="width: 20%;">
			<col span="1" style="width: 20%;">
		</colgroup>
		<thead>
			<tr>
				<th>Job Name</th>
				<th>Progress</th>
				<th>Status</th>
				<th>Timestamp</th>
				<th>Committers</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${jobs}" var="job">
				<tr>
					<td><c:out value="${job.name()}"/></td>
					<td>
						<div class="progress">
							<div class="progress-bar" style="width: ${job.progress()}%"></div>
						</div>
					</td>
					<td><c:out value="${job.status()}"/></td>
					<td><c:out value="${job.timestamp()}"/></td>
					<td><c:out value="${job.committers()}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<hr>
	<footer>
		<p>&copy; Dan Grew 2017</p>
	</footer>
</div>

</body>
</html>