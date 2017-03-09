function filterAndRefreshJobs() {
    var j_value = $('#jobFilter').val();
    var u_value = $('#userFilter').val();
    
    var jobs = j_value == null ? "" : j_value.toString();
    var users = u_value == null ? "" : u_value.toString();
    $.ajax({
        type : "GET",
        url : "jobs-table",
        data : {
            "filteredJobsList" : jobs,
            "filteredUsersList" : users
        },
        success : function(result) {
            $('#job-table').html(result);
        }
    });
}

function sortAndRefreshJobs() {
    var f = $('#jobSort').val().toString();
    $.ajax({
        type : "GET",
        url : "jobs-table",
        data : {
            "sort" : f
        },
        success : function(result) {
            $('#job-table').html(result);
        }
    });
}