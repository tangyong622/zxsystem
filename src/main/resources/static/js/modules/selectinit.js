layui.define('layer',function(exports){

    var selectObj = {
        // 高级部门option
        forsenioroption:function(data,isall){
            var optionstr = "<option value=''>请选择高级部门</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].seniorId+"'>"+data[i].name+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充高级部门
        selectsenioroption:function($dept,url,param,form,id){
            //
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.forsenioroption(dataoption);
                $dept.append(optionstr);
                if(id!==undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==id){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 部门option
        fordeptoption:function(data,isall){
            var optionstr = "<option value=''>请选择部门</option>";
            for (var i = 0; i < data.length; i++) {
               var optionone="<option value='"+data[i].departmentid+"'>"+data[i].department+"</option>"   
               optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充部门
        selectdeptoption:function($dept,url,param,form,departmentId){
            //
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.fordeptoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(departmentId!==undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==departmentId){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 分组option
        forgrouoption:function(data,isall){
            var optionstr ="<option value=''>请选择分组</option>";
            if(isall){
                optionstr += "<option value='0'>全部</option>";
            }
            for (var i = 0; i < data.length; i++) {
               var optionone="<option value='"+data[i].groupid+"'>"+data[i].group+"</option>"   
               optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充分组
        selectgrouption:function($dept,url,param,form,isall,datagroupId){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.forgrouoption(dataoption,isall);
                $dept.empty();
                $dept.append(optionstr);
                if(datagroupId!==undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==datagroupId){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 职位option
        forstatoption:function(data){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充职位
        selectstatoption:function($dept,url,param,form,stationId){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.forstatoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(stationId!==undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==stationId){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        useroption:function (data) {
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].empid+"'>"+data[i].username+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        selectuseroption:function ($dept,url,param,form,empid) {
            $.get(url,param,function (data) {
                var dataoption = data.data;
                var optionstr = selectObj.useroption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(empid!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==empid){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            })
        },

        // 招聘专员
        leadernameoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].username+"'>"+data[i].username+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充招聘专员
        selectleadername:function($dept,url,param,form,username){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.leadernameoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(username!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==username){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },

        // 公司员工
        nameoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].empid+"'>"+data[i].username+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充公司员工
        selectname:function($dept,url,param,form,username){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.nameoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(username!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==username){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },

        // 系统名称
        systemnameoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充系统名称
        selectsystemname:function($dept,url,param,form,sysname){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.systemnameoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(sysname!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==sysname){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 系统名称
        systemcompanyoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option data-id='"+data[i].id+"' value='"+data[i].name+"'>"+data[i].name+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充系统名称
        selectcompanyoption:function($dept,url,param,form,sysname){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.systemcompanyoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(sysname!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==sysname){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 岗位职级
        stationrankoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i]+"'>"+data[i]+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充岗位职级
        selectstationrank:function($dept,url,param,form,username){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.stationrankoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(username!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==username){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 职级序列
        rankstationoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充职级序列
        selectrankstation:function($dept,url,param,form,username){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.rankstationoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(username!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==username){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },
        // 办公地点
        addressoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充办公地点
        selectaddressstation:function($dept,url,param,form,username){
            $.get(url,param,function(data){
                var dataoption=data.data;
                var optionstr= selectObj.addressoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(username!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==username){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },

        // 数据字段
        dictoption:function(data,isall){
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone="<option value='"+data[i].value+"'>"+data[i].label+"</option>"
                optionstr+=optionone;
            }
            return optionstr;
        },
        // 填充数据字段
        selectdicttation:function($dept,url,param,form,username){
            $.get(url,param,function(data){
                var dataoption=data;
                var optionstr= selectObj.dictoption(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if(username!=undefined){
                    $dept.find('option').each(function(){
                        if($(this).val()==username){
                            $(this).attr('selected',true);
                        }
                    });
                }
                form.render('select');
            });
        },


    };

    exports('selectinit',selectObj);

});