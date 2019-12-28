function MetadataLayerClick(){
    let jsonObj = {};
    /*
        Jquery默认Content-Type为application/x-www-form-urlencoded类型
     */
    $.ajax({
        type: 'POST',
        url: "/metadata-layer-construct",
        dataType: "json",
        data: JSON.stringify(jsonObj),
        contentType : "application/json",
        success: function(data) {
            console.log(data);
            if(data.flag){
                alert("构建元数据层成功!");
            }
        },
        error: function() {
            console.log("error...");
        }
    });
}