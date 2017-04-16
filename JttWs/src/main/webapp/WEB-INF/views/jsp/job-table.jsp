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
			<c:forEach items="${data.columns()}" var="column">
				<col span="1" style="width: 20%;">
			</c:forEach>
		</colgroup>
		<thead>
			<tr>
				<c:forEach items="${data.columns()}" var="column">
					<th>${column.name()}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.jobs()}" var="job">
				<tr>
					<c:forEach items="${data.columns()}" var="column">
						<td>
							<c:if test="${column.type() eq 'String'}">
								<c:out value="${column.valueFor( job )}" />
							</c:if>
							<c:if test="${column.type() eq 'ProgressBar'}">
								<div class="progress">
									<div class="progress-bar" style="width: ${column.valueFor( job )}%"></div>
								</div>
							</c:if>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


</html>