/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

// Replace "componentName" with something representing which component this is
axKOH.main.componentManager.contactForm = (function () {
	var componentClass = "c-koh-contact-form";

	// Main init function; contains the main loop through component instances
	var initComponents = function() {
		// Find all instances of the component on the page
		var $components = $("."+componentClass);

		// For each component instance, initialize it
		$components.each( function(componentIndex) {
			var $component = $(this); // the single component instance we're currently operating on

			var $contactForm		= $component.find("form");
			var $contactToggle	= $component.find(".koh-contact-toggle").children("input");
			var $contactPhotos	= $contactForm.find(".koh-contact-photo");
			var $contactTerms		= $contactForm.find("#newsletter");
			var $captchaError   = $contactForm.find(".koh-contact-captcha .error-message");

			var $countrySelector 	= $contactForm.find(".country").children("select");
			var $stateSelector 		= $contactForm.find(".state").children("select");




			// Show different forms on selection
			$contactToggle.change(function () {
				var toggleChoice = $(this).val();
				if (toggleChoice === "yes") {
					$(".koh-contact-fields").hide();
					$(".koh-contact-fields.v-yes").show();
				} else {
					$(".koh-contact-fields").hide();
					$(".koh-contact-fields.v-no").show();
				}

        $contactForm.each(function(){
          $(this)[0].reset();
        });
			});



			/*  jQuery Nice Select - v1.0
	    https://github.com/hernansartorio/jquery-nice-select
	    Made by Hernán Sartorio  */
			!function(e){e.fn.niceSelect=function(t){function s(t){t.after(e("<div></div>").addClass("nice-select").addClass(t.attr("class")||"").addClass(t.attr("disabled")?"disabled":"").attr("tabindex",t.attr("disabled")?null:"0").html('<span class="current"></span><ul class="list"></ul>'));var s=t.next(),n=t.find("option"),i=t.find("option:selected");s.find(".current").html(i.data("display")||i.text()),n.each(function(t){var n=e(this),i=n.data("display");s.find("ul").append(e("<li></li>").attr("data-value",n.val()).attr("data-display",i||null).addClass("option"+(n.is(":selected")?" selected":"")+(n.is(":disabled")?" disabled":"")).html(n.text()))})}if("string"==typeof t)return"update"==t?this.each(function(){var t=e(this),n=e(this).next(".nice-select"),i=n.hasClass("open");n.length&&(n.remove(),s(t),i&&t.next().trigger("click"))}):"destroy"==t?(this.each(function(){var t=e(this),s=e(this).next(".nice-select");s.length&&(s.remove(),t.css("display",""))}),0==e(".nice-select").length&&e(document).off(".nice_select")):console.log('Method "'+t+'" does not exist.'),this;this.hide(),this.each(function(){var t=e(this);t.next().hasClass("nice-select")||s(t)}),e(document).off(".nice_select"),e(document).on("click.nice_select",".nice-select",function(t){var s=e(this);e(".nice-select").not(s).removeClass("open"),s.toggleClass("open"),s.hasClass("open")?(s.find(".option"),s.find(".focus").removeClass("focus"),s.find(".selected").addClass("focus")):s.focus()}),e(document).on("click.nice_select",function(t){0===e(t.target).closest(".nice-select").length&&e(".nice-select").removeClass("open").find(".option")}),e(document).on("click.nice_select",".nice-select .option:not(.disabled)",function(t){var s=e(this),n=s.closest(".nice-select");n.find(".selected").removeClass("selected"),s.addClass("selected");var i=s.data("display")||s.text();n.find(".current").text(i),n.prev("select").val(s.data("value")).trigger("change")}),e(document).on("keydown.nice_select",".nice-select",function(t){var s=e(this),n=e(s.find(".focus")||s.find(".list .option.selected"));if(32==t.keyCode||13==t.keyCode)return s.hasClass("open")?n.trigger("click"):s.trigger("click"),!1;if(40==t.keyCode){if(s.hasClass("open")){var i=n.nextAll(".option:not(.disabled)").first();i.length>0&&(s.find(".focus").removeClass("focus"),i.addClass("focus"))}else s.trigger("click");return!1}if(38==t.keyCode){if(s.hasClass("open")){var l=n.prevAll(".option:not(.disabled)").first();l.length>0&&(s.find(".focus").removeClass("focus"),l.addClass("focus"))}else s.trigger("click");return!1}if(27==t.keyCode)s.hasClass("open")&&s.trigger("click");else if(9==t.keyCode&&s.hasClass("open"))return!1});var n=document.createElement("a").style;return n.cssText="pointer-events:auto","auto"!==n.pointerEvents&&e("html").addClass("no-csspointerevents"),this}}(jQuery);

			$('.c-koh-contact-form select').niceSelect();

			$countrySelector.change(function(e){
        // ******* Hack *********
        // the custom select plugin is running slow enough that the jquery is finding more than one "selected" item.
        // jq is passing the text of both items to the api. this hack just grabs the last item in the array.


				var selectedCountry = $(e.target).next('.nice-select').find(".selected").text();
				$stateSelector.removeAttr("disabled").parent().removeClass('disabled');
        if($stateSelector.attr('name').length > 0){
				$.get( "/api/dealerlookup/"+ selectedCountry, function( data ) {
					$stateSelector.html(data);
          $('.c-koh-contact-form select').niceSelect('update');
				});
      }
      $('.c-koh-contact-form select').niceSelect('update');
			});

			// Photo fields preview
			$contactPhotos.each(function () {
				var $photoInput = $(this).find("input[type=file]");
				function readURL(input) {
					if (input.files && input.files[0]) {
						var photoPreview = $photoInput.parent().find(".preview");
						photoPreview.html("");
						var reader = new FileReader();
						reader.onload = function (e) {
							var img = $("<img />");
							img.attr("style", "height:41px;");
							img.attr("src", e.target.result);
							photoPreview.append(img);
						}
						reader.readAsDataURL(input.files[0]);
					}
				}
				$photoInput.parsley().on('field:success', function() {
					var inputID = $photoInput.attr('id');
					var inputTarget = '#' + inputID;
					var inputValue = document.getElementById(inputID);
					readURL(inputValue);
					$(inputTarget).parent().find('.koh-photo-remove').show();
					$contactForm.find('#terms').prop('required',true);
				});
			});

			// Form validation
			$contactForm.parsley({
        errorClass: "error",
        classHandler: function (el) {
        	return el.$element.closest('div');
        },
        errorsWrapper: '<div class="error-message"></div>',
        errorTemplate: '<span></span>'
    	});

			window.Parsley.addValidator('textfield', {
		    requirementType: 'regexp',
		    validateString: function(value, requirement) {
		      return /^[0-9A-Za-z _@',\-]+$/i.test(value)
		    },
		    messages: {
		      en: 'Invalid characters detected.',
					'pt-br': 'Caracteres inválidos detectados'
		    }
		  });

			window.Parsley.addValidator('textarea', {
		    requirementType: 'regexp',
		    validateString: function(value, requirement) {
		      return /^[0-9A-Za-z _@',.!_/$\-]+$/i.test(value)
		    },
		    messages: {
		      en: 'Invalid characters detected.',
					'pt-br': 'Caracteres inválidos detectados'
		    }
		  });

			window.Parsley.addValidator('filesize', {
			  validateString: function(_value, maxSize, parsleyInstance) {
			    var files = parsleyInstance.$element[0].files;
			    return files.length != 1  || files[0].size <= maxSize * 1024 * 1024;
			  },
			  requirementType: 'integer',
			  messages: {
			    en: 'Photo cannot be larger than %s MB',
					'pt-br': 'Foto não pode ser maior que %s MB'
			  }
			});

			window.Parsley.addValidator('filetype', {
				requirementType: 'string',
				validateString: function (value, requirement, parsleyInstance) {
					var file = parsleyInstance.$element[0].files;
					if (file.length == 0) {
						return true;
					}
					var allowedMimeTypes = requirement.replace(/\s/g, "").split(',');
					return allowedMimeTypes.indexOf(file[0].type) !== -1;
				},
				messages: {
					en: 'Photo must be a JPG, GIF, PNG or BMP',
					'pt-br': 'A foto deve ser JPG, GIF, PNG or BMP'
				}
			});

			// Remove photo
			$(this).on('click', '.koh-photo-remove', function(e) {
				var prevInput = $(this).parent().find("input[type=file]").attr('id');
				var prevID 		= $('#' + prevInput);
				var prevImg 	= $(this).parent().find('.preview img');
				var totalImg	= $('.koh-contact-photo.parsley-success').length;
				$(this).hide();
				prevID.parsley().reset();
				prevID.val('');
				prevImg.remove();
				if (totalImg <= 1) {
					$contactForm.find('#terms').prop('required',false);
				}
			});

			$contactForm.on('submit', function (e) {
				if ($(this).parent().hasClass('v-yes')) {
					var response = grecaptcha.getResponse(widgetId1);
					if(response.length == 0) {
						e.preventDefault();
						$captchaError.show();
					}
				}
				if ($(this).parent().hasClass('v-no')) {
					var response = grecaptcha.getResponse(widgetId2);
					if(response.length == 0) {
						e.preventDefault();
						$captchaError.show();
					}
				}
				if (e.isDefaultPrevented()) {
					return false;
				} else {
					return true;
				}
			});

		});
	};

	/* Defines functions exposed through manager object */
	return {
		initialize: function () {
			initComponents();
		},
		onLoad: function () {
		}
	};
})();

var verifyCallback = function(response) {
	$(".koh-contact-captcha .error-message").hide();
};
var widgetId1;
var widgetId2;
var captchaKey = $('#captchaKey').val();
var onloadCallback = function() {
	widgetId1 = grecaptcha.render('recaptcha1', {
		'sitekey' : captchaKey,
		'callback' : verifyCallback
  });
	widgetId2 = grecaptcha.render('recaptcha2', {
		'sitekey' : captchaKey,
		'callback' : verifyCallback
  });
};

$(function () {
	$(document).ready(function() {
		// executes when HTML-Document is loaded and DOM is ready
		axKOH.main.componentManager.contactForm.initialize();
	});

	$(window).load(function() {
		// executes after the window is fully loaded, including all images, etc.
		axKOH.main.componentManager.contactForm.onLoad();
	});
});
