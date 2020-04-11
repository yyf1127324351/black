$(document).ready(function () {
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            queryHolidayDays();
        }
    };


    $('#menu_tree').tree({
        url : "/menu/getAllMenuTree",
        cascadeCheck:false,
        onSelect : function(node) {
            debugger;
            //点击查询
            // search();
        },
        onBeforeExpand : function(node) {
            debugger;
            // node.id = node.id + "_"+ getSalaryCompany(node);
        },
        onExpand : function(node) {
            debugger;
            node.id = node.id.split("_")[0];
        },
        onLoadSuccess : function(node) {
            debugger;
            $(".tree-icon,.tree-file").removeClass("tree-icon tree-file");
            $(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed");
        }
    });


});
