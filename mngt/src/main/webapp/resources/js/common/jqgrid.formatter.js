/**
 * jqGrid custom format/unformat functions After loading the jqGrid Java Script
 * files you can define in script tag the following
 */

var validOrInvalidOptionsJson;
$.getJSON("rest/enums/status", function(data) {
    validOrInvalidOptionsJson = data;
});

jQuery.extend($.fn.fmatter, {
    fmValidOrInvalid : function(cellvalue, options, rowdata) {
        return JsonUtil.getValue(validOrInvalidOptionsJson, cellvalue);
    }
});
jQuery.extend($.fn.fmatter.fmValidOrInvalid, {
    unformat : function(cellvalue, options) {
        return JsonUtil.getKey(validOrInvalidOptionsJson, cellvalue);
    }
});
