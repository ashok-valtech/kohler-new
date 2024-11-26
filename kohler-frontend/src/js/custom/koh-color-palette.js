/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

function populateValues($target) {
	var title = $($target).data('title');
	var description = $($target).data('description');
	var largeImage = $($target).data('largeimage')
	var colorCodesearchURL = $($target).data('colorcodesearchurl')
	var colorUUID = $($target).data('coloruuid')
	
	$(".koh-modal-title").html(title);
	$("#shareTitle").val(title);
	$("#modalDescription").html(description);
	$("#shareDescription").val(description);
	$("#modalImage").attr("src", largeImage);
	$("#modalImage").attr("alt",title);
	$("#shareImage").val(largeImage);
	if(colorCodesearchURL === ''){
		$("#colorCodeSearchId").css('display','none');
	}else{
		$("#colorCodeSearchId").attr("href", colorCodesearchURL);
		$("#colorCodeSearchId").css('display','block');
	}
	$("#colorUUID").html(colorUUID);
	$("#colorUUID").val(colorUUID);
	var urlvalue = {"colorUUID":colorUUID};	
	var param = '';
	if(window.location.href.indexOf('?') > 0 ){
		param  = window.location.href.substring(0,window.location.href.indexOf('?'));
		param  = param + '?' + $.map(urlvalue, function(v, k) {
		    return encodeURIComponent(k) + '=' + encodeURIComponent(v);
		});	
	}else{
	 param = window.location.href + '?' + $.map(urlvalue, function(v, k) {
		    return encodeURIComponent(k) + '=' + encodeURIComponent(v);
		});	
	 
	}
	$('.koh-share-facebook').data('share-url',param);
	$('.koh-share-twitter').data('share-url',param);
}

$( document ).ready(function() {
	$('#koh-modal-color-palette-detail').on('hide.bs.modal', function () {
		$("#modalImage").attr("src", "");
		$("#modalImage").attr("alt","");
		
		var curPageURL = window.location.href;
		if(curPageURL.indexOf('colorUUID')){
			try{
				curPageURL = curPageURL.substring(0,curPageURL.indexOf('colorUUID') - 1);
				var title = $(document).find("title").text();
				window.history.pushState({"urlPath": curPageURL}, title, curPageURL);
			}catch(err){
			}
		}
	});
});