/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

"use strict";

var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};

axKOH.main.seoUpdate = function() {
    var componentClass = "c-koh-seo-page";
    
    var titleErrorMessage2 = "";
    var descErrorMessage = "";
    var canonicalURLErrorMessage = "";
    var seoTitleErrorMessage = "";
    var seoTitleCharValue = "";
    var seoDescCharValue = "";
    var seoCanonicalURLCharValue = "";
    var productDetailsId = "";
    
    var initseoUpdate = function($components) {
    		if ($components) {
	    		var availableFiltersClass = ".koh-seo-available-filters";
	    		var $availableFilters = $components.find(availableFiltersClass);
		    	
		    	if ($availableFilters) {
		    		$availableFilters.find('.name').each(function(e) {
						$(this).click({
							target: $components
		                }, function(e) {
		                	e.preventDefault ();
		                	removeError('#seo-title');
		                	removeError('#seo-description');
		                	removeError('#canonicalUrl');
		                	$components = e.data.target;
		                	
		                	$components.find(".name").each(function() {
		        				if ($(this).hasClass("active")) {
		        					$(this).removeClass("active");
		        				}
		        			});
		        			
		                	$(this).addClass("active");
		                	
		                	var updatedDocument = $components.find('#updatedDocument')
		                	if (updatedDocument.length > 0) {
		                		if (updatedDocument.val() == '') {
				                	$('#successMessage').remove();
				                	$('#errorMessage').remove();
		                		}
		                		updatedDocument.val('');
		                	} else {
		                		$('#successMessage').remove();
			                	$('#errorMessage').remove();
		                	}
		                	var $components = e.data.target;
		                	var category = "";
		                	var prop = $(this).data("prop"),
		                	seoTitle = $(this).data("seotitle"),
		                	seoDesc = $(this).data("seodesc"),
		                	noIndex = $(this).data("noindex"),
		                	noFollow = $(this).data("nofollow"),
		                	canonicalUrl = $(this).data("canonicalurl");
		                	if(typeof $(this).data("category") !== 'undefined') {
		                		category = $(this).data("category");
		                	}
		                	if(noIndex == true) {
		        			 	$components.find("#noIndex").prop("checked", true);
		        			} else {
		        				$components.find("#noIndex").prop("checked", false);
		        			}
		        			if(noFollow == true) {
		        				$components.find("#noFollow").prop("checked", true);
		        			} else
		        			{
		        				$components.find("#noFollow").prop("checked", false);
		        			}
		        			$components.find("#pageDocument").val(prop);
		        			$components.find("#seo-title").val(seoTitle);
		        			$components.find("#seo-description").val(seoDesc);
		        			$components.find("#canonicalUrl").val(canonicalUrl);
		        			if (category !== '') {
		        				$components.find("#category").val(category);
		        			}
		        			$components.find(".koh-active-seo").each(function() {
		        				if ($(this).hasClass("active")) {
		        					$(this).removeClass("active");
		        				}
		        			});
		        			
		                	$(this).find(".koh-active-seo").addClass("active");
		                	
		                	var $siteMapItem = $components.find('#siteMapItem');
		                	if ($siteMapItem) {
		                		var $sitemap = $siteMapItem.val();
		                		var $visitAn = $components.find('.visit-an');
		                		if ($visitAn) {
		                			var $visitAnTitle = $visitAn.attr('title');
		                			if ($visitAnTitle.indexOf($sitemap) > 1) {
		                				$visitAnTitle = $visitAnTitle.substring(0,$visitAnTitle.indexOf($sitemap));
		                				$visitAnTitle = $visitAnTitle + $sitemap;
		                				if (prop != "") {
		                					$visitAnTitle = $visitAnTitle +"/" + prop + ".html";
		                					$visitAn.attr('title', $visitAnTitle)
		                				}
		                			}
		                		}
		                		
		                	}
		                });
					});
		    	}	
		    	
		    	titleErrorMessage2 = $components.find('#titleErrorMessage2Id').val();
		        descErrorMessage = $components.find('#descErrorMessageId').val();
		        canonicalURLErrorMessage = $components.find('#canonicalURLErrorMessageId').val();
		        seoTitleErrorMessage = $components.find('#seoTitleErrorMessageId').val();
		        seoTitleCharValue = $components.find('#seoTitleCharValueId').val();
		        seoDescCharValue = $components.find('#seoDescCharValueId').val();
		        seoCanonicalURLCharValue = $components.find('#seoCanonicalURLCharValueId').val();
    		}
    };
    
    var visitPage = (function ($components) {
    	if ($components) {
    		var visitPageClass = ".visit-an";
    		var $visitPages = $components.find(visitPageClass);
	    	if ($visitPages) {
	    		$visitPages.each(function(e) {
					$(this).click({
						target: $components
	                }, function(e) {
	                	e.preventDefault();
	                	$components = e.data.target;
	                	var seoPage ="";
	                	var pageDocumentVal = "";
	                	var $siteMapItem = $components.find('#siteMapItem');
	                	var $pageDocument = $components.find('#pageDocument');
	                	if ($siteMapItem) {
	                		seoPage = $siteMapItem.val();
	                	}
	                	if ($pageDocument) {
	                		pageDocumentVal = $pageDocument.val();
	                	}
	                	if (pageDocumentVal && (pageDocumentVal != "")) {
	                		var category = $components.find('#category').val();
	                		if (category == '') {
	                			var productDetailsId=$components.find('#productDetailsId').val();
	                			if(productDetailsId.includes(seoPage)) {
	                				window.open(seoPage + "/" + pageDocumentVal, '_blank');	
	                			}
	                			else {
	                			window.open(seoPage + "/" + pageDocumentVal + ".html", '_blank');
	                		  }	
	                		} else {
	                			window.open(seoPage + category, '_blank');
	                		}
	                	} else {	
	                		window.open(seoPage, '_blank');
	                	}
	                });
	    		});
	    	}
    	}	
	});
	
	var scrollInResponsive = (function ($components){
		var $seoSmScroll = $components.find ('#koh-seo-sm-scroll');
		var $scrollLocation = "";
		var $scrollSpeed = 0;
		if ($seoSmScroll) {
			if ($seoSmScroll.is(":visible")) {
				$scrollLocation = "#koh-seo-sm-scroll";
				$scrollSpeed = 2000;
			} else {
				$scrollLocation = ".koh-page-inner";
				$scrollSpeed = 1000;
			}
		}
		var $names = $components.find ('.name');
		if ($names) {
			$names.each (function(){
				$(this).click(function() {
					$('html, body').animate({
						scrollTop: $($scrollLocation).offset().top
					}, $scrollSpeed);
				});
			});	
		}
		var $parentElement = $components.find ('.koh-seo-li');
		if ($parentElement) {
			$parentElement.each (function(){
				if ($(this).hasClass ('active')) {
					var $glyphiconMinusElement = $(this).find ('.glyphicon-minus');
					if ($glyphiconMinusElement.length == 0) {
							$('html, body').animate({
								scrollTop: $($scrollLocation).offset().top
							}, $scrollSpeed);
					}
				}
			});	
		}
	});
    
    var formSubmit = (function ($components) {
    	var $seoUpdateForm = $components.find ('#seoUpdateForm');
    	if ($seoUpdateForm) {
    		$seoUpdateForm.submit({
    			target: $components
    		},function(e) {
    			var $components = e.data.target;
    	        if (validateSeoUpdateForm($components) == false) {
    	            e.preventDefault();
    	        }
    	    });
    		
    		$seoUpdateForm.find ('input').each (function(e){
	    			$(this).keyup(function(e) {
	    			    var currentVal = "";
	    			    var currentIdName = $(this).attr('id');
	    			    var currentId = '#' + currentIdName;
	    			    if (currentIdName == 'seo-title') {
	    			        currentVal = $(this).val();
	    			        var seoTitleLength = currentVal.length;
	    			        if (currentVal == "" || currentVal.trim().length==0) {
	    			            showError($(this), seoTitleErrorMessage);
	    			        } else if (seoTitleLength > seoTitleCharValue) {
	    			            showError($(this), titleErrorMessage2);
	    			        } else {
	    			            removeError($(this));
	    			        }
	    			    }
	
	    			    if (currentIdName == 'seo-description') {
	    			        currentVal = $(this).val();
	    			        var seoDescLength = currentVal.length;
	    			        if (seoDescLength > seoDescCharValue) {
	    			            showError(currentId, descErrorMessage);
	    			        } else {
	    			            removeError(currentId);
	    			        }
	    			    }
	
	    			    if (currentIdName == 'canonicalUrl') {
	    			        currentVal = $(this).val();
	    			        var canonicalUrl = currentVal.length;
	    			        if (canonicalUrl > seoCanonicalURLCharValue) {
	    			            showError(currentId, canonicalURLErrorMessage);
	    			        } else {
	    			            removeError(currentId);
	    			        }
	    			    }
	    			});
    		});
    	}
    });
    
    var validateSeoUpdateForm = (function($components){
    	var seoTitleValue = $components.find('#seo-title').val();
        var seoDescValue = $components.find('#seo-description').val();
        var canonicalUrlValue = $components.find('#canonicalUrl').val();
        var isValid = true;
        var seoTitleLength = seoTitleValue.length;
        var seoDescLength = seoDescValue.length;
        var seoCanonicalUrlLength = canonicalUrlValue.length;
        if (seoTitleValue == '' || seoTitleValue.trim().length==0) {
            isValid = false;
            showError('#seo-title', seoTitleErrorMessage);
        } else if (seoTitleLength > seoTitleCharValue) {
            isValid = false;
            showError('#seo-title', titleErrorMessage2);
        }
        if (seoDescLength > seoDescCharValue) {
            isValid = false;
            showError('#seo-description', descErrorMessage);
        }
        if (seoCanonicalUrlLength > seoCanonicalURLCharValue) {
            isValid = false;
            showError('#canonicalUrl', canonicalURLErrorMessage);
        }
        return isValid;
    });
    
    var showError = (function(ele, errorMessage) {
        $(ele).parent().find('.koh-error').remove();
        $(ele).after("<div class='koh-error'>" + errorMessage + "</div>");
        $(ele).css({
            'border-color': '#e7102e'
        })
        $(ele).closest('.form-group').css({
            'margin-bottom': '6px'
        })
    });

    var removeError = (function(ele) {
        $(ele).parent().find('.koh-error').remove();
        $(ele).css({
            'border-color': '#cccccc'
        })
        $(ele).closest('.form-group').css({
            'margin-bottom': '15px'
        })
    });
    
    
    var setUpdatedDocumentData = (function($components){
    	var updatedDocument = $components.find('#updatedDocument')
    	if (updatedDocument) {
    		var updatedDocumentVal = updatedDocument.val();
    		if (updatedDocumentVal != '') {
    			var availableFiltersClass = ".koh-seo-available-filters";
	    		var $availableFilters = $components.find(availableFiltersClass);
		    	if ($availableFilters) {
		    		$availableFilters.find('.name').each(function(e) {
		    			var prop = $(this).data("prop")
		    			if (updatedDocumentVal == prop) {
		    				$(this).trigger( "click" );
		    			}
		    		});	
		    	}		
    		}
    	}
    });
    
    return {
        initialize: function() {
            var $components = $("." + componentClass);
            initseoUpdate($components), visitPage($components), formSubmit ($components), scrollInResponsive($components), setUpdatedDocumentData($components);
        }
    }
}();


$(function() {
    $(document).ready(function() {
    	axKOH.main.seoUpdate.initialize();
    });
});
