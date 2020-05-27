$(document).ready(function () {
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            queryList();
        }
    };

    $("#addEditDialog").dialog({
        width:'600',
        height:'330',
        close : true,
        shadow:false,
        modal:true,
        buttons:[{
            text:'提交',
            iconCls:'icon-ok',
            handler:function(){
                addUpdateRole();
            }
        },{
            text:'取消',
            iconCls:'icon-cancel',
            handler:function(){
                $('#addEditDialog').dialog('close');
            }
        }],
        onClose:function(){
            //清空所有数据
            $('#id').val('');
            $('#roleCode').textbox('clear');
            $('#roleName').textbox('clear');
        },
        closable: true,
        closed: true   //已关闭
    });

    $("#data_table").datagrid({
        queryParams: getFormData("searchForm"), //参数
        url: '/role/getRolePageList',
        method: 'post',
        loadMsg: "数据装载中,请稍等....",
        nowrap: true, //单元格内容是否可换行
        fitColumns: true, //自适应网格宽度
        showFooter: false, //是否显示最后一行，统计使用
        pagination: true,
        checkbox: true,
        singleSelect: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        toolbar: '#button_tab',
        onLoadSuccess: function (data) {
        },
        frozenColumns: [[
            {title: '操作', field: 'id', width: 110, align: 'center',
                formatter: function (val, row, index) {
                    var html = "";
                    var status = row.status;
                    var id = row.id;
                    if (status == 1){
                        html = html + '<a class="sel_btn ch_cls" onclick="offUseRole(' + id + ')" style="text-decoration:none;">停用</a>&nbsp;';
                        html = html + '<a class="sel_btn ch_cls" onclick="openEdit(' + index + ')" style="text-decoration:none;">编辑</a>';
                    }else {
                        html = html + '<a class="sel_btn ch_cls" onclick="onUseRole(' + id + ')" style="text-decoration:none;">启用</a>';
                    }
                    return html;
                }
            },
            {title: '状态', field: 'status', width: 100, align: 'center',
                formatter: function (val, row, index) {
                    var status = row.status;
                    if (status == 1) {
                        return "使用中";
                    }else {
                        return "<font color='#216DDD'>已停用</font>";
                    }

                }
            },
            {title: '角色编码', field: 'roleCode', width: 150, align: 'center'},
            {title: '角色名称', field: 'roleName', width: 220, align: 'center'}

        ]],
        columns: [[
            {title: '备注', field: 'remark', width: 200, align: 'center'}
        ]]


    });

});

function clearQuery() {
    $('#search_form').form('clear');
    queryList();
}

function queryList() {
    var data = getFormData("search_form");
    $('#data_table').datagrid({url: '/role/getRolePageList', queryParams: data});
}

function addUpdateRole() {
    
}
function offUseRole(id) {
    updateRoleStatus(id,2);
}
function onUseRole(id) {
    updateRoleStatus(id,1);
}
function updateRoleStatus(id,status){
    $.messager.progress();	//防止重复提交
    $.ajax({
        type : "POST",
        url : '/role/updateRole',
        data : {
            "id": id,
            "status":status
        },
        dataType: "json",
        success : function(result) {
            $.messager.progress('close');
            if(result.code == 200){
                queryList();
            }else {
                layer.alert(result.message, {icon: 5, title: "提示"});
            }
        },
        error :function(){
            $.messager.progress('close');
            layer.alert('系统异常', {icon: 5, title: "提示"});
        }
    });
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
