<#include "../include/imports.ftl">
<@fmt.message key="warranty-contact-title" var="contactForm"/>
<#--  <@fmt.message key="warranty-thankyou-message" var="thankyouMessage"/>  -->
<#--  <@fmt.message key="warranty-unsubscribe-message" var="unsubscribeMessage"/>  -->
<@fmt.message key="warranty-table-heading" var="tableHeading"/>
<@fmt.message key="warranty-items-heading" var="warrentyItems"/>
<@fmt.message key="print" var="print"/>

<@fmt.message key="warranty-card-label" var="warrantyCard"/>
<@fmt.message key="warranty-serial-number-label" var="serialNumber"/>
<@fmt.message key="warranty-email-label" var="email"/>
<@fmt.message key="warranty-name-label" var="name"/>
<@fmt.message key="warranty-mobile-label" var="mobileNumber"/>
<@fmt.message key="warranty-address-label" var="address"/>
<@fmt.message key="warranty-deliveryDate-label" var="deliveryDate"/>
<@fmt.message key="warranty-countryCity-label" var="countryCity"/>
<@fmt.message key="warranty-buyingLocation-label" var="buyingLocation"/>
<@fmt.message key="warranty-itemNo-label" var="itemNo"/>
<@fmt.message key="warranty-itemNo-label" var="itemNo"/>

<input type="hidden" id="cardLabel" value="${warrantyCard}"/>
<input type="hidden" id="emailLabel" value="${email}"/>
<input type="hidden" id="fullNameLabel" value="${name}"/>
<input type="hidden" id="mobileLabel" value="${mobileNumber}"/>
<input type="hidden" id="addressLabel" value="${address}"/>
<input type="hidden" id="productDeliveryDateLable" value="${deliveryDate}"/>
<input type="hidden" id="cityNameLable" value="${countryCity}"/>
<input type="hidden" id="storeNameLabel" value="${buyingLocation}"/>
<input type="hidden" id="productNameLabel" value="${itemNo}"/>
<input type="hidden" id="serialNumberLabel" value="${serialNumber}"/>
<input type="hidden" id="kohlerPurchaseLabel" value="${purchased}"/>
<input type="hidden" id="checkboxLabel" value="${knowAbout}"/>
<input type="hidden" id="kohlerAdviceLabel" value="${advice}"/>


<section class="c-koh-warranty-registration-form">
    <div class="koh-contact-container">
        <div class="koh-contact-header">
            <span class="koh-contact-title">${contactForm}</span>
        </div>
     <#--  <div class="koh-contact-form-wrapper">
         <div class="form-group">
          <h3 class="text-center">${thankyouMessage}</h3>
          <em><h5 class="text-center">${unsubscribeMessage}</h5></em>
         </div>
     </div>  -->
     <div class="koh-warranty-form-details">
        <span class="table-header">${tableHeading}</span>
        <div class="user-details-section"></div>
        <div class="product-warranty-details">
            <span class="warranty-heading">${warrentyItems}</span>
        </div>
        <div class="warranty-print-section">
            <button onclick="javascript:window.print()" class="koh-product-button" data-hasqtip="2">
            <span class="icon" data-icon="&#xe620;"></span>
            <span class="label">${print}</span>
            </button>
        </div>
     </div>
    </div>
</section>


<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-warranty-registration-form.min.js"></script>
</@hst.headContribution>