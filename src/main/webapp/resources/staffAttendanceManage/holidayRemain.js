$(document).ready(function() {
        document.onkeydown = function (e) {
            if (e.keyCode == '13') {
                queryHolidayDays();
            }
        };

    // $("#adjustmentExchangeHoliday").textbox('textbox').bind('keyup', function(e){
    //     $("#adjustmentExchangeHoliday").textbox('setValue', $(this).val().replace(/\D/g,''));
    // });

//查询条件部门选单
    $('#query_dept').combotree({
        url : "/department/loaddepartmentTreeById?isNeedPermissionControl=1",
        editable:true,
        onSelect : function(node) {
            if(node.type == null){
                $.messager.alert('提示','部门选择请不要选择公司层级','warning');
                $('#query_dept').combotree('clear');
            }
        }
    });
    //查询结果datagrid初始化
    $("#data_result").datagrid({
        queryParams:getFormData("searchForm"),
        url:'holidayRemainList',
        method:'post',
        loadMsg:"数据装载中,请稍等....",
        nowrap: false,
        fitColumns: false,
        pagination:true,
        showFooter:true,
        checkbox:true,
        singleSelect:true,
        width:'auto',
        rownumbers:true,
        pageSize:50,
        pageList:[50,100,200],
        toolbar: '#buttonTab',
        onLoadSuccess: function(data) {
            if(data.isOk == false) {
                $.messager.alert('提示',data.msg);
                console.log(data.data);
            }
        },
        frozenColumns:[[
            {title:'操作',field:'id',width:60,align:'center',
                formatter:function(val,row){
                    var id = row.id;
                    var year = row.year;
                    var url = '&nbsp;';
                    var mydate = new Date();
                    var nowYear = mydate.getFullYear();
                    if (nowYear == year){
                        if(null!=id&&id != ''){
                            url =url + '<input type=hidden value='+id+' id=hidden_'+id+'><a class="sel_btn ch_cls" href="javascript:editHoliday('+id+')" style="text-decoration:none;">编辑</a>';
                            // url =url + '<input type=hidden value='+id+' id=hidden_'+id+'><a href="javascript:editHoliday()" style="text-decoration:none;">编辑</a>';
                        }
                        return url;
                    }

                }
            },
            {title:'年份',field:'year',width:40,align:'center',sortable:true},
            {title:'员工工号',field:'operatorNo',width:100,align:'center',sortable:true},
            {title:'员工姓名',field:'operatorName',width:80,align:'center',sortable:true},
            {title:'员工部门',field:'departmentName',width:80,align:'center',sortable:true}

        ]],
        columns:[[
            {title:'总年假(天)',field:'totalYearlyHoliday',width:70,align:'center',sortable:true},
            {title:'剩余年假(天)',field:'remainYearlyHoliday',width:80,align:'center',sortable:true},
            {title:'已用年假(天)',field:'usedYearlyHoliday',width:100,align:'center',sortable:true},
            {title:'上年结转年假(天)',field:'lastyearYearlyHoliday',width:100,align:'center',sortable:true},
            {title:'当年年假(天)',field:'calculateYearlyHoliday',width:100,align:'center',sortable:true},
            {title:'当年公司年假(天)',field:'calculateCompanyYearlyHoliday',width:100,align:'center',sortable:true},
            {title:'年假调整(天)',field:'adjustmentYearlyHoliday',width:100,align:'center',sortable:true},


            {title:'总调休(小时)',field:'totalExchangeHoliday',width:80,align:'center',sortable:true},
            {title:'剩余调休(小时)',field:'remainExchangeHoliday',width:90,align:'center',sortable:true},
            {title:'已用调休(小时)',field:'usedExchangeHoliday',width:100,align:'center',sortable:true},
            {title:'上年结转调休假(小时)',field:'lastyearExchangeHoliday',width:130,align:'center',sortable:true},
            {title:'当年调休(小时)',field:'calculateExchangeHoliday',width:100,align:'center',sortable:true},
            {title:'调休假调整(小时)',field:'adjustmentExchangeHoliday',width:100,align:'center',sortable:true},

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
//假期余额-回车键搜索
function bindEnter(event) {
    if (event.keyCode == 13) {
        queryHolidayDays();
    }
}
//假期余额-搜索
function queryHolidayDays(){
    var data=getFormData("searchForm");
    $('#data_result').datagrid({url:'/holiday/holidayRemainList',queryParams:data});
}
//假期余额-重置查询条件输入框
function clearFormData(){
    $('#searchForm').form('clear');
}
//假期余额-导出
function exportInfo(){
    var exportData = [];
    $.messager.progress({title:'导出中...'});
    var data=getFormData("searchForm");
    $.ajax({
        url: "exportHolidayDaysList",
        type:"post",
        dataType:"json",
        data: data,
        success:function(result){
            for(var i=0; i<result.length; i++){
                var exportInfo = {};
                exportInfo.年份 =result[i].year;
                exportInfo.员工工号 ="'"+result[i].operatorNo;
                exportInfo.员工姓名 = result[i].operatorName;
                exportInfo.员工部门 = result[i].departmentName==null?"":result[i].departmentName;
                exportInfo.总年假 = result[i].totalYearlyHoliday;
                exportInfo.剩余年假_天 = result[i].remainYearlyHoliday;
                exportInfo.已用年假_天 = result[i].usedYearlyHoliday;
                exportInfo.上年结转年假_天 = result[i].lastyearYearlyHoliday;
                exportInfo.当年年假_天= result[i].calculateYearlyHoliday;
                exportInfo.当年公司年假_小时 = result[i].calculateCompanyYearlyHoliday;
                exportInfo.年假调整_小时 = result[i].adjustmentYearlyHoliday;
                exportInfo.总调休_小时 = result[i].totalExchangeHoliday;
                exportInfo.剩余调休_小时 = result[i].remainExchangeHoliday;
                exportInfo.已用调休_小时 = result[i].usedExchangeHoliday;
                exportInfo.上年结转调休假_小时 = result[i].lastyearExchangeHoliday;
                exportInfo.当年调休_小时 = result[i].calculateExchangeHoliday;
                exportInfo.调休假调整_小时 = result[i].adjustmentExchangeHoliday;

                exportData.push(exportInfo);
            }
            JSONToCSVConvertor(exportData, "假期余额信息", "假期余额信息");
            $.messager.progress('close');
        },
        error:function(){
            $.messager.alert('提示', '后台查询失败！', 'warning');
        }
    });
}


//假期余额-计算假期余额
function calculateHoliday() {
    $.messager.progress();	// display the progress bar，防止重复提交
    $.ajax({
        type : "POST",
        url : "/holiday/calculateHoliday",
        dataType: "json",
        success : function(data) {
            $.messager.progress('close');
            if(data.isOk){
                $.messager.alert('提示','假期余额计算完成');
            }else {
                $.messager.alert('提示',data.msg);
            }
            $('#data_result').datagrid('reload');
        }
    });

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