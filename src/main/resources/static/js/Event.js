function EventClick(){
    let jsonObj = {
        "event_expression": document.getElementById("event-textarea").value
    };
    console.log(jsonObj);

    $.ajax({
        type: 'POST',
        url: "/event",
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