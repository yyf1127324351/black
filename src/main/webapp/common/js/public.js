var public = {
    // 账户下拉
    fn_account: function () {
        $(".name").hover(function () {
            $(this).find("ul").stop(true, true).slideDown(300);
        }, function () {
            $(this).find("ul").stop(true, true).slideUp(300);
        });
    },


    // 窗口tab
    fn_windowTab: function () {
        //点击
        $("ul.window").delegate("li", "click", function (e) {
            var len = $(this).find("i").length;
            if (!len) {
                var i = '<i class="anticon icon-close">';
                $(this).addClass("active").append(i).siblings().removeClass("active").find("i").remove();
            }
        });

        //关闭
        $("body").on("click", "ul.window > li > i", function () {
            var i = '<i class="anticon icon-close">';
            var next = $(this).parent().next().length;
            var prev = $(this).parent().prev().length;
            var index = $(this).parent().index(),
                activeNum = 0;
            //中间 || 第一个
            if (next && (prev || !prev)) {
                $(this).parent().next().addClass("active").append(i);
                $('#tabIframe .tab-content').eq(index + 1).show();
                activeNum = index + 1;
            }
            //最后一个
            else if (!next && prev) {
                $(this).parent().prev().addClass("active").append(i);
                $('#tabIframe .tab-content').eq(index - 1).show();
                activeNum = index - 1;
            }
            //只有一个
            // else if (!next && !prev) {
            //     alert("不能关闭");
            //     return false;
            // }
            layer.close(layer.tips())
            $(this).parent().remove();
            console.log(index);

            $('#tabIframe .tab-content').eq(index).remove();
            qims.fn_tabOverflow(activeNum)
        });
    },
    // 导航tab
    fn_navTab: function () {
        $(document).on('click', '#tabList li', function () {
            // var index = $(this).index();
            // $(this).addClass('active').siblings('li').removeClass('active')
            // $('#tabIframe .tab-content').hide().eq(index).show()

            var menuid = $(this).attr('menuid');
            var index = $(this).index();
            $(this).addClass('active').siblings('li').removeClass('active')
            $('#tabIframe .tab-content').hide()
            $('#tabIframe .tab-content[menuid=' + menuid + ']').show()
        })

        $(document).on('click', '.tab li', function () {
            var index = $(this).index();
            $(this).addClass('active').siblings('li').removeClass('active')
            $(this).parents('.tab').next('.tab-content').find('.tab-content-inner').hide().eq(index).show()

        })
    },


    // 左侧菜单
    fn_sideBar: function () {
        // 展开收起
        $("body").on("click", ".change", function (e) {
            $('.fixed-table-header').hide();
            $('.fixed-table-body>table').css({
                'marginTop': 0
            })
            $('.fixed-table-body thead tr').css('borderBottom', '1px  solid #f4f4f4')
            $('.fixed-table-body').css({
                'marginBottom': 36
            })
            if ($(this).hasClass("icon-menufold")) {
                $(this).removeClass("icon-menufold").addClass("icon-menuunfold");
                $(".leftside").animate({
                    width: 42,
                });

                $(".logo").addClass("minilogo");
                $(".main-menu").addClass("mini-menu");
                $(".main-menu>li>a").addClass("js-sub-menu-toggle-degree1")
                $(".js-sub-menu-toggle.on").next('.sub-menu').hide();



            } else {
                $(this).removeClass("icon-menuunfold").addClass("icon-menufold");
                $(".leftside").animate({
                    width: 160
                });
                //  $('.fixed-table-header>table').animate({
                //     width: wid1,
                // });

                $(".logo").removeClass("minilogo");
                $(".main-menu").removeClass("mini-menu");
                $(".main-menu>li>a").removeClass("js-sub-menu-toggle-degree1")
                $(".js-sub-menu-toggle.on").next('.sub-menu').show();
            }
        });

        // mini菜单hover状态
        // var timer;
        $("body").on("mouseenter", ".mini-menu li >.sub-menu", function (e) {

            $(this).siblings('.js-sub-menu-toggle').addClass('subactive');
        });

        $("body").on("mouseleave", ".mini-menu li >.sub-menu", function (e) {
            $(this).siblings('.js-sub-menu-toggle').removeClass('subactive');
        });


        // 左侧下拉菜单折叠
        $("body").on("click", ".main-menu .js-sub-menu-toggle:not('.js-sub-menu-toggle-degree1')", function (e) {
            e.preventDefault();
            $(this).toggleClass("on");
            $(this).parent().find('> .sub-menu').stop(true, true).slideToggle(300);
        });

        // 左侧下拉菜单点击状态
        // $("body").on("click", ".main-menu .sub-menu >li", function(e) {
        //     e.preventDefault();
        //     if (!$(this).find(">a").hasClass("js-sub-menu-toggle")) {
        //         $(this).parents('.main-menu').find('a').removeClass('active');
        //         $(this).find('> a').addClass('active');
        //     }
        // });
        $("body").on("click", ".main-menu li a:not(.js-sub-menu-toggle)", function (e) {
            e.preventDefault();

            $(this).parents('.main-menu').find('a').removeClass('active');


            $(this).addClass('active')

        });

    },
    // 控制左侧菜单高度
    fn_sideBarHeight: function () {
        var _height = $(window).height() - 90;
        var _height1 = $(window).height();
        $('.main-menu').css('height', _height)
        $('.leftside-sub').css('height', _height1)
    },
    // 窗口改变
    fn_resize: function () {
        $(window).resize(function () {
            public.fn_sideBarHeight();
        });
    },
    // 下拉菜单
    fn_select: function () {
        //点击下拉展开
        $(document).on("click", ".selectshowAction:not(.disabled)", function (e) {
            e.stopPropagation()
            // e.preventDefault();
            var _this = $(this).next("ul")[0]
            $('.select').not(_this).slideUp(300)
            $('.selectshowAction').not(this).removeClass("on")
            $(this).toggleClass("on");
            if ($(this).parents('.group-selectSearch').length != 0) {
                $(this).next(".select-search").stop(true, true).slideToggle(100).find('input[type=text]').focus();
            } else {
                $(this).next("ul").stop(true, true).slideToggle(300);
            }
            return false;
        });


        $(document).on("click", "ul.select:not(#setting) li,.select-search ul li", function (e) {
            var text = $(this).text();
            var vId = $(this).attr("value");
            var that = $(this)
            if ($(this).parents('.group-select').length != 0) {
                if ($(this).parents('.select-search').length != 0) {
                    $(this).parents('.group-select').find('.selectshowAction .scm-input').val(text).toggleClass("on");
                    $(this).parents('.group-select').find('.selectshowAction .hidden-input').val(vId).toggleClass("on");
                    $(this).parents('.select-search').stop(true, true).slideToggle(300);

                } else {
                    $(this).parent().prev(".selectshowAction").find(".scm-input").val(text).toggleClass("on");
                    $(this).parent().prev(".selectshowAction").find(".hidden-input").val(vId).toggleClass("on");
                    $(this).parent().stop(true, true).slideToggle(300);

                }
            } else {
                $(".selectshowAction,.selectshowDefault").find("span").removeClass('active')
                $(this).parent().prev(".selectshowAction").find("span").addClass('active').text(text).toggleClass("on");
                $(this).parent().prev(".selectshowAction").find("input").val(text).toggleClass("on");

                $(this).parent().stop(true, true).slideToggle(300);

            }
        });
        $(document).on("click", ".select-search .scm-input-wrap,.icon-closecircle", function (e) {
            e.stopPropagation()
        })

        //点击其他地方消失
        $(document).on("click", function (e) {
            $('ul.select,.select-search').slideUp(300)
            $('.selectshowAction').removeClass("on")
        })

    },

    // input显示删除图标
    fn_inputClose: function () {
        var clear = '<i class="anticon icon-closecircle">';
        $(document).on({
            mouseenter: function () {
                $(this).parents('.group').find(".icon-closecircle").remove()
                var val = $(this).find('input[type=text]').val();
                if (val) {
                    $(this).addClass('iconhide').removeClass('selectshow')
                    $(this).find('input[type=text]').before(clear);
                    $(this).find('.icon-closecircle').show()
                }
            },
            mouseleave: function () {
                $(this).removeClass('iconhide').find('input[type=text]').prev(".icon-closecircle").remove();
                var parent = $(this).parents('.group-select')
                if (parent) {
                    parent.find('.scm-input-wrap').addClass('selectshow')
                }

            },
        }, ".scm-input-wrap")
        $(document).on('mouseenter', '.icon-closecircle', function () {
            $(this).on('click', function () {
                $(this).siblings('input').val('');
                $(this).parents('.scm-input-wrap').removeClass('iconhide')
                var parent = $(this).parents('.group-select')
                var that = $(this)
                $(this).remove()
                if (parent) {
                    parent.find('.scm-input-wrap').addClass('selectshow')
                }
            })
        })
    },

    // table操作按钮下拉
    fn_tableselect: function () {
        $(document).on('click', '.tableselect .icon-down', function (e) {
            e.stopPropagation()
            var _this = $(this).parents('.tableselect')[0]
            var _this1 = $(this).parents('.tableselect').find('.tableselect-bottom')[0]
            $('.tableselect').not(_this).removeClass("active")
            $('.tableselect-bottom').not(_this1).slideUp(300)
            $(this).parents('.tableselect').toggleClass('active')
            $(this).parents('.tableselect').find('.tableselect-bottom').stop(true, true).slideToggle(300)
        })
        //点击其他地方消失
        $(document).on("click", function (e) {
            $('.tableselect-bottom').slideUp(300)
            $('.tableselect').removeClass("active")
        })
    },
    // 上传
    fn_selectexcel: function () {
        $('.selectexcle').on('click', 'button', function () {
            $(this).siblings('input[type=file]').click();
        })
        $('.selectexcle').on('change', 'input[type=file]', function () {
            $(this).siblings('span').html(this.files[0].name);
        })
    },
    fn_fileImg:function(){
        function getFileUrl(obj) { 
            var url,name; 
            if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
                url = obj.value; 
                name = url.substring( url.lastIndexOf('\\')+1);
            } else{ 
                url = window.URL.createObjectURL(obj.files.item(0)); 
                name = obj.files[0].name;
            }
                return {url:url,name:name}; 
        }
        $('body').on('change','.fileImg input',function(){
            var val = getFileUrl(this); 
            var img='<img class="fileImgPreview" src='+val.url+'>'
            $(this).parent().append(img)
            
            // $(this).parent().find('img').eq(1).attr('src',val.url);
            // $(this).parent().prev('button').html(val.name).parent().addClass('active');
        })
      
    },
    

    // 单复选框
    fn_checkbtn: function () {
        $(document).on('click', '.mycheckbox', function () {
            if ($(this).hasClass('active')) {
                $(this).removeClass('active')
            } else {
                $(this).addClass('active')
            }
        })
        $(document).on('click', '.mycheckradio', function () {
            $(this).parents('.mycheckradioall').find('.mycheckradio').removeClass('active')
            $(this).addClass('active');
        })
    },
    // table内下拉框滚动
    fn_tableSelect: function () {
        setTimeout(function () {

            $(document).on('click', '.box-table .fixed-table-body .selectshowAction', function () {
                var top = parseInt($(this).closest('tr').position().top) + 30 + 'px';
                var left = $(this).position().left

                $(this).siblings('.select').css({
                    'top': top,
                    'left': left
                })
            })
            $(".box-table .fixed-table-body").scroll(function () {
                $('.box-table .fixed-table-body .group-select .select').hide();
            })

        }, 1)

    },
    // 设置table高度
    fn_tableHeight: function () {
        var height = $(".form-list:visible").outerHeight(true) + $(".operation:visible").outerHeight(true) + 20

        return height
    },
    // 点击table行添加背景色
    fn_tableTr: function () {
        $(document).on('click', '.box-tableTr .table tbody>tr', function () {
            $(this).toggleClass('active')
            if ($(this).hasClass('active')) {

                $(this).siblings('tr').removeClass('active')
            }
        })
    },
  
   
    // 点击左侧菜单radio列表显示修改按钮
    fn_leftsideRadio: function () {
        var str = '<button class="scm-btn blue btn-edit">修改</button>'
        $(document).on('click', '.radio-list:not(.radio-list-notedit) li:not(.read)', function () {
            $(this).parents('.radio-list').find('li').removeClass('active')
                .find('label').removeClass('active')
              
            $(this).parents('.radio-list').find('.btn-edit').remove()
            $(this).addClass('active')
                .append(str)
                .find('label').addClass('active')
        })
        $(document).on('click', '.radio-list-notedit li:not(.read)', function () {
            $(this).parents('.radio-list').find('li').removeClass('active')
                .find('label').removeClass('active')
               
            $(this).addClass('active').find('label').addClass('active')
        })
    },
    //table备注
    fn_formatterNote: function (value) {
        return "<div class='note ellipsis'>" + value + "</div>"

    },
    // table增加编辑框
    fn_formatterInput: function (value) {
    	if(value!=''&&value!=null){
    		 return '<input style="width:80px" type="text" value="' + value + '" />';
    	}else{
    		 return '<input style="width:80px" type="text"  />';
    	}
       
    },
    fn_formatterInput2: function (value) {
    	if(value!=''&&value!=null){
    		 return '<input style="width:80px" onblur="onInputchange(this)" type="text" value="' + value + '" />';
    	}else{
    		 return '<input style="width:80px" onblur="onInputchange(this)" type="text"  />';
    	}
       
    },
    // 清除字符串前后空格（若第二个参数为'g',包括清除中间空格）
    fn_clearSpace: function (str, is_global) {
        var result;
        result = str.replace(/(^\s+)|(\s+$)/g, "");
        if (is_global&&is_global.toLowerCase() == "g") {
            result = result.replace(/\s/g, "");
        }
        return result;
    },
    // input失去焦点保存值
    getData: function (target) {
        var aim = this;
        var odiv = $(aim).closest('table'),
            otr = $(aim).closest('tr'),
            otrindex = $(aim).closest('tr').index(),
            otd = $(aim).closest('td'),
            otdindex = otd.index(),
            oid = otr.data('uniqueid'),
            odata = odiv.bootstrapTable('getRowByUniqueId', oid),
            options = odiv.bootstrapTable('getOptions');
        ofield = options.columns[0][otdindex].field;
        odata[ofield] = otd.find('input').val();
    },
    fn_saveData: function (linkTitle) {
        $(document).on('blur', '.fixed-table-body td input',public.getData);
    },


    //汉化
    fn_chinese: function () {

        // 日历汉化
        $.daterangepicker = function (pickerSelect, options) {
            var defualts = {
                singleDatePicker: true, //设置成单日历
                 startDate: moment().startOf('day'),
                endDate: moment().startOf('day'),
                defaultValue:false,
                // minDate: '01/01/2012', //最小时间
                // maxDate: moment().subtract(-1, 'days'), //最大时间 今天
                timePicker: false, //是否显示小时和分钟
                timePickerIncrement: 1, //时间的增量，单位为分钟
                timePicker12Hour: false, //是否使用24小时制来显示时间
                "autoApply": false, // 选中后自动关闭控件窗口
                format: "YYYY-MM-DD", // 日期格式
                separator: " ~ ",
                "locale": {
                    daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
                    monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
                    applyLabel: '确定',
                    cancelLabel: '清空'
                },
                "applyClass": 'blue',
                "dateLimit": {
                    "days": 2000 // 时间的最大选择范围。
                },

                ranges: {
                    '今日': [moment(), moment()],
                    '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '最近7日': [moment().subtract(6, 'days'), moment()],
                    // '这周': [moment().startOf('week'), moment().endOf('week')],
                    '最近30日': [moment().subtract(29, 'days'), moment()],
                    '近三个月': [moment().subtract(3, 'month'), moment()],
                    '最近1年': [moment().subtract(365, 'days'), moment()],
                }
            }
            var opts = $.extend({}, defualts, options);
            $(pickerSelect).daterangepicker(opts)
        };
        // 页面缩放关闭日历
        $(window).resize(function () {
            $('.daterangepicker').hide();
        });
    },


    init: function (a) {
        this.fn_account();
        this.fn_sideBar();
        this.fn_windowTab();
        this.fn_navTab();

        this.fn_sideBarHeight();
        this.fn_resize();
        this.fn_select();
        //this.fn_inputClose();
        this.fn_tableselect();
        this.fn_selectexcel();
        this.fn_fileImg();
        
        this.fn_checkbtn();
        this.fn_tableSelect();
        this.fn_tableHeight();
        this.fn_tableTr();
        this.fn_leftsideRadio();
        //this.fn_saveData();
        this.fn_chinese();
    }
}
$(function (a) {
    public.init(a);
})
function closeParentPage(menuId){
    $('#tabList li[menuId=' + menuId + ']').remove();
}

function openTabNew(menuId, title, url) {
	 if (url&&url!='') {
         if (!$('#tabList li[menuId=' + menuId + ']').length) {
             qims.fn_addtabiframe(menuId, title, url);
         } else {
        	 qims.fn_tabOverflow($('#tabList li[menuId=' + menuId + ']').index());
        	 //刷新iframe
             $('#tabIframe div[menuId=' + menuId + ']').children().eq(0).attr("src",url);
         }
         $('#tabList li[menuId=' + menuId + ']').click();
        
         
     }
}

function fnTabOverflow(number) {
        fnTabBorder();
        var total = 0,
            arrAll = $('#tabList li'),
            windowWidth = $(window).width() - $('.leftside').outerWidth(true) - 60;
        $('#tabList li').show();
        $('.more .more-list').empty();
        for (var i = number; i >= 0; i--) {
            var _width = arrAll.eq(i).outerWidth(true);
            total += _width;
            if (total > windowWidth) {
                arrAll.eq(i).hide();
                arrAll.eq(number).next('li').hide();
                $('.more').show();
            }
        };
        if (total <= windowWidth && number < arrAll.length - 1) {
            for (var i = number + 1; i < arrAll.length; i++) {
                var _width = arrAll.eq(i).outerWidth(true);
                total += _width;
                if (total > windowWidth) {
                    arrAll.eq(i).hide();
                }
            };
        }
        $('#tabList li:hidden').each(function (index, item) {
            var clone = item.outerHTML.replace('<i class="anticon icon-close"></i>', "");
            $('.more .more-list').append(clone).find('li').show();
        });
    }

function fnTabBorder() {
    if ($('#tabList li').length === 0) {
        $(".window-wrap").removeClass('active');
    } else {
        $(".window-wrap").addClass('active');
    }
}
//初始化日期框，传入id
function initdate(column){
	$(column).daterangepicker({
		singleDatePicker: true,
	    showDropdowns: true,
	    autoUpdateInput: false,
	    startDate: moment(),
	    timePicker : true,
	    format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式 
	    "locale": {
	    	daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
	        monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
	                       '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
	        firstDay : 1 
	    }
	}, function(start, end, label) { 
	}); 
}
//初始化下拉框
function initselect(valueField,textField,url,id){
	var html='';
	$('#'+id).html('');
	 $.ajax({
	        url: url,
	        data: '',
	        type: 'POST',
	        dataType: 'json',
	        success: function(data) {
	        	if(data!=null&&data.length>0){
	        		for(var i=0;i<data.length;i++){
	        			//.操作需要特殊处理才能获取到值
	        			var value='';
	        			var text='';
	        			for(var key in data[i]){
	        				if(key==valueField){
	        					if(data[i][key]!=null)
	        					value=data[i][key];
	        				}
	        				if(key==textField){
	        					text=data[i][key];
	        				}
	        			}
	        			html +='<li value="'+value+'">'+text+'</li>'
	        		}
	        	}
	        	if(html!=''){
	        		$('#'+id).html(html);
	        	}
	        }
	    });
}

