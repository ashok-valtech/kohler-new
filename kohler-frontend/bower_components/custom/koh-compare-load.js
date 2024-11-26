"use strict";
$(window).on("load", function() {	
	function show_popup(){
	  	var contextBase = $('#contextBase').val();
	  	var addToCompareLink = $('#addToCompareLink').val();
	  	var productDetailslink = $('#productDetailslink').val();
	  	var cookie = getCookie(contextBase);
	    if (cookie !== null && cookie !== "" ){
	    	var values = cookie.split("."),
	        category = values[0];
	     	$.ajax({
	            url: addToCompareLink,
	            cache: !1,
	            data: {'itemNumber':values[1]} ,
	            success: function (data) { 
	            	if(data.success == true){
	            		for(var i=0; i < data.items.length; i++){
	            			//added for remove compare
	            			$($(".add_"+data.items[i].itemNumber)[0]).closest(".koh-product-tile").addClass("product-added");
	            			$($(".add_"+data.items[i].itemNumber)).closest(".koh-product-tools").addClass("product-added");
	            			//$(".add_"+data.items[i].itemNumber[0]).closest(".koh-product-tools").addClass("product-added");
	            			var htmlData = "<div class=\"koh-compare-item\" id=\""+data.items[i].itemNumber+"\"><a href=\"" + productDetailslink + "/" + data.items[i].itemNumber + "\" onclick=\"getNextProduct(null)\" ><img class='koh-compare-image' src='//kohler.scene7.com/is/image/PAWEB/Category_Template?$GridResults$&$gradient_src=PAWEB%2Forganic%2Dgradient&$shadow_src=PAWEB%2FBlank&$Badge1_src=PAWEB%2FBlank&$Badge4_src=PAWEB%2FBlank&$Badge3_src=PAWEB%2FBlank&$Badge2_src=PAWEB%2FBlank&$product_src=is{PAWEB%2F"+data.items[i].src+"}' alt='' height='100' width='68'>";
	            			htmlData +="</br><span class=\"koh-compare-name\"><span class=\"koh-product-family\">"+data.items[i].family+"</span>"+data.items[i].description+"</span>";
	            			htmlData +="</br><span class=\"koh-compare-sku\">"+data.items[i].defaultSku+"</span></a>";
	            			htmlData +="</br><button class=\"koh-compare-remove\" href=\"\" data-product-itemno=\""+data.items[i].itemNumber+"\">Remove Product</button></div>" ;
	            			if(i==0){
		                		var compareLink = $('#compareLink').val();
		                		var htmlMainData = "<input id=\"compare-cookie-info\" type=\"hidden\" data-cookie-name=\""+contextBase+"\" data-cookie-duration=\"5\" data-cookie-max-items-number=\"3\" data-cookie-max-items-message=\"Only three comparison products are allowed. Please remove one in order to allow adding this new compare to the list.\" data-cookie-diff-category-message=\"You can only compare products from the same category. Adding this product will clear your current comparison.\" data-cookie-category=\""+category+"\">";
		                		htmlMainData += "<div class=\"c-koh-compare-panel\"><div class=\"koh-compare-header\"><span class=\"koh-compare-title\">Compare Products</span>";
		                		htmlMainData += "<button type=\"button\" class=\"koh-compare-toggle open\" onclick=\"$('.koh-compare-toggle').toggleClass('open');$('.koh-compare-items').toggleClass('open');\">Show/Hide</button>";
		                		htmlMainData += "<a href=\""+compareLink+"\" class=\"koh-compare-link btn--primary full-width-sm marg-b-10-sm active\">Compare</a>";
		                		htmlMainData += "<button type=\"button\" class=\"koh-compare-clear\">Clear Results</button></div><div class=\"koh-compare-items\"></div></div>"; 
		                		$("#comparePanel").html(htmlMainData);
		                		htmlData +="<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-demo\">2</span></div><div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank\">3</span></div>";
		                		$(".koh-compare-items").html(htmlData);
	            			}else if(i==1){
	            				$(".koh-compare-items").children().last().remove();
	                			$(".koh-compare-items").children().last().remove();
	            				htmlData +="<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-demo\">3</span></div>";
	            				$(".koh-compare-items").append(htmlData);
	            			}else if(i==2){
	            				$(".koh-compare-items").children().last().remove();
	                			$(".koh-compare-items").append(htmlData);
	            			}
	            		}
	            		 $(".koh-compare-items").addClass("open")
	            	}
	            	$(".koh-compare-toggle").addClass("open"), $(".koh-compare-items").addClass("open");
	            }
	        }).done(function () {
	            //$(".koh-compare-toggle").addClass("open"), $(".koh-compare-items").addClass("open");
	        }).fail(function () {
	        	alert("failed");
	        }) 
	    }
	   };
	   window.setTimeout( show_popup, 1400 );
 });
function createCookie(name, value, minutes) {
    var expires = "",
        cookie = "";
    if (minutes) {
        var date = new Date;
        date.setTime(date.getTime() + 60 * minutes * 1e3), expires = "expires=" + date.toUTCString()
    }
    cookie = name + "=" + value + "; " + expires + "; path=/", document.cookie = cookie
}

function getCookie(name) {
    for (var nameEQ = name + "=", ca = document.cookie.split(";"), i = ca.length - 1; i >= 0; --i) {
        for (var c = ca[i];
            " " === c.charAt(0);) c = c.substring(1, c.length);
        if (0 === c.indexOf(nameEQ)) return c.substring(nameEQ.length, c.length)
    }
    return null
}

function deleteCookie(name) {
    document.cookie = name + "=; expires=Thu, 01-Jan-70 00:00:01 GMT;"
}