$(function() {
	$('.userdatarow').click(function() {
		var id = $(this).attr('id');
		location.href = '/framework/user/edit/' + id;
	});

	$('#doSearch,.pager').click(doSearch);
});

/**
 * 搜索功能,带分页
 */
function doSearch(obj) {
	var searchConditions = '';
	// 查询条件
	$('#search input:text').each(function() {
		var name = $(this).attr('name');
		var value = $(this).val();
		// name=user
		var s = '&' + name + '=' + value;
		searchConditions += s;
	});

	// 点搜索按钮,默认当前页=1
	var curtPage = 1;
	// 如果是点击的翻页按钮,获取翻页页码
	if ($(this).hasClass('pager')) {
		curtPage = $(this).attr('page');
	}
	// alert(JSON.stringify(searchConditions));
	var url = ctxpath + '/user/list/page/' + curtPage + '?'
			+ searchConditions.substring(1);
	location.href = url;
}