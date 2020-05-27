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
    <title>系统数据字典配置</title>
    <%@include file="/resources/common/common.jsp" %>
    <script type="text/javascript" src="/resources/common/export.js"></script>
    <script type="text/javascript" src="/resources/systemManage/sysConfig.js?v=${v}"></script>
</head>
<body class="easyui-layout">
<!-- 首页左边Div  begin-->
<div data-options="region:'west',split:true" style="width:15%">
    <ul id="sysConfig_tree" class="easyui-tree" style="">

    </ul>
</div>

<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" class="panel-fit">
            <form id="search_form">
                <div style="padding: 4px 5px;">
                    <div class="search-row">
                        <div class="form-group">
                            <label class="search-label">参数类型:</label>
                            <input name="typeName" class="easyui-textbox" type="text" style="width: 120px;">
                        </div>
                        <div class="form-group">
                            <label class="search-label">参数类型编码:</label>
                            <input name="typeCode" class="easyui-textbox" type="text" style="width: 120px;">
                        </div>
                        <div class="form-group">
                            <label class="search-label">参数值Value:</label>
                            <input name="paramValue" class="easyui-textbox" type="text" style="width: 120px;">
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
            <table id="data_table" style="height: 100%;"></table>
        </div>
    </div>
</div>

<div id="handleSysConfig" class="easyui-menu" style="width:120px;">
    <div id="sysConfig_add" data-options="iconCls:'icon-add'">新增系统参数</div>
    <div id="sysConfig_type_add" data-options="iconCls:'icon-add'">新增参数类型</div>
    <div id="sysConfig_edit" data-options="iconCls:'icon-edit'">编辑参数类型</div>
    <div id="sysConfig_del" data-options="iconCls:'icon-remove'">删除参数类型</div>
</div>

<div style="display:none">
    <div id="addEditDialog" class="dialog">
        <input type="hidden" id="valueId"/>
        <table style="width:95%;margin:10px 10px 0 20px;">
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;" >参数键Key：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="paramKey" class="easyui-textbox" prompt="1/30" type="text" style="width: 400px;"data-options="validType:'length[1,30]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">参数值Value：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="paramValue" class="easyui-textbox" prompt="1/20" type="text" style="width: 400px;"data-options="validType:'length[1,20]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">描述：&nbsp;&nbsp;</th>
                <td style="align:center;">
                    <input id="describe" class="easyui-textbox" type="text" style="width: 400px;"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">备注：&nbsp;&nbsp;</th>
                <td style="align:center;">
                    <input id="remark" class="easyui-textbox" type="text" style="width: 400px;"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">参数类型：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="typeName" type="text" class="easyui-textbox" readonly="readonly"  style="width: 400px;"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">参数类型编码：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="typeCode" type="text" class="easyui-textbox" readonly="readonly" style="width: 400px;"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">参数值类型ID：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="typeId" type="text" class="easyui-textbox" readonly="readonly" style="width: 400px;"/>
                </td>
            </tr>

        </table>
    </div>
</div>

<div style="display:none">
    <div id="addEditTypeDialog" class="dialog">
        <input type="hidden" id="id" name="id"/>
        <table style="width:95%;margin:10px 10px 0 20px;">
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;" >参数类型编码：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="typeCode1" class="easyui-textbox" prompt="1/30" type="text" style="width: 300px;"data-options="validType:'length[1,30]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">参数类型名称：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="typeName1" class="easyui-textbox" prompt="1/20" type="text" style="width: 300px;"data-options="validType:'length[1,20]'"/>
                </td>
            </tr>
            <tr style="height:30px;">
                <th style="width: 110px;text-align:right;">排序值：<font size="3" color="red">*</font></th>
                <td style="align:center;">
                    <input id="sortNumber1" type="text" class="easyui-numberbox" prompt="请输入正整数" style="width: 300px;" data-options="min:0,precision:0"/>
                </td>
            </tr>

        </table>
    </div>
</div>


</body>
</html>
