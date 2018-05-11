

layui.use(['form', 'table', 'layer', 'laytpl'], function () {
    var form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        layer = layui.layer;

    //  初始化列表
    var fulltimetable = table.render({
        elem: '#fulltime-manage',
        url: 'customer/findList',
        page: 'true',
        cols: [[
            {field: 'name', title: '客户姓名', width: 200},
            {field: 'phone', title: '手机号', width: 200},
            {field: 'address', title: '收货地址', width: 200},
            {field: 'remark', title: '备注', width: 200},
            {title: '操作', width: 250, fixed: 'right', align: 'center', toolbar: '#entryopertion'}
        ]],
        height: 750,
        id: 'fulltime'
    });

    table.on('tool(fulltime)', function (obj){
        var data = obj.data;
        if (obj.event == "edit") {
            var editHtml = $('#edit').html();
            laytpl(editHtml).render(data, function (html) {
                var index = layer.open({
                    type: 1,
                    title: '修改客户信息',
                    content: html,
                    area: ['400px', '400px'],
                    btn:['确定','取消'],
                    yes: function (index, layero) {
                        var id = data.id;
                        var name = $("#name").val();
                        if(!name){
                            layer.msg('请填写客户姓名');
                            return false;
                        }
                        var phone = $("#phone").val();
                        var reg = /^1(3|4|5|7|8|9)\d{9}$/;
                        if (!reg.test(phone)) {
                            alert("请填写有效的手机号");
                            return false;
                        }
                        var address = $("#address").val();
                        var remark = $("#remark").val();
                        $.post('customer/form',{
                            id:id,
                            name:name,
                            phone:phone,
                            address:address,
                            remark:remark
                        },function (result) {
                            if(result.code == 0){
                                layer.msg('修改成功');
                                layer.closeAll();
                                fulltimetable.reload();
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

                $.post('customer/delete',{id:id},function (result) {
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
            title: '添加客户信息',
            content: addHtml,
            area: ['400px', '400px'],
            btn:['确定','取消'],
            yes: function (index, layero) {
                var name = $("#name").val();
                if(!name){
                    layer.msg('请填写客户姓名');
                    return false;
                }
                var phone = $("#phone").val();
                var reg = /^1(3|4|5|7|8|9)\d{9}$/;
                if (!reg.test(phone)) {
                    alert("请填写有效的手机号");
                    return false;
                }
                var address = $("#address").val();
                var remark = $("#remark").val();
                $.post('customer/form',{
                    name:name,
                    phone:phone,
                    address:address,
                    remark:remark
                },function (result) {
                    if(result.code == 0){
                        layer.msg('添加成功');
                        layer.closeAll();
                        fulltimetable.reload();
                    }else{
                        layer.msg(result.msg);
                    }
                });
                return false;
            }
        });
    });

    // 离职列表查询
    form.on('submit(search)', function (data) {
        fulltimetable.reload({
            where: data.field
        });
        return false;
    });

    form.render();

})