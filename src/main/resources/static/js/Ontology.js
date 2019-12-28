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
            if(data.data.length){
                let buf_str = "";
                for(let i=0;i<data.data.length;++i){
                    buf_str += String(i+1) + ". " + data.data[i] + "\n";
                }
                alert(buf_str);
            }else{
                alert("本体推理结果为空!")
            }

        },
        error: function() {
            console.log("error...");
        }
    });
}