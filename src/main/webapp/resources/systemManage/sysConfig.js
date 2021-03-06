$(document).ready(function () {
    document.onkeydown = function (e) {
        if (e.keyCode == '13') {
            queryList();
        }
    };


    $('#sysConfig_tree').tree({
        url: "/sysConfig/getSysConfigTree",
        cascadeCheck: false,
        onClick: function (node) {
            //点击查询
            var typeId = node.id;
            treeClickQueryList(typeId);
        },
        onLoadSuccess: function (node) {
            // $(".tree-icon,.tree-file").removeClass("tree-icon tree-file");
            $(".tree-icon,.tree-folder").removeClass("tree-icon tree-folder tree-folder-open tree-folder-closed");
        },
        onContextMenu : function(e, node) {
            $(this).tree('select', node.target);
            e.preventDefault();
            var id = node.id;
            if (id == 0) {
                $('#sysConfig_edit').hide();
                $('#sysConfig_del').hide();
                $('#sysConfig_type_add').show();
                $('#sysConfig_add').hide();

            }else {
                $('#sysConfig_edit').show();
                $('#sysConfig_del').show();
                $('#sysConfig_type_add').hide();
                $('#sysConfig_add').show();
            }
            $('#handleSysConfig').menu('show', {
                left: e.pageX,
                top: e.pageY,
                onClick: function(item) {
                    if (item.text == '新增系统参数'){
                        $('#addEditDialog').dialog('setTitle','新增系统参数【'+ node.text+'】');
                        $('#addEditDialog').dialog('open');

                        $('#typeName').textbox('setText',node.text);
                        $('#typeName').textbox('textbox').css('background','#ccc');
                        $('#typeCode').textbox('setText',node.code);
                        $('#typeCode').textbox('textbox').css('background','#ccc');
                        $('#typeId').textbox('setText',node.id);
                        $('#typeId').textbox('textbox').css('background','#ccc');
                    }else if(item.text == '新增参数类型') {
                        $('#addEditTypeDialog').dialog('setTitle','新增参数类型');
                        $('#addEditTypeDialog').dialog('open');
                    } else if(item.text == '编辑参数类型') {
                        $('#addEditTypeDialog').dialog('setTitle','编辑参数类型');
                        $('#addEditTypeDialog').dialog('open');
                        $('#id').val(node.id);
                        $('#typeCode1').textbox('setText',node.code);
                        $('#typeName1').textbox('setText',node.text);
                        $('#sortNumber1').numberbox('setValue',node.sort);
                    } else if(item.text == '删除参数类型') {
                        layer.confirm('确认删除该参数类型的所有系统参数？', {icon: 3},function () {
                            deleteSysConfigType(node.id);
                        });
                    }
                }
            });
        }
    });

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
                addUpdateSysConfig();
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
            $('#paramKey').textbox('clear');
            $('#paramValue').textbox('clear');
            $('#paramCode').textbox('clear');
            $('#describe').textbox('clear');
            $('#remark').textbox('clear');
            $('#typeName').textbox('clear');
            $('#typeId').textbox('clear');
        },
        closable: true,
        closed: true   //已关闭
    });

    $("#addEditTypeDialog").dialog({
        width:'500',
        height:'195',
        close : true,
        shadow:false,
        modal:true,
        buttons:[{
            text:'提交',
            iconCls:'icon-ok',
            handler:function(){
                addUpdateSysConfigType();
            }
        },{
            text:'取消',
            iconCls:'icon-cancel',
            handler:function(){
                $('#addEditTypeDialog').dialog('close');
            }
        }],
        onClose:function(){
            //清空所有数据
            $('#id').val('');
            $('#typeCode1').textbox('clear');
            $('#typeName1').textbox('clear');
            $('#sortNumber1').numberbox('clear');
        },
        closable: true,
        closed: true   //已关闭
    });

    $("#data_table").datagrid({
        queryParams: getFormData("searchForm"), //参数
        url: '/sysConfig/getSysConfigPageList',
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
        },
        onClickRow:function (rowIndex, rowData) {
            var id = rowData.typeId;
            var node = $('#sysConfig_tree').tree('find', id);
            $('#sysConfig_tree').tree('select', node.target);
        },
        frozenColumns: [[
            {title: '操作', field: 'id', width: 110, align: 'center',
                formatter: function (val, row, index) {
                    var html = "";
                    var status = row.status;
                    var id = row.id;
                    var typeId = row.typeId;
                    if (status == 1){
                        html = html + '<a class="sel_btn ch_cls" onclick="offUse(' + id + ',' + typeId + ')" style="text-decoration:none;">停用</a>&nbsp;';
                        html = html + '<a class="sel_btn ch_cls" onclick="openEdit(' + index + ')" style="text-decoration:none;">编辑</a>';
                    }else {
                        html = html + '<a class="sel_btn ch_cls" onclick="onUse(' + id + ',' + typeId + ')" style="text-decoration:none;">启用</a>';
                    }
                    return html;
                }
            },
            {title: '状态', field: 'status', width: 50, align: 'center',
                formatter: function (val, row, index) {
                    var status = row.status;
                    if (status == 1) {
                        return "使用中";
                    }else {
                        return "<font color='#216DDD'>已停用</font>";
                    }

                }
            },
            {title: '参数键Key', field: 'paramKey', width: 80, align: 'center'},
            {title: '参数值Value', field: 'paramValue', width: 220, align: 'center'},
            {title: '参数类型', field: 'typeName', width: 200, align: 'center'}

        ]],
        columns: [[
            {title: '参数类型编码', field: 'typeCode', width: 200, align: 'center'},
            {title: '参数类型ID', field: 'typeId', width: 100, align: 'center'},
            {title: '参数描述', field: 'describe', width: 210, align: 'center'},
            {title: '参数备注', field: 'remark', width: 200, align: 'center'}
        ]]


    });


});

function clearQuery() {
    $('#search_form').form('clear');
    $('#sysConfig_tree').tree('reload');
    queryList();
}

function queryList() {
    var data = getFormData("search_form");
    $('#sysConfig_tree').tree('reload');
    $('#data_table').datagrid({url: '/sysConfig/getSysConfigPageList', queryParams: data});
}
function treeClickQueryList(typeId) {
    var data = getFormData("search_form");
    data.typeId = typeId;
    $('#data_table').datagrid({url: '/sysConfig/getSysConfigPageList', queryParams: data});
}
function addUpdateSysConfigType(){
    var url = '/sysConfig/addSysConfigType';
    var id = $('#id').val();
    if (null != id && '' != id) {
        url = '/sysConfig/updateSysConfigType';
    }
    var typeCode = $('#typeCode1').textbox('getText').replace(/\s+/g, "");
    var typeName = $('#typeName1').textbox('getText').replace(/\s+/g, "");
    var sortNumber = $('#sortNumber1').val();
    if (null == typeCode || '' == typeCode) {
        layer.alert('参数类型编码不能为空！', {icon: 0, title: "提示"});
        return;
    }
    if (typeCode.length > 30){
        layer.alert('参数类型编码不能超过30个字符！', {icon: 0, title: "提示"});
        return;
    }
    if (null == typeName || '' == typeName) {
        layer.alert('参数类型名称不能为空！', {icon: 0, title: "提示"});
        return;
    }
    if (typeCode.length > 20){
        layer.alert('参数类型名称不能超过20个字符！', {icon: 0, title: "提示"});
        return;
    }
    if (null == sortNumber || '' == sortNumber) {
        layer.alert('排序值不能为空！', {icon: 0, title: "提示"});
        return;
    }
    $.messager.progress();	//防止重复提交
    $.ajax({
        type : "POST",
        url : url,
        data : {
            "id": id,
            "typeCode":typeCode,
            "name":typeName,
            "sortNumber":sortNumber
        },
        dataType: "json",
        success : function(result) {
            $.messager.progress('close');
            if(result.code == 200){
                layer.alert('操作成功', {icon: 6, title: "提示"});
                $('#sysConfig_tree').tree('reload');
                queryList();
                $("#addEditTypeDialog").dialog('close');
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

function deleteSysConfigType(typeId) {
    $.messager.progress();	//防止重复提交
    $.ajax({
        type : "POST",
        url : "/sysConfig/deleteSysConfigType",
        data : {
            "typeId": typeId
        },
        dataType: "json",
        success : function(result) {
            $.messager.progress('close');
            if(result.code == 200){
                layer.alert('操作成功', {icon: 6, title: "提示"});
                $('#sysConfig_tree').tree('reload');
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
function openEdit(index){
    $("#data_table").datagrid('selectRow',index);
    var row = $("#data_table").datagrid('getSelected');
    $('#addEditDialog').dialog('setTitle','修改系统参数');
    $('#addEditDialog').dialog('open');

    $('#ValueId').val(row.id);
    $('#paramKey').textbox('setText',row.paramKey);
    $('#paramValue').textbox('setText',row.paramValue);
    $('#describe').textbox('setText',row.describe);
    $('#remark').textbox('setText',row.remark);

    $('#typeName').textbox('setText',row.typeName);
    $('#typeName').textbox('textbox').css('background','#ccc');
    $('#typeCode').textbox('setText',row.typeCode);
    $('#typeCode').textbox('textbox').css('background','#ccc');
    $('#typeId').textbox('setText',row.typeId);
    $('#typeId').textbox('textbox').css('background','#ccc');
}

function offUse(id,typeId) {
    updateSysConfigValueStatus(id,typeId,2);
}
function onUse(id,typeId) {
    updateSysConfigValueStatus(id,typeId,1);
}
function updateSysConfigValueStatus(id,typeId,status){
    $.messager.progress();	//防止重复提交
    $.ajax({
        type : "POST",
        url : '/sysConfig/updateSysConfigValue',
        data : {
            "id": id,
            "status":status
        },
        dataType: "json",
        success : function(result) {
            $.messager.progress('close');
            if(result.code == 200){
                // queryList();
                treeClickQueryList(typeId);
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

function addUpdateSysConfig() {
    var url = '/sysConfig/addSysConfigValue';
    var id = $('#valueId').val();
    if (null != id && '' != id) {
        url = '/sysConfig/updateSysConfigValue';
    }
    var paramKey = $('#paramKey').textbox('getText').replace(/\s+/g, "");
    var paramValue = $('#paramValue').textbox('getText').replace(/\s+/g, "");
    if (null == paramKey || '' == paramKey) {
        layer.alert('参数键Key不能为空！', {icon: 0, title: "提示"});
        return;
    }
    if (paramKey.length > 30){
        layer.alert('参数键Key不能超过30个字符！', {icon: 0, title: "提示"});
        return;
    }
    if (null == paramValue || '' == paramValue) {
        layer.alert('参数值Value不能为空！', {icon: 0, title: "提示"});
        return;
    }
    if (paramValue.length > 20){
        layer.alert('参数值Value不能超过20个字符！', {icon: 0, title: "提示"});
        return;
    }
    var describe = $('#describe').textbox('getText').replace(/\s+/g, "");
    var remark = $('#remark').textbox('getText').replace(/\s+/g, "");
    var typeName = $('#typeName').textbox('getText').replace(/\s+/g, "");
    var typeCode = $('#typeCode').textbox('getText').replace(/\s+/g, "");
    var typeId = $('#typeId').textbox('getText').replace(/\s+/g, "");
    $.messager.progress();	//防止重复提交
    $.ajax({
        type : "POST",
        url : url,
        data : {
            "id": id,
            "paramKey":paramKey,
            "paramValue":paramValue,
            "describe":describe,
            "remark":remark,
            "typeName":typeName,
            "typeCode":typeCode,
            "typeId":typeId
        },
        dataType: "json",
        success : function(result) {
            $.messager.progress('close');
            if(result.code == 200){
                layer.alert('操作成功', {icon: 6, title: "提示"});
                // queryList();
                treeClickQueryList(typeId);
                $("#addEditDialog").dialog('close');
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
