/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */
 
// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};
axKOH.main.componentManager.comparePanel = function () {
    var componentClass = "c-koh-compare-panel";
    var initComponents = function () {

        /*var $components = $("."+componentClass);

        $components.each( function(componentIndex) {
        	var $component = $(this);
        	var $panelToggle 	= $component.find(".koh-compare-toggle");
        	var $panelItems 	= $component.find(".koh-compare-items");

        	$panelToggle.click( function() {
        		$panelItems.toggleClass('open');
        		$(this).toggleClass('open');
        	});

        });*/
    };

    var initCompareModal = function ($components) {
        $components
            .each(function (componentIndex) {
                var $component = $(this),
                    remodalID = "CompareModal" + componentIndex,
                    $button = $component.find(".koh-compare-panel-button"),
                    $panel = $component.find(".koh-compare-panel-modal");
                initAsModal($button, $panel, remodalID),
                    initColorOverlays();
                var $compareProducts = $(".c-koh-compare-products").find(".koh-compare-product");
                $compareProducts.each(function (componentIndex) {
                    var $product = $(this),
                        $button = $product.find(".koh-compare-product-required-button"),
                        $panel = $product.find(".koh-compare-required-products-panel"),
                        remodalID = "RequiredCompareItems" + componentIndex;
                    initAsModal($button, $panel, remodalID)
                })
            })
    };

    var initAsModal = function ($button, $panel, remodalID) {
        $panel.data("remodal-id", remodalID),
            $panel.prepend('<button data-remodal-action="close" class="remodal-close"></button>'),
            $panel.attr("data-koh-component", componentClass),
            $panel.addClass(componentClass + "-modal");
        var $remodalPanel = $panel.remodal({
            hashTracking: true,
            closeOnCancel: true,
            closeOnEscape: true,
            closeOnOutsideClick: true,
            modifier: ""
        });
        $button.data("remodal-target", remodalID),
            $button.click({
                target: $remodalPanel
            }, function (e) {
                e.preventDefault(),
                    e.stopPropagation();
                var $target = e.data.target;
                $target.open()
            })
    };

    var initAsModalAndOpen = function ($button, $panel, remodalID) {
        $panel.data("remodal-id", remodalID),
            $panel.prepend('<button data-remodal-action="close" class="remodal-close"></button>'),
            $panel.attr("data-koh-component", componentClass),
            $panel.addClass(componentClass + "-modal");
        var $remodalPanel = $panel
            .remodal({
                hashTracking: true,
                closeOnCancel: true,
                closeOnEscape: true,
                closeOnOutsideClick: true,
                modifier: ""
            })
            .open();
        $button.data("remodal-target", remodalID),
            $button.click({
                target: $remodalPanel
            }, function (e) {
                e.preventDefault(),
                    e.stopPropagation();
                var $target = e.data.target;
                $target.open()
            })
    };

    var initColorOverlays = function () {
        var $colorSwatches = $(".compareColorSwatch");
        $colorSwatches.each(function (swatchIndex) {
            var $colorSwatch = $(this),
                $colorLabel = $colorSwatch.attr("alt");
            $colorSwatch.qtip({
                id: "swatch" + swatchIndex,
                content: {
                    text: $colorLabel
                },
                position: {
                    my: "top left",
                    at: "bottom left",
                    adjust: {
                        y: 6
                    }
                },
                style: {
                    classes: "koh-qtip-tooltip koh-product-detail-tooltip qtip-bootstrap",
                    tip: false
                }
            })
        })
    };

    var initComparePanel = function () {
        function removeModalProduct(event, product) {
            event.preventDefault();
            var cookieContent,
                uri = product.attr("href"),
                itemno = product.data("product-itemno"),
                cookieName = $("#compare-cookie-info").data("cookie-name"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration"),
                cookie = getCookie(cookieName);
            if (null === cookie || "" === cookie)
                return void $(".c-koh-compare-panel").remove();
            var products,
                values = cookie.split("."),
                category = values[0];
            values.length > 1 && (products = values[1].split("|"));
            var index = products.indexOf(itemno.toString());
            if (products.splice(index, 1), cookieContent = category + "." + products.join("|"), deleteCookie(cookieName), !(products.length > 0))
                return $(".c-koh-compare-panel").remove(),
                    deleteCookie(cookieName),
                    void createCookie(cookieName, "", durationMinutes);
            createCookie(cookieName, cookieContent, durationMinutes),
                product
                .closest(".koh-compare-product")
                .remove(),
                $(".c-koh-compare-products")
                .find(".koh-compare-remove")
                .remove(),
                removeProductText(product);
            $.ajax({
                    url: uri,
                    cache: false,
                    success: function (data) {
                        $("#comparePanel").html(data),
                            $(".koh-compare-items").addClass("open")
                    }
                })
                .done(function () {
                    reloadAndOpenModal()
                })
                .fail(function () {})
        }

        /*function createCookie(name, value, minutes) {
            var expires = "",
                cookie = "";
            if (minutes) {
                var date = new Date;
                date.setTime(date.getTime() + 60 * minutes * 1e3),
                    expires = "expires=" + date.toUTCString()
            }
            cookie = name + "=" + value + "; " + expires + "; path=/",
                document.cookie = cookie
        }*/

        function getCookie(name) {
            for (var nameEQ = name + "=", ca = document.cookie.split(";"), i = ca.length - 1; i >= 0; --i) {
                for (var c = ca[i];
                    " " === c.charAt(0);)
                    c = c.substring(1, c.length);
                if (0 === c.indexOf(nameEQ))
                    return c.substring(nameEQ.length, c.length)
            }
            return null
        }

        function deleteCookie(name) {
            document.cookie = name + "=; expires=Thu, 01-Jan-70 00:00:01 GMT;"
        }

        function addProductText($panel) {
            $(".add_" + $panel.data("product-itemno")).closest(".koh-product-tools").addClass("product-added");
            $(".add_" + $panel.data("product-itemno")).closest(".koh-product-tile").addClass("product-added");
            $panel.hasClass("koh-compare-button-quickview") && ($panel.closest(".koh-product-quick-view-panel").addClass("product-added"), $("div[data-modal-link='" + $panel.closest(".koh-product-quick-view-panel").attr("id") + "']").addClass("product-added")),
                $panel.hasClass("koh-compare-button-tile") && ($panel.closest(".koh-product-tile").addClass("product-added"), $("#" + $panel.closest(".koh-product-tile").attr("data-modal-link")).addClass("product-added"), $(".koh-product-details").find(".koh-product-tools").addClass("product-added"))
        }

        function removeProductText($panel) {
            $(".remove_" + $panel.data("product-itemno")).closest(".koh-product-tools").removeClass("product-added");
            $(".remove_" + $panel.data("product-itemno")).closest(".koh-product-tile").removeClass("product-added");
            $panel.hasClass("koh-compare-button-quickview") && ($panel.closest(".koh-product-quick-view-panel").removeClass("product-added"), $("div[data-modal-link='" + $panel.closest(".koh-product-quick-view-panel").attr("id") + "']").removeClass("product-added")),
                $panel.hasClass("koh-compare-button-tile") && ($panel.closest(".koh-product-tile").removeClass("product-added"), $("#" + $panel.closest(".koh-product-tile").attr("data-modal-link")).removeClass("product-added")),
                $panel.hasClass("koh-compare-remove-modal") && ($("div[data-modal-link='QuickView" + $panel.attr("data-product-itemno") + "']").removeClass("product-added"), $("#QuickView" + $panel.attr("data-product-itemno")).removeClass("product-added"), $(".koh-product-details").find(".koh-product-tools").removeClass("product-added"))
        }

        setTimeout(function () {
            $(".c-koh-product-faceted-search-modal .koh-compare-add")
                .each(function () {
                    var itemno = ($(this).attr("href"), $(this).data("product-itemno")),
                        cookieName = $("#compare-cookie-info").data("cookie-name"),
                        cookie = ($("#compare-cookie-info").data("cookie-duration"), $("#compare-cookie-info").data("cookie-max-items-number"), $("#compare-cookie-info").data("cookie-max-items-message"), $("#compare-cookie-info").data("cookie-diff-category-message"), $("#category-info").data("cookie-category-product"), getCookie(cookieName));
                    if (null !== cookie) {
                        var values = cookie.split(".");
                        values[0];
                        if (values[1]) {
                            var products = values[1].split("|");
                            /*for (i in products) 
                                products[i] == itemno && $(this).addClass("added")*/
                        }
                    }
                })
        }, 1e3);

        var reloadModal = function () {
            var $components = $("." + componentClass);
            $components.each(function (componentIndex) {
                var $component = $(this),
                    remodalID = "CompareModal" + componentIndex,
                    $button = $component.find(".koh-compare-panel-button"),
                    $panel = $component.find(".koh-compare-panel-modal");
                $(".koh-compare-panel-modal").remove(),
                    initAsModal($button, $panel, remodalID),
                    $(".c-koh-compare-products").on("click", ".koh-compare-remove-modal", function (event) {
                        removeModalProduct(event, $(this))
                    }),
                    2 == $(".koh-compare-product").size() ?
                    $(".koh-compare-product").width("50%") :
                    $(".koh-compare-product").width("33.3333%"),
                    initColorOverlays();
                var $compareProducts = $(".c-koh-compare-products").find(".koh-compare-product");
                $compareProducts.each(function (componentIndex) {
                    var $product = $(this),
                        $button = $product.find(".koh-compare-product-required-button"),
                        $panel = $product.find(".koh-compare-required-products-panel"),
                        remodalID = "RequiredCompareItems" + componentIndex;
                    initAsModal($button, $panel, remodalID)
                })
            })
        };

        var reloadAndOpenModal = function () {
            var $components = $("." + componentClass);
            $components.each(function (componentIndex) {
                var $component = $(this),
                    remodalID = "CompareModal" + componentIndex,
                    $button = $component.find(".koh-compare-panel-button"),
                    $panel = $component.find(".koh-compare-panel-modal");
                $(".koh-compare-panel-modal").remove(),
                    initAsModal($button, $panel, remodalID),
                    $(".c-koh-compare-products").on("click", ".koh-compare-remove-modal", function (event) {
                        removeModalProduct(event, $(this))
                    }),
                    2 == $(".koh-compare-product").size() ?
                    $(".koh-compare-product").width("50%") :
                    $(".koh-compare-product").width("33.3333%");
                var $compareProducts = $(".c-koh-compare-products").find(".koh-compare-product");
                $compareProducts.each(function (componentIndex) {
                    var $product = $(this),
                        $button = $product.find(".koh-compare-product-required-button"),
                        $panel = $product.find(".koh-compare-required-products-panel"),
                        remodalID = "RequiredCompareItems" + componentIndex;
                    initAsModalAndOpen($button, $panel, remodalID)
                })
            })
        };

        var changeUrlDam = function(domainUrl, lastSlashIndex, imageUrl, damName) {
            var defImgDomainWithoutDam = domainUrl.slice(0, lastSlashIndex);
            var newDomainUrl = defImgDomainWithoutDam + "/"+ damName;    
            imageUrl = imageUrl.replace("{domain}", newDomainUrl);
            imageUrl = imageUrl.replaceAll("{dam}", damName);
            imageUrl = imageUrl.replace("product_src=is{PAWEB", "product_src=is{kohlerchina");
            return imageUrl;
        }

        $("body").on("click", ".koh-compare-add", function (event) {
            event.preventDefault();
            var productDetailslink = $('#productDetailslink').val();
            var gridImageProductUrl = $('#gridImageProductUrl').val();
            var gridImageNewProductUrl = $('#gridImageNewProductUrl').val();
            var comparePanelProducts = $('#comparePanelProducts').val();
            var compareButtonLable = $('#compareButtonLable').val();
            var compareClearResults = $('#compareClearResult').val();
            var common_galleryNewProductUrl = $('#common_galleryNewProductUrl').val();
        	var common_gridImageUrl = $('#common_gridImageUrl').val();
        	var currencyCode = $('#currencyCode').val();
        	
            var cookieContent,
                uri = $(this).data("href"),
                itemno = $(this).data("product-itemno"),
                defaultCategoryKey = $(this).data("category"),
                itemNoMultiAnscestors = $(this).data("product-itemno-multianscestors"),
                cookieName = $("#compare-cookie-info").data("cookie-name"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration"),
                compareMaxItemsNumber = $("#compare-cookie-info").data("cookie-max-items-number"),
                compareMaxItemsMessage = $("#compare-cookie-info").data("cookie-max-items-message"),
                compareDiffCategoryMessage = $("#compare-cookie-info").data("cookie-diff-category-message"),
                cookie = getCookie(cookieName),
                cookieProductCount = $("#compare-cookie-info").data("cookie-product_count"),
                products = "";
            if (null === cookie || "" === cookie) {
                cookieContent = defaultCategoryKey + "." + itemNoMultiAnscestors,
                    createCookie(cookieName, cookieContent, durationMinutes),
                    addProductText($(this));
            } else {
                var values = cookie.split("."),
                    category = values[0],
                    products = values[1].split("|"),
                    flagExists = false;
	                
                if (category === defaultCategoryKey) {
                	for (var i = 0; i < products.length; i++) {
	                    if (products[i].toString() === itemNoMultiAnscestors.toString()) {
	                        return false;
	                    }
                	}
                }
                
                if (flagExists || products.push(itemNoMultiAnscestors), defaultCategoryKey != category) {
                    var r = confirm(compareDiffCategoryMessage);
                    if (r === true) {
                        $(".koh-compare-item").remove();
                        $(".product-added").removeClass("product-added");
                        deleteCookie(cookieName);
                        cookieContent = defaultCategoryKey + "." + itemNoMultiAnscestors;
                        createCookie(cookieName, cookieContent, durationMinutes);
                        addProductText($(this));
                        products = "";

                    } else {
                        //$(".koh-product-tools").removeClass("product-added");
                        return false;
                    }
                } else {
                    cookieContent = category + "." + products.join("|");
                }
        		if (products.length > compareMaxItemsNumber) {		
        			 removeProductText($(this));
                     return alert(compareMaxItemsMessage),
                         false;
                 }
        		defaultCategoryKey == category && products.length <= compareMaxItemsNumber && addProductText($(this)),
                     deleteCookie(cookieName),
                     createCookie(cookieName, cookieContent, durationMinutes)
            }
            var itemNumbers = "" ;
            if (isNaN(itemNoMultiAnscestors)) {
            	itemNumbers = itemNoMultiAnscestors.split("!_");
            }
            var defaultCategory = "";
            if (itemNumbers.length > 1) {
            	defaultCategory = "&defaultCategory=" + itemNumbers[1];
            }
            $.ajax({
                    url: uri,
                    cache: false,
                    data: {
                        'itemNumber': itemno
                    },
                    success: function (data) {
                        if (data.success == true) {
                        	var productImageUrl;
                        	if (data.items[0].src.startsWith('http')) {
								var lastSlashIndex = data.items[0].src.lastIndexOf('/');
								const defImgFromUrl = data.items[0].src.slice(lastSlashIndex + 1);
								const domainStartIndex = data.items[0].src.indexOf("//") + 2;
								const defImgDomainFromUrl = data.items[0].src.slice(domainStartIndex, lastSlashIndex)
								lastSlashIndex = defImgDomainFromUrl.lastIndexOf('/');
								const defImgDamNameFromUrl = defImgDomainFromUrl.slice(lastSlashIndex + 1);
								if(data.items[0].isNew)
	                        	{
	                            	productImageUrl = common_galleryNewProductUrl.replace("{0}",defImgFromUrl);
	                        	}else{
	                        		productImageUrl = common_gridImageUrl.replace("{0}",defImgFromUrl);
	                        	}
                                /*checking DAM name, if DAM name is kohlerchina replacing DAM names with PAWEB 
                                    and product_src DAM to remain as kohlerchina 
                                */
                               if(defImgDamNameFromUrl === "kohlerchina") {
                                   productImageUrl = changeUrlDam(defImgDomainFromUrl,lastSlashIndex,productImageUrl,"PAWEB");
                               }else {
                                   productImageUrl = productImageUrl.replaceAll("{domain}", defImgDomainFromUrl);
                                   productImageUrl = productImageUrl.replaceAll("{dam}", defImgDamNameFromUrl);
                               }
							} else {
	                        	if(data.items[0].isNew)
	                        	{
	                        		productImageUrl = gridImageNewProductUrl.replace("{0}",data.items[0].src);
	                        	}else{
	                        		productImageUrl = gridImageProductUrl.replace("{0}",data.items[0].src);
	                        	}
	                        }
                            var priceVat = $('#price-vat').val();
                            var multipleValue = $('#multiply-value').val();
                            var productPrice = data.items[0].productPrice;
                            var roundPrice = Math.round(productPrice * priceVat);
                            productPrice = (roundPrice * multipleValue).toLocaleString('en-US');
                        	var multiAncestorefaultCategoryData = "";
                        	multiAncestorefaultCategoryData = " data-product-itemno-multianscestors=\""+ itemNoMultiAnscestors + "\"";
                            var comparePrice = currencyCode && data.items[0].productPrice && data.items[0].productPrice.length > 0 ? "<span class=\"koh-compare-price\">"+currencyCode+" "+ productPrice + "</span></a>" : "";
                        	
                            var htmlData = "<div class=\"koh-compare-item\" id=\"" + itemno + "\"><a href=\"" + productDetailslink + "/" + data.items[0].itemNumber + "/?skuid="+ data.items[0].defaultSku + defaultCategory + "\" ><img class='koh-compare-image' src='"+productImageUrl+"' alt='' height='100' width='68'>";
                            htmlData += "</br><span class=\"koh-compare-name\"><span class=\"koh-product-family\">" + data.items[0].family + "</span>" + data.items[0].description + "</span>";
                            htmlData += "</br><span class=\"koh-compare-sku\">K-" + data.items[0].defaultSku + "</span>";
                            htmlData += comparePrice;
                            htmlData += "</br><button class=\"koh-compare-remove\" data-product-itemno=\"" + data.items[0].itemNumber + "\"" + multiAncestorefaultCategoryData + ">Remove Product</button></div>";
                            if (products == "") {
                                var compareLink = $('#compareLink').val();
                                var htmlMainData = "<input id=\"compare-cookie-info\" type=\"hidden\" data-cookie-name=\"" + cookieName + "\" data-cookie-duration=\"15\" data-cookie-max-items-number=\"1\" data-cookie-ma" +
                                    "x-items-message=\"Only three comparison products are allowed. Please remove one " +
                                    "in order to allow adding this new compare to the list.\" data-cookie-diff-catego" +
                                    "ry-message=\"You can only compare products from the same category. Adding this p" +
                                    "roduct will clear your current comparison.\" data-cookie-category=\"" + defaultCategoryKey + "\" data-cookie-product_count=\"cookieProductCount\">";
                                htmlMainData += "<div class=\"c-koh-compare-panel\"><div class=\"koh-compare-header\"><span class" +
                                    "=\"koh-compare-title\">"+ comparePanelProducts +"</span>";
                                htmlMainData += "<button type=\"button\" class=\"koh-compare-toggle open\" onclick=\"$('.koh-comp" +
                                    "are-toggle').toggleClass('open');$('.koh-compare-items').toggleClass('open');\">" +
                                    "Show/Hide</button>";
                                htmlMainData += "<a href=\"\" data-listing-page-url = \"" + window.location.href + "\" data-compare-link = \"" + compareLink + "\" class=\"koh-compare-link btn--primary full-width-sm marg-b-10-sm active\">"+compareButtonLable+"</a>";
                                htmlMainData += "<button type=\"button\" class=\"koh-compare-clear\">"+compareClearResults+"</button></div>" +
                                    "<div class=\"koh-compare-items\"></div></div>";
                                $("#comparePanel").html(htmlMainData);

                                htmlData += "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                                    "demo\">2</span></div><div class=\"koh-compare-item\"><span class=\"koh-compare-a" +
                                    "dd-blank compare-add-demo\">3</span></div>";
                                $(".koh-compare-items").html(htmlData),
                                    $(".koh-compare-items").addClass("open");
                            } else {
                                var count = products.length;
                                var cookiesValue = null;
                                for (var i = 0; i < products.length; i++) {
                                    cookiesValue = products[i];

                                }
                                if (count == 2) {
                                    $(".koh-compare-items")
                                        .children()
                                        .last()
                                        .remove();
                                    $(".koh-compare-items")
                                        .children()
                                        .last()
                                        .remove();

                                    htmlData += "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                                        "demo\">3</span></div>";
                                    $(".koh-compare-items").append(htmlData),
                                        $(".koh-compare-items").addClass("open");
                                } else if (count == 3) {
                                    $(".koh-compare-items")
                                        .children()
                                        .last()
                                        .remove();
                                    $(".koh-compare-items").append(htmlData),
                                        $(".koh-compare-items").addClass("open");
                                }
                            }
                        }
                    }
                })
                .done(function () {
                    $(".koh-compare-toggle").addClass("open"),
                        $(".koh-compare-items").addClass("open");
                })
                .fail(function () {
                    // alert("failed");
                })

            /* $(".koh-compare-items").addClass("open");
            $(".koh-compare-toggle").addClass("open"), $(".koh-compare-items").addClass("open"), reloadModal()*/

        });

        $("body").on("click", ".koh-compare-removes", function (event) {
            event.preventDefault();
            var uri = $(this).data("href"),
                itemno = $(this).data("product-itemno"),
                itemMultiAncestors = $(this).data("product-itemno-multianscestors"),
                cookieName = $("#compare-cookie-info").data("cookie-name"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration");
            // $("#compare-cookie-info").data("cookie-max-items-number"),
            // $("#compare-cookie-info").data("cookie-max-items-message"),
            // $("#compare-cookie-info").data("cookie-diff-category-message");
            removeProductText($(this));
            //$($(".remove_"+itemno)).closest(".koh-product-tools").removeClass("product-add
            //ed");
            var cookieContent,
                cookie = getCookie(cookieName);
            if (null === cookie || "" === cookie)
                return void $(".c-koh-compare-panel").remove();
            var products,
                values = cookie.split("."),
                category = values[0];
            values.length > 1 && (products = values[1].split("|"));
            var index = products.indexOf(itemno.toString());
            if (itemMultiAncestors != undefined){
            	index = products.indexOf(itemMultiAncestors.toString());
            }
            if (index < 0){
            	return;
            }
            if (products.splice(index, 1), cookieContent = category + "." + products.join("|"), deleteCookie(cookieName), !(products.length > 0))
                return $(".c-koh-compare-panel").remove(),
                    deleteCookie(cookieName),
                    void createCookie(cookieName, "", durationMinutes);
            createCookie(cookieName, cookieContent, durationMinutes);
            var count = products.length;
            var cookiesValue = null;
            for (var i = 0; i < products.length; i++) {
                cookiesValue = products[i];
            }

            $("#" + itemno + "").remove();
            var count = products.length;
            $(".compare-add-demo")
                .parent()
                .remove();
            if (count == 1) {
                var htmlData = "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                    "demo\">2</span></div>";
                htmlData += "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                    "demo\">3</span></div>";
                $(".koh-compare-items").append(htmlData);
                //$(".koh-compare-toggle").addClass("open");
            }
            if (count == 2) {
                var htmlData = "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                    "demo\">3</span></div>";
                $(".koh-compare-items").append(htmlData);
                //$(".koh-compare-toggle").addClass("open");
            }
        });

        $("#comparePanel").on("click", ".koh-compare-remove", function (event) {
            event.preventDefault();
            var uri = $(this).attr("href"),
                itemno = $(this).data("product-itemno"),
                itemMultiAncestors = $(this).data("product-itemno-multianscestors"),
                cookieName = $("#compare-cookie-info").data("cookie-name"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration");
            // $("#compare-cookie-info").data("cookie-max-items-number"),
            // $("#compare-cookie-info").data("cookie-max-items-message"),
            // $("#compare-cookie-info").data("cookie-diff-category-message");
            var elem = $(".remove_" + itemno)[0];
            removeProductText($(elem));
            $($(".remove_" + itemno))
                .closest(".koh-product-tools")
                .removeClass("product-added");
            var cookieContent,
                cookie = getCookie(cookieName);
            if (null === cookie || "" === cookie)
                return void $(".c-koh-compare-panel").remove();
            var products,
                values = cookie.split("."),
                category = values[0];
            values.length > 1 && (products = values[1].split("|"));
            var index = products.indexOf(itemno.toString());
            if (itemMultiAncestors != undefined){
            	index = products.indexOf(itemMultiAncestors.toString());
            }
            if (products.splice(index, 1), cookieContent = category + "." + products.join("|"), deleteCookie(cookieName), !(products.length > 0))
                return $(".c-koh-compare-panel").remove(),
                    deleteCookie(cookieName),
                    void createCookie(cookieName, "", durationMinutes);
            createCookie(cookieName, cookieContent, durationMinutes);
            var count = products.length;
            var cookiesValue = null;
            for (var i = 0; i < products.length; i++) {
                cookiesValue = products[i];
            }

            $("#" + itemno + "").remove();
            var count = products.length;
            $(".compare-add-demo")
                .parent()
                .remove();
            if (count == 1) {
                var htmlData = "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                    "demo\">2</span></div>";
                htmlData += "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                    "demo\">3</span></div>";
                $(".koh-compare-items").append(htmlData);
                $(".koh-compare-toggle").addClass("open");
            }
            if (count == 2) {
                var htmlData = "<div class=\"koh-compare-item\"><span class=\"koh-compare-add-blank compare-add-" +
                    "demo\">3</span></div>";
                $(".koh-compare-items").append(htmlData);
                $(".koh-compare-toggle").addClass("open");
            }

        });

        $("#comparePanel").on("click", ".koh-compare-clear", function (event) {
            $(".c-koh-compare-panel").remove(),
                $(".product-added").removeClass("product-added");
            var cookieName = $("#compare-cookie-info").data("cookie-name"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration");
            deleteCookie(cookieName),
                createCookie(cookieName, "", durationMinutes),
                reloadModal();
            var cookieProductCount = $("#compare-cookie-info").data("cookie-product_count"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration");
            deleteCookie(cookieProductCount);
            createCookie(cookieProductCount, "", durationMinutes);
        });

        $("#comparePanel").on("click", ".koh-compare-link", function (event) {
            event.preventDefault();
            var compareLinkUrl = $(this).data("compare-link");
            var listingPageUrl = $(this).data("listing-page-url");
            createCookie('listingPageUrl', listingPageUrl, 5);
            window.location.href = compareLinkUrl;
        });

        $(".c-koh-compare-products").on("click", ".koh-history-page button", function (event) {
            event.preventDefault();
            var listingPageUrl = getCookie('listingPageUrl');
            if (null === listingPageUrl || "" === listingPageUrl) {} else {
                window.location.href = listingPageUrl;
                //deleteCookie('listingPageUrl');
            }
        });

        $(".c-koh-compare-products").on("click", ".koh-compare-remove-modal", function (event) {
            removeModalProduct(event, $(this))
        });

        $("#compare-product-details").on("click", ".koh-compare-remove", function (event) {
            // add compare details  close button.
            event.preventDefault();
            itemno = $(this).data("product-itemno"),
            	itemMultiAncestors = $(this).data("product-itemno-multianscestors"),
                cookieName = $("#compare-cookie-info").data("cookie-name"),
                durationMinutes = $("#compare-cookie-info").data("cookie-duration");
            var cookieContent,
                cookie = getCookie(cookieName);
            if (null === cookie || "" === cookie)
                return void $(".c-koh-compare-panel").remove();
            var products,
                values = cookie.split("."),
                category = values[0];
            values.length > 1 && (products = values[1].split("|"));
            var index = products.indexOf(itemno.toString());
            if (itemMultiAncestors != undefined){
            	index = products.indexOf(itemMultiAncestors.toString());
            }
            if (products.splice(index, 1), cookieContent = category + "." + products.join("|"), deleteCookie(cookieName), !(products.length > 0))
                return $(this).parent().parent().remove(),
                    deleteCookie(cookieName),
                    void createCookie(cookieName, "", durationMinutes);
            createCookie(cookieName, cookieContent, durationMinutes);
            $(this)
                .parent()
                .parent()
                .remove();
        });
    };

    return window.addEventListener("pageshow", function (event) {
        (event.persisted || window.performance && 2 == window.performance.navigation.type) &&  (isIEBrowser()) && location.reload()
    }, false), {
        initialize: function () {
            initComponents();
            var $components = $("." + componentClass);
            initComparePanel($components),
                initCompareModal($components),
				initColorOverlays();
        },
        onLoad: function () {}
    }

}();

$(function () {
    $(document).ready(function () {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.componentManager.comparePanel.initialize();
    });
    $(window).load(function () {
        // executes after the window is fully loaded, including all images, etc.
        axKOH.main.componentManager.comparePanel.onLoad();

        //move compare panel div into the header for fixed scroll
        var compareHTML = $('#comparePanel');
        $(".koh-global-navigation-components").append(compareHTML);
    });
});

$(document).on("opened", ".remodal", function () {
    adjustRowHeights(),
        $(".c-koh-branding-bar").addClass("print-hidden"),
        $("#comparePanel").addClass("print-hidden"),
        $(".koh-page").addClass("print-hidden"),
        $(".c-koh-printfooter").addClass("print-hidden")
});

$(document).on("closing", ".remodal", function () {
    $(".print-hidden").removeClass("print-hidden")
});

var adjustRowHeights = function () {
    var numRows = $(".koh-compare-features-list").last().find("li").length;
    maxCellHeight = 0;
    numCols = $(".koh-compare-features-list").length;
    $row = $(".row0");
    for (var i = 0; numRows > i; i++)
        $row = $(".row" + i);
    $row.each(function () {
        var $cell = $(this);
        $cell.height() > maxCellHeight && (maxCellHeight = $cell.height())
    });
    $row.each(function () {
        $(this).height(maxCellHeight)
    });
};

var isIEBrowser = function () {
	if (navigator.appName == 'Microsoft Internet Explorer' ||  !!(navigator.userAgent.match(/Trident/) || navigator.userAgent.match(/rv:11/)) || (typeof $.browser !== "undefined" && $.browser.msie == 1))
	{
	  return true;
	}else{
		return false;
	}
}	
