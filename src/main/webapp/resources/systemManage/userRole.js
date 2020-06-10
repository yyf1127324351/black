$(document).ready(function () {
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            queryList();
        }
    };

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

