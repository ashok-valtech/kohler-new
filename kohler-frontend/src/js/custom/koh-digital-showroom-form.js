

var firstNameErrorMessageLink = $('#nameErrorMessageLink').val();
var emailErrorMessageLink = $('#emailErrorMessageLink').val();
var mobileErrorMessageLink = $('#mobileErrorMessageLink').val();
var emailErrorMessageLink2 = $('#emailErrorMessageLink2').val();
var mobileErrorMessageLink2 = $('#mobileErrorMessageLink2').val();
var messageTimingLink = $('#messageTimingLink').val();
var messageFadingTimingLink = $('#messageFadingTimingLink').val();
var privacyErrorMessage = $('#privacyErrorMessage').val();
var bathroomFixtureErrorMessage = $('#bathroomFixtureErrorMessage').val();
var interestedProductErrorMessage = $('#interestedProductErrorMessage').val();

var disableSubmit = function(errorCount) {
	if(errorCount > 0) {
		$("#hk-digital-from-btn").attr("disabled", true);
	}else {
		$("#hk-digital-from-btn").removeAttr("disabled")

	}
}

function onAjaxError(customMsg) {
	if(customMsg.length > 0) {
		$("#failureMessage").html(customMsg);
	}
    $('#hk-digital-from-btn').removeClass('koh-form-btn-loader');
    $('.koh-upload-wrapper').show();
    $('form').find("input[type=text], input[type=file], textarea, select").val("");
    $('#failureMessage').removeClass('hide').fadeIn('fast');
    setTimeout(function () {
        $('#failureMessage').fadeOut(messageFadingTimingLink, function () {
            $(this).addClass('hide');
        });
    }, messageTimingLink); // <-- time in milliseconds
}

function dateFormattingtoHKLocale(submissionDate) {
    var hongKongDateTime = submissionDate.toLocaleString('en-US', {
        timeZone: 'Asia/Hong_Kong',
        weekday: 'short',
        year: 'numeric',
        month: 'short',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    });

    var offsetHours = 8; // Hong Kong is GMT+0800
    var sign = offsetHours >= 0 ? '+' : '';
    var offsetString = String(Math.abs(offsetHours));
    if (offsetString.length < 2) {
        offsetString = '0' + offsetString;
    }
    var gmtString = 'GMT' + sign + offsetString + '00';
    var timeZoneName = '(Hong Kong Standard Time)';
    var dateString = hongKongDateTime+" "+gmtString+" "+timeZoneName;
    var finalDateString = dateString.replace(/,/g, '');
    return finalDateString;
}

$(document).ready(function () {
    $('.hk-digital-checkbox').first().prop('checked', true);

    $('#hkDigitalLeadForm').submit(function (e) {
        e.preventDefault();
        var contactEmailLink = $(this).attr('action');
        validateContactForm(contactEmailLink);
    })
});

function validateContactForm(contactEmailLink) {
    var name = $('#hk-digital-form-name').val();
    var emailAddress = $('#hk-digital-form-email-address').val();
    var mobileNumber = $('#hk-digital-form-mobile-number').val();
    var bathroomFixture = $('#koh-bathroom-fixtures').val();
    var interestProduct = $('#koh-interest-products').val();
    var thankyouPage = $('#hk-digital-ThankyouPage').val();
    var isValid = true;

    if(bathroomFixture == '' || bathroomFixture == null) {
        isValid = false;
        showError('#koh-bathroom-fixtures', bathroomFixtureErrorMessage);
    }
    if(interestProduct == '' || interestProduct == null) {
        isValid = false;
        showError('#koh-interest-products', interestedProductErrorMessage);
    }
    if (name == '' || name == null) {
        isValid = false;
        showError('#hk-digital-form-name', firstNameErrorMessageLink);
    }
    if (emailAddress == '' || emailAddress == null) {
        isValid = false;
        showError('#hk-digital-form-email-address', emailErrorMessageLink);
    }
    if (mobileNumber == '') {
        isValid = false;
        showError('#hk-digital-form-mobile-number', mobileErrorMessageLink);
    }
    if ((document.getElementById('kohler-warranty-privacy-policy').checked == false)) {
		isValid = false;
		showError('#hk-privacy-label', privacyErrorMessage);
	}
    if (isValid == true) {
        var curDate = new Date();
        var formattedDate = dateFormattingtoHKLocale(curDate);
        var fdata = new FormData();
        fdata.append("koh-bathroom-fixtures", bathroomFixture);
        fdata.append("koh-interest-products", interestProduct);
        fdata.append("hk-digital-form-name", name);
        fdata.append("hk-digital-form-email-address", emailAddress);
        fdata.append("hk-digital-form-mobile-number", mobileNumber);
        fdata.append("hk-digital-form-submission-date", formattedDate);

        $.ajax({
            url: contactEmailLink,
            cache: false,
            contentType: false,
            processData: false,
            data: fdata,
            type: 'POST',
            beforeSend: function () {
                $('#hk-digital-from-btn').addClass('koh-form-btn-loader');
            },
            success: function (data) {
            	 if (data.success == 'true') {
            		 window.open(thankyouPage, '_self');
            	 }else if(data.sfmcAPIError) {
                    onAjaxError(data.sfmcAPIError);
                } else {
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
}
var showError = function (ele, errorMessage) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).after("<div class='koh-error'>" + errorMessage + "</div>");
    $(ele).css({
        'border-color': '#e7102e'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '6px'
    })
    disableSubmit(1);
}
var removeError = function (ele) {
    $(ele).parent().find('.koh-error').remove();
    $(ele).css({
        'border-color': '#cccccc'
    })
    $(ele).closest('.form-group').css({
        'margin-bottom': '15px'
    })
    var sfmcErrorCount = $(document).find('.koh-error').size();
	disableSubmit(sfmcErrorCount);
}
var validateEmail = function (ele) {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var currentVal = $(ele).val();
    if (reg.test(currentVal) == false) {
        return false;
    }
    return true;
}
$('#hk-digital-form-mobile-number').on('input', function (event) {
    var start = this.selectionStart - 1,
        end = this.selectionEnd - 1;
    if ($(this).val().match(/[^0-9-+()]/gi)) {
        $(this).val($(this).val().replace(/[^0-9-+()]/gi, ''));
        this.setSelectionRange(start, end);
    }
});

$(document).on('keyup change', '#hkDigitalLeadForm input, #hkDigitalLeadForm select, #hkDigitalLeadForm textarea', function (e) {
    var currentIdName = $(this).attr('id');
    var currentId = '#' + currentIdName;
   
    if (currentIdName == 'hk-digital-form-name') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, firstNameErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'hk-digital-form-email-address') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, emailErrorMessageLink);
        } else if (!validateEmail(currentId)) {
            isValid = false;
            showError(currentId, emailErrorMessageLink2);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'hk-digital-form-mobile-number') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, mobileErrorMessageLink);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'kohler-warranty-privacy-policy') {
        if ((document
                .getElementById('kohler-warranty-privacy-policy').checked == false)) {
            isValid = false;
            showError('#hk-privacy-label', privacyErrorMessage);
        } else {
            isValid = true;
            removeError('#hk-privacy-label');
        }
    }

    if (currentIdName == 'koh-bathroom-fixtures') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, bathroomFixtureErrorMessage);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
    if (currentIdName == 'koh-interest-products') {
        var currentVal = $(this).val();
        if (currentVal == "") {
            isValid = false;
            showError(currentId, interestedProductErrorMessage);
        } else {
            isValid = true;
            removeError(currentId);
        }
    }
})

