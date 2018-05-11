

layui.use(['form', 'table', 'layer', 'laytpl'], function () {
    var form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        layer = layui.layer;

    //  初始化列表
    var menutable = table.render({
        elem: '#menu-manage',
        url: 'menu/userList',
        page: 'true',
        cols: [[
            {field: 'username', title: '用户名', width: 200},
            {field: 'loginname', title: '登录名', width: 200},
            {field: 'loginTime', title: '最后登录时间', width: 200},
            {field: 'addTime', title: '添加时间', width: 200},
            {title: '操作', width: 250, fixed: 'right', align: 'center', toolbar: '#checkUserMenu'}
        ]],
        height: 750,
        id: 'menu'
    });

    table.on('tool(menu)', function (obj) {
        var data = obj.data;
        if (obj.event === 'editMune') {
            $.post("menu/getMenuTree", {userId:data.id}, function (res) {
                var ztreeData = res.data;
                var checkMenuHtml = checkMenu.innerHTML;
                laytpl(checkMenuHtml).render(data, function (html) {
                    var index = layer.open({
                        type: 1,
                        title: '角色权限',
                        content: html,
                        area: ['350px', '350px'],
                        success: function (layero, index) {
                        }
                    })
                    // 初始化ztree
                    var setting = {
                        async: {
                            enable: false
                        },
                        check: {
                            enable: true,
                        },
                    };
                    $.fn.zTree.init($("#accessTree"), setting, ztreeData);

                    // 监听修改事件表单
                    form.on('submit(updateMenu)', function () {
                        var ztreeObj = $.fn.zTree.getZTreeObj('accessTree');
                        var checkZnodes = ztreeObj.getCheckedNodes();
                        var checkedValueArr = [];
                        $.each(checkZnodes, function (czi, czv) {
                            checkedValueArr.push(czv.id);
                        })
                        $.ajax({
                            url: 'menu/updateMenu',
                            method: 'post',
                            data: {
                                userId: data.id,
                                mids: checkedValueArr
                            },
                            traditional: true,
                            success: function (data) {
                                layer.closeAll();
                                if (data.code == 0) {
                                    layer.msg('修改成功!');
                                    menutable.reload();
                                } else {
                                    layer.msg('修改失败!');
                                    menutable.reload();
                                }
                            }
                        });
                        return false;
                    })
                })
            });
        }
        else if (obj.event == "edit") {
            var editHtml = $('#edit').html();
            laytpl(editHtml).render(data, function (html) {
                var index = layer.open({
                    type: 1,
                    title: '修改管理员信息',
                    content: html,
                    area: ['400px', '400px'],
                    btn:['确定','取消'],
                    yes: function (index, layero) {
                        var id = data.id;
                        var loginname = $("#loginname").val();
                        if(!loginname){
                            layer.msg('请填写登录名');
                            return false;
                        }
                        var username = $("#username").val();
                        if (!username) {
                            alert("请填写用户名");
                            return false;
                        }
                        var remark = $("#remark").val();
                        $.post('login/form',{
                            id:id,
                            loginname:loginname,
                            username:username,
                            remark:remark
                        },function (result) {
                            if(result.code == 0){
                                layer.msg('修改成功');
                                layer.closeAll();
                                menutable.reload();
                            }else{
                                layer.msg(result.msg);
                            }
                        })
                        return false;
                    }
                });
            });

        }
        else if(obj.event == "delete"){
            var id = data.id;
            layer.confirm('你确认要删除吗？', function (index) {

                $.post('login/delete',{id:id},function (result) {
                    if (result != null) {
                        layer.msg(result.msg);
                        if(result.code == 0){
                            obj.del();
                        }
                    }
                });
            })
        }
    });

    $('.inducbtnadd').on('click', function () {
        var addHtml = $('#add').html();
        layer.open({
            type: 1,
            title: '添加管理员信息',
            content: addHtml,
            area: ['400px', '400px'],
            btn:['确定','取消'],
            yes: function (index, layero) {
                var loginname = $("#loginname").val();
                if(!loginname){
                    layer.msg('请填写登录名');
                    return false;
                }
                var username = $("#username").val();
                if (!username) {
                    alert("请填写用户名");
                    return false;
                }
                var remark = $("#remark").val();
                $.post('login/form',{
                    loginname:loginname,
                    username:username,
                    remark:remark
                },function (result) {
                    if(result.code == 0){
                        layer.msg('添加成功');
                        layer.closeAll();
                        menutable.reload();
                    }else{
                        layer.msg(result.msg);
                    }
                });
                return false;
            }
        });
    });

    form.on('submit(search)', function (obj) {
        menutable.reload({
            where: obj.field
        });
        return false;
    });
    form.render();

})