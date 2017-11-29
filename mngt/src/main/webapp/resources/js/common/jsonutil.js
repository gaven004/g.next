(function(global, factory) {
    "use strict";
    if (typeof module === "object" && typeof module.exports === "object") {
        module.exports = global.document ? factory(global, true) : function(w) {
            if (!w.document) {
                throw new Error("Required a window with a document");
            }
            return factory(w);
        };
    } else {
        factory(global);
    }
})(typeof window !== "undefined" ? window : this, function(window, noGlobal) {
    "use strict";
    var JsonUtil = {
        getValue : function(jsonObject, key) {
            if (key) {
                for ( var jKey in jsonObject) {
                    if (key == jKey) {
                        return jsonObject[jKey];
                    }
                }
            }
            return key;
        },
        getKey : function(jsonObject, value) {
            if (value) {
                for ( var jKey in jsonObject) {
                    if (value == jsonObject[jKey]) {
                        return jKey;
                    }
                }
            }
            return value;
        }
    }
    if ( !noGlobal ) {
        window.JsonUtil = JsonUtil;
    }
    return JsonUtil;
});