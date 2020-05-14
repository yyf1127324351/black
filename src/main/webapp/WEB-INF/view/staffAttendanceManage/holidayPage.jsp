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
                    <input name="staffName" class="easyui-textbox" type="text" style="width: 120px;">
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
                <a class="easyui-linkbutton toolButton" onclick="clearQuery()" data-options="iconCls:'icon-clear'"style="margin-top: 2px" >重置</a>
                <a class="easyui-linkbutton toolButton" onclick="addInfo()" data-options="iconCls:'icon-add'"style="margin-top: 2px" >新增</a>
                <a class="easyui-linkbutton toolButton" onclick="importInfo()" data-options="iconCls:'icon-undo'" style="margin-top: 2px">导入</a>
                <a class="easyui-linkbutton toolButton" onclick="exportInfo()" data-options="iconCls:'icon-redo'" style="margin-top: 2px">导出</a>
            </td>
        </table>
    </div>
    <table id="data_table" style="height: 100%;"></table>
</div>

<div style="display:none">
    <div id="addEditDialog" class="dialog">
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
