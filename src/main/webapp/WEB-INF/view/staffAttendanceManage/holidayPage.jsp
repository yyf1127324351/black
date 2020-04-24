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
                <a class="easyui-linkbutton toolButton" id="export-btn" data-options="iconCls:'icon-undo'" style="margin-top: 2px">导出</a>
                <a class="easyui-linkbutton toolButton" onclick="javascript:openImportOrAddDialog(2)" id="add-btn" data-options="iconCls:'icon-add'" style="margin-top: 2px">新增</a>
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
</body>
</html>
