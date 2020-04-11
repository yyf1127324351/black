$(function(){  
	  setFileName("idCard","idCardFileId","idCard_file_tr");
	  setFileName("accountBook","abCardFileId","abCard_file_tr");
	  setFileName("highestGraduation","hgCardFileId","hgCard_file_tr");
	  setFileName("diploma","diCardFileId","diCard_file_tr");
	  setFileName("studentCard","scCardFileId","scCard_file_tr");
	  setFileName("returnTicket","rtCardFileId","rtCard_file_tr");
	  setFileName("debitCard","dcCardFileId","dcCard_file_tr");
	  setFileName("lifePhotos","lpCardFileId","lpCard_file_tr");
	  setFileName("medicalReport","mrCardFileId","mrCard_file_tr");
});
//上传附件
function doSubmit(uploadFile,fromId,fileId,spanId,urlId,trId){
	var files = uploadFile.files;
	var trLength = $('.'+trId).length;
	//减去已经有的空行
	$("input[name="+urlId+"]").each(function(i,item){
		 if(item.value==null||item.value==""){
			 trLength--;
		 }
	});
	var fileLength = files.length;
	var length = trLength+fileLength;
	if(files == null || fileLength==0 || files[0].name==""){
		$.messager.alert('提示','请选择文件!','warning');
		return;
	}
	//每个证明都可以上传多个附件（体检报告最多可以上传10个附件，其他证明最多可以上传5个附件）
	if("upload_form_mrCard"==fromId&&length>10){
		$.messager.alert('提示','体检报告最多可以上传10个附件!','warning');
		$("#"+fileId).val("");
		return;
	}
	if("upload_form_mrCard"!=fromId&&length>5){
		$.messager.alert('提示','最多可以上传5个附件!','warning');
		$("#"+fileId).val("");
		return;
	}
	for(i=0;i<fileLength;i++){
		if(files[i].name.length>50){
			$.messager.alert('提示','文件名不能超过50个文字!','warning');
			$("#"+fileId).val("");
			return ;
		}
		if(files[i].size>30*1024*1024){
			$.messager.alert('提示','单个文件最大不能超过30M!','warning');
			$("#"+fileId).val("");
			return;
		}
		//文件不为空的时候判断格式和大小
		var fileend = files[i].name.substring(files[i].name.indexOf(".")).toLowerCase(); 
		if(!(fileend.indexOf(".doc")>-1||fileend.indexOf(".docx")>-1||fileend.indexOf(".pdf")>-1
			||fileend.indexOf(".jpg")>-1||fileend.indexOf(".png")>-1||fileend.indexOf(".bmp")>-1)){
			$.messager.alert('提示','请上传如下格式的证明文件：jpg、png、bmp、doc、docx、pdf!','warning');
			return;
		}
	}
	$.messager.progress({title:'处理中...'});//防止重复点击，必须加上
	$("#"+fromId).ajaxSubmit({
		url : '/upload/uploadFile',
		type : 'post',
		dataType:'json',
		success : function(data){
			$.messager.progress('close');
			if(data.isOk){
				//清空fileForm
				$("#"+fileId).val("");
				var fileName = data.msg.split(",");
				var firstFileName = $("input[name="+urlId+"]:first").val();
				var firstFileId = $("input[name="+urlId+"]:first").next().children().text();
				for(i=0;i<fileName.length;i++){
					if(i==0&&(firstFileName==""||firstFileName==null)&&(firstFileId==""||firstFileId==null)){
						var file = fileName[i].split("|");
						if(file.length=2){
							$("input[name="+urlId+"]:first").val(file[0]);
							$("input[name="+urlId+"]:first").next().children().html(file[1]);
						}
					}else{
						//添加一行文件显示
						addTr(trId,urlId,fileName[i]);
					}
				}
			}else{
				$.messager.progress('close');
				$.messager.alert('提示',data.msg,'warning');
			}
		},
		error : function(){
			$.messager.progress('close');
			$.messager.alert('提示','上传文件异常，请稍后再试!','warning');
		}
	});
}
function addTr(trId,urlId,fileName){
	var file = fileName.split("|");
	var id = "";
	var name ="";
	if(file.length=2){
		id = file[0];
		name = file[1];
	}
	
	var html='<tr class="'+trId+'">'+
	 				'<td  align="center" ><a href="javascript:void(0)"  onclick="delFile(this,\''+trId+'\')"><span>删除</span></a></td>'+
	 				'<input type="hidden" name="'+urlId+'" value="'+id+'"></input>'+
	 				'<td><span >'+name+'</span></td>'+
	 		 '</tr> ';
	$("tr."+trId+":last").after(html);
}

function delFile(obj,trId){
	var id = $(obj).parent().parent().children("td:first-child").next().val();
//	console.log(id);
	//只删除页面行不删除数据库的文件（防止误删登记表的文件）

	//删除行,如果只剩一行清除
	var trLength = $('.'+trId).length;
	if(trLength>1){
		$(obj).parent().parent().remove();
	}else{
		 $(obj).parent().parent().children("td:first-child").next().val("");
		 $(obj).parent().parent().children("td:first-child").next().next().children().html("");
	}

	
//	//删除文件行及数据库
//	$.ajax({
//		type: "POST",
//		dataType:"json",
//		url: "/upload/delFile",
//	    data:{
//    	   "id" :id,
//        },
//		success: function(data){
//			if(data.isOk==true){
//				//删除行,如果只剩一行清除
//				var trLength = $('.'+trId).length;
//				if(trLength>1){
//					$(obj).parent().parent().remove();
//				}else{
//					 $(obj).parent().parent().children("td:first-child").next().val("");
//					 $(obj).parent().parent().children("td:first-child").next().next().children().html("");
//				}
//			}
//		},
//		error : function(){
//			$.messager.alert('提示','删除文件异常，请稍后再试!','warning');
//		}
//	});
}

function getfileIds(name){
    var ids = "";
	$("input[name='"+name+"']").each(function(index,item){
		if(ids!=null&&ids!=""){
			ids = ids+","+item.value;
		}else{
			ids = item.value;
		}
	});
//	console.log(ids);
	return ids;
}

function setFileName(fileId,urlId,trId){
	var file = $("#"+fileId).val();
	if(file==null || file==""){
     	  return;
	}
	$.ajax({
		type: "POST",
		dataType:"json",
		url: "/upload/findFileById",
	    data:{
    	   "ids" :file,
        },
		success: function(data){
			if(data.isOk==true){
//				console.log(data.msg)
				var files = data.msg.split(",");
				var id = $("#"+fileId).val().split(",");

				for(i=0;i<id.length;i++){
					var file = files[i].split("|");
					var name = file[0];
					if(i==0){
						$("input[name="+urlId+"]").val(id[0]);
						$("input[name="+urlId+"]").next().children().text(name);
					}else{
						var html='<tr class="'+trId+'">'+
					 				'<td  align="center" ><a href="javascript:void(0)"  onclick="delFile(this,\''+trId+'\')"><span>删除</span></a></td>'+
					 				'<input type="hidden" name="'+urlId+'" value="'+id[i]+'"></input>'+
					 				'<td><span >'+name+'</span></td>'+
					 			'</tr> ';
					    $("tr."+trId+":last").after(html);
					}
				}
			
//				if(files.length==id.length){}else{
//					$.messager.alert('提示','查询上传文件名异常，请稍后再试!','warning');
//				}
			}
		},
		error : function(){
			$.messager.alert('提示','查询上传文件异常，请稍后再试!','warning');
		}
	});
}

