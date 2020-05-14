$(function() {
        document.onkeydown = function (e) {
            if (e.keyCode == '13') {
                queryList();
            }
        };

    //查询结果datagrid初始化
    $("#data_table").datagrid({
        queryParams:getFormData("searchForm"), //参数
        url:'/holiday/getHolidayPageList',
        method:'post',
        loadMsg:"数据装载中,请稍等....",
        nowrap: true, //单元格内容是否可换行
        fitColumns: false, //自适应网格宽度
        showFooter:false, //是否显示最后一行，统计使用
        pagination:true,
        checkbox:true,
        singleSelect:true,
        rownumbers:true,
        pageSize:50,
        pageList:[50,100,200],
        toolbar: '#button_tab',
        onLoadSuccess: function(data) {
            if(data.isOk == false) {
                $.messager.alert('提示',data.msg);
                console.log(data.data);
            }
        },
        frozenColumns:[[
            {title:'操作',field:'id',width:100,align:'center',
                formatter:function(val,row){
                    var id = row.id;
                    var html = "";
                    html = html +'<a class="sel_btn ch_cls" onclick="editHoliday('+id+')" style="text-decoration:none;">编辑</a>';
                    return html;
                }
            },
            {title:'员工姓名',field:'staffName',width:120,align:'center'}
        ]],
        columns:[[
            {title:'假期类型',field:'holidayType',width:80,align:'center'},
            {title:'请假时长(小时)',field:'hours',width:100,align:'center',sortable:true},
            {title:'请假开始时间',field:'startTime',width:160,align:'center',sortable:true},
            {title:'请假结束时间',field:'endTime',width:160,align:'center',sortable:true},
            {title:'状态',field:'status',width:100,align:'center',
                formatter:function(val){
                   if (val == 1) {
                       return "审批中";
                   }else if(val == 2) {
                       return "撤回";
                   }else if(val == 3) {
                       return "完成";
                   }else {
                       return "撤销";
                   }
                }
            },
            {title:'数据来源',field:'systemSource',width:100,align:'center',
                formatter:function(val){
                    if (val == 1) {
                        return "OA";
                    }else if(val == 2) {
                        return "HR";
                    }else {
                        return "导入";
                    }
                }
            },
            {title:'OA单号',field:'oaId',width:100,align:'center'},
            {title:'请假原因',field:'reason',width:340,align:'center'},
            {title:'备注',field:'remark',width:200,align:'center'}
        ]]


    });


    //编辑dialog初始化
    $("#editHolidayDialog").dialog({
        title:'编辑假期余额',
        width:'400',
        height:'300',
        close : true,
        shadow:false,
        modal:true,
        buttons:[{
            text:'提交',
            iconCls:'icon-ok',
            handler:function(){
                updateRemainHoliday();
            }
        },{
            text:'取消',
            iconCls:'icon-cancel',
            handler:function(){
                $('#editHolidayDialog').dialog('close');
            }
        }],
        onClose:function(){
        },
        closable: true,
        closed: true   //已关闭
    });

    //导入dialog初始化
    $("#importHolidayDialog").dialog({
        title:'假期余额导入',
        width:'500px',
        height:'200px',
        close : true,
        shadow:false,
        modal:true,
        onClose:function(){
        },
        closable: true,
        closed: true   //已关闭
    });

});

//搜索
function queryList(){
    var data=getFormData("search_form");
    $('#data_table').datagrid({url:'/holiday/getHolidayPageList',queryParams:data});
}
//重置查询条件输入框
function clearQuery(){
    $('#search_form').form('clear');
    queryList();
}


//假期余额-导入
function  importInfo(){
    $("#importHolidayDialog").dialog('open');
}
//假期余额-导入结果判断
function importResult(result){
    if(result ==  'yes'){
        var file = document.getElementById("file").files[0];
        if(file == undefined){
            $.messager.alert('提示', '请先上传指定模板excel文件', 'warning');
            return;
        }else if(!(file.name.indexOf(".xlsx")>-1||file.name.indexOf(".xls")>-1)){
            $.messager.alert('提示', '文件类型不正确，只能导入Excel文件', 'warning');
            return;
        }
        $.messager.progress({title:'导入中...'});



        $("#uploadForm").ajaxSubmit({
            url : '/holiday/holidayUpload',
            type : 'post',
            dataType:'json',
            success : function(data){
                $.messager.progress('close');
                $('#file').val('');
                $.messager.alert('提示', data.msg, 'info');
                $('#importHolidayDialog').dialog('close');
                queryHolidayDays();

                // if(data!=null&&data.msg!=null){
                //     $.messager.alert('提示', data.msg, 'info');
                //
                // }else{
                //     $.messager.alert('提示', '导入成功!', 'info');
                //     $('#importHolidayDialog').dialog('close');
                //     queryHolidayDays();
                // }

            },
            error : function(){
                $.messager.alert('提示', '导入异常!', 'info');
                $.messager.progress('close');
                $('#importHolidayDialog').dialog('close');
            }
        });
    }else{
        $('#importHolidayDialog').dialog('close');
    }
}



//假期余额编辑
function editHoliday() {
    var node = $('#data_result').datagrid('getSelected');

    $("#id").val(node.id);
    $("#theYear").val(node.year);
    $("#operatorNo").val(node.operatorNo);
    $("#operatorName").val(node.operatorName);

    $("#adjustmentExchangeHoliday").textbox('setValue', node.adjustmentExchangeHoliday);

    $("#adjustmentYearlyHoliday").numberbox('setValue', node.adjustmentYearlyHoliday);
    // $("#adjustmentExchangeHoliday").numberbox('setValue', node.adjustmentExchangeHoliday);

    $("#editHolidayDialog").dialog("open");
}
//假期余额-编辑-保存
function updateRemainHoliday() {
    var data=getFormData("edit_holiday_form");

    var yearHoliday = $("#adjustmentYearlyHoliday").numberbox('getValue');
    var exchangeHoliday =  $("#adjustmentExchangeHoliday").numberbox('getValue');

    // var reg = /^(-?)(0|[1-9][0-9]*)$/;
    // if (!reg.test(exchangeHoliday) && ""!=exchangeHoliday){
    //     $("#adjustmentExchangeHoliday").textbox('setValue', '');
    //     $.messager.alert('提示',"调休假调整小时数请输入整数！");
    //     return;
    // }


    if (""==yearHoliday && ""==exchangeHoliday){
        $.messager.alert('提示',"请至少输入一个调整值！");
        return;
    }
    $.messager.progress();	// display the progress bar，防止重复提交
    $.ajax({
        type : "POST",
        url : "/holiday/updateRemainHoliday",
        data :data,
        dataType: "json",
        success : function(data) {
            $.messager.progress('close');
            if(!data.isOk){
                $.messager.alert('提示',data.message);
            }

            $("#editHolidayDialog").dialog('close');
            $('#data_result').datagrid('reload');
        }
    });
}



//假期余额-获取条件输入框参数
function getFormData(form){
    var array=$("#"+form).serializeArray();
    var data={};
    $.each(array, function() {
        var item = this;
        if (data[item["name"]]) {
            data[item["name"]] = data[item["name"]] + "," + item["value"];
        } else {
            data[item["name"]] = item["value"];
        }
    });
    return data;
}