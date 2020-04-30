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
    <script type="text/javascript" src="/resources/staffAttendanceManage/holiday.js?v=${v}"></script>

</head>
<body class="easyui-layout">
<div data-options="region:'north'" class="panel-fit">
    <form id="search_form" >
        <div style="padding: 4px 5px;">
            <div class="search-row">
                <div class="form-group">
                    <label class="search-label">姓名:</label>
                    <input name="operatorName" class="easyui-textbox" type="text" style="width: 120px;">
                </div>
            </div>
        </div>
    </form>
</div>


<div data-options="region:'center'" >
    <div id="button_tab" style="">
        <table class="button_table">
            <td style="height: 20px;width: 100%">
                <a class="easyui-linkbutton toolButton" onclick="queryList()" data-options="iconCls:'icon-search'" >搜索</a>
                <a class="easyui-linkbutton toolButton" id="reset-btn" data-options="iconCls:'icon-clear'"style="margin-top: 2px" >重置</a>
                <a class="easyui-linkbutton toolButton" onclick="importInfo()" data-options="iconCls:'icon-undo'" style="margin-top: 2px">导出</a>
                <a class="easyui-linkbutton toolButton" onclick="javascript:openAddDialog()" id="add-btn" data-options="iconCls:'icon-add'" style="margin-top: 2px">新增</a>
                <a class="easyui-linkbutton toolButton" onclick="javascript:openImportOrAddDialog(1)" data-options="iconCls:'icon-undo'" style="margin-top: 2px">导入</a>
                <a class="easyui-linkbutton toolButton" id="export-answer-btn" data-options="iconCls:'icon-redo'" style="margin-top: 2px">答题导出</a>
                <a class="easyui-linkbutton toolButton" id="enter-interview-btn" data-options="iconCls:'icon-man'" style="margin-top: 2px">进入面试环节</a>
                <a class="easyui-linkbutton toolButton" id="template-btn" data-options="iconCls:'icon-edit'" style="margin-top: 2px">列表模板设置</a>
                <a class="easyui-linkbutton toolButton" id="resume-select" data-options="iconCls:'icon-edit'" style="margin-top: 2px">需求部门筛选</a>
                <a href="/zhaopin/downloadAts" class="easyui-linkbutton toolButton" id="download-ats-btn" data-options="iconCls:'icon-edit'" style="margin-top: 2px">下载插件</a>
                <a class="easyui-linkbutton toolButton"  data-options="iconCls:'fa fa-tags'" onclick="javascript:addTagsDialog()" style="margin-top: 2px">打标签</a>
            </td>
        </table>
    </div>
    <table id="data_table" style="height: 100%;"></table>
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
