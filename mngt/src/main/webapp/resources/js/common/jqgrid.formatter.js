/**
 * jqGrid custom format/unformat functions After loading the jqGrid Java Script
 * files you can define in script tag the following
 */

var validOrInvalidOptionsJson;
function getValidOrInvalidOptionsJson() {
    if (!validOrInvalidOptionsJson) {
        $.ajax({
            dataType: "json",
            url: "rest/enums/status",
            async: false,
            success: function(data) {
                validOrInvalidOptionsJson = data;
            }
        });
    }
    return validOrInvalidOptionsJson;
}

jQuery.extend($.fn.fmatter, {
    fmValidOrInvalid : function(cellvalue, options, rowdata) {
        return JsonUtil.getValue(getValidOrInvalidOptionsJson(), cellvalue);
    }
});
jQuery.extend($.fn.fmatter.fmValidOrInvalid, {
    unformat : function(cellvalue, options) {
        return JsonUtil.getKey(getValidOrInvalidOptionsJson(), cellvalue);
    }
});
