<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<div id='job-table' style="width: 100%; float: right; margin-top: 20px">
	<%
	   response.setIntHeader( "Refresh", 5 );
	%>
	<table class="table table-striped">
		<colgroup>
			<c:forEach items="${columns}" var="column">
				<col span="1" style="width: ${column.width()}%;">
			</c:forEach>
		</colgroup>
		<thead>
			<tr>
				<c:forEach items="${columns}" var="column">
					<th>${column}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${jobs}" var="job">
				<tr>
					<td><c:out value="${job.name()}" /></td>
					<td>
						<div class="progress">
							<div class="progress-bar" style="width: ${job.progress()}%"></div>
						</div>
					</td>
					<td><c:out value="${job.status()}" /></td>
					<td><c:out value="${job.timestamp()}" /></td>
					<td><c:out value="${job.committers()}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


</html>