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

    function styleEditForm(form) {
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

    function styleDeleteForm(form) {
        let buttons = form.next().find('.EditButton .fm-button');
        buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
        buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
        buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
    }

    function resultMsg(response, postdata, oper) {
        const result = eval('(' + response.responseText + ')');
        if (!result || !result.code) {
            return [false, '服务器异常'];
        }
        if (result.code === 1) {
            // layer.msg(!result.msg ? '成功！' : result.msg, {
            //     icon: 1
            // });
            return [true, "", ""];
        } else {
            return [false, !result.msg ? '服务器异常' : result.msg];
        }
    }

    function errorMsg(response) {
        const result = eval('(' + response.responseText + ')');
        return [false, !result.msg ? '服务器异常' : result.msg];
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
                if(form.data('styled')) return false;
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                    .wrapInner('<div class="widget-header" />');
                styleDeleteForm(form);
                form.data('styled', true);
            },
            afterSubmit: resultMsg,
            errorTextFormat: errorMsg
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
                form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                    .wrapInner('<div class="widget-header" />');
                styleEditForm(form);
            },
            afterSubmit: resultMsg,
            errorTextFormat: errorMsg
        };
    }

    function jqgAddFunc(grid_id, beforeProcessing, afterProcessing) {
        return function () {
            const grid = $("#" + grid_id);

            if (beforeProcessing) {
                beforeProcessing();
            }

            const url = $(this).attr("data-url");
            const token = $(this).attr("data-token");
            const id = $(this).attr("data-id");
            const editData = id ? {"_csrf": token, "id": id} : {"_csrf": token};

            let formHeight = grid.getGridParam("formEditHeight");
            let formDataHeight = grid.getGridParam("formDataHeight");
            let formWidth = grid.getGridParam("formEditWidth");
            let top = grid.getGridParam("top");
            let left = grid.getGridParam("left");

            grid.editGridRow("new", {
                url: url,
                editData: editData,
                top: !top ? $(window).height() / 5 + $(document).scrollTop() : top,
                left: !left ? $(window).width() / 3 : left,
                width: !formWidth ? $(window).width() / 3 : formWidth,
                height: !formHeight ? 'auto' : formHeight,
                dataheight: !formDataHeight ? 'auto' : formDataHeight,
                resize: true,
                recreateForm: true,
                closeAfterAdd: true,
                beforeShowForm: function (e) {
                    let form = $(e[0]);
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                        .wrapInner('<div class="widget-header" />');
                    styleEditForm(form);
                },
                afterSubmit: resultMsg,
                errorTextFormat: errorMsg
            });

            if (afterProcessing) {
                afterProcessing();
            }
        }
    }

    function jqgDelFunc(grid_id, data_url, data_token, options) {
        return function () {
            let i;
            const grid = $("#" + grid_id);

            if (!grid.getGridParam("selrow")) {
                // layer.msg("请选择需要删除的行", {icon: 0});
                return;
            }

            const url = data_url ? data_url : $(this).attr("data-url");
            const token = data_token ? data_token : $(this).attr("data-token");
            const selectedIds = grid.getGridParam("selarrrow");
            const idList = new Array();
            for (i = 0; i < selectedIds.length; i++) {
                if (selectedIds[i] !== '') {
                    idList.push(selectedIds[i]);
                }
            }

            let params = {
                keys: true,
                url: url,
                delData: {
                    "_csrf": token,
                    "idList": idList
                },
                top: $(window).height() / 3 + $(document).scrollTop(),
                left: $(window).width() / 2.5,
                afterSubmit: resultMsg,
                errorTextFormat: errorMsg
            };

            params = $.extend(true, params, options || {});
            for (i = 0; i < selectedIds.length; i++) {
                const rowKey = selectedIds[i];
                grid.delGridRow(rowKey, params);
            }
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
        pager: '#grid-pager', // 默认导航栏id
        viewrecords: true,
        altRows: true,
        disableView: true,
        multiselect: !!o.delUrl,
        multiboxonly: !!o.delUrl,
        prmNames: {page: "page", rows: "size"},
        jsonReader: {page: "page.number", total: "page.totalPages", records: "page.totalElements", root: "content"},
        serializeGridData: function (postData) {
            // 把id为form的表单数据附加到postData
            return $.extend($("#" + (o.form ? o.form : "form")).serializeObject(), postData);
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

    $("#jqgSearch").click(function () {
        grid.trigger('reloadGrid');
    });

    $("#jqgAdd").click(jqgAddFunc("grid-table"));
    $("#jqgDel").click(jqgDelFunc("grid-table"));

    setTimeout(function () {
        updatePagerIcons(grid);
        enableTooltips(grid);
    }, 20);

    return grid;
};
