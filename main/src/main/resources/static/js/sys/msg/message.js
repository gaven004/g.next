jQuery(function ($) {
    let msgEditor;
    let previousTab = 'inbox';
    let messageEntity;

    let breadcrumb = "<li class='active'>站内通信</li>";
    $("#breadcrumb > .active").remove();
    $("#breadcrumb").append($(breadcrumb));

    const Inbox = {
        //displays a toolbar according to the number of selected messages
        display_bar: function (count) {
            if (count == 0) {
                $('#id-toggle-all').removeAttr('checked');
                $('#id-message-list-navbar .message-toolbar').addClass('hide');
                $('#id-message-list-navbar .message-infobar').removeClass('hide');
            } else {
                $('#id-message-list-navbar .message-infobar').addClass('hide');
                $('#id-message-list-navbar .message-toolbar').removeClass('hide');
            }
        }
        ,
        select_all: function () {
            var count = 0;
            $('.message-item input[type=checkbox]').each(function () {
                this.checked = true;
                $(this).closest('.message-item').addClass('selected');
                count++;
            });

            $('#id-toggle-all').get(0).checked = true;

            Inbox.display_bar(count);
        }
        ,
        select_none: function () {
            $('.message-item input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');
            $('#id-toggle-all').get(0).checked = false;

            Inbox.display_bar(0);
        }
        ,
        select_read: function () {
            $('.message-unread input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');

            var count = 0;
            $('.message-item:not(.message-unread) input[type=checkbox]').each(function () {
                this.checked = true;
                $(this).closest('.message-item').addClass('selected');
                count++;
            });
            Inbox.display_bar(count);
        }
        ,
        select_unread: function () {
            $('.message-item:not(.message-unread) input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');

            var count = 0;
            $('.message-unread input[type=checkbox]').each(function () {
                this.checked = true;
                $(this).closest('.message-item').addClass('selected');
                count++;
            });

            Inbox.display_bar(count);
        }
        ,
        //show write mail form
        show_form: async function () {
            if ($('.message-form').is(':visible')) return;

            $('.message-container').append('<div class="message-loading-overlay"><i class="fa-spin ace-icon fa fa-spinner orange2 bigger-160"></i></div>');

            if (messageEntity && messageEntity.id) {
                // 查询附件信息
                const promise = COMM.fetch("sys/attachment?module=MSG_ATTACHMENT&srcRecode=" + messageEntity.id);
                await promise.then(response => {
                    if (response.result === 'SUCCESS' && response.body) {
                        $.extend(messageEntity, {files: response.body, readonly: false});
                    }
                });
            }

            $('.message-container').find('.message-loading-overlay').remove();

            let $message = $('#message-write');
            $('#message-write').empty();
            $('#messageFormTemplate').template(messageEntity || {}).appendTo($message);

            // 校验初始化
            $('form#id-message-form').validate({
                focusInvalid: true,
                ignore: ":hidden:not(select)",
                rules: {
                    recipientId: {
                        required: true,
                    },
                    subject: {
                        required: true
                    }
                },
                messages: {
                    columnId: "请选择收件人",
                    title: "请输入主题",
                },

                highlight: function (e) {
                    $(e).closest('.form-group').addClass('has-error');
                },

                success: function (e) {
                    $(e).closest('.form-group').removeClass('has-error');
                    $(e).closest('.form-group').find('div.help-block').html('');
                },

                errorPlacement: function (error, element) {
                    const block = $(element).closest('.form-group').find('div.help-block');
                    block.html('');
                    error.appendTo(block);
                }
            });

            // reset form??
            msgEditor = CKEDITOR.replace("message", {
                filebrowserImageUploadUrl: 'upload/MSG_IMG?_csrf=' + _csrf,
                height: '30vh'
            });
            $('#recipientId').chosen({});
        },
        save_as_draft: function () {
            $('#message').val(msgEditor.getData());
            const id = $('#id-message-form > input#id').val();
            const formData = new FormData($("#id-message-form")[0]);
            const promise = COMM.fetchAndMessage(
                id ? 'sys/message/' + id + '?action=save' : 'sys/message?action=save',
                id ? 'PATCH' : 'POST',
                formData, null, '保存成功');
            promise.then(data => {
                if (data.result === 'SUCCESS') {
                    $('#id-message-form > input#id').val(data.body.id);
                }
            });
        },
        send_mail: function () {
            if (!$('#id-message-form').valid()) {
                return;
            }

            $('#message').val(msgEditor.getData());
            const id = $('#id-message-form > input#id').val();
            const formData = new FormData($("#id-message-form")[0]);
            const promise = COMM.fetchAndMessage(
                id ? 'sys/message/' + id + '?action=send' : 'sys/message?action=send',
                id ? 'PATCH' : 'POST',
                formData, null, '保存成功');
            promise.then(data => {
                if (data.result === 'SUCCESS') {
                    $('.btn-back-message-list').click();
                }
            });
        },
    };

    $("#inbox-grid-table").grid({
        url: "sys/message?type=inbox&sort=ctime,desc",
        token: _csrf,
        multiselect: true,
        multiboxonly: true,
        altRows: false,
        pager: '#inbox-grid-pager',
        serializeGridData: function (postData) {
            return $.extend($("#inbox-form-search").serializeObject(), postData);
        },
        colModel: [{
            key: true,
            name: 'id',
            width: 60,
            hidden: true,
            editable: false,
        }, {
            name: 'recipients',
            width: 50,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: function (cellvalue) {
                if (cellvalue[0].tag) {
                    if ('UNREAD' == cellvalue[0].tag) {
                        return '<i class="message-star ace-icon fa fa-star orange2"></i>';
                    }
                }
                return '<i class="message-star ace-icon fa fa-star-o light-grey"></i>';
            },
        }, {
            name: 'senderName',
            width: 100,
            editable: false,
        }, {
            name: 'subject',
            width: 300,
            editable: false,
        }, {
            name: 'hasAttachment',
            width: 40,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: function (cellvalue) {
                return cellvalue === 1 ? '<i class="ace-icon fa fa-paperclip bigger-125"></i>' : '';
            },
        }, {
            name: 'stime',
            width: 100,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: 'date',
        }]
    });
    $("#sent-grid-table").grid({
        url: "sys/message?type=sent&sort=ctime,desc",
        token: _csrf,
        multiselect: true,
        multiboxonly: true,
        altRows: false,
        pager: '#sent-grid-pager',
        serializeGridData: function (postData) {
            return $.extend($("#sent-form-search").serializeObject(), postData);
        },
        colModel: [{
            key: true,
            name: 'id',
            width: 60,
            hidden: true,
            editable: false,
        }, {
            name: 'recipients',
            width: 100,
            editable: false,
            formatter: function (cellvalue) {
                if (cellvalue && cellvalue.length > 0) {
                    return cellvalue.map(e => e.recipientName).join(', ');
                }
                return '';
            },
        }, {
            name: 'subject',
            width: 300,
            editable: false,
        }, {
            name: 'hasAttachment',
            width: 40,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: function (cellvalue) {
                return cellvalue === 1 ? '<i class="ace-icon fa fa-paperclip bigger-125"></i>' : '';
            },
        }, {
            name: 'stime',
            width: 100,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: 'date',
        }]
    });
    $("#draft-grid-table").grid({
        url: "sys/message?type=draft&sort=ctime,desc",
        token: _csrf,
        multiselect: true,
        multiboxonly: true,
        altRows: false,
        pager: '#draft-grid-pager',
        serializeGridData: function (postData) {
            return $.extend($("#draft-form-search").serializeObject(), postData);
        },
        onCellSelect: function (rowid, iCol, cellcontent, e) {
            if (iCol == 2 || iCol == 3) {
                e.preventDefault();
                messageEntity = $("#draft-grid-table").jqGrid('getRowData', rowid);
                $('#inbox-tabs a[href="#write"]').tab('show');
            }
        },
        onSelectRow: function (rowid, status, e) {
            if (!e.defaultPrevented) {

            }
            return false;
        },
        colModel: [{
            key: true,
            name: 'id',
            width: 60,
            hidden: true,
            editable: false,
        }, {
            name: 'recipients',
            width: 100,
            editable: false,
            formatter: function (cellvalue) {
                if (cellvalue && cellvalue.length > 0) {
                    let label = cellvalue.map(e => e.recipientName).join(', ');
                    let val = cellvalue.map(e => e.recipientId).toString();
                    return '<span class="message-text" val="' + val + '">' + label + '</span>';
                }
                return '';
            },
            unformat: function (cellvalue, options, cellobject) {
                if ('' === cellvalue) {
                    return [];
                }
                return $(cellobject.innerHTML).attr("val").split(',');
            },
        }, {
            name: 'subject',
            width: 300,
            editable: false,
            formatter: function (cellvalue) {
                return '<span class="message-text">' + cellvalue + '</span>';
            },
            unformat: function (cellvalue, options, cellobject) {
                return cellvalue;
            },
        }, {
            name: 'message',
            hidden: true,
            editable: false,
        }, {
            name: 'hasAttachment',
            width: 40,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: function (cellvalue) {
                return cellvalue === 1 ? '<i class="ace-icon fa fa-paperclip bigger-125"></i>' : '';
            },
            unformat: function (cellvalue) {
                return cellvalue === '' ? 0 : 1;
            },
        }, {
            name: 'ctime',
            width: 100,
            fixed: true,
            align: 'center',
            editable: false,
            formatter: 'date',
        }]
    });

    $("#inbox-subject").on('keydown', function (e) {
        if (e.code == 'Enter') {
            e.preventDefault();
            $("#inbox-grid-table").trigger('reloadGrid');
        }
    });
    $("#sent-subject").on('keydown', function (e) {
        if (e.code == 'Enter') {
            e.preventDefault();
            $("#sent-grid-table").trigger('reloadGrid');
        }
    });
    $("#draft-subject").on('keydown', function (e) {
        if (e.code == 'Enter') {
            e.preventDefault();
            $("#draft-grid-table").trigger('reloadGrid');
        }
    });

    //handling tabs and loading/displaying relevant messages and forms
    //not needed if using the alternative view, as described in docs
    $('#inbox-tabs a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        const currentTab = $(e.target).data('target');
        if (currentTab == 'inbox') {
            $('#inbox').siblings().removeClass('in').removeClass('active');
            $('#inbox').addClass('in').addClass('active');
            $(window).triggerHandler('resize.jqGrid');
            previousTab = currentTab;
        } else if (currentTab == 'sent') {
            $('#sent').siblings().removeClass('in').removeClass('active');
            $('#sent').addClass('in').addClass('active');
            $(window).triggerHandler('resize.jqGrid');
            previousTab = currentTab;
        } else if (currentTab == 'draft') {
            $('#draft').siblings().removeClass('in').removeClass('active');
            $('#draft').addClass('in').addClass('active');
            $(window).triggerHandler('resize.jqGrid');
            previousTab = currentTab;
        } else if (currentTab == 'write') {
            $('#write').siblings().removeClass('in').removeClass('active');
            $('#write').addClass('in').addClass('active');
            Inbox.show_form();
        }
    });

    //basic initializations
    $('.message-list .message-item input[type=checkbox]').removeAttr('checked');
    $('.message-list').on('click', '.message-item input[type=checkbox]', function () {
        $(this).closest('.message-item').toggleClass('selected');
        if (this.checked) Inbox.display_bar(1);//display action toolbar when a message is selected
        else {
            Inbox.display_bar($('.message-list input[type=checkbox]:checked').length);
            //determine number of selected messages and display/hide action toolbar accordingly
        }
    });

    //back to message list
    $('.btn-back-message-list').on('click', function (e) {
        e.preventDefault();
        if (previousTab == 'inbox') {
            $('#inbox-tabs a[href="#inbox"]').tab('show');
        } else if (previousTab == 'sent') {
            $('#inbox-tabs a[href="#sent"]').tab('show');
        } else if (previousTab == 'draft') {
            $('#inbox-tabs a[href="#draft"]').tab('show');
        }
        $("#inbox-grid-table").trigger('reloadGrid');
        $("#sent-grid-table").trigger('reloadGrid');
        $("#draft-grid-table").trigger('reloadGrid');
    });

    $('#save-as-draft').on('click', function (e) {
        e.preventDefault();
        Inbox.save_as_draft();
    });

    $('#send-mail').on('click', function (e) {
        e.preventDefault();
        Inbox.send_mail();
    });
});
