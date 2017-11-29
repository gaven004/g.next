function removeURLParameter(url, parameter) {
    // prefer to use l.search if you have a location/link object
    var urlparts = url.split('?');
    if (urlparts.length >= 2) {

        var prefix = encodeURIComponent(parameter) + '=';
        var pars = urlparts[1].split(/[&;]/g);

        // reverse iteration as may be destructive
        for (var i = pars.length; i-- > 0;) {
            // idiom for string.startsWith
            if (pars[i].lastIndexOf(prefix, 0) !== -1) {
                pars.splice(i, 1);
            }
        }

        url = urlparts[0] + (pars.length > 0 ? '?' + pars.join('&') : "");
        return url;
    } else {
        return url;
    }
}

function addURLParameter(url, parameter, value) {
    url = removeURLParameter(url, parameter);
    if (url.indexOf('?') < 0) {
        url = url + '?';
    } else {
        url = url + '&';
    }
    return url + encodeURIComponent(parameter) + '=' + encodeURIComponent(value);
}