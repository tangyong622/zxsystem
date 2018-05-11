

layui.use(['form', 'table', 'layer', 'laytpl','laydate'], function () {
    var form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        layer = layui.layer;

    //  初始化列表
    var fulltimetable = table.render({
        elem: '#fulltime-manage',
        url: 'good/findList',
        page: 'true',
        cols: [[
            {field: 'name', title: '商品名', width: 200},
            {field: 'spec', title: '规格', width: 200},
            {field: 'batchNum', title: '批号', width: 200},
            {field: 'spec', title: '规格', width: 200},
            {field: 'productionDate', title: '生产日期', width: 200},
            {field: 'introduce', title: '简介', width: 200},
            {field: 'remark', title: '备注', width: 200},
            {title: '操作', width: 250, fixed: 'right', align: 'center', toolbar: '#entryopertion'}
        ]],
        height: 750,
        id: 'fulltime'
    });

    //日期范围
    laydate.render({
        elem: '#productionDate'
        , range: ' ~ '
    });

    table.on('tool(fulltime)', function (obj){
        var data = obj.data;
        if (obj.event == "edit") {
            var editHtml = $('#edit').html();
            laytpl(editHtml).render(data, function (html) {
                var index = layer.open({
                    type: 1,
                    title: '修改商品信息',
                    content: html,
                    area: ['400px', '550px'],
                    btn:['确定','取消'],
                    yes: function (index, layero) {
                        var id = data.id;
                        var name = $("#name").val();
                        if(!name){
                            layer.msg('请填写商品名称');
                            return false;
                        }
                        var spec = $("#spec").val();
                        var batchNum = $("#batchNum").val();
                        var productionDate = $("#productionDate2").val();
                        var introduce = $("#introduce").val();
                        var remark = $("#remark").val();
                        $.post('good/form',{
                            id:id,
                            name:name,
                            spec:spec,
                            batchNum:batchNum,
                            productionDate:productionDate,
                            introduce:introduce,
                            remark:remark,
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

                //日期范围
                laydate.render({
                    elem: '#productionDate2'
                    , range: false
                    ,type: 'date'
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
            title: '添加商品',
            content: addHtml,
            area: ['400px', '550px'],
            btn:['确定','取消'],
            yes: function (index, layero) {
                var name = $("#name").val();
                if(!name){
                    layer.msg('请填写商品名称');
                    return false;
                }
                var spec = $("#spec").val();
                var batchNum = $("#batchNum").val();
                var productionDate = $("#productionDate1").val();
                var introduce = $("#introduce").val();
                var remark = $("#remark").val();
                $.post('good/form',{
                    name:name,
                    spec:spec,
                    batchNum:batchNum,
                    productionDate:productionDate,
                    introduce:introduce,
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
        //日期范围
        laydate.render({
            elem: '#productionDate1'
            , range: false
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