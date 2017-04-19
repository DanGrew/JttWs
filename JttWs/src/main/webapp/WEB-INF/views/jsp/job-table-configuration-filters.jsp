<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<div id='job-table-configuration' style="width: 100%; float: right; margin-top: 20px">
	<%
	   response.setIntHeader( "Refresh", 5 );
	%>
	<table class="table">
		<colgroup>
			<c:forEach items="${data.columns()}" var="entry">
				<c:if test="${entry.isActive()}">
					<col span="1" style="width: 20%;">
				</c:if>
			</c:forEach>
		</colgroup>
		<thead>
			<tr>
				<c:forEach items="${data.columns()}" var="entry">
					<c:if test="${entry.isActive()}">
						<th>${entry.name()} Filter</th>
					</c:if>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:forEach items="${data.columns()}" var="column">
					<c:if test="${column.isActive()}">
						<td><select class="selectpicker" id="${column.id()}" multiple
							data-live-search="true" onchange="filterAndRefreshJobs( this )">
								<c:forEach items="${data.filtersFor( column )}" var="entry">
									<c:if test="${entry.isActive()}">
										<option selected>${entry.name()}</option>
									</c:if>
									<c:if test="${not entry.isActive()}">
										<option>${entry.name()}</option>
									</c:if>
								</c:forEach>
						</select></td>
					</c:if>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>

</html>