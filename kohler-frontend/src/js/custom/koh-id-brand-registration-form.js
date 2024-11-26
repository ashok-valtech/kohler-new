var emailErrorMessage1Id = $('#emailErrorMessage1Id').val();
var emailErrorMessage2Id = $('#emailErrorMessage2Id').val();
var projectTypeErrorMessage = $('#projectTypeErrorMessage').val();
var projectAsErrorMessage = $('#projectAsErrorMessage').val();
var budgetRangeErrorMessage = $('#budgetRangeErrorMessage').val();
var timelineProjectErrorMessage = $('#timelineProjectErrorMessage').val();
var projectLocationErrorMessage = $('#projectLocationErrorMessage').val();
var customerNameErrorMessage = $('#customerNameErrorMessage').val();
var phoneNumberErrorMessage = $('#phoneNumberErrorMessage').val();
var privacyErrorMessage = $('#privacyErrorMessage').val();
var messageFadingTimingLink = $('#messageFadingTimingLink').val();
var messageTimingLink = $('#messageTimingLink').val();
var captchaErrorMessageId = $('#captchaErrorMessageId').val();
var thankYouPageURL = $('#koh-brand-registration-thankyouPage').val();

// Adding script for google captcha
var head = document.getElementsByTagName('head')[0];
var script = document.createElement('script');
script.type = 'text/javascript';
script.onload = function() {
	scaleCaptcha();
}

var scaleCaptcha = function() {

	// Width of the reCAPTCHA element, in pixels
	var reCaptchaWidth = 304;
	// Get the containing element's width
	var containerWidth = $('.captcha-container').width();
	var captchaElements = $('.g-recaptcha');
	// Only scale the reCAPTCHA if it won't fit
	// inside the container
	if (containerWidth <= reCaptchaWidth) {
		var scale = 0.82;
		captchaElements.css('transform', 'scale(' + scale + ')').css(
				'-webkit-transform', 'scale(' + scale + ')').css(
				'-ms-transform', 'scale(' + scale + ')').css('-o-transform',
				'scale(' + scale + ')').css('transform-origin', '0 0').css(
				'-webkit-transform-origin', '0 0').css('-ms-transform-origin',
				'0 0').css('-o-transform-origin', '0 0');
		$('.captcha-container').height(68);
	}
}
script.src = 'https://www.google.com/recaptcha/api.js';
head.appendChild(script);


var disableSubmit = function(errorCount) {
	if(errorCount > 0) {
		$("#brand-submit-btn").attr("disabled", true);
	}else {
		$("#brand-submit-btn").removeAttr("disabled")

	}
}

function onAjaxError(customMsg) {
	if(customMsg.length > 0) {
		$("#submit-error-message").html(customMsg);
	}
	grecaptcha.reset();
	$('#brand-submit-btn').removeClass('koh-form-btn-loader');
	$("html, body").animate({ scrollTop: 0 }, "fast");
	$('form').find("input[type=text], select").val("");
	
	$('#submit-error-message').removeClass('hide').fadeIn('fast');
	setTimeout(function () {
		$('#submit-error-message').fadeOut(messageFadingTimingLink, function () {
			$(this).addClass('hide');
		});
	}, messageTimingLink); // <-- time in milliseconds
}


$(document).ready(function() {
	$('#idBrandRegistratationForm').submit(function(e) {
		e.preventDefault();
		var contactEmailLink = $(this).attr('action');
		validateRegistrationForm(contactEmailLink);
	})
});


function validateRegistrationForm(contactEmailLink) {
	var customerEmail = $('#koh-brand-customer-email').val();
	var customerName = $('#koh-brand-customer-name').val();
	var phoneNumber = $('#koh-brand-phone-number').val();
	var location = $('#koh-brand-project-location').val();
	var budgetRange = $('#koh-brand-budget-range').val();
	var timelineProject = $('#koh-brand-timeline-project').val();
	var projectAs = $('#koh-brand-project-as').val();
	var recaptchaResponse = grecaptcha.getResponse();
	var privacyPolicy = $("#kohler-warranty-privacy-policy").val();
	var contactInfoCheckbox = $("#kohler-contact-info").val();

	var isValid = true;

	if (location == '' || location == null) {
		isValid = false;
		showError('#koh-brand-project-location', projectLocationErrorMessage);
	}

	if (customerName == '' || customerName == null) {
		isValid = false;
		showError('#koh-brand-customer-name', customerNameErrorMessage);
	}

	if (customerEmail == '' || customerEmail == null) {
		isValid = false;
		showError('#koh-brand-customer-email', emailErrorMessage1Id);
	} else {
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		if (reg.test(customerEmail) == false) {
			isValid = false;
			showError('#koh-brand-customer-email', emailErrorMessage2Id);
		}
	}

	if (phoneNumber == '' || phoneNumber == null) {
		isValid = false;
		showError('#koh-brand-phone-number', phoneNumberErrorMessage);
	}

	if (budgetRange == '') {
		isValid = false;
		showError('#koh-brand-budget-range', budgetRangeErrorMessage);
	}
	if (timelineProject == '') {
		isValid = false;
		showError('#koh-brand-timeline-project', timelineProjectErrorMessage);
	}

	if (projectAs == '') {
		isValid = false;
		showError('#koh-brand-project-as', projectAsErrorMessage);
	}
	
	if ($('.checkbox-round:checked').length === 0) {
		showError('#multi-checkbox-wrapper', projectTypeErrorMessage);
	}

	if ((document.getElementById('kohler-warranty-privacy-policy').checked == false)) {
		isValid = false;
		showError('#privacy-checkbox', privacyErrorMessage);
	}

	if (recaptchaResponse.length === 0) {
		isValid = false;
		showError('.g-recaptcha', captchaErrorMessageId);
		$('.g-recaptcha > div').css({
			'border' : '1px solid #e7102e'
		})
	} else {
		removeError('#g-recaptcha');
		$('.g-recaptcha > div').css({
			'border' : 'none'
		})
	}

	if (isValid == true) {
		var projectType = $('.checkbox-round:checked').map(function() {
			return $(this).val();
		}).get().join(', ');
		var fdata = new FormData();
		fdata.append("koh-brand-customer-email", customerEmail);
		fdata.append("koh-brand-customer-name", customerName);
		fdata.append("koh-brand-phone-number", phoneNumber);
		fdata.append("koh-brand-project-location", location);
		fdata.append("koh-brand-budget-range", budgetRange);
		fdata.append("koh-brand-timeline-project", timelineProject);
		fdata.append("koh-brand-project-as", projectAs);
		fdata.append("koh-brand-project-type", projectType);
		fdata.append("kohler-warranty-privacy-policy", privacyPolicy);
		fdata.append("kohler-contact-info", contactInfoCheckbox);

		$.ajax({
			url : contactEmailLink,
			cache : false,
			contentType : false,
			processData : false,
			data : fdata,
			type : 'POST',
			beforeSend : function() {
				$('#brand-submit-btn').addClass('koh-form-btn-loader');
			},
			success : function(data) {
				if (data.success == 'true') {
					window.open(thankYouPageURL, "_self");
				}else if(data.sfmcAPIError) {
                    onAjaxError(data.sfmcAPIError);
                }else {
					var errorMessage = $("#submit-error-message").val();
					onAjaxError(errorMessage);
				}
			},
			error: function(error) {
				var errorMessage = $("#submit-error-message").val();
				onAjaxError(errorMessage);
			}
		})
	}
}

var showError = function(ele, errorMessage) {
	$(ele).parent().find('.error-message').remove();
	$(ele).after("<div class='error-message'>" + errorMessage + "</div>");
	$(ele).css({
		'border-color' : '#e7102e'
	})
	$(ele).closest('.brand-form-group').css({
		'margin-bottom' : '6px'
	})
	disableSubmit(1);
}

var showCheckboxError = function(ele,errorMessage) {
	$(ele).find('.error-message').remove();
	$(ele).append("<div class='error-message'>" + errorMessage + "</div>");
	disableSubmit(1);
};

var removeCheckboxError = function(ele) {
	$(ele).find('.error-message').remove();
	var sfmcErrorCount = $(document).find('.error-message').size();
    disableSubmit(sfmcErrorCount);
}

var removeError = function(ele) {
	$(ele).parent().find('.error-message').remove();
	$(ele).css({
		'border-color' : '#cccccc'
	})
	$(ele).closest('.brand-form-group').css({
		'margin-bottom' : '15px'
	})
	var sfmcErrorCount = $(document).find('.error-message').size();
	disableSubmit(sfmcErrorCount);
}
var validateEmail = function(ele) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var currentVal = $(ele).val();
	if (reg.test(currentVal) == false) {
		return false;
	}
	return true;
}


$('#koh-brand-phone-number').on('input', function(event) {
	var start = this.selectionStart - 1, end = this.selectionEnd - 1;
	if ($(this).val().match(/[^0-9-+()]/gi)) {
		$(this).val($(this).val().replace(/[^0-9-+()]/gi, ''));
		this.setSelectionRange(start, end);
	}
});

$("#multi-checkbox-wrapper .multi-checkbox-label").on('click', function(event) {
	if ($('.checkbox-round:checked').length === 0) {
		isValid = false;
		showError('#multi-checkbox-wrapper', projectTypeErrorMessage);
	}else {
		isValid = true;
		removeError('#multi-checkbox-wrapper');
	}
})

$(document)
		.on(
				'keyup change',
				'#idBrandRegistratationForm input, #idBrandRegistratationForm select',
				function(e) {
					var currentIdName = $(this).attr('id');
					var currentId = '#' + currentIdName;

					if (currentIdName == 'koh-brand-customer-name') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, customerNameErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-brand-customer-email') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, emailErrorMessage1Id);
						} else if (!validateEmail(currentId)) {
							isValid = false;
							showError(currentId, emailErrorMessage2Id);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-brand-project-location') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, projectLocationErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}
					if (currentIdName == 'koh-brand-phone-number') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, phoneNumberErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-brand-budget-range') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, budgetRangeErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-brand-timeline-project') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, timelineProjectErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-brand-project-as') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, projectAsErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'kohler-warranty-privacy-policy') {
						if ((document
								.getElementById('kohler-warranty-privacy-policy').checked == false)) {
							isValid = false;
							showError('#privacy-checkbox', privacyErrorMessage);
						} else {
							isValid = true;
							removeError('#privacy-checkbox');
						}
					}
				})

function recaptchaCallback() {
	var captchaRes = grecaptcha.getResponse();
	if(captchaRes.length !== 0) {
		removeError('#g-recaptcha');
		$("#g-recaptcha").css("border", "none");
		$("#g-recaptcha").children('div:first-child').css("border", "none");
	}
};