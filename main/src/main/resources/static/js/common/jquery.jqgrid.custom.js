$.fn.grid = function (o) {
    //replace icons with FontAwesome icons like above
    function updatePagerIcons(table) {
        const replacement = {
            'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
            'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
            'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
            'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            const icon = $(this);
            const $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

    function enableTooltips(table) {
        $('.navtable .ui-pg-button').tooltip({
            container: 'body'
        });
        $(table).find('.ui-pg-div').tooltip({
            container: 'body'
        });
    }

    function style_edit_form(form) {
        //update buttons classes
        let buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
        buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

        buttons = form.next().find('.navButton a');
        buttons.find('.ui-icon').hide();
        buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
        buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
    }

    function style_delete_form(form) {
        let buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
        buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
    }

    function jqgDelOptions(url, token) {
        return {
            url: url,
            delData: {"_csrf": token},
            top: $(window).height() / 3 + $(document).scrollTop(),
            left: $(window).width() / 2.5,
            width: $(window).width() / 3,
            recreateForm: true,
            closeAfterEdit: true,
            beforeShowForm: function (e) {
                let form = $(e[0]);
                style_delete_form(form);
            },
            // afterSubmit: resultMsg,
            // errorTextFormat: errorMsg
        };
    }

    function jqgEditOptions(url, token, formHeight, formWidth, formDataHeight, top, left) {
        return {
            url: url,
            editData: {"_csrf": token},
            top: !top ? $(window).height() / 5 + $(document).scrollTop() : top,
            left: !left ? $(window).width() / 3 : left,
            width: !formWidth ? $(window).width() / 3 : formWidth,
            height: !formHeight ? 'auto' : formHeight,
            dataheight: !formDataHeight ? 'auto' : formDataHeight,
            resize: true,
            recreateForm: true,
            closeAfterEdit: true,
            beforeShowForm: function (e) {
                let form = $(e[0]);
                style_edit_form(form);
            },
            // afterSubmit: resultMsg,
            // errorTextFormat: errorMsg
        };
    }

    // 增加编辑列参数
    const acColModel = [{
        label: ' ',
        name: 'myac',
        index: '',
        width: !!o.editUrl && !!o.delUrl ? 70 : 60,
        align: 'center',
        fixed: true,
        sortable: false,
        resize: false,
        formatter: 'actions',
        formatoptions: {
            keys: true,
            editbutton: false,
            editformbutton: !!o.editUrl,
            editOptions: $.extend(o.editOptions || {}, jqgEditOptions(o.editUrl, o.token, o.formEditHeight, o.formEditWidth, o.formDataHeight, o.top, o.left)),
            delbutton: !!o.delUrl,
            delOptions: $.extend(o.delOptions || {}, jqgDelOptions(o.delUrl, o.token))
        },
    }];

    if (o && o.colModel) {
        o.colModel = acColModel.concat(o.colModel);
    } else {
        o.colModel = acColModel;
    }

    // 预定义jqGrid参数，以下参数均可覆盖
    o = $.extend({
        datatype: "json",
        height: "100%",
        rowNum: 10,
        rowList: [10, 20, 50, 100],
        pager: '#grid-pager',           // 默认导航栏id
        altRows: true,
        disableView: true,
        multiselect: !!o.delUrl,
        multiboxonly: !!o.delUrl,
        prmNames: {page: "page", rows: "size"},
        jsonReader: {page: "page.number", total: "page.totalPages", records: "page.totalElements", root: "content"},
        serializeGridData: function (postData) {
            // 把id为form的表单数据附加到postData
            // return $.extend($("#" + (o.form ? o.form : "form")).serializeObject(), postData);
        },
        loadError: function (xhr, status, error) {   // 弹出错误信息
            // showErrMsg(xhr && xhr.responseJSON && xhr.responseJSON.msg ? xhr.responseJSON.msg : null);
        },
        loadComplete: function (data) {
            // if (data && data.result && data.result === 'ERROR') {
            //     // showErrMsg(data.msg);
            //     return;
            // }
        }
    }, o || {});

    const grid = $(this).jqGrid(o);

    const parent_column = $(this).closest('[class*="col-"]');

    //resize to fit page size
    $(window).on('resize.jqGrid', function () {
        grid.jqGrid('setGridWidth', parent_column.width());
    });

    //resize on sidebar collapse/expand
    $(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
        if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
            //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
            setTimeout(function () {
                grid.jqGrid('setGridWidth', parent_column.width());
            }, 20);
        }
    });

    //trigger window resize to make the grid get the correct size
    $(window).triggerHandler('resize.jqGrid');

    setTimeout(function () {
        updatePagerIcons(grid);
        enableTooltips(grid);
    }, 20);

    return grid;
};
