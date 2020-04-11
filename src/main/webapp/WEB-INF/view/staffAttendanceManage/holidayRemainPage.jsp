<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="request" />
<c:set var="v" value="<%= String.valueOf((new Date()).getTime()) %>"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>假期余额</title>
    <%@include file="/resources/common/common.jsp" %>
    <script type="text/javascript" src="/resources/common/export.js"></script>
    <script type="text/javascript" src="/resources/staffAttendanceManage/holidayRemain.js?v=${v}"></script>
</head>
<body class="easyui-layout">
<form id="searchForm" method="post" action="" target="_blank">
    <input id="template" name=template type="hidden"/>
    <table cellpadding="2" class="search_table" style="padding:10px 0px 5px 10px;" >
        <tr>
            <th>年份：</th>
            <td style="align:center;">
                <select class="easyui-combobox" id="year" name="year" style="width:140px;" data-options="editable:false">
                    <option value="">全部</option>
                    <option value="2018">2018</option>
                    <option value="2019">2019</option>
                    <option value="2020">2020</option>
                    <option value="2021">2021</option>
                    <option value="2022">2022</option>
                </select>
            </td>
            <th>员工部门：</th>
            <td style="align:center;">
                <select class="easyui-combotree"  url="/department/loaddepartmentTreeById" name="departmentId" id="departmentId" style="width: 150px;" data-options="editable:false"/>
            </td>
            <th>员工工号：</th>
            <td style="align:center;">
                <input name="operatorNo"class="easyui-textbox" type="text" style="width: 110px;">
            </td>
            <th>员工姓名：</th>
            <td style="align:center;">
                <input name="operatorName" id="name" class="textbox" type="text" style="height: 20px;width: 110px;">
            </td>
        </tr>

    </table>
</form>
<div id ='buttonTab' >
    <table class="button_table">
        <tr>
            <td>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:queryHolidayDays()" style="float:left; height:22px;">搜索</a>
            </td>
            <td>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-clear'" onclick="javascript:clearFormData()" style="float:left; height:22px;">重置搜索条件</a>
            </td>
            <td>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-redo'" onclick="javascript:importInfo()" style="float:left; height:22px;">导入</a>
            </td>
            <td>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="javascript:exportInfo()" style="float:left; height:22px;">导出</a>
            </td>
            <td>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" onclick="javascript:calculateHoliday()" style="float:left; height:22px;">计算假期余额</a>
            </td>
        </tr>
    </table>
</div>
<div  style="padding:2px 5px 5px 0px;height:95%;">
    <table id="data_result"  class="easyui-datagrid" style="width:100%;height:100%;"></table>
</div>

<div style="display:none">
    <div id="editHolidayDialog" class="dialog">
        <form id="edit_holiday_form"  name= "edit_holiday_form" method = 'post'  action="">
            <input type="hidden" id="id" name="id"/>
            <table style="width:95%;margin:10px 10px 0 20px;">
                <tr style="height:30px;">
                    <td>年份:</td>
                    <td style="align:center;">
                    <td >
                        <input class="textbox" id="theYear" name="year" type="theYear" readonly="readonly" style="width: 150px;" >
                    </td>
                    </td>
                </tr>
                <tr style="height:30px;">
                    <td>员工工号:</td>
                    <td style="align:center;">
                    <td >
                        <input class="textbox" id="operatorNo" name="operatorNo" type="text" readonly="readonly" style="width: 150px;">
                    </td>
                    </td>
                </tr>
                <tr style="height:30px;">
                    <td>员工姓名:</td>
                    <td style="align:center;">
                    <td >
                        <input class="textbox" id="operatorName" name="operatorName" type="text" readonly="readonly" style="width: 150px;">
                    </td>
                    </td>
                </tr>
                <tr style="height:30px;">
                    <td>年假调整天数:</td>
                    <td style="align:center;">
                    <td >
                        <input prompt="请填数字(单位：天)" class="easyui-numberbox" precision="4" max="999.9999" id="adjustmentYearlyHoliday" name="adjustmentYearlyHoliday" type="text"  style="width: 150px;" />
                    </td>
                    </td>
                </tr>
                <tr style="height:30px;">
                    <td>调休假调整小时数:</td>
                    <td style="align:center;">
                    <td >
                        <input prompt="请填数字(单位：小时)" class="easyui-numberbox"  precision="2" max="999.99" id="adjustmentExchangeHoliday" name="adjustmentExchangeHoliday" type="text" style="width: 150px;"/>
                    </td>
                    </td>
                </tr>

            </table>

            </table>
        </form>
    </div>
</div>

<div style="display:none;border:5px">
    <div id="importHolidayDialog" class="dialog" title="假期余额导入" style="padding-top:5px;border-top:5px">
        <div style="width: 309px">&nbsp;&nbsp;</div>
        <a href="/holiday/holidayDownload" style="margin-top:15%;margin-left:10%;">下载假期余额导入模板(点击下载)</a>
        <form id="uploadForm" style="margin-top:4%;margin-left:10%;">
            <input style="width: 370px" type="file" id="file" name="file"/>
        </form>
        <div align="center" style="margin-top:2%">
            <a href="javascript:importResult('yes')" class="easyui-linkbutton" >导入</a>
            <a href="javascript:importResult('no')" class="easyui-linkbutton" >取消</a>
        </div>
    </div>
</div>

</body>
</html>
