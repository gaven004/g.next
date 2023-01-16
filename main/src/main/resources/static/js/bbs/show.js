jQuery(function ($) {
    async function loadArticle(data) {
        let s = localStorage.getItem(readItemKey);
        let readItem = s ? JSON.parse(s) : {};
        readItem[data.id] = 1;
        localStorage.setItem(readItemKey, JSON.stringify(readItem));

        if (data && data.id) {
            // 查询附件信息
            const promise = COMM.fetch("sys/attachment?module=BBS_ATTACHMENT&srcRecode=" + data.id);
            await promise.then(response => {
                if (response.result === 'SUCCESS' && response.body) {
                    $.extend(data, {attachments: response.body});
                }
            });
        }

        let parent = $("#article-modal");
        parent.empty();
        $("#template_article").template(data).appendTo(parent);
        parent.modal('show');
    }

    fetch('bbs/column')
        .then(response => response.json())
        .then(data => data.body)
        .then(body => {
            let columns = body.filter(item => "0" === item.parentId);
            return columns.map(main => {
                let children = body.filter(item => main.id === item.parentId);
                return Object.assign({children: children}, main);
            });
        })
        .then(columns => {
            let mainColumnTab = $("#mainColumnTab");
            $("#template_menu").template(columns).appendTo(mainColumnTab);

            let subColumnTab = $("#subColumnTab");
            $("#template_submenu").template(columns).appendTo(subColumnTab);

            // 展示第1个栏目的文章列表
            if (columns[0].id) {
                $("#columnId").val(columns[0].id);
                $("#grid-table").setGridParam({
                    datatype: "json",
                }).trigger('reloadGrid');
            }

            $("a.bbs-column-menu").click(function () {
                $("#form")[0].reset();
                let column = $(this).attr("column-id");
                $("#columnId").val(column);
                $("#grid-table").trigger("reloadGrid");
            });
        });

    let breadcrumb = "<li class='active'>通知公告</li>";
    $("#breadcrumb > .active").remove();
    $("#breadcrumb").append($(breadcrumb));

    let isnew_formatter = function (cellvalue, options, rowObject) {
        if (rowObject.id) {
            let s = localStorage.getItem(readItemKey);
            if (s) {
                let readItem = JSON.parse(s);
                if (readItem && readItem[rowObject.id]) {
                    return "";
                }
            }
        }
        return "<i class=\"message-star ace-icon fa fa-star orange2\"></i>";
    };

    $("#grid-table").grid({
        url: "bbs/article",
        datatype: "local",
        colModel: [{
            name: 'id',
            hidden: true,
        }, {
            label: ' ',
            name: 'is_new',
            width: '40px',
            align : 'center',
            fixed: true,
            formatter: isnew_formatter,
        }, {
            label: '标题',
            name: 'title',
            width: '70%',
        }, {
            label: '',
            name: 'content',
            hidden: true
        }, {
            label: '发布人',
            name: 'author',
            width: '15%',
        }, {
            label: '发布时间',
            name: 'mtime',
            width: '15%',
        }],
        onSelectRow: function (rowid) {
            if (rowid) {
                let data = $("#grid-table").jqGrid('getRowData', rowid);  // 当前行数据
                if (data && data.id) {
                    loadArticle(data);
                }
            }
        },
        ondblClickRow: null,
    });

    $("#btnSearch").click(function () {
        $("#grid-table").trigger("reloadGrid");
    });

    $('#article-modal').on('hidden.bs.modal', function (e) {
        $("#grid-table").trigger("reloadGrid");
    });
});
