function OntologyClick(){
    let jsonObj = {};
    /*
        Jquery默认Content-Type为application/x-www-form-urlencoded类型
     */
    $.ajax({
        type: 'POST',
        url: "/ontology-reasoning",
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