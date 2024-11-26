var emailErrorMessage1Id = $('#emailErrorMessage1Id').val();
var emailErrorMessage2Id = $('#emailErrorMessage2Id').val();
var nameErrorMessageId = $('#nameErrorMessageId').val();
var mobileErrorMessageId = $('#mobileErrorMessageId').val();
var addressErrorMessageId = $('#addressErrorMessageId').val();
var deliveryErrorMessage1Id = $('#deliveryErrorMessage1Id').val();
var deliveryErrorMessage2Id = $('#deliveryErrorMessage2Id').val();
var purchaseErrorMessageId = $('#purchaseErrorMessageId').val();
var captchaErrorMessageId = $('#captchaErrorMessageId').val();
var validFileErrorMessage = $('#validFileErrorMessageId').val();
var fileSizeErrorMessage = $('#fileSizeErrorMessageId').val();
var uploadErrorMessage = $('#uploadErrorMessageId').val();
var salutationErrormessage = $('#salutationErrormessageId').val();
var itemNoErrorMessage = $('#itemNoErrorMessageId').val();
var dealerErrorMessage = $('#dealerErrorMessageId').val();
var productTypeErrorMessage = $('#productTypeErrorMessageId').val();
var districtErrorMessage = $('#districtErrorMessageId').val();
var purchaseDateErrorMessage1 = $('#purchaseDateErrorMessage1Id').val();
var purchaseDateErrorMessage2 = $('#purchaseDateErrorMessage2Id').val();
var thankYouPageURL = $('#koh-warranty-thankyouPage').val();
var invalidDateErrorMessage = $('#invalidDateErrorMessage').val();

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
		$("#koh-contact-form-btn").attr("disabled", true);
	}else {
		$("#koh-contact-form-btn").removeAttr("disabled")

	}
}

var isValidDateFormat = function (dateString) {
	var dateFormat = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	return dateFormat.test(dateString);
}

$('#koh-contact-upload-file').on(
		'change',
		function(e) {
			var ext = $(this).val().split('.').pop().toLowerCase();
			var fileName = e.target.files[0].name;
			var fileSize = e.target.files[0].size;
			if ($.inArray(ext, [ 'pdf', 'jpg' ]) == -1) {
				$(".koh-contact-upload-note").next('.koh-file-upload-error')
						.remove();
				$('#koh-contact-upload-file-name').hide();
				$('.koh-upload-wrapper').show();
				$(".koh-contact-upload-note").after(
						"<div class='koh-file-upload-error'>"
								+ validFileErrorMessage + "</div>");
				$('.koh-upload-wrapper').css('border',
						'1px solid rgb(231, 16, 46)');
				disableSubmit(1);
			} else if (fileSize > 4194304) {
				$(".koh-contact-upload-note").next('.koh-file-upload-error')
						.remove();
				$('#koh-contact-upload-file-name').hide();
				$('.koh-upload-wrapper').show();
				$(".koh-contact-upload-note").after(
						"<div class='koh-file-upload-error'>"
								+ fileSizeErrorMessage + "</div>");
				$('.koh-upload-wrapper').css('border',
						'1px solid rgb(231, 16, 46)');
				disableSubmit(1);
			} else {
				$('.koh-upload-wrapper').hide();
				$(".koh-contact-upload-note").next('.koh-file-upload-error')
						.remove();
				$('#koh-contact-upload-file-name').text(fileName).show();
				$('.koh-upload-wrapper').css('border', 'none');
				removeError("#koh-contact-upload-file");
			}
		})

$('#koh-contact-upload-file-name').on('click', function() {
	$('#koh-contact-upload-file').trigger('click');
})

$(document).ready(function() {
	$('#sgLuckyDrawForm').submit(function(e) {
		e.preventDefault();
		var contactEmailLink = $(this).attr('action');
		validateWarrantyForm(contactEmailLink);
	})
});

function validateWarrantyForm(contactEmailLink) {
	var salutation = $('#koh-warranty-salutation').val();
	var email = $('#koh-warranty-email').val();
	var fullName = $('#koh-warranty-full-name').val();
	var mobile = $('#koh-warranty-mobile').val();
	var address = $('#koh-warranty-address').val();
	var productDeliveryDate = $('#koh-warranty-product-delivery').val();
	var purchaseDate = $('#koh-warranty-product-purchase').val();
	var kohlerPurchase = $('#koh-warranty-choose').val();
	var advice = $('#koh-warranty-suggestions').val();
	var privacyPolicy = $('#kohler-warranty-privacy-policy').val();
	var contactInfo = $('#kohler-contact-info').val();
	var whereToBuy = $('#koh-warranty-outlet').val();
	if (whereToBuy === "Others") {
		whereToBuy = $('#koh-others-checkbox').val();
	}

	/*
	 * var inputElements =
	 * document.getElementsByName('koh-warranty-about-kohler'); var checkedValue =
	 * ""; for(var i=0; i < inputElements.length; i++){
	 * if(inputElements[i].checked){
	 *  checkedValue += inputElements[i].value+",";
	 *   } 
	 *   }
	 */

	var checkedValue = [];
	var checkList = document.getElementsByName('koh-warranty-about-kohler');
	for (var i = 0; i < checkList.length; i++) {
		if (checkList[i].checked && checkList[i].value) {
			checkedValue.push(checkList[i].value);
		}
	}
	var checkedItemListStr = checkedValue.join();

	var recaptchaResponse = grecaptcha.getResponse();
	var isValid = true;

	var inpFile = document.getElementById('koh-contact-upload-file');
	if (inpFile.files.length === 0) {
		isValid = false;
		showFileUploadError('.koh-contact-upload-note', uploadErrorMessage);
	}

	if (salutation == '' || salutation == null) {
		isValid = false;
		showError('#koh-warranty-salutation', salutationErrormessage);
	}

	if (whereToBuy == '' || whereToBuy == null) {
		isValid = false;
		showError('#koh-warranty-outlet', dealerErrorMessage);
	}

	if (email == '' || email == null) {
		isValid = false;
		showError('#koh-warranty-email', emailErrorMessage1Id);
	} else {
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		if (reg.test(email) == false) {
			isValid = false;
			showError('#koh-warranty-email', emailErrorMessage2Id);
		}
	}

	if (fullName == '' || fullName == null) {
		isValid = false;
		showError('#koh-warranty-full-name', nameErrorMessageId);
	}

	if (mobile == '') {
		isValid = false;
		showError('#koh-warranty-mobile', mobileErrorMessageId);
	}
	if (address == '') {
		isValid = false;
		showError('#koh-warranty-address', addressErrorMessageId);
	}

	if (purchaseDate == '') {
		isValid = false;
		showError('#koh-warranty-product-purchase', purchaseDateErrorMessage1)
	} else if (validatePurchaseDate('#koh-warranty-product-purchase') == false) {
		isValid = false;
		showError('#koh-warranty-product-purchase', purchaseDateErrorMessage2);
	}else if(!isValidDateFormat(purchaseDate)) {
		isValid = false;
		showError('#koh-warranty-product-purchase', invalidDateErrorMessage);
	}

	if ((document.getElementById('kohler-warranty-privacy-policy').checked == false)) {
		isValid = false;
		showError('#kohler-warranty-privacy-policy',
				"Please select privacy policy");
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
		var fdata = new FormData();
		fdata.append("koh-warranty-salutation", salutation);
		fdata.append("koh-warranty-mobile", mobile);
		fdata.append("koh-warranty-address", address);
		fdata.append("koh-warranty-full-name", fullName);
		fdata.append("koh-warranty-email", email);
		fdata.append("koh-warranty-product-delivery", productDeliveryDate);
		fdata.append("koh-warranty-product-purchase", purchaseDate);
		fdata.append("koh-warranty-outlet", whereToBuy);
		fdata.append("koh-warranty-choose", kohlerPurchase);
		fdata.append("koh-warranty-suggestions", advice);
		fdata.append("kohler-warranty-privacy-policy", privacyPolicy);
		fdata.append("kohler-contact-info", contactInfo);
		fdata.append("koh-warranty-about-kohler", checkedItemListStr);

		var attachedFile = document.getElementById('koh-contact-upload-file');
		if (attachedFile.files.length > 0) {
			fdata.append('file', attachedFile.files[0]);
		} else {
			fdata.append('file', "");
		}

		$.ajax({
			url : contactEmailLink,
			cache : false,
			contentType : false,
			processData : false,
			data : fdata,
			type : 'POST',
			beforeSend : function() {
				$('#koh-contact-form-btn').addClass('koh-form-btn-loader');
			},
			success : function(data) {
				if (data.success == 'true') {
					window.open(thankYouPageURL, "_self");
				}
			}
		})
	}
}

var showError = function(ele, errorMessage) {
	$(ele).parent().find('.koh-sfmc-error').remove();
	$(ele).after("<div class='koh-sfmc-error'>" + errorMessage + "</div>");
	$(ele).css({
		'border-color' : '#e7102e'
	})
	$(ele).closest('.form-group').css({
		'margin-bottom' : '6px'
	})
	disableSubmit(1);
}

var showFileUploadError = function(ele, errorMessage) {
	$(ele).parent().find('.koh-file-upload-error').remove();
	$(ele).after(
			"<div class='koh-file-upload-error'>" + errorMessage + "</div>");
	$(ele).css({
		'border-color' : '#e7102e'
	})
	$(ele).closest('.form-group').css({
		'margin-bottom' : '6px'
	})
}

var removeError = function(ele) {
	$(ele).parent().find('.koh-sfmc-error').remove();
	$(ele).css({
		'border-color' : '#cccccc'
	})
	$(ele).closest('.form-group').css({
		'margin-bottom' : '15px'
	})
	var sfmcErrorCount = $(document).find('.koh-sfmc-error').size();
	var uploadFileError = $(document).find('.koh-file-upload-error').size();
	var totalErrorCount = sfmcErrorCount + uploadFileError;
	disableSubmit(totalErrorCount);
}
var validateEmail = function(ele) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var currentVal = $(ele).val();
	if (reg.test(currentVal) == false) {
		return false;
	}
	return true;
}

/*var validateDeliveryDate = function(ele) {
	var inputDateVal = $('#koh-warranty-product-delivery').val();
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1;
	var yyyy = today.getFullYear();
	if (dd < 10) {
		dd = '0' + dd;
	}
	if (mm < 10) {
		mm = '0' + mm;
	}
	today = yyyy + '-' + mm + '-' + dd;
	if (inputDateVal > today) {
		return false;
	} else {
		return true;
	}
}
*/

var validatePurchaseDate = function(ele) {
	var inputDateVal = $('#koh-warranty-product-purchase').val();
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1;
	var yyyy = today.getFullYear();
	if (dd < 10) {
		dd = '0' + dd;
	}
	if (mm < 10) {
		mm = '0' + mm;
	}
	today = yyyy + '-' + mm + '-' + dd;
	if (inputDateVal > today) {
		return false;
	} else {
		return true;
	}
}

$('#koh-warranty-mobile').on('input', function(event) {
	var start = this.selectionStart - 1, end = this.selectionEnd - 1;
	if ($(this).val().match(/[^0-9-+()]/gi)) {
		$(this).val($(this).val().replace(/[^0-9-+()]/gi, ''));
		this.setSelectionRange(start, end);
	}
});

function checkOthersValue(val) {
	var element = document.getElementById('koh-others-checkbox');
	if (val == 'Others')
		element.style.display = 'block';
	else
		element.style.display = 'none';
}

$(function() {
	$('body').on('keydown', '#koh-warranty-salutation', function(e) {
		if (e.which === 32 && e.target.selectionStart === 0) {
			return false;
		}
	});
});

$(function() {
	$('body').on('keydown', '#koh-warranty-full-name', function(e) {
		if (e.which === 32 && e.target.selectionStart === 0) {
			return false;
		}
	});
});

$(function() {
	$('body').on('keydown', '#koh-warranty-address', function(e) {
		if (e.which === 32 && e.target.selectionStart === 0) {
			return false;
		}
	});
});

$(function() {
	$('body').on('keydown', '#koh-others-checkbox', function(e) {
		if (e.which === 32 && e.target.selectionStart === 0) {
			return false;
		}
	});
});

$(function() {
	$('body').on('keydown', '#koh-warranty-product-sku', function(e) {
		if (e.which === 32 && e.target.selectionStart === 0) {
			return false;
		}
	});
});

$(function() {
	$('body').on('keydown', '#koh-warranty-suggestions', function(e) {
		if (e.which === 32 && e.target.selectionStart === 0) {
			return false;
		}
	});
});

$(document)
		.on('keyup change', '#sgLuckyDrawForm input, #sgLuckyDrawForm select, #sgLuckyDrawForm textarea', function(e) {
					var currentIdName = $(this).attr('id');
					var currentId = '#' + currentIdName;

					if (currentIdName == 'koh-warranty-salutation') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, salutationErrormessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-warranty-email') {
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

					if (currentIdName == 'koh-warranty-outlet') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, dealerErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-warranty-full-name') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, nameErrorMessageId);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-warranty-mobile') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, mobileErrorMessageId);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}
					if (currentIdName == 'koh-warranty-address') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, addressErrorMessageId);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-warranty-product-purchase') {
						var currentVal = $(this).val();
						if (currentVal == "") {
							isValid = false;
							showError(currentId, purchaseDateErrorMessage1);
						} else if (!validatePurchaseDate(currentId)) {
							isValid = false;
							showError(currentId, purchaseDateErrorMessage2);
						}else if(!isValidDateFormat(currentVal)) {
							isValid = false;
							showError('#koh-warranty-product-purchase', invalidDateErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'koh-warranty-product-delivery') {
						var currentVal = $(this).val();
						if(!isValidDateFormat(currentVal)) {
							isValid = false;
							showError('#koh-warranty-product-delivery', invalidDateErrorMessage);
						} else {
							isValid = true;
							removeError(currentId);
						}
					}

					if (currentIdName == 'kohler-warranty-privacy-policy') {
						if ((document
								.getElementById('kohler-warranty-privacy-policy').checked == false)) {
							isValid = false;
							// showError('#kohler-warranty-privacy-policy',
							// 		purchaseDateErrorMessage1);
							showError('#kohler-warranty-privacy-policy', "Please select privacy policy");
						} else {
							isValid = true;
							removeError('#kohler-warranty-privacy-policy');
						}
					}
				})

function recaptchaCallback() {
	var captchaRes = grecaptcha.getResponse();
	if(captchaRes.length !== 0) {
		removeError(".captcha-container");
		$("#g-recaptcha").css("border", "none");
		$("#g-recaptcha").children('div:first-child').css("border", "none");
	}
};
