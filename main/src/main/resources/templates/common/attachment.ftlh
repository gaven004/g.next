<script id="attachmentTemplate" type="text/x-handlebars-template">
    <div id="divAttachmentRow{{flag}}{{idx}}" class="form-group">
        <label class="col-sm-2 control-label no-padding-right">{{#unless idx}}附件：{{/unless}}</label>
        <div class="col-sm-9">
            <input type="hidden" name="files{{flag}}[{{idx}}].id" value="{{id}}">
            <input type="file" id="file{{flag}}{{idx}}" name="files{{flag}}[{{idx}}].file" style="display:none"
                   onchange="fileChange('{{flag}}', {{idx}})">
            <input type="text" id="inputFile{{flag}}{{idx}}" value="{{path}}" class="col-sm-6">
            {{#unless readonly}}
            <button class="btn btn-sm btn-success" type="button" onclick="browseClick('{{flag}}', {{idx}})">
                <i class="ace-icon glyphicon glyphicon-upload"></i>
                上传
            </button>
            {{/unless}}
            <button class="btn btn-sm btn-primary" type="button" {{#unless id}}disabled="disabled"{{/unless}}
                    onclick="download('{{id}}')">
                <i class="ace-icon glyphicon glyphicon-zoom-in"></i>
                阅览
            </button>
            {{#unless readonly}}
            <button class="btn spinbox-down btn-sm btn-danger" type="button"
                    onclick="getAttachment('{{flag}}').remove({{idx}})">
                <i class="icon-only ace-icon ace-icon fa fa-minus bigger-110"></i>
            </button>
            {{#unless idx}}
            <button class="btn spinbox-up btn-sm btn-success" type="button"
                    onclick="getAttachment('{{flag}}').addRow()">
                <i class="icon-only ace-icon ace-icon fa fa-plus bigger-110"></i>
            </button>
            {{/unless}}
            {{/unless}}
        </div>
    </div>
</script>

<script src="js/common/attachment.js"></script>
