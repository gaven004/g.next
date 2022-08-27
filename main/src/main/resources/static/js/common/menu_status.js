const MENU = {
	save : function(menu) {
		const href = $(menu).attr("href");
		if (href == "javascript:void(0)") {
			return;
		}
		// 把菜单链接保存为cookie，以便加载页面时激活
		$.cookie('s6_menu_href', href, {
			path : '/'
		});
	},
	active : function() {
		const href = $.cookie('s6_menu_href');
		if (href) {
			const menuItem = $('a[href="' + href + '"]').parent("li");
			menuItem.addClass("active");
			let breadcrumb = "<li class='active'>" + menuItem.text() + "</li>";
			menuItem.parentsUntil("div").each(
					function() {
						if ($(this).is("li")) {
							$(this).addClass("active open");
							breadcrumb = "<li class='active'>"
									+ $(this).children("a").text() + "</li>"
									+ breadcrumb;
						}
					});
			$("#breadcrumb").append($(breadcrumb));
		}
	}
};
MENU.active();
