var setting = {
    async: {
        enable: false
    },
    callback: {
        onClick: function (event, treeId, treeNode) {//事件的类型可变
            edit(treeNode);
        }
    }
};

var setting2 = {
    async: {
        enable: false
    },
    check: {
        enable: true,
        chkStyle: "radio",  //单选框
        radioType: "all"   //对所有节点设置单选
    },
    callback: {
        onCheck: function (event, treeId, treeNode) {//事件的类型可变
            $('#pid').val(treeNode.id);
        }
    }
};

$(function () {

    //主页树加载
    $.ajax({
        async: false,//是否异步
        cache: false,//是否使用缓存
        type: 'POST',//请求方式：post
        data: {},
        dataType: 'json',//数据传输格式：json
        url: 'menu/findListByTree',//请求的action路径
        success: function (result) {
            //把后台封装好的简单Json格式赋给treeNodes
            var treeObj = $.fn.zTree.init($("#mainTree"), setting, result.data);
            treeObj.expandAll(true);
        }
    });
})

function edit(obj) {
    $('#id').val("");
    $('#pid').val("");
    $('#name').val("");
    $('#url').val("");
    $('#code').val("");
    $('#sort').val("");
    if (obj != null && obj.id != null && obj.id != '') {
        $('#title').text("修改菜单");
        $('#id').val(obj.id);
        $('#pid').val(obj.pid);
        $('#name').val(obj.name);
        $('#url').val(obj.menuUrl);
        $('#code').val(obj.code);
        $('#sort').val(obj.sort);
        $('.layui-layer-btn2').show();
        onLoadZTree(obj.pid);
    } else {
        $('#title').text("添加菜单");
        $('.layui-layer-btn2').hide();
        onLoadZTree();
    }
    $("#addtagtpl").show();
}

function onLoadZTree(pid) {
    $.ajax({
        async: false,//是否异步
        cache: false,//是否使用缓存
        type: 'POST',//请求方式：post
        data: {"pid": pid},
        dataType: 'json',//数据传输格式：json
        url: 'menu/findListByTree',//请求的action路径
        success: function (result) {
            //把后台封装好的简单Json格式赋给treeNodes
            $.fn.zTree.init($("#accessTree"), setting2, result.data);
        }
    });
}

function cancle() {
    $("#addtagtpl").hide();
}
//提交
function sub() {
    var id = $('#id').val();
    var pid = $('#pid').val();
    if (pid == null || pid == '') {
        layer.msg("上级菜单不能为空!");
        return false;
    }
    var name = $('#name').val();
    if (name == null || name == '') {
        layer.msg("名称不能为空!");
        return false;
    }
    var url = $("#url").val();
    var code = $("#code").val();
    var sort = $('#sort').val();
    $.ajax({
        url: 'menu/add',
        method: 'post',
        data: {
            "id": id,
            "name": name,
            "menuUrl": url,
            "code": code,
            "pid": pid,
            "sort":sort
        },
        dataType: 'json',
        success: function (result) {
            if (result.code == 0) {
                layer.msg(result.msg);
                ajaxhtml('menu/index');
            } else {
                layer.msg(result.msg);
            }
        }
    });
}

function del() {
    var id = $("#id").val();
    if (id === "" || undefined) return;
    layer.confirm('确定删除此菜单？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: 'menu/del',
            method: 'post',
            data: {"id": id},
            dataType: 'json',
            success: function (result) {
                if (result.code == 2) {
                    layer.msg(result.msg);
                } else {
                    layer.msg(result.msg);
                }
                ajaxhtml('menu/index');
            }
        });
    })
}

function ajaxhtml(url) {
    $layui_body_container = $('.layui-body-container')
    $layui_body_container.html("");
    if (url === "" || undefined) return;
    //显示加载loading动画
    layer.load(2);
    //请求成功，关闭loading动画
    $layui_body_container.load(url, function (response, status, xhr) {
        if (status === "success") {
            layer.closeAll('loading');
        } else if (status === "error") {
            layer.closeAll('loading');
            layer.msg("加载失败，请检查网络链接是否正常！");
        }
    });
}