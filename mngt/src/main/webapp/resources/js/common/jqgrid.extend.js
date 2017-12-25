{
    // replace icons with FontAwesome icons like above
    function updatePagerIcons(table) {
        var replacement = {
            'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
            'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
            'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
            'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
            if ($class in replacement)
                icon.attr('class', 'ui-icon ' + replacement[$class]);
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

    function resultMsg(data, postdata, oper) {
        var response = eval('(' + data.responseText + ')');

        if (!response || !response.result) {
            return [false, '服务器异常'];
        }

        if (response.result === "SUCCESS") {
            return [true, "", ""];
        } else {
            return [false, !response.message ? '服务器异常' : response.message];
        }
    }

    function jqgDelOptions(url, token) {
        return {
            url: url,
            delData: {
                "_csrf": token
            },
            top: $(window).height() / 3,
            left: $(window).width() / 2.5,
            closeAfterEdit: true,
            afterSubmit: resultMsg,
        };
    }

    function jqgEditOptions(url, token, formEditHeight) {
        return {
            url: url,
            editData: {
                "_csrf": token
            },
            top: $(window).height() / 5,
            left: $(window).width() / 3,
            width: $(window).width() / 3,
            dataheight: !formEditHeight ? 'auto' : formEditHeight,
            resize: true,
            recreateForm: true,
            closeAfterAdd: true,
            closeAfterEdit: true,
            afterSubmit: resultMsg,
        };
    }

    $.fn.grid = function (o) {
        o = o || {};

        if (!!o.editUrl || !!o.delUrl) {
            // 增加编辑列参数
            var arr = [{
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
                    editOptions: $.extend(jqgEditOptions(o.editUrl, o.token, o.formEditHeight), o.customEditOptions || {}),
                    delbutton: !!o.delUrl,
                    delOptions: $.extend(jqgDelOptions(o.delUrl, o.token), o.delOptions || {})
                },
                editable: true
            }];

            if (o.colModel) {
                o.colModel = arr.concat(o.colModel);
            } else {
                o.colModel = arr;
            }

            // 增加复选框参数
            o = $.extend({
                multiselect: !!o.delUrl,
                multiboxonly: !!o.delUrl
            }, o);
        }

        // 预定义jqGrid参数，以下参数均可覆盖
        o = $.extend({
            prmNames: {
                page: "current",
                rows: "size"
            },
            sortorder: "0",
            jsonReader: {
                page: "current",
                total: "pages",
                records: "total",
                root: "records"
            },
            datatype: "json",
            height: "100%",
            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 50, 100],
            pager: '#grid-pager', // 默认导航栏id
            altRows: true,
            ondblClickRow: function (rowid) { // 双击打开view页面
                if (!!o.viewable) {
                    jQuery(this).jqGrid('viewGridRow', rowid, {
                        top: $(window).height() / 5,
                        left: $(window).width() / 3
                    });
                }
            },
            serializeGridData: function (postData) {
                // 把id为form的表单数据附加到postData
                return $.extend($("#" + (o.form ? o.form : "form")).serializeObject(), postData);
            },
            loadError: function (xhr, status, error) { // 弹出错误信息
                var res = xhr.responseJSON;
                if (res && res.msg) {
                    bootbox.autoCloseDialog({
                        message: res.msg || "发生系统错误",
                        title: "提示"
                    });
                }
            },
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            }
        }, o);

        var grid = $(this).jqGrid(o);

        var parent_column = $(this).closest('[class*="col-"]');
        // resize to fit page size
        $(window).on('resize.jqGrid', function () {
            grid.jqGrid('setGridWidth', parent_column.width());
        });
        // resize on sidebar collapse/expand
        $(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
            if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
                // setTimeout is for webkit only to give time for DOM changes
                // and then redraw!!!
                setTimeout(function () {
                    grid.jqGrid('setGridWidth', parent_column.width());
                }, 20);
            }
        });
        // trigger window resize to make the grid get the correct size
        $(window).triggerHandler('resize.jqGrid');
        return grid;
    };

    $("#jqg_del").click(function () {
        var grid = $("#grid-table");
        var selected = grid.getGridParam("selarrrow");
        if (selected && selected.length > 0) {
            var url = $(this).attr("data-url");
            var token = $(this).attr("data-token");
            var options = jqgDelOptions(url, token);
            grid.delGridRow(selected, options);
        } else {
            bootbox.autoCloseDialog({
                message: "请选择需要删除的行",
                title: "提示"
            });
        }
    });

    $("#jqg_add").click(function () {
        var grid = $("#grid-table");
        var url = $(this).attr("data-url");
        var token = $(this).attr("data-token");
        var options = jqgEditOptions(url, token);
        grid.editGridRow("new", options);
    });
}