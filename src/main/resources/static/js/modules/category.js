

layui.use(['form', 'table', 'layer', 'laytpl'], function () {
    var form = layui.form,
        table = layui.table,
        laytpl = layui.laytpl,
        layer = layui.layer;

    //  初始化列表
    var fulltimetable = table.render({
        elem: '#fulltime-manage',
        url: 'category/findList',
        page: 'true',
        cols: [[
            {field: 'name', title: '类别名称', width: 200},
            {field: 'categoryNo', title: '分类编号', width: 200},
            {field: 'remark', title: '备注', width: 200},
            {title: '操作', width: 250, fixed: 'right', align: 'center', toolbar: '#entryopertion'}
        ]],
        height: 750,
        id: 'fulltime'
    });

    table.on('tool(fulltime)', function (obj){
        var data = obj.data;
        if (obj.event == "edit") {
            var editCategoryHtml = $('#editCategory').html();
            laytpl(editCategoryHtml).render(data, function (html) {
                var index = layer.open({
                    type: 1,
                    title: '修改分类',
                    content: html,
                    area: ['400px', '370px'],
                    btn:['确定','取消'],
                    yes: function (index, layero) {
                        var id = data.id;
                        var name = $("#name").val();
                        if(!name){
                            layer.msg('请填写类别名称');
                            return false;
                        }
                        var categoryNo = $("#categoryNo").val();
                        var remark = $("#remark").val();
                        $.post('category/form',{
                            id:id,
                            name:name,
                            categoryNo:categoryNo,
                            remark:remark
                        },function (result) {
                            if(result.code == 0){
                                layer.msg('添加成功');
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
                obj.del();
                $.post('category/delete',{id:id},function (result) {
                    if (result != null) {
                        layer.msg(result.msg);
                    }
                });
            })
        }
    })

    $('.inducbtnadd').on('click', function () {
        var addCategoryHtml = $('#addCategory').html();
        layer.open({
            type: 1,
            title: '添加分类',
            content: addCategoryHtml,
            area: ['400px', '370px'],
            btn:['确定','取消'],
            yes: function (index, layero) {
                var name = $("#name").val();
                if(!name){
                    layer.msg('请填写类别名称');
                    return false;
                }
                var categoryNo = $("#categoryNo").val();
                var remark = $("#remark").val();
                $.post('category/form',{
                    name:name,
                    categoryNo:categoryNo,
                    remark:remark
                },function (result) {
                    if(result.code == 0){
                        layer.msg('添加成功');
                        layer.closeAll();
                        fulltimetable.reload();
                    }else{
                        layer.msg(result.msg);
                    }
                })
                return false;
            }
        });
    })

    form.render();

})