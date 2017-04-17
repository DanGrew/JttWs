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
    var sorting = $('#jobSort').val().toString();
    $.ajax({
        type : "GET",
        url : "jobs-table",
        data : {
            "sort" : sorting
        },
        success : function(result) {
            $('#job-table').html(result);
        }
    });
}

function filterColumns() {
    var filter = $('#columnFilter').val().toString();
    $.ajax({
        type : "GET",
        url : "jobs-table",
        data : {
            "columns" : filter
        },
        success : function(result) {
            $('#job-table').html(result);
        }
    });
}