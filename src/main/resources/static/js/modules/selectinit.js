layui.define('layer', function (exports) {

    var selectObj = {
        // 选项
        options: function (data, isall) {
            var optionstr = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                var optionone = "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
                optionstr += optionone;
            }
            return optionstr;
        },
        // 填充
        selectoptions: function ($dept, url, param, form, value) {
            $.get(url, param, function (data) {
                var dataoption = data.data;
                var optionstr = selectObj.options(dataoption);
                $dept.empty();
                $dept.append(optionstr);
                if (value != undefined) {
                    $dept.find('option').each(function () {
                        if ($(this).val() == value) {
                            $(this).attr('selected', true);
                        }
                    });
                }
                form.render('select');
            });
        },
    };

    exports('selectinit', selectObj);

});