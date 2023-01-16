jQuery(function ($) {
    var compiled = {};
    $.fn.template = function (data) {
        var template = $.trim($(this).first().html());
        if (compiled[template] == undefined) {
            compiled[template] = Handlebars.compile(template);
        }
        return $(compiled[template](data));
    };
    Handlebars.registerHelper("substr", function (str, start, end) {
        if (!str) {
            return str;
        }
        return str.substring(start, end);
    });
    Handlebars.registerHelper("eq", function (v1, v2, options) {
        if (v1 == v2)
            return options.fn(this);
        else
            return options.inverse(this);
    });
    Handlebars.registerHelper("ne", function (v1, v2, options) {
        if (v1 != v2)
            return options.fn(this);
        else
            return options.inverse(this);
    });
    Handlebars.registerHelper("gt", function (v1, v2, options) {
        if (v1 > v2)
            return options.fn(this);
        else
            return options.inverse(this);
    });
    Handlebars.registerHelper("ge", function (v1, v2, options) {
        if (v1 >= v2)
            return options.fn(this);
        else
            return options.inverse(this);
    });
    Handlebars.registerHelper("lt", function (v1, v2, options) {
        if (v1 < v2)
            return options.fn(this);
        else
            return options.inverse(this);
    });
    Handlebars.registerHelper("le", function (v1, v2, options) {
        if (v1 <= v2)
            return options.fn(this);
        else
            return options.inverse(this);
    });
});
