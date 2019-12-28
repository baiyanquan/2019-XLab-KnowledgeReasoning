function QueryClick(){
    let jsonObj = {
        "query_expression": document.getElementById("query-textarea").value
    };
    console.log(jsonObj);

    $.ajax({
        type: 'POST',
        url: "/query",
        dataType: "json",
        data: JSON.stringify(jsonObj),
        contentType : "application/json",
        success: function(data) {
            console.log(data);
            alert(data.data);
        },
        error: function() {
            console.log("error...");
        }
    });
}