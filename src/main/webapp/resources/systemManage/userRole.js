$(document).ready(function () {
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            queryList();
        }
    };
    $("#distributeRoleDialog").dialog({
        title: '分配角色',
        width:'440',
        height:'195',
        shadow:false,
        modal:true,
        closable: true,
        closed: true,
        buttons:[{
            text: '保存',
            iconCls: 'icon-ok',
            handler: function() {

            }
        },{
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function() {
                $("#distributeRoleDialog").dialog('close');
            }
        }],
        onClose:function() {
            $("#loginName").textbox("clear");
            $("#userName").textbox("clear");
            $("#roleIds").combobox("clear");
            $("id").val('');
        },
        closable: true,
        closed: true   //已关闭
    });


    $("#roleIds").combobox({
        url:'/role/getRoleList',
        valueField:'id',
        textField:'roleName',
        // editable:false,
        multiple: true
    });


    $("#data_table").datagrid({
        queryParams: getFormData("search_form"), //参数
        url: '/userRole/getUerRolePageList',
        method: 'post',
        loadMsg: "数据装载中,请稍等....",
        nowrap: true, //单元格内容是否可换行
        fitColumns: true, //自适应网格宽度
        showFooter: false, //是否显示最后一行，统计使用
        pagination: true,
        singleSelect: true,
        rownumbers: true,
        pageSize: 50,
        pageList: [50, 100, 200],
        toolbar: '#button_tab',
        onLoadSuccess: function (data) {
            $("#data_table").datagrid("clearSelections").datagrid("clearChecked");
        },
        frozenColumns: [[
            {title: '操作', field: 'id', width: 180, align: 'center',
                formatter: function (val, row) {
                    var html = "";
                    var id = row.id;
                    html = html + '<a class="sel_btn ch_cls" href="javascript:distributeRole(' + id + ')" style="text-decoration:none;">分配角色</a>';
                    return html;
                }
            },
            {title: '工号', field: 'userNo', width: 150, align: 'center'},
            {title: '姓名', field: 'userName', width: 180, align: 'center'}

        ]],
        columns: [[
            {title: '账号', field: 'loginName', width: 200, align: 'center'},
            {title: '关联角色', field: 'userRoleName', width: 130, align: 'center'}
        ]]


    });

});

function clearQuery() {
    $('#search_form').form('clear');
    queryList();
}

function queryList() {
    var data = getFormData("search_form");
    $('#data_table').datagrid({url: '/userRole/getUserRolePageList', queryParams: data});
}

function distributeRole(id) {
    $('#distributeRoleDialog').dialog('open');
    var rowDate = $("#data_table").datagrid('getSelected');
    $('#id').val(id);
    $('#loginName').textbox('setText',rowDate.loginName);
    $('#userName').textbox('setText',rowDate.userName);
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

