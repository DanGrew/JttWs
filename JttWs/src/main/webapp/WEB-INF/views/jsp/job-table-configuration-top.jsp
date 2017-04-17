<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<div id='job-table-configuration-top' style="width: 100%; float: right; margin-top: 20px">
	<%
	   response.setIntHeader( "Refresh", 5 );
	%>
	<table class="table">
		<colgroup>
			<col span="1" style="width: 20%;">
			<col span="1" style="width: 20%;">
		</colgroup>
		<thead>
			<tr>
				<th>Columns</th>
				<th>Sort</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><select class="selectpicker" id="columnFilter" multiple
					data-live-search="true" onchange="filterColumns()">
						<c:forEach items="${data.columns()}" var="entry">
							<c:if test="${entry.isActive()}">
								<option selected>${entry.name()}</option>
							</c:if>
							<c:if test="${not entry.isActive()}">
								<option>${entry.name()}</option>
							</c:if>
						</c:forEach>
				</select></td>

				<td><select class="selectpicker" id="jobSort"
					data-live-search="true" onchange="sortAndRefreshJobs()">
						<c:forEach items="${data.sortings()}" var="entry">
							<c:if test="${entry.isActive()}">
								<option selected>${entry.name()}</option>
							</c:if>
							<c:if test="${not entry.isActive()}">
								<option>${entry.name()}</option>
							</c:if>
						</c:forEach>
				</select></td>
			</tr>
		</tbody>
	</table>
</div>

</html>