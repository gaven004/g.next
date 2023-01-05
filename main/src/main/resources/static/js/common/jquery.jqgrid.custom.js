function jqgBuildSelect(text, xhr, cm, col, value_prop, label_prop, show_tips) {
    let html = '<select id="' + cm.name + '" name="' + cm.name + '" size="1" class="FormElement ui-widget-content ui-corner-all">';
    if (typeof show_tips === "undefined" || show_tips) {
        html += '<option value="">请选择...</option>';
    }
    if (text) {
        try {
            let data = JSON.parse(text);
            if (data) {
                if (data.content) {
                    // for spring data rest
                    data.content.forEach(
                        item => html += '<option value="' + item[value_prop] + '">' + item[label_prop] + '</option>'
                    );
                } else if (data.body) {
                    // for old rest api
                    data.body.forEach(
                        item => html += '<option value="' + item[value_prop] + '">' + item[label_prop] + '</option>'
                    );
                }
            }
        } catch (e) {
        }
    }
    html += '</select>';
    return html;
}

(function ($) {
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
            let buttons = form.parent().next().find('.EditButton .fm-button').attr('href', '#');
            buttons.addClass('btn btn-sm');
            buttons.eq(0).removeClass('btn-default').addClass('btn-primary');

            //update next / prev buttons
            form.parent().next()
                .find('.navButton .fm-button')
                .addClass('btn btn-white btn-primary');
        }

        function styleDeleteForm(form) {
            let buttons = form.parent().next().find('.EditButton .fm-button').attr('href', '#');
            buttons.addClass('btn btn-sm');
            buttons.eq(0).removeClass('btn-default').addClass('btn-danger');
        }

        function resultMsg(response, postdata, oper) {
            if (response && response.status && response.status < 400) {
                return [true, "", ""];
            } else {
                return [false, '服务器异常'];
            }
        }

        function showErrMsg(msg, title) {
            if (!msg || /^\s*$/.test(msg)) {
                msg = '<p>无法从服务器获取数据，请试试以下办法：</p><ul><li>检查输入的条件是否有问题</li><li>重新登录系统再试</li></ul>';
            }
            if (!title || /^\s*$/.test(title)) {
                title = '系统异常';
            }
            bootbox.dialog({
                message: msg,
                title: title
            });
        }

        function errorMsg(response) {
            const result = eval('(' + response.responseText + ')');
            return [false, !result.msg ? '服务器异常' : result.msg];
        }

        function jqgDelOptions(url, token) {
            return {
                baseurl: url,
                url: function (id, postdata, o) {
                    return id ? o.baseurl + '/' + id : o.baseurl;
                },
                mtype: 'DELETE',
                delData: {"_csrf": token},
                recreateForm: true,
                closeAfterEdit: true,
                beforeShowForm: function (e) {
                    let form = $(e[0]);
                    if (form.data('styled')) return false;
                    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                        .wrapInner('<div class="widget-header" />');
                    styleDeleteForm(form);
                    form.data('styled', true);
                },
                afterSubmit: resultMsg,
                errorTextFormat: errorMsg
            };
        }

        function jqgEditOptions(url, token, formHeight, formWidth, formDataHeight, top, left, beforeSubmit) {
            return {
                baseurl: url,
                url: function (id, oper, postdata, o) {
                    return id ? o.baseurl + '/' + id : o.baseurl;
                },
                mtype: 'PATCH',
                editData: {"_csrf": token},
                ajaxEditOptions: {
                    dataType: 'json',
                    contentType: "application/json;charset=utf-8",
                    processData: false,
                    headers: {"X-CSRF-TOKEN": _csrf},
                },
                top: !top ? ($(window).height() > 768 ? $(window).height() / 5 + $(document).scrollTop() : 0) : top,
                left: !left ? ($(window).width() > 1200 ? $(window).width() / 3 : 0) : left,
                width: !formWidth ? ($(window).width() > 1200 ? $(window).width() / 3 : 360) : formWidth,
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
                beforeSubmit: beforeSubmit,
                afterSubmit: resultMsg,
                errorTextFormat: errorMsg
            };
        }

        function jqgAddFunc(grid_id, beforeProcessing, afterProcessing, beforeSubmit) {
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
                    ajaxEditOptions: {
                        dataType: 'json',
                        contentType: "application/json;charset=utf-8",
                        headers: {"X-CSRF-TOKEN": _csrf},
                        processData: false
                    },
                    top: !top ? ($(window).height() > 768 ? $(window).height() / 5 + $(document).scrollTop() : 0) : top,
                    left: !left ? ($(window).width() > 1200 ? $(window).width() / 3 : 0) : left,
                    width: !formWidth ? ($(window).width() > 1200 ? $(window).width() / 3 : 360) : formWidth,
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
                    beforeSubmit: beforeSubmit,
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
                    let dialog = bootbox.dialog({
                        message: '<div class="text-center"><i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i> 请选择需要删除的行 </div>',
                        size: 'sm',
                        backdrop: true,
                        closeButton: false,
                    });
                    setTimeout(() => {
                        dialog.modal('hide');
                    }, 3000);
                    return;
                }

                const url = data_url ? data_url : $(this).attr("data-url");
                const token = data_token ? data_token : $(this).attr("data-token");
                const selectedIds = grid.getGridParam("selarrrow");
                const ids = new Array();
                for (i = 0; i < selectedIds.length; i++) {
                    if (selectedIds[i] !== '') {
                        ids.push(selectedIds[i]);
                    }
                }

                let params = {
                    keys: true,
                    url: url,
                    mtype: 'DELETE',
                    delData: {
                        "_csrf": token,
                        "ids": ids
                    },
                    beforeShowForm: function (e) {
                        let form = $(e[0]);
                        if (form.data('styled')) return false;
                        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
                            .wrapInner('<div class="widget-header" />');
                        styleDeleteForm(form);
                        form.data('styled', true);
                    },
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

        $.jgrid.icons.iconsnext = {
            baseIconSet: "jQueryUI",
            common: "fa",
            form: {
                prev: "fa-chevron-left",
                next: "fa-chevron-right",
                save: "fa-check",
                undo: "fa-times",
                del: "fa-trash-o",
                cancel: "fa-times",
            },
        };

        // 增加编辑列参数
        const acColModel = [{
            label: ' ',
            name: 'myac',
            index: '',
            width: !!o.editUrl && !!o.delUrl ? 66 : 40,
            align: 'center',
            fixed: true,
            resize: false,
            sortable: false,
            formatter: 'actions',
            formatoptions: {
                keys: true,
                editbutton: false,
                editformbutton: !!o.editUrl,
                editOptions: $.extend(o.editOptions || {}, jqgEditOptions(o.editUrl, o.token, o.formEditHeight, o.formEditWidth, o.formDataHeight, o.top, o.left, o.beforeEditSubmit)),
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
            iconSet: "iconsnext",
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
            multiselectWidth: 30,
            prmNames: {page: "page", rows: "size"},
            jsonReader: {page: "body.page.number", total: "body.page.totalPages", records: "body.page.totalElements", root: "body.content"},
            serializeGridData: function (postData) {
                // 兼容QueryDSL的Sorting
                if (postData && postData.sidx && postData.sidx.trim().length > 0) {
                    if (postData && postData.sord && postData.sord === 'desc') {
                        postData.sort = postData.sidx + ",desc";
                    } else {
                        postData.sort = postData.sidx + ",asc";
                    }
                }
                // 把id为form的表单数据附加到postData
                return $.extend($("#" + (o.form ? o.form : "form")).serializeObject(), postData);
            },
            serializeEditData: function (postData) {
                return JSON.stringify(postData);
            },
            loadError: function (xhr, status, error) {   // 弹出错误信息
                showErrMsg(xhr && xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : null);
            },
            loadComplete: function (data) {
                if (data && data.result && data.result !== 'SUCCESS') {
                    showErrMsg(data.message);
                }
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

        $("#jqgAdd").click(jqgAddFunc("grid-table", null, null, o.beforeEditSubmit));
        $("#jqgDel").click(jqgDelFunc("grid-table"));

        setTimeout(function () {
            updatePagerIcons(grid);
            enableTooltips(grid);
        }, 20);

        return grid;
    };
})(jQuery);
