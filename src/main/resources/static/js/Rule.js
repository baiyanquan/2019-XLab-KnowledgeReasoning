function RuleClick(){
    let jsonObj = {
        "rule_expression": document.getElementById("rule-textarea").value
    };
    console.log(jsonObj);

    $.ajax({
        type: 'POST',
        url: "/rule-reasoning",
        dataType: "json",
        data: JSON.stringify(jsonObj),
        contentType : "application/json",
        success: function(data) {
            console.log(JSON.stringify(data));
            alert(data.data);
        },
        error: function() {
            console.log("error...");
        }
    });
}