<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="v" value="<%= String.valueOf((new Date()).getTime()) %>"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>菜单管理</title>
    <%@include file="/resources/common/common.jsp" %>
    <script type="text/javascript" src="/resources/common/export.js"></script>
    <script type="text/javascript" src="/resources/systemManage/menu.js?v=${v}"></script>
</head>
<body class="easyui-layout">
<!-- 首页左边Div  begin-->
<div data-options="region:'west',split:true" style="width:15%">
    <ul id="menu_tree" class="easyui-tree" style="">

    </ul>
</div>
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" class="panel-fit">
            <form id="search_form">
                <div style="padding: 4px 5px;">
                    <div class="search-row">
                        <div class="form-group">
                            <label class="search-label">菜单名称:</label>
                            <input name="name" class="easyui-textbox" type="text" style="width: 120px;">
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div data-options="region:'center'">
            <div id="button_tab" style="">
                <table class="button_table">
                    <td style="height: 20px;width: 100%">
                        <a class="easyui-linkbutton toolButton" onclick="queryList()"
                           data-options="iconCls:'icon-search'">搜索</a>
                        <a class="easyui-linkbutton toolButton" onclick="javascript:clearQuery()" data-options="iconCls:'icon-clear'"
                           style="margin-top: 2px">重置</a>
                        <%--<a class="easyui-linkbutton toolButton" onclick="javascript:openAddDialog()" id="add-btn"--%>
                           <%--data-options="iconCls:'icon-add'" style="margin-top: 2px">新增</a>--%>
                    </td>
                </table>
            </div>
            <table id="data_table" style="height: 100%;"></table>
        </div>
    </div>
</div>

</body>
</html>
