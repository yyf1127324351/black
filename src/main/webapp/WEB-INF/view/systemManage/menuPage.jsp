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
                        <div class="form-group">
                            <a class="easyui-linkbutton" style="height: 22px" data-options="iconCls:'icon-search'" onclick="queryList()" >搜索</a>
                            <a class="easyui-linkbutton" style="height: 22px" data-options="iconCls:'icon-clear'"  onclick="javascript:clearQuery()" >重置</a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div data-options="region:'center'">
           <%-- <div id="button_tab" style="">
                <table class="button_table">
                    <td style="height: 20px;width: 100%">
                        <a class="easyui-linkbutton toolButton" onclick="queryList()"
                           data-options="iconCls:'icon-search'">搜索</a>
                        <a class="easyui-linkbutton toolButton" onclick="javascript:clearQuery()" data-options="iconCls:'icon-clear'"
                           style="margin-top: 2px">重置</a>
                    </td>
                </table>
            </div>--%>
            <table id="data_table" style="height: 100%;"></table>
        </div>
    </div>
</div>

<div id="handleMenu" class="easyui-menu" style="width:120px;">
    <div id="menu_add" data-options="iconCls:'icon-add'">新增</div>
    <div id="menu_edit" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="menu_del" data-options="iconCls:'icon-remove'">删除</div>
</div>

<div style="display:none">
    <div id="addEditMenuDialog" class="dialog">
        <input type="hidden" id="id" name="id"/>
        <table style="width:95%;margin:10px 10px 0 20px;">
            <tr style="height:30px;">
                <th style="width: 70px;text-align:right;" >菜单名称：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="name" class="easyui-textbox" prompt="1/30" type="text" style="width: 400px;"data-options="validType:'length[1,30]'"/>
                </td>

            </tr>
            <tr style="height:30px;">
                <th style="width: 75px;text-align:right;">权限编码：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="code" class="easyui-textbox" prompt="1/50" type="text" style="width: 400px;"data-options="validType:'length[1,50]'"/>
                </td>
            </tr>
            <tr id="url_tr" style="height:30px;">
                <th style="width: 75px;text-align:right;">菜单地址：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="url" class="easyui-textbox" prompt="1/100" type="text" style="width: 400px;"data-options="validType:'length[1,100]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 75px;text-align:right;">类型：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input type="hidden" id="type"/>
                    <input type="hidden" id="level"/>
                    <input id="typeName" class="easyui-textbox" readonly="readonly" prompt="1/50" type="text" style="width: 400px;"data-options="validType:'length[1,50]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 75px;text-align:right;">父菜单名：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input type="hidden" id="parentId"/>
                    <input id="parentName" class="easyui-textbox" readonly="readonly" prompt="1/50" type="text" style="width: 400px;"data-options="validType:'length[1,50]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 75px;text-align:right;">排序值：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="sortNumber" type="text" class="easyui-numberbox" prompt="请输入正整数" style="width: 400px;" data-options="min:0,precision:0"/>
                </td>
            </tr>

        </table>
    </div>
</div>

</body>
</html>
