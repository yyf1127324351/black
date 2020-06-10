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
    <title>人员角色管理页面</title>
    <%@include file="/resources/common/common.jsp" %>
    <script type="text/javascript" src="/resources/systemManage/userRole.js?v=${v}"></script>

</head>
<body class="easyui-layout">
<div data-options="region:'north'" class="panel-fit">
    <form id="search_form" >
        <div style="padding: 4px 5px;">
            <div class="search-row">
                <div class="form-group">
                    <label class="search-label">工号:</label>
                    <input name="userNo" class="easyui-textbox" type="text" style="width: 120px;">
                </div>
                <div class="form-group">
                    <label class="search-label">姓名:</label>
                    <input name="userName" class="easyui-textbox" type="text" style="width: 120px;">
                </div>
                <div class="form-group">
                    <label class="search-label">账号:</label>
                    <input name="loginName" class="easyui-textbox" type="text" style="width: 120px;">
                </div>
                <div class="form-group">
                    <a class="easyui-linkbutton" style="height: 22px" data-options="iconCls:'icon-search'" onclick="queryList()" >搜索</a>
                    <a class="easyui-linkbutton" style="height: 22px" data-options="iconCls:'icon-clear'"  onclick="javascript:clearQuery()" >重置</a>
                </div>
            </div>
        </div>
    </form>
</div>


<div data-options="region:'center'" >
    <table id="data_table" style="height: 100%;"></table>
</div>

<div style="display:none;">
    <div id="distributeRoleDialog" class="dialog">
        <input type="hidden" id="id"/>
        <table style="width:95%;margin:10px 10px 10px 10px;">
            <tr style="height:30px;">
                <th style="width:70px; text-align: right">姓名：<font size="3" color="red">*</font></th>
                <td>
                    <input type="text" class="easyui-textbox" id="userName" readonly="readonly" data-options="disabled:true" style="width:300px" />
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="text-align: right">账号：<font size="3" color="red">*</font></th>
                <td>
                    <input type="text" class="easyui-textbox" id="loginName" readonly="readonly" data-options="disabled:true" style="width:300px" />
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="text-align: right">角色：<font size="3" color="red">*</font></th>
                <td>
                    <input type="text" class="easyui-combobox" id="roleIds" style="width:300px" />
                </td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>