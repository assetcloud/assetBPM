var rolesetting = {
    check: {
        enable: true
    },view: {
        dblClickExpand: dblClickExpand
    },data: {
        simpleData: {
            enable: true
        }
    },callback:{
        onCheck:zTreeOnClick
    }
};
var zNodes =[
    { id:10011001,name:"角色A"},
    { id:10011002,name:"角色B"},
    { id:10011003,name:"角色C"},
    { id:10011004,name:"角色D"},
];
function dblClickExpand(treeId, treeNode) {
    return treeNode.level > 0;
}
function zTreeOnClick(event, treeId, treeNode) {
    var treeObj = jQuery.fn.zTree.getZTreeObj("roleZtree");
    nodes = treeObj.getCheckedNodes(true);
    var v1 = "";//id
    var v2 = "";//name
    var v3 = "<li style='height:30px;'>名称</li>";//li
    for (var i = 0; i < nodes.length; i++) {
        if(i==(nodes.length-1)) {
            v1 = v1 + nodes[i].id;
            v2 = v2 + nodes[i].name;
        }else {
            v1 = v1 + nodes[i].id + ",";
            v2 = v2 + nodes[i].name + ",";
        }
        v3 = v3 +"<li style='height:25px;'>"+ nodes[i].name + "</li>";
    }
    jQuery('#froleids').val(v1);
    jQuery('#frolenames').val(v2);
    jQuery('#lirolenames').val(v3);
}
function gplus() {
    jQuery('#resultsrolesZtree').html("");
    jQuery('#resultsrolesZtree').append(jQuery('#lirolenames').val());
}
jQuery(document).ready(function(){

    jQuery.ajax({
        url:"/permission/api/role?fetchProperties=id,name",
        type:"get",
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data!=null&&data!="") {
                zNodes = data;
                jQuery.fn.zTree.init(jQuery("#roleZtree"),rolesetting, zNodes);
            }
        },
        error: function(){

        }
    });
    //jQuery.fn.zTree.init(jQuery("#roleZtree"),rolesetting);
    jQuery.fn.zTree.init(jQuery("#roleZtree"),rolesetting, zNodes);
    jQuery('#resultsrolesZtree').append("<li style='height:30px;'>名称</li>");
});