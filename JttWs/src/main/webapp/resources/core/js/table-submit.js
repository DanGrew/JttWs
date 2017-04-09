function filterAndRefreshJobs( filter ) {
    var name = '#' + filter.id
    var value = $(name).val();
    
    var parameter = value == null ? "" : value.toString();
    $.ajax({
        type : "GET",
        url : "jobs-table",
        data : {
            "id" : filter.id,
            "value" : parameter
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