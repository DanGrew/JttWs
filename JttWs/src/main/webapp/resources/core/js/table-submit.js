function filterAndRefreshJobs() {
    var f = $('#jobFilter').val().toString();
    $.ajax({
        type : "GET",
        url : "jobs-table",
        data : {
            "filteredJobsList" : f
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
