<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>编辑页面</title>
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <script type="text/javascript"
            src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
    <!-- 编辑区域 -->
    <form id="editForm" method="post">
        <!--提供隐藏域 装载id -->
        <input type="hidden" name="id" />
        <table class="table-edit" width="80%" align="center">
            <tr>
                <td>通道号</td>
                <td>
                    <input type="text" name="province" class="easyui-validatebox" required="true" />
                </td>
                <td>通道名</td>
                <td>
                    <input type="text" name="province" class="easyui-validatebox" required="true" />
                </td>
                <td>结算方式</td>
                <td>
                    <input type="text" name="province" class="easyui-validatebox" required="true" />
                </td>
            </tr>
            <tr>地址</tr>
            <tr>
                <td>省</td>
                <td>市</td>
                <td>区</td>
                <td>家</td>
            </tr>
            <tr>
                <td>
                    <input type="text" name="district" class="easyui-validatebox" required="true" />
                </td>
                <td>
                    <input type="text" name="district" class="easyui-validatebox" required="true" />
                </td>
                <td>
                    <input type="text" name="district" class="easyui-validatebox" required="true" />
                </td>
                <td>
                    <input type="text" name="district" class="easyui-validatebox" required="true" />
                </td>
            </tr>
        </table>
    </form>