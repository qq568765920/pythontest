<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/commons/common-js.jsp"></jsp:include>
<link href="/js/kindeditor-4.1.10/themes/default/default.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript"
	src="/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<body>
	<h1>实现图片长传</h1>

	<div style="padding: 10px 10px 10px 10px">
		<form id="itemAddForm" class="itemForm" method="post">
			<table cellpadding="5">

				<tr>
					<td>商品图片:</td>
					<td><a href="javascript:void(0)"
						class="easyui-linkbutton picFileUpload">上传图片</a> <input
						type="hidden" name="image" /></td>
				</tr>
			</table>
			<input type="hidden" name="itemParams" />
		</form>
		<div style="padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="submitForm()">确认</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" onclick="clearForm()">重置</a>
		</div>
	</div>

</body>
<script type="text/javascript">
	$("#qqq").click(function() {
		$.post("/pic/upload", $("#aa").val(), function(data) {
			alert("Data: " + data);
		});
	});

	var itemAddEditor;
	$(function() {
		//和form下的itemDesc组件绑定
		itemAddEditor = KindEditorUtil
				.createEditor("#itemAddForm [name=itemDesc]");
		KindEditorUtil.init({
			fun : function(node) {
				KindEditorUtil.changeItemParam(node, "itemAddForm");
			}
		});
	});

	function submitForm() {
		//表单校验
		if (!$('#itemAddForm').form('validate')) {
			$.messager.alert('提示', '表单还未填写完成!');
			return;
		}
		//转化价格单位，将元转化为分  数据库价格*100
		$("#itemAddForm [name=price]").val(
				eval($("#itemAddForm [name=priceView]").val()) * 100);
		itemAddEditor.sync();//将输入的内容同步到多行文本中

		var paramJson = [];
		$("#itemAddForm .params li").each(function(i, e) {
			var trs = $(e).find("tr");
			var group = trs.eq(0).text();
			var ps = [];
			for (var i = 1; i < trs.length; i++) {
				var tr = trs.eq(i);
				ps.push({
					"k" : $.trim(tr.find("td").eq(0).find("span").text()),
					"v" : $.trim(tr.find("input").val())
				});
			}
			paramJson.push({
				"group" : group,
				"params" : ps
			});
		});
		paramJson = JSON.stringify(paramJson);//将对象转化为json字符串

		$("#itemAddForm [name=itemParams]").val(paramJson);

		/*$.post/get(url,JSON,function(data){....})  
			?id=1&title="天龙八部&key=value...."
		 */
		//alert($("#itemAddForm").serialize());
		$.post("/item/save", $("#itemAddForm").serialize(), function(data) {
			if (data.status == 200) {
				$.messager.alert('提示', '新增商品成功!');
			} else {
				$.messager.alert("提示", "新增商品失败!");
			}
		});
	}

	function clearForm() {
		$('#itemAddForm').form('reset');
		itemAddEditor.html('');
	}
</script>





</html>