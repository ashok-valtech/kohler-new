
var emailErrorMessage1Id = $('#emailErrorMessage1Id').val();
var emailErrorMessage2Id = $('#emailErrorMessage2Id').val();
var nameErrorMessageId = $('#nameErrorMessageId').val();
var mobileErrorMessageId = $('#mobileErrorMessageId').val();
var addressErrorMessageId = $('#addressErrorMessageId').val();
var deliveryErrorMessage1Id = $('#deliveryErrorMessage1Id').val();
var deliveryErrorMessage2Id = $('#deliveryErrorMessage2Id').val();
var cityErrorMessageId = $('#cityErrorMessageId').val();
var dealerErrorMessageId = $('#dealerErrorMessageId').val();
var itemNoErrorMessageId = $('#itemNoErrorMessageId').val();
var purchaseErrorMessageId = $('#purchaseErrorMessageId').val();
// var emailNotificationErrorMessageId = $('#emailNotificationErrorMessageId').val();
var captchaErrorMessageId = $('#captchaErrorMessageId').val();
var invalidDateErrorMessage = $('#invalidDateErrorMessage').val();
var validFileErrorMessage = $('#validFileErrorMessageId').val();
var fileSizeErrorMessage = $('#fileSizeErrorMessageId').val();
var uploadErrorMessage = $('#uploadErrorMessageId').val();
var thankYouPageURL = $('#koh-warranty-thankyouPage').val();
var cardErrorMessage = $('#cardErrorMessageId').val();
var serialNumberErrorMessage = $('#serialNumberErrorMessageId').val();
var messageFadingTimingLink = $('#messageFadingTimingLink').val();
var messageTimingLink = $('#messageTimingLink').val();
var fileLengthErrorMessage = $('#fileLengthErrorMessageId').val();


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
		}else if(fileName.length > 84) {
			$(".koh-contact-upload-note").next('.koh-file-upload-error')
					.remove();
			$('#koh-contact-upload-file-name').hide();
			$('.koh-upload-wrapper').show();
			$(".koh-contact-upload-note").after(
					"<div class='koh-file-upload-error'>"
							+ fileLengthErrorMessage + "</div>");
			$('.koh-upload-wrapper').css('border',
					'1px solid rgb(231, 16, 46)');
			disableSubmit(1);
		}else {
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

var formdata = {};

var formatDateFunc = function(date) {
  var parts = date.split("-");
  var year = parts[0];
  var month = parts[1];
  var day = parts[2];
  
	return month+"/"+day+"/"+year;
}

//Form labels
var cardLabel = $("#cardLabel").val();
var emailLabel = $("#emailLabel").val();
var fullNameLabel  = $("#fullNameLabel").val();
var mobileLabel = $("#mobileLabel").val();
var addressLabel = $("#addressLabel").val();
var productDeliveryDateLable = $("#productDeliveryDateLable").val();
var cityNameLable = $("#cityNameLable").val();
var storeNameLabel = $("#storeNameLabel").val();
var productNameLabel = $("#productNameLabel").val();
var serialNumberLabel = $("#serialNumberLabel").val();
var kohlerPurchaseLabel = $("#kohlerPurchaseLabel").val();
var checkboxLabel = $("#checkboxLabel").val();
var adviceLabel = $("#kohlerAdviceLabel").val();
var fileLabel = $('#fileLabel').val();

var previewFormPopUp = function () {
	var $submitBtn = $(document).find("#koh-contact-form-btn");
	var previewPopUpTargetID = $submitBtn.data('remodal-id');
	var parentPopDiv = $(document).find("#koh-preview-from-remodal");
	parentPopDiv.data("remodal-id", previewPopUpTargetID);
	var inst = $(parentPopDiv).remodal({
		hashTracking: false,
		closeOnCancel: false,
		closeOnEscape: false,
		closeOnOutsideClick: false,
		modifier: ""
	});

	inst.open();

	//Form data
	var formTitle = $('#previewTitle').val();
	var card = $('#koh-warranty-card-number').val();
	var email = $('#koh-warranty-email').val();
	var fullName = $('#koh-warranty-full-name').val();
	var mobile = $('#koh-warranty-mobile').val();
	var address = $('#koh-warranty-address').val();
	var productDeliveryDate = $('#koh-warranty-product-delivery').val();
	var cityName = $('#koh-warranty-purchased').val();
	var storeName = $('#koh-warranty-Store').val();
	var productName = $('#koh-warranty-product-name').val();
	var serialNumber = $('#koh-warranty-product-serial-number').val();
	var kohlerPurchase = $('#koh-warranty-choose').val();
	var adviceToKohler = $("#koh-warranty-suggestions").val();
	var fileName = $("#koh-contact-upload-file").val().replace(/.*(\/|\\)/, '');

	var checkboxValuesArray = [];
	var checkboxValues = '';
	//get checkbox value
	$('.checkbox-about-kohler > input[type=checkbox]:checked').map(function(_,e) {
		checkboxValuesArray.push($(e).val());
	})

	if(checkboxValuesArray[checkboxValuesArray.length - 1] === 'Other') {
		checkboxValuesArray.pop();
		var otherText = $('#koh-warranty-others-checkbox').val();
		checkboxValuesArray.push(otherText);
	}
	
	checkboxValues = checkboxValuesArray.join(',');

	var editBtn = $('#editButtonText').val();
	var confirmBtn = $('#confirmButtonText').val();

	var formadattedDate = formatDateFunc(productDeliveryDate);

	//form object to store data in local storage
	formdata = {
		"cardNumber": card,
		"email": email,
		"fullName": fullName,
		"mobile": mobile,
		"address": address,
		"deliveryDate": formadattedDate,
		"city": cityName,
		"storeName": storeName,
		"productName": productName,
		"serialNumber": serialNumber,
		"kohlerPurchase": kohlerPurchase,
		"checkboxValues": checkboxValues,
		"advice": adviceToKohler,
		"cardLabel": cardLabel,
		"emailLabel": emailLabel,
		"nameLabel": fullNameLabel,
		"mobileLabel": mobileLabel,
		"addressLabel": addressLabel,
		"deliveryDateLabel": productDeliveryDateLable,
		"cityNameLabel": cityNameLable,
		"storeNameLabel": storeNameLabel,
		"productNameLabel": productNameLabel,
		"serialNumberLabel": serialNumberLabel,
		"kohlerPurchaseLabel": kohlerPurchaseLabel,
		"checkboxLabel": checkboxLabel,
		"adviceLabel": adviceLabel
	};

	if(localStorage.getItem('warranty-form-details') !== null) {
		localStorage.removeItem('warranty-form-details');
	}
	localStorage.setItem("warranty-form-details", JSON.stringify(formdata));

	var popupBody = '';

	popupBody += '<h4 class="modal-title">'+ formTitle +'</h4>';
	popupBody += '<hr>';
	popupBody += '<ul class="preview-details">';
	popupBody += '<li><span class="preview-label">'+cardLabel+': </span><span class="preview-data">'+card+'</span></li>';
	popupBody += '<li><span class="preview-label">'+emailLabel+': </span><span class="preview-data">'+email+'</span></li>';
	popupBody += '<li><span class="preview-label">'+fullNameLabel+': </span><span class="preview-data">'+fullName+'</span></li>';
	popupBody += '<li><span class="preview-label">'+mobileLabel+': </span><span class="preview-data">'+mobile+'</span></li>';
	popupBody += '<li><span class="preview-label">'+cityNameLable+': </span><span class="preview-data">'+cityName+'</span></li>';
	popupBody += '<li><span class="preview-label">'+addressLabel+': </span><span class="preview-data">'+address+'</span></li>';
	popupBody += '<li><span class="preview-label">'+productDeliveryDateLable+': </span><span class="preview-data">'+formadattedDate+'</span></li>';
	popupBody += '<li><span class="preview-label">'+storeNameLabel+': </span><span class="preview-data">'+storeName+'</span></li>';
	popupBody += '<li><span class="preview-label">'+productNameLabel+': </span><span class="preview-data">'+productName+'</span></li>';
	popupBody += '<li><span class="preview-label">'+serialNumberLabel+': </span><span class="preview-data">'+serialNumber+'</span></li>';
	popupBody += '<li><span class="preview-label">'+kohlerPurchaseLabel+': </span><span class="preview-data">'+kohlerPurchase+'</span></li>';
	if(checkboxValues.length > 0) {
		popupBody += '<li><span class="preview-label">'+checkboxLabel+': </span><span class="preview-data">'+checkboxValues+'</span></li>';
	}

	if(adviceToKohler.length > 0) {
		popupBody += '<li><span class="preview-label">'+adviceLabel+': </span><span class="preview-data">'+adviceToKohler+'</span></li>';
	}
	popupBody += '<li><span class="preview-label">'+fileLabel+': </span><span class="preview-data">'+fileName+'</span></li>';
	popupBody += '</ul>';
	popupBody += '<div class="row"><button id="koh-preview-edit-btn" type="button" class="col-xs-5 col-sm-5 col-md-4 col-md-offset-2 col-xs-offset-1 btn btn-default koh-primary-btn" data-remodal-action="close">' + editBtn + '</button><button id="confirm-btn" type="submit" class="col-xs-5 col-sm-5 col-md-4 btn btn-default koh-primary-btn" data-remodal-action="close">' + confirmBtn + '</button></div></div>';
	$(parentPopDiv).html(popupBody);
}

function onAjaxError(customMsg) {
	if(customMsg.length > 0) {
		$("#failureMessage").html(customMsg);
	}
	grecaptcha.reset();
	$('#koh-contact-form-btn').removeClass('koh-form-btn-loader');
	$('#koh-contact-upload-file-name').hide();
	$('.koh-upload-wrapper').show();
	$("html, body").animate({ scrollTop: 500 }, "fast");
	$('form').find("input[type=text], input[type=file], textarea, select").val("");
	
	$('#failureMessage').removeClass('hide').fadeIn('fast');
	setTimeout(function () {
		$('#failureMessage').fadeOut(messageFadingTimingLink, function () {
			$(this).addClass('hide');
		});
	}, messageTimingLink); // <-- time in milliseconds
}

function handleFormData(contactEmailLink, formData, privacyCheckbox, contactInfoCheckbox) {
		var fdata = new FormData();
		fdata.append("koh-warranty-card-number", formData.cardNumber);
		fdata.append("koh-warranty-mobile", formData.mobile);
		fdata.append("koh-warranty-address", formData.address);
		fdata.append("koh-warranty-full-name", formData.fullName);
		fdata.append("koh-warranty-email", formData.email);
		fdata.append("koh-warranty-product-delivery", formData.deliveryDate);
		fdata.append("koh-warranty-purchased", formData.city);
		fdata.append("koh-warranty-Store", formData.storeName);
		fdata.append("koh-warranty-product-name", formData.productName);
		fdata.append("koh-warranty-choose", formData.kohlerPurchase);
		fdata.append("koh-warranty-about-kohler", formData.checkboxValues);
		fdata.append("koh-warranty-suggestions", formData.advice);
		fdata.append("kohler-warranty-privacy-policy", privacyCheckbox);
		fdata.append("kohler-contact-info", contactInfoCheckbox);
		fdata.append("koh-warranty-product-serial-number",formData.serialNumber);


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
				}else if(data.sfmcAPIError) {
                    onAjaxError(data.sfmcAPIError);
                }else if(data.fileExtError) {
					onAjaxError(data.fileExtError);
				}else {
					var errorMessage = $("#formErrorMessage").val();
					onAjaxError(errorMessage);
				}
			},
			error: function(error) {
				var errorMessage = $("#formErrorMessage").val();
				onAjaxError(errorMessage);
			}
		})
}

function createThankyouTable() {
	var userDetailDiv = $(document).find(".user-details-section").first();
	var productWarrantyDiv = $(document).find(".product-warranty-details").first();

	var userDetailContent = '';
	var productDetailContent = '';

	var formDetails = JSON.parse(localStorage.getItem('warranty-form-details'));

	//table user details section elements
	userDetailContent += '<div class="user-profile">';
	userDetailContent += '<ul>';
	userDetailContent += '<li><span class="label">'+fullNameLabel+': </span><span class="value">'+formDetails.fullName+'</span></li>';
	userDetailContent += '<li><span class="label">'+emailLabel+': </span><span class="value">'+formDetails.email+'</span></li>';
	userDetailContent += '<li><span class="label">'+mobileLabel+': </span><span class="value">'+formDetails.mobile+'</span></li>';
	userDetailContent += '</ul>';
	userDetailContent += '<ul>';
	userDetailContent += '<li><span class="label address">'+addressLabel+'</span><span class="value">'+formDetails.address+'</span></li>';
	userDetailContent += '<li><span class="label">'+cityNameLable+': </span><span class="value">'+formDetails.city+'</span></li>';
	userDetailContent += '<li><span class="label">'+storeNameLabel+': </span><span class="value">'+formDetails.storeName+'</span></li>';
	userDetailContent += '</ul>';
	userDetailContent += '</div>';
	userDetailContent += '<hr>';

	//table product details section
	productDetailContent += '<ul>';
	productDetailContent += '<li><span class="label">'+productNameLabel+'</span><span class="value">'+formDetails.productName+'</span></li>';
	productDetailContent += '<li><span class="label">'+serialNumberLabel+'</span><span class="value">'+formDetails.serialNumber+'</span></li>';
	productDetailContent += '<li><span class="label">'+cardLabel+'</span><span class="value">'+formDetails.cardNumber+'</span></li>';
	productDetailContent += '<li><span class="label">'+productDeliveryDateLable+'</span><span class="value">'+formDetails.deliveryDate+'</span></li>';
	productDetailContent += '</ul>';
	productDetailContent += '<hr>';

	$(userDetailDiv).append(userDetailContent);
	$(productWarrantyDiv).append(productDetailContent);
}

$(document).ready(function() {
	if(thankYouPageURL !== undefined && localStorage.getItem("thankyouPageURL") !== thankYouPageURL) {
		localStorage.setItem("thankyouPageURL", thankYouPageURL);
	}
	$(document).on('click', "#koh-contact-form-btn", function(e) {
		var valid = validateWarrantyForm();
		e.preventDefault();
		if (valid){
			previewFormPopUp();
			$("#confirm-btn").off('click').on('click', function(e) {
				e.preventDefault();
				$("#hidden-btn").trigger("click");
			});
		}
	});

	var privacyCheckboxValue = $('#kohler-warranty-privacy-policy').val();
	var contactCheckboxValue = $('#kohler-contact-info').val();

	$('#twWarrantyRegistratationForm').on(
		'submit',
		function(e) {
			e.preventDefault();
			contactEmailLink = $(this).attr('action');
			handleFormData(contactEmailLink, formdata, privacyCheckboxValue, contactCheckboxValue);
		})
	var thankyouPageURLocal = localStorage.getItem("thankyouPageURL");
	if(window.location.pathname.includes(thankyouPageURLocal) || thankyouPageURLocal.includes(window.location.pathname)) {
		createThankyouTable();
	}
});



function validateWarrantyForm() {
	var card = $('#koh-warranty-card-number').val();
	var email = $('#koh-warranty-email').val();
	var fullName = $('#koh-warranty-full-name').val();
	//var dob = $('#koh-warranty-Dob').val();
	var mobile = $('#koh-warranty-mobile').val();
	var address = $('#koh-warranty-address').val();
	var productDeliveryDate = $('#koh-warranty-product-delivery').val();
	var cityName = $('#koh-warranty-purchased').val();
	var storeName = $('#koh-warranty-Store').val();
	var productName = $('#koh-warranty-product-name').val();
	var serialNumber = $('#koh-warranty-product-serial-number').val();
	var kohlerPurchase = $('#koh-warranty-choose').val();
	var recaptchaResponse = grecaptcha.getResponse();
	var isValid = true;

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

	if (card == '' || card == null) {
		isValid = false;
		showError('#koh-warranty-card-number', cardErrorMessage);
	}
	

	if (fullName == '' || fullName == null) {
		isValid = false;
		showError('#koh-warranty-full-name', nameErrorMessageId);
	}
	
	/*if (dob == '') {
		isValid = false;
		showError('#koh-warranty-Dob', "enter your Dob");
	}else if (validateDOB('#koh-warranty-Dob') == false) {
		isValid = false;
		showError('#koh-warranty-Dob', 'Eligibility 18 years ONLY')
	}*/
	
	if (mobile == '') {
		isValid = false;
		showError('#koh-warranty-mobile', mobileErrorMessageId);
	}
	if (address == '') {
		isValid = false;
		showError('#koh-warranty-address', addressErrorMessageId);
	}
	
	if(productDeliveryDate == ''){
		isValid = false;
		showError('#koh-warranty-product-delivery', deliveryErrorMessage1Id)
	}else if(validateDeliveryDate('#koh-warranty-product-delivery') == false){
		isValid = false;
		showError('#koh-warranty-product-delivery', deliveryErrorMessage2Id);
	}else if(!isValidDateFormat(productDeliveryDate)) {
		isValid = false;
		showError('#koh-warranty-product-delivery', invalidDateErrorMessage);
		
	}
	
	if (cityName == '') {
		isValid = false;
		showError('#koh-warranty-purchased', cityErrorMessageId);
	}
	if (storeName == '') {
		isValid = false;
		showError('#koh-warranty-Store', dealerErrorMessageId);
	}
	if (productName == '') {
		isValid = false;
		showError('#koh-warranty-product-name', itemNoErrorMessageId);
	}

	if (serialNumber == '') {
		isValid = false;
		showError('#koh-warranty-product-serial-number', serialNumberErrorMessage);
	}

	if (kohlerPurchase == '') {
		isValid = false;
		showError('#koh-warranty-choose', purchaseErrorMessageId);
	}

	var inpFile = document.getElementById('koh-contact-upload-file');
	if (inpFile.files.length === 0) {
		isValid = false;
		showFileUploadError('.koh-contact-upload-note', uploadErrorMessage);
	}
	
	/*if((document.getElementById('koh-warranty-agree').checked == false) && (document.getElementById('koh-warranty-disagree').checked == false)){
		isValid = false;
		showError('#koh-radiobutton-email', emailNotificationErrorMessageId);
	}*/
	
	if((document.getElementById('kohler-warranty-privacy-policy').checked == false)){
		isValid = false;
		showError('#kohler-warranty-privacy-policy', "Please select privacy policy");
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
		return true;
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
var removeError = function(ele) {
	$(ele).parent().find('.koh-sfmc-error').remove();
	$(ele).css({
		'border-color' : '#cccccc'
	})
	$(ele).closest('.form-group').css({
		'margin-bottom' : '15px'
	})
	var sfmcErrorCount = $(document).find('.koh-sfmc-error').size();
	disableSubmit(sfmcErrorCount)
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

var validateEmail = function(ele) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var currentVal = $(ele).val();
	if (reg.test(currentVal) == false) {
		return false;
	}
	return true;
}

/*var validateDOB = function(ele){
	var inputDate = new Date($("#koh-warranty-Dob").val());
    var Cnow = new Date();  
    if ($("#koh-warranty-Dob").val() == "")   
    {  
        $("#koh-warranty-Dob").focus();  
    }   
    else if (Cnow.getFullYear() - inputDate.getFullYear() < 18)   
    {  
        //$("#koh-warranty-Dob").val('');
    	return false;
    }  
    else {
    	return true;
    }  
}*/

var validateDeliveryDate = function(ele) {
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
	today = yyyy+'-'+mm+'-'+dd;
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
  $('body').on('keydown', '#koh-warranty-purchased', function(e) {
    if (e.which === 32 && e.target.selectionStart === 0) {
      return false;
    }  
  });
});

$(function() {
  $('body').on('keydown', '#koh-warranty-Store', function(e) {
    if (e.which === 32 && e.target.selectionStart === 0) {
      return false;
    }  
  });
});

$(function() {
  $('body').on('keydown', '#koh-warranty-product-name', function(e) {
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

$(function() {
	$('body').on('keydown', '#koh-warranty-card-number', function(e) {
	  if (e.which === 32 && e.target.selectionStart === 0) {
		return false;
	  }  
	});
});

$(function() {
	$('body').on('keydown', '#koh-warranty-product-serial-number', function(e) {
	  if (e.which === 32 && e.target.selectionStart === 0) {
		return false;
	  }  
	});
});


$(document).on('keyup change','#twWarrantyRegistratationForm input, #twWarrantyRegistratationForm select, #twWarrantyRegistratationForm textarea', function(e) {
	var currentIdName = $(this).attr('id');
	var currentId = '#' + currentIdName;
	
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
	
	/*if (currentIdName == 'koh-warranty-Dob') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, "enter your Dob");
		} else if (!validateDOB(currentId)) {
			isValid = false;
			showError(currentId, "Eligibility 18 years ONLY.");
		} else {
			isValid = true;
			removeError(currentId);
		}
	}*/
	
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
	
	if (currentIdName == 'koh-warranty-product-delivery') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, deliveryErrorMessage1Id);
		} else if (!validateDeliveryDate(currentId)) {
			isValid = false;
			showError(currentId, deliveryErrorMessage2Id);
		}else if(!isValidDateFormat(currentVal)) {
			isValid = false;
			showError('#koh-warranty-product-delivery', invalidDateErrorMessage);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}
	
	if (currentIdName == 'koh-warranty-purchased') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, cityErrorMessageId);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}
	
	if (currentIdName == 'koh-warranty-Store') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, dealerErrorMessageId);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}
	
	if (currentIdName == 'koh-warranty-product-name') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, itemNoErrorMessageId);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}
	
	if (currentIdName == 'koh-warranty-choose') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, purchaseErrorMessageId);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}

	if (currentIdName == 'koh-warranty-card-number') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, cardErrorMessage);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}

	if (currentIdName == 'koh-warranty-product-serial-number') {
		var currentVal = $(this).val();
		if (currentVal == "") {
			isValid = false;
			showError(currentId, serialNumberErrorMessage);
		} else {
			isValid = true;
			removeError(currentId);
		}
	}
	
	/*if(currentIdName == 'koh-warranty-agree' || currentIdName == 'koh-warranty-disagree'){
		if((document.getElementById('koh-warranty-agree').checked == false) && (document.getElementById('koh-warranty-disagree').checked == false)){
			isValid = false;
			showError('#koh-radiobutton-email', emailNotificationErrorMessageId);
		}else{
			isValid = true;
			removeError('#koh-radiobutton-email');
		}
	}*/
	
	if(currentIdName == 'kohler-warranty-privacy-policy'){
		if((document.getElementById('kohler-warranty-privacy-policy').checked == false)){
			isValid = false;
			showError('#kohler-warranty-privacy-policy', "Please select privacy policy");
		}else{
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

