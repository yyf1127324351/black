$(document).ready(function () {
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            queryList();
        }
    };


    $('#menu_tree').tree({
        url: "/menu/getAllMenuTree",
        cascadeCheck: false,
        onSelect: function (node) {
            //点击查询
            var finalMenuIds = node.id; //顶层id
            var childrenList = node.children; //第一层子节点
            if (null != childrenList && childrenList.length > 0) {
                for (var i = 0; i < childrenList.length; i++) {
                    var menu = childrenList[i];
                    finalMenuIds = finalMenuIds + "," + menu.id;
                    var childrenList2 = menu.children;
                    if (null != childrenList2 && childrenList2.length > 0) {
                        for (var j = 0; j < childrenList2.length; j++) {
                            var menu2 = childrenList2[j];
                            finalMenuIds = finalMenuIds + "," + menu2.id;
                            var childrenList3 = menu2.children;
                            if (null != childrenList3 && childrenList3.length > 0) {
                                for (var k = 0; k < childrenList3.length; k++) {
                                    var menu3 = childrenList3[k];
                                    finalMenuIds = finalMenuIds + "," + menu3.id;
                                }
                            }
                        }
                    }
                }

            }
            treeClickQueryList(finalMenuIds);
        },
        onBeforeExpand: function (node) {
        },
        //右键组织crud
        onContextMenu : function(e, node) {
            $(this).tree('select',node.target);
            e.preventDefault();

            debugger;
            var menuId = node.id;
            if (menuId == 0) {
                $('#menu_edit').hide();
                $('#menu_del').hide();
            }else {
                $('#menu_edit').show();
                $('#menu_del').show();
            }
            var type = node.type;
            if(type == 1) {
                layer.alert('末级菜单下不能新增！', {icon: 0, title: "提示"});
                return;
            }else {
                $('#handleMenu').menu('show', {
                    left : e.pageX,
                    top : e.pageY,

                    onClick: function(item) {
                        if(item.text == '新增') {

                            addInfo(node);
                        } else if(item.text == '编辑') {
                            if(node.type==null){
                                $.messager.alert('提示', '请勿编辑公司节点', 'warning');
                                return;
                            }
                            updateInfo(node);
                        } else if(item.text == '失效') {
                            if(node.type==null){
                                $.messager.alert('提示', '请勿删除公司节点', 'warning');
                                return;
                            }
                            disableInfo(node);
                        }else if(item.text == '导出'){
                            exportInfo(node);
                        }

                    }

                });
            }

        },
        onExpand: function (node) {
            node.id = node.id.split("_")[0];
        },
        onLoadSuccess: function (node) {
            $(".tree-icon,.tree-file").removeClass("tree-icon tree-file");
            $(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed");
        }
    });

    $("#data_table").datagrid({
        queryParams: getFormData("searchForm"), //参数
        url: '/menu/getMenuPageList',
        method: 'post',
        loadMsg: "数据装载中,请稍等....",
        nowrap: true, //单元格内容是否可换行
        fitColumns: false, //自适应网格宽度
        showFooter: false, //是否显示最后一行，统计使用
        pagination: true,
        checkbox: true,
        singleSelect: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        toolbar: '#button_tab',
        onLoadSuccess: function (data) {
            if (data.isOk == false) {
                $.messager.alert('提示', data.msg);
                console.log(data.data);
            }
        },
        frozenColumns: [[
            {title: '操作', field: 'id', width: 100, align: 'center',
                formatter: function (val, row) {
                    var id = row.id;
                    var html = "";
                    html = html + '<a class="sel_btn ch_cls" onclick="edit(' + id + ')" style="text-decoration:none;">编辑</a>';
                    return html;
                }
            },
            {title: '菜单名称', field: 'name', width: 190, align: 'center'}
        ]],
        columns: [[
            {title: '权限编码', field: 'code', width: 250, align: 'center'},
            {title: '类型', field: 'type', width: 50, align: 'center', sortable: true,
                formatter: function (val,row) {
                    var level = row.level;
                    if (level == 1) {
                        return "菜单栏";
                    } else if (level == 2){
                        return "菜单";
                    } else {
                        return "按钮";
                    }
                }
            },
            {title: '父节点', field: 'parentId', width: 60, align: 'center'},
            {title: '级别', field: 'level', width: 50, align: 'center', sortable: true},
            {title: '菜单地址', field: 'url', width: 400, align: 'left', sortable: true},
            {title: '是否有子节点', field: 'hasChild', width: 100, align: 'center',
                formatter: function (val) {
                    if (val == 0) {
                        return "无";
                    } else {
                        return "有";
                    }
                }
            },
            {title: '排序', field: 'sortNumber', width: 50, align: 'center', sortable: true},
            {title: '状态', field: 'deleted', width: 70, align: 'center', sortable: true,
                formatter: function (val) {
                    if (val == 0) {
                        return "使用中";
                    } else {
                        return "已删除";
                    }
                }
            }
        ]]


    });


});

function queryList() {
    var data = getFormData("search_form");
    $('#data_table').datagrid({url: '/menu/getMenuPageList', queryParams: data});
}

function treeClickQueryList(menuIds) {
    var data = getFormData("search_form");
    data.menuIds = menuIds;
    $('#data_table').datagrid({url: '/menu/getMenuPageList', queryParams: data});
}

function clearQuery() {
    $('#search_form').form('clear');
}

function getFormData(form) {
    var array = $("#" + form).serializeArray();
    var data = {};
    $.each(array, function () {
        var item = this;
        if (data[item["name"]]) {
            data[item["name"]] = data[item["name"]] + "," + item["value"];
        } else {
            data[item["name"]] = item["value"];
        }
    });
    return data;
}

