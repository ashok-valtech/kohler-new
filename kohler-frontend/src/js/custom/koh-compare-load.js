/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

"use strict";
$(window).on("load", function() {

    var changeUrlDam = function(domainUrl, lastSlashIndex, imageUrl, damName) {
        var defImgDomainWithoutDam = domainUrl.slice(0, lastSlashIndex);
        var newDomainUrl = defImgDomainWithoutDam + "/"+ damName;    
        imageUrl = imageUrl.replace("{domain}", newDomainUrl);
        imageUrl = imageUrl.replaceAll("{dam}", damName);
        imageUrl = imageUrl.replace("product_src=is{PAWEB", "product_src=is{kohlerchina");
        return imageUrl;
    };

    function show_popup() {
        var contextBase = $("#compare-cookie-info").data("cookie-name");
        var addToCompareLink = $('#addToCompareLink').val();
        var productDetailslink = $('#productDetailslink').val();
        var gridImageProductUrl = $('#gridImageProductUrl').val();
        var gridImageNewProductUrl = $('#gridImageNewProductUrl').val();
        var common_galleryNewProductUrl = $('#common_galleryNewProductUrl').val();
        var common_gridImageUrl = $('#common_gridImageUrl').val();
        var cookie = getCookie(contextBase);
        var comparePanelProducts = $('#comparePanelProducts').val();
        var compareButtonLable = $('#compareButtonLable').val();
        var compareClearResults = $('#compareClearResult').val();
        var defaultCategoryKey = $('#defaultCategoryKey').val();
        if (cookie !== null && cookie !== "") {
            var values = cookie.split("."),
                category = values[0];
            $.ajax({
                url: addToCompareLink,
                cache: !1,
                data: { 'itemNumber': values[1] },
                success: function(data) {
                    if (data.success == true) {
                        for (var i = 0; i < data.items.length; i++) {
                        	var itemno = "";
                        	var itemNumbers = "";
                        	if(isNaN(data.items[i].itemNumber)){
                        		 itemNumbers = data.items[i].itemNumber.split ("!_");
                        	}
                            var defaultCategory = "";
                            var multiAncestorefaultCategoryData = "";
                            if (itemNumbers.length > 1) {
                            	itemno = itemNumbers[0];
                            	defaultCategory = "&defaultCategory=" + itemNumbers[1];
                            	multiAncestorefaultCategoryData = " data-product-itemno-multianscestors=\""+ itemno + "!_"+ itemNumbers[1] + "\"";
                            }else {
                            	itemno = data.items[i].itemNumber;
                            	multiAncestorefaultCategoryData = " data-product-itemno-multianscestors=\""+ itemno + "\"";
                            }
                        	//added for remove compare
                        	if (category === defaultCategoryKey || defaultCategoryKey === '') {
	                            $($(".add_" + itemno)[0]).closest(".koh-product-tile").addClass("product-added");
	                            $($(".add_" + itemno)).closest(".koh-product-tools").addClass("product-added");
                        	}
                            var productImageUrl;
                            if (data.items[i].src.startsWith('http')) {
								var lastSlashIndex = data.items[i].src.lastIndexOf('/');
								const defImgFromUrl = data.items[i].src.slice(lastSlashIndex + 1);
								const domainStartIndex = data.items[i].src.indexOf("//") + 2;
								const defImgDomainFromUrl = data.items[i].src.slice(domainStartIndex, lastSlashIndex)
								lastSlashIndex = defImgDomainFromUrl.lastIndexOf('/');
								const defImgDamNameFromUrl = defImgDomainFromUrl.slice(lastSlashIndex + 1);
								if(data.items[i].isNew)
	                        	{
	                            	productImageUrl = common_galleryNewProductUrl.replace("{0}",defImgFromUrl);
	                        	}else{
	                        		productImageUrl = common_gridImageUrl.replace("{0}",defImgFromUrl);
	                        	}
                                if(defImgDamNameFromUrl === "kohlerchina") {
                                    productImageUrl = changeUrlDam(defImgDomainFromUrl,lastSlashIndex,productImageUrl,"PAWEB");
                                }else {
                                    productImageUrl = productImageUrl.replace("{domain}", defImgDomainFromUrl);
                                    productImageUrl = productImageUrl.replaceAll("{dam}", defImgDamNameFromUrl);
                                }
							} else {
	                            if(data.items[i].isNew)
	                        	{
	                            	productImageUrl = gridImageNewProductUrl.replace("{0}",data.items[i].src);
	                        	}else{
	                        		productImageUrl = gridImageProductUrl.replace("{0}",data.items[i].src);
	                        	}
	                        }
                            var currencyCode = $('#currencyCode').val();
                            var priceVat = $('#price-vat').val();
                            var multipleValue = $('#multiply-value').val();
                            var productPrice = data.items[i].productPrice;
                            var roundPrice = Math.round(productPrice * priceVat);
                            productPrice = (roundPrice * multipleValue).toLocaleString('en-US');
                            var comparePrice = currencyCode && data.items[i].productPrice && data.items[i].productPrice.length > 0 ? "<span class=\"koh-compare-price\">"+currencyCode+" "+ productPrice + "</span></a>" : "";
                            //$(".add_"+data.items[i].itemNumber[0]).closest(".koh-product-tools").addClass("product-added");
                            var htmlData = "<div class=\"koh-compare-item\" id=\"" + itemno + "\"><a href=\"" + productDetailslink + "/" + itemno +  "/?skuid="+ data.items[i].defaultSku + defaultCategory + "\" ><img class='koh-compare-image' src='"+productImageUrl+"' alt='' height='100' width='68'>";
                            htmlData += "</br><span class=\"koh-compare-name\"><span class=\"koh-product-family\">" + data.items[i].family + "</span>" + data.items[i].description + "</span>";
                            htmlData += "</br><span class=\"koh-compare-sku\">K-" + data.items[i].defaultSku + "</span></a>";
                            htmlData += comparePrice;
                            htmlData += "</br><button class=\"koh-compare-remove\" href=\"\" data-product-itemno=\"" + itemno + "\"" + multiAncestorefaultCategoryData + ">Remove Product</button></div>";
                            if (i == 0) {
                                var compareLink = $('#compareLink').val();
                                var htmlMainData = "<input id=\"compare-cookie-info\" type=\"hidden\" data-cookie-name=\"" + contextBase + "\" data-cookie-duration=\"5\" data-cookie-max-items-number=\"3\" data-cookie-max-items-message=\"Only three comparison products are allowed. Please remove one in order to allow adding this new compare to the list.\" data-cookie-diff-category-message=\"You can only compare products from the same category. Adding this product will clear your current comparison.\" data-cookie-category=\"" + category + "\">";
                                htmlMainData += "<div class=\"c-koh-compare-panel\"><div class=\"koh-compare-header\"><span class=\"koh-compare-title\">"+ comparePanelProducts +"</span>";
                                htmlMainData += "<button type=\"button\" class=\"koh-compare-toggle\" onclick=\"$('.koh-compare-toggle').toggleClass('open');$('.koh-compare-items').toggleClass('open');\">Show/Hide</button>";
                                htmlMainData += "<a href=\"\" data-listing-page-url = \"" + window.location.href + "\" data-compare-link = \"" + compareLink + "\" class=\"koh-compare-link btn--primary full-width-sm marg-b-10-sm active\">"+compareButtonLable+"</a>";
                                htmlMainData += "<button type=\"button\" class=\"koh-compare-clear\">"+compareClearResults+"</button></div><div class=\"koh-compare-items\"></div></div>";
                                $("#comparePanel").html(htmlMainData);
                                htmlData += "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-demo\">2</span></div><div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank\">3</span></div>";
                                $(".koh-compare-items").html(htmlData);
                            } else if (i == 1) {
                                $(".koh-compare-items").children().last().remove();
                                $(".koh-compare-items").children().last().remove();
                                htmlData += "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-demo\">3</span></div>";
                                $(".koh-compare-items").append(htmlData);
                            } else if (i == 2) {
                                $(".koh-compare-items").children().last().remove();
                                $(".koh-compare-items").append(htmlData);
                            }
                        }
                        /*$(".koh-compare-items").addClass("open")*/
                    }
                    /*$(".koh-compare-toggle").addClass("open"), $(".koh-compare-items").addClass("open")*/;
                }
            }).done(function() {
                //$(".koh-compare-toggle").addClass("open"), $(".koh-compare-items").addClass("open");
            }).fail(function() {
                // alert("failed");
            })
        }
    };
    window.setTimeout(show_popup, 1400);
    if (document.documentMode === 10) {
        sessionStorage.setItem("Page2Visited", "True");
    }
});

/*function createCookie(name, value, minutes) {
    var expires = "",
        cookie = "";
    if (minutes) {
        var date = new Date;
        date.setTime(date.getTime() + 60 * minutes * 1e3), expires = "expires=" + date.toUTCString()
    }
    cookie = name + "=" + value + "; " + expires + "; path=/", document.cookie = cookie
}*/

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
