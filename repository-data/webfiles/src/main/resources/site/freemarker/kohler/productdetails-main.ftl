<#include "../include/imports.ftl">
<@hst.link var="storelink" siteMapItemRefId="storelocator"/>
<@hst.link var="addToCompareLink" siteMapItemRefId="addToCompare"/>
<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
<@hst.link var="contactuslink" siteMapItemRefId="contact-us"/>
<@hst.link var="shareWithEmailLink" siteMapItemRefId="shareEmailLink"/>
<@hst.link var="shareContactFormLink" siteMapItemRefId="shareContactForm"/>
<@fmt.message key="contactUs-enquiry-withUs" var="contactUsEnquiryWithUs"/>
<input type="hidden" id="productDetailslink" value="${productDetailslink}" />
<input type="hidden" id="addToCompareLink" value="${addToCompareLink}" />
<input class="fac-current-category" type="hidden" data-product-categoryname="<#if breadcrumbSubCat??>${breadcrumbSubCat?html}</#if>" />
<input type="hidden" autocomplete="off" id="defaultCategoryKey" value="<#if breadcrumbSubCat??>${breadcrumbSubCat?html}</#if>" />
<input type="hidden" id="shareOnEmailLink" value="${shareWithEmailLink}" />

<@fmt.message key="compare" var="compare"/>
<@fmt.message key="dimensions-measurements" var="dimensionsMeasurements"/>
<@fmt.message key="store-locator" var="storeLocator"/>
<@fmt.message key="share" var="share"/>
<@fmt.message key="facebook" var="facebook"/>
<@fmt.message key="twitter" var="twitter"/>
<@fmt.message key="print" var="print"/>
<@fmt.message key="material" var="material"/>
<@fmt.message key="features" var="features"/>
<@fmt.message key="technology" var="technology"/>
<@fmt.message key="installation" var="installation"/>
<@fmt.message key="rebates" var="rebates"/>
<@fmt.message key="hydrotherapy" var="hydrotherapy"/>
<@fmt.message key="resources" var="resources"/>
<@fmt.message key="cad-templates" var="cadTemplates"/>
<@fmt.message key="2-cad-file" var="twocadfile"/>
<@fmt.message key="3-cad-file" var="threeDCadfile"/>
<@fmt.message key="cutout-templates" var="cutoutTemplates"/>
<@fmt.message key="dxf" var="dxf"/>
<@fmt.message key="dwg" var="dwg"/>
<@fmt.message key="side" var="side"/>
<@fmt.message key="overall" var="overall"/>
<@fmt.message key="videos" var="videos"/>
<@fmt.message key="callus" var="callUs"/>
<@fmt.message key="emailus" var="emailus"/>
<@fmt.message key="care-cleaning" var="careCleaning"/>
<@fmt.message key="warranty-information" var="warrantyInformation"/>
<@fmt.message key="technical-information" var="technicalInfo"/>
<@fmt.message key="dimensions" var="dimensions"/>
<@fmt.message key="please-note-product" var="pleaseNoteProduct"/>
<@fmt.message key="pairs-well" var="pairsWell"/>
<@fmt.message key="plan" var="plan"/>
<@fmt.message key="front" var="front"/>
<@fmt.message key="side" var="side"/>
<@fmt.message key="service-support" var="serviceSupport"/> 
<@fmt.message key="have-quest-product" var="haveQuestProduct"/>
<@fmt.message key="collection" var="collection"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="productDetailsMetaDesc" var="productDetailsMetaDesc"/>
<@fmt.message key="dimentionMeasurement" var="dimentionandmeasurement"/> 
<@fmt.message key="facebookProductVideoURL" var="facebookProductVideoURL"/>
<@fmt.message key="facebookProductDescVideoURL" var="facebookProductDescVideoURL"/>
<@fmt.message key="facebookProductVideoThumbURL" var="facebookProductVideoThumbURL"/>
<@fmt.message key="contacttodistributor" var="contacttodistributor"/>
<@fmt.message key="line-art-url" var="lineArtURL"/>
<@fmt.message key="ProductDetailColor" var="productDetailColor"/>

<@fmt.message key="initialViewUrl" var="initialViewUrl"/>
<@fmt.message key="common_initialViewUrl" var="common_initialViewUrl"/>
<@fmt.message key="zoomViewUrl" var="zoomViewUrl"/>
<@fmt.message key="common_zoomViewUrl" var="common_zoomViewUrl"/>
<@fmt.message key="PDPzoomNewProductViewUrl" var="PDPzoomNewProductViewUrl"/>
<@fmt.message key="common_PDPzoomNewProductViewUrl" var="common_PDPzoomNewProductViewUrl"/>
<@fmt.message key="expViewWithZoomUrl" var="expViewWithZoomUrl"/>
<@fmt.message key="common_expViewWithZoomUrl" var="common_expViewWithZoomUrl"/>
<@fmt.message key="expViewWithoutZoomUrl" var="expViewWithoutZoomUrl"/>
<@fmt.message key="common_expViewWithoutZoomUrl" var="common_expViewWithoutZoomUrl"/>
<@fmt.message key="galleryURLWithFlag" var="galleryURLWithFlag"/>
<@fmt.message key="common_galleryURLWithFlag" var="common_galleryURLWithFlag"/>
<@fmt.message key="galleryUrl" var="galleryUrl"/>
<@fmt.message key="common_galleryUrl" var="common_galleryUrl"/>
<@fmt.message key="galleryNewProductUrl" var="gridImageNewProductUrl"/>
<@fmt.message key="common_galleryNewProductUrl" var="common_gridImageNewProductUrl"/>
<@fmt.message key="PDPgalleryNewProductUrl" var="PDPgalleryNewProductUrl"/>
<@fmt.message key="common_PDPgalleryNewProductUrl" var="common_PDPgalleryNewProductUrl"/>
<@fmt.message key="shareURL" var="shareURL"/>
<@fmt.message key="common_shareURL" var="common_shareURL"/>
<@fmt.message key="quickViewUrl" var="quickViewUrl"/>
<@fmt.message key="imgswatch" var="imgswatch"/>
<@fmt.message key="specPdfUrls" var="pdfUrl"/>
<@fmt.message key="productDetailMetaDescription" var="metaDesc"/>
<@fmt.message key="cadUrls" var="cadUrl"/>
<@fmt.message key="revit" var="revit"/>
<@fmt.message key="tds" var="tds"/>
<@fmt.message key="obj" var="obj"/>
<@fmt.message key="sketchup" var="sketchup"/>
<@fmt.message key="dimensionunit" var="dimensionsdetail"/>
<@fmt.message key="compare-products" var="comparePanelProducts"/>
<@fmt.message key="clear-results" var="compareClearResults"/>
<@fmt.message key="categories" var="categories"/>
<@fmt.message key="home" var="home"/>
<@fmt.message key="back-to-results" var="backToResults"/>
<@fmt.message key="sent_email" var="sentEmail"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSecretKey"/>
<@fmt.message key="privacy_policy_pdp_share_email" var="privacyPolicyShareEmailURL"/>
<@fmt.message key="copy-right" var="copyRight"/>
<#assign aDateTime = .now>
<#assign copyRight = copyRight?replace("{0}","${aDateTime?string.yyyy}") />
<@fmt.message key="pdp_email_template_privacy_policy" var="emailTemplatePrivacyPolicy"/>
<@fmt.message key="pdp_email_popup_help_text1" var="emailPopupHelpText1"/>
<@fmt.message key="pdp_email_popup_help_text2" var="emailPopupHelpText2"/>
<@fmt.message key="pdp_email_popup_help_text3" var="emailPopupHelpText3"/> 
<@fmt.message key="pdp_email_popup_share_title" var="emailPopupShareTitle"/>
<@fmt.message key="pdp_email_popup_input_box_title1" var="emailPopupInputBoxTitle1"/>
<@fmt.message key="pdp_email_popup_input_box_title2" var="emailPopupInputBoxTitle2"/>
<@fmt.message key="pdp_email_popup_input_box_title3" var="emailPopupInputBoxTitle3"/>
<@fmt.message key="pdp_email_popup_input_box_title4" var="emailPopupInputBoxTitle4"/>
<@fmt.message key="pdp_email_popup_send_button_text" var="emailPopupSendButtonText"/>
<@fmt.message key="pdp_email_popup_cancel_button_text" var="emailPopupCancelButtonText"/>
<@fmt.message key="pdp_email_popup_success_title" var="emailPopupSuccessTitle"/>
<@fmt.message key="pdp_email_popup_success_message" var="emailPopupSuccessMessage"/>
<@fmt.message key="pdp_email_popup_failure_message" var="emailPopupFailureMessage"/>
<@fmt.message key="pdp_email_popup_name_validation_message" var="emailPopupNameValidationMessage"/>
<@fmt.message key="pdp_email_popup_email_validation_message1" var="emailPopupEamilValidationMessage1"/>
<@fmt.message key="pdp_email_popup_email_validation_message2" var="emailPopupEamilValidationMessage2"/>
<@fmt.message key="pdp_email_popup_recipients_email_validation_message1" var="emailPopupRecipientsEamilValidationMessage1"/>
<@fmt.message key="pdp_email_popup_recipients_email_validation_message2" var="emailPopupRecipientsEamilValidationMessage2"/>
<@fmt.message key="pdp_email_popup_captcha_validation_message" var="emailPopupCaptchaValidationMessage"/>
<@fmt.message key="pdp_email_popup_place_holder" var="emailPopupPlaceHolderMessage"/>
<@fmt.message key="isSchemaPresent" var="isSchemaPresent"/>

<@fmt.message key="SiteUrl_SKU_APAC" var="SiteUrl_SKU_APAC"/>
<@fmt.message key="SiteUrl_SKU_HK" var="SKU_HK_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_ID" var="SKU_ID_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_KR" var="SKU_KR_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_MY" var="SKU_MY_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_PH" var="SKU_PH_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_SG" var="SKU_SG_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_TH" var="SKU_TH_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_TW" var="SKU_TW_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_VN" var="SKU_VN_SiteUrl"/>

<@fmt.message key="SiteUrl_SKU_NZ" var="SKU_NZ_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_MO" var="SKU_MO_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_MN" var="SKU_MN_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_MM" var="SKU_MM_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_LA" var="SKU_LA_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_JP" var="SKU_JP_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_CN" var="SKU_CN_SiteUrl"/>
<@fmt.message key="SiteUrl_SKU_AU" var="SKU_AU_SiteUrl"/>

<@fmt.message key="discontinuedImageMessage" var="discontinuedImageMessage"/>
<@fmt.message key="product-details-buy-now" var="buyNow"/>
<@fmt.message key="featured-retailers" var="featuredRetailers"/>
<@fmt.message key="retailerImgUrl" var="retailerImgUrl"/>
<@fmt.message key="apac-GA" var="apacGA" />
<@fmt.message key="buynow-Tracking" var="buynowTracking" />
<@fmt.message key="common_galleryUrl" var="common_gridImageUrl"/>
<@fmt.message key="product-currency-code" var="currencyCode"/>
<@fmt.message key="product-price-vat" var="productVatValue"/>
<@fmt.message key="product-price-multiply-value" var="productMultiplicationValue"/>

<#assign productVat = productVatValue?number/>
<#assign productMultiplication = productMultiplicationValue?number/>

<input type="hidden" id="SKU_APAC_SiteUrl" value="${SiteUrl_SKU_APAC}" />
<input type="hidden" id="SKU_HK_SiteUrl" value="${SKU_HK_SiteUrl}" />
<input type="hidden" id="SKU_ID_SiteUrl" value="${SKU_ID_SiteUrl}" />
<input type="hidden" id="SKU_KR_SiteUrl" value="${SKU_KR_SiteUrl}" />
<input type="hidden" id="SKU_MY_SiteUrl" value="${SKU_MY_SiteUrl}" />
<input type="hidden" id="SKU_PH_SiteUrl" value="${SKU_PH_SiteUrl}" />
<input type="hidden" id="SKU_SG_SiteUrl" value="${SKU_SG_SiteUrl}" />
<input type="hidden" id="SKU_TH_SiteUrl" value="${SKU_TH_SiteUrl}" />
<input type="hidden" id="SKU_TW_SiteUrl" value="${SKU_TW_SiteUrl}" />
<input type="hidden" id="SKU_VN_SiteUrl" value="${SKU_VN_SiteUrl}" />

<input type="hidden" id="SKU_NZ_SiteUrl" value="${SKU_NZ_SiteUrl}" />
<input type="hidden" id="SKU_MO_SiteUrl" value="${SKU_MO_SiteUrl}" />
<input type="hidden" id="SKU_MN_SiteUrl" value="${SKU_MN_SiteUrl}" />
<input type="hidden" id="SKU_MM_SiteUrl" value="${SKU_MM_SiteUrl}" />
<input type="hidden" id="SKU_LA_SiteUrl" value="${SKU_LA_SiteUrl}" />
<input type="hidden" id="SKU_JP_SiteUrl" value="${SKU_JP_SiteUrl}" />
<input type="hidden" id="SKU_CN_SiteUrl" value="${SKU_CN_SiteUrl}" />
<input type="hidden" id="SKU_AU_SiteUrl" value="${SKU_AU_SiteUrl}" />

<#assign shortDescription="" />
<#assign brandName="" />
<#assign shortDescriptionHead="" />
<#assign brandNameHead="" />
<#assign shareUrl="" />
<#assign itemNo="" />
<#assign collectionName="" />
<@fmt.message key="discontinuedLableWithSku" var="discontinuedLableWithSku"/>
<input type="hidden" id="gridImageProductUrl" value="${galleryUrl}" />
<input type="hidden" id="gridImageNewProductUrl" value="${gridImageNewProductUrl}" />
<input type="hidden" id="comparePanelProducts" value="${comparePanelProducts}" />
<input type="hidden" id="compareButtonLable" value="${compare}" />
<input type="hidden" id="compareClearResult" value="${compareClearResults}" />
<input type="hidden" autocomplete="off" id="productDetailPage" value="true" />

<input type="hidden" id="capthcaClientSecretKey" value="${clientSecretKey}" />
<input type="hidden" id="privacyPolicyShareEmailURLPDP" value="${privacyPolicyShareEmailURL}" />
<input type="hidden" id="pdpEmailTemplateCopyRight" value="${copyRight}" />
<input type="hidden" id="pdpEmailTemplatePrivacyPolicy" value="${emailTemplatePrivacyPolicy}" />
<input type="hidden" id="pdpEmailAFriendTitle" value="${sentEmail}" />
<input type="hidden" id="pdpEmailPopupHelpText1" value="${emailPopupHelpText1}" />
<input type="hidden" id="pdpEmailPopupHelpText2" value="${emailPopupHelpText2}" />
<input type="hidden" id="pdpEmailPopupHelpText3" value="${emailPopupHelpText3}" />
<input type="hidden" id="pdpEmailPopupShareTitle" value="${emailPopupShareTitle}" />
<input type="hidden" id="pdpEmailPopupInputBoxTitle1" value="${emailPopupInputBoxTitle1}" />
<input type="hidden" id="pdpEmailPopupInputBoxTitle2" value="${emailPopupInputBoxTitle2}" />
<input type="hidden" id="pdpEmailPopupInputBoxTitle3" value="${emailPopupInputBoxTitle3}" />
<input type="hidden" id="pdpEmailPopupInputBoxTitle4" value="${emailPopupInputBoxTitle4}" />
<input type="hidden" id="pdpEmailPopupSendButtonText" value="${emailPopupSendButtonText}" />
<input type="hidden" id="pdpEmailPopupCancelButtonText" value="${emailPopupCancelButtonText}" />
<input type="hidden" id="pdpEmailPopupSuccessTitle" value="${emailPopupSuccessTitle}" />
<input type="hidden" id="pdpEmailPopupSuccessMessage" value="${emailPopupSuccessMessage}" />
<input type="hidden" id="pdpEmailPopupFailureMessage" value="${emailPopupFailureMessage}" />
<input type="hidden" id="pdpEmailPopupNameValidationMessage" value="${emailPopupNameValidationMessage}" />
<input type="hidden" id="pdpEmailPopupEamilValidationMessage1" value="${emailPopupEamilValidationMessage1}" />
<input type="hidden" id="pdpEmailPopupEamilValidationMessage2" value="${emailPopupEamilValidationMessage2}" />
<input type="hidden" id="pdpEmailPopupRecipientsEamilValidationMessage1" value="${emailPopupRecipientsEamilValidationMessage1}" />
<input type="hidden" id="pdpEmailPopupRecipientsEamilValidationMessage2" value="${emailPopupRecipientsEamilValidationMessage2}" />
<input type="hidden" id="pdpEmailPopupCaptchaValidationMessage" value="${emailPopupCaptchaValidationMessage}" />
<input type="hidden" id="pdpEmailPopupPlaceHolderMessage" value="${emailPopupPlaceHolderMessage}" />
<input type="hidden" id="retailerImgUrl" value="${retailerImgUrl}" />
<input type="hidden" id="featureRetailersLabel" value="${featuredRetailers}" />
<input type="hidden" id="buyNowLabel" value="${buyNow}" />
<input type="hidden" autocomplete="off" id="common_gridImageUrl" value="${common_gridImageUrl}" />
<input type="hidden" autocomplete="off" id="currencyCode" value="${currencyCode}" />
<input type="hidden" id="price-vat" value="${productVatValue}" />
<input type="hidden" id="multiply-value" value="${productMultiplicationValue}" />

<@hst.include ref="container"/>

<#assign defaultCategory = "" />
<#if hstRequestContext??>
	<#assign productURL = hstRequestContext.getServletRequest().getPathInfo() +"?" />
	<#assign skuidSeperator = "" />
	<#if hstRequestContext.servletRequest.getParameter('skuid')?has_content>
		<#assign skuIdParam = hstRequestContext.servletRequest.getParameter('skuid') />
		<#assign productURL = productURL + "skuid=" + skuIdParam />
		<#assign skuidSeperator = "&" />
	</#if>
	<#if hstRequestContext.servletRequest.getParameter('defaultCategory')?has_content>
		<#assign defaultCategory = hstRequestContext.servletRequest.getParameter('defaultCategory') />
		<#assign productURL = productURL + skuidSeperator + "defaultCategory=" + defaultCategory />
	</#if>
	<#assign currentURL = hstRequestContext.getHstLinkCreator().create(productURL, hstRequestContext.getResolvedMount().getMount()).toUrlForm(hstRequestContext, true) />
</#if>

<#assign currentSite =hstRequestContext.getServletRequest().getScheme()+"s://" />
<#assign serverName =hstRequestContext.getServletRequest().getServerName()/>
<#assign rootUrl = currentSite + serverName />

<input type="hidden" id="pdpEmailTemplateCurrentURL" value="${currentURL}" />
<#if product??>
	<#assign defaultSwatchId = "" />
	<#if product.defaultSKU?? && product.defaultSKU?has_content>
		<#assign defaultSwatchId = product.defaultSKU/>
	</#if>
	<#assign itemNo="" />
	<#assign itemNoForCompare="" />
	<#if product.itemNo?? && product.itemNo?has_content>
		<#assign itemNo=product.itemNo />
		<#assign itemNoForCompare=itemNo />
	</#if>
	<#if product.keys??>
   		<#if (product.keys?size > 1)>
   			<#assign itemNoForCompare = itemNo + "!_" + breadcrumbSubCat />
   		</#if>
    </#if>
	<#if product.techDetail.installationPdf??>
		<#if product.techDetail.specPDF??>
			<#assign specpdfurl = pdfUrl + product.techDetail.specPDF[0] />
		</#if>
	</#if>
	<#if product.imgIso?? && product.imgIso?has_content>
		<#assign imgIso = product.imgIso />
	<#else>
		<#assign imgIso="BLANK" />
	</#if>
	<#assign isFullImageURL=false>
	<#--<p>imgIso: ${imgIso}</p> -->
	<#if imgIso?starts_with("https")>
		<#assign isFullImageURL=true>
		<#assign imgIsoWithoutHttps=imgIso?remove_beginning("https://") />
		<#assign imgIsoFromUrl=imgIsoWithoutHttps?keep_after_last("/")>
		<#assign imgDomainFromUrl=imgIsoWithoutHttps?keep_before_last("/") />
		<#assign damNameFromUrl=imgDomainFromUrl?keep_after_last("/") />
		<#-- <p>isFullImageURL: ${isFullImageURL}</p>
		<p>imgIsoWithoutHttps: ${imgIsoWithoutHttps}</p>
		<p>imgIsoFromUrl: ${imgIsoFromUrl}</p>
		<p>imgDomainFromUrl: ${imgDomainFromUrl}</p>
		<p>damNameFromUrl: ${damNameFromUrl}</p>  -->
	</#if>
	
	<#if isFullImageURL>
		<#assign imgShareURL = common_shareURL?replace("{0}","${imgIsoFromUrl}") />
		<#assign imgShareURL = imgShareURL?replace("{domain}","${imgDomainFromUrl}") />
		<#assign imgShareURL = imgShareURL?replace("{dam}","${damNameFromUrl}") />
		
		<#if product.glamImg?? && product.glamImg?has_content>
			<#assign glamImg=product.glamImg />
		</#if>
		
		<#if product.isNew>
			<#assign imgZoomViewUrl = common_PDPzoomNewProductViewUrl?replace("{0}","${imgIsoFromUrl}") />
			<#assign imgInitialViewUrl = common_PDPgalleryNewProductUrl?replace("{0}","${imgIsoFromUrl}") />
			<#assign imgExpViewWithZoomUrl = imgZoomViewUrl />
			<#assign imgExpViewWithoutZoomUrl = imgInitialViewUrl />
		<#else>
			<#assign imgInitialViewUrl = common_initialViewUrl?replace("{0}","${imgIsoFromUrl}") />
			<#assign imgZoomViewUrl = common_zoomViewUrl?replace("{0}","${imgIsoFromUrl}") />
			<#assign imgExpViewWithZoomUrl = common_expViewWithZoomUrl?replace("{0}","${imgIsoFromUrl}") />
			<#assign imgExpViewWithoutZoomUrl = common_expViewWithoutZoomUrl?replace("{0}","${imgIsoFromUrl}") />
		</#if>

		<#if glamImg?? && glamImg?has_content>
				<#assign glamImgWithoutHttps=glamImg?remove_beginning("https://") />
				<#assign glamImgFromUrl=glamImgWithoutHttps?keep_after_last("/")>
				<#assign glamImgDomainFromUrl=glamImgWithoutHttps?keep_before_last("/") />
				<#assign glamImgDamNameFromUrl=glamImgDomainFromUrl?keep_after_last("/") />

				<#assign glamInitialViewUrl = common_initialViewUrl?replace("{0}","${glamImgFromUrl}") />
				<#assign glamInitialViewUrl = glamInitialViewUrl?replace("{domain}","${glamImgDomainFromUrl}") />
				<#assign glamInitialViewUrl = glamInitialViewUrl?replace("{dam}","${glamImgDamNameFromUrl}") />

				<#assign glamZoomViewUrl = common_zoomViewUrl?replace("{0}","${glamImgFromUrl}") />
				<#assign glamZoomViewUrl = glamZoomViewUrl?replace("{domain}","${glamImgDomainFromUrl}") />
				<#assign glamZoomViewUrl = glamZoomViewUrl?replace("{dam}","${glamImgDamNameFromUrl}") />

				<#if glamImgDamNameFromUrl == 'kohlerchina'>
					<#assign glamInitialViewUrl = glamInitialViewUrl?replace("kohlerchina","PAWEB") />
					<#assign glamInitialViewUrl = glamInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
					<#assign glamZoomViewUrl = glamZoomViewUrl?replace("kohlerchina","PAWEB") />
					<#assign glamZoomViewUrl = glamZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 		</#if>
				<#assign glamExpViewWithZoomUrl = glamZoomViewUrl />
				<#assign glamExpViewWithoutZoomUrl = glamInitialViewUrl />
		</#if>
		
		<#assign imgInitialViewUrl = imgInitialViewUrl?replace("{domain}","${imgDomainFromUrl}") />
		<#assign imgInitialViewUrl = imgInitialViewUrl?replace("{dam}","${damNameFromUrl}") />
			
		<#assign imgZoomViewUrl = imgZoomViewUrl?replace("{domain}","${imgDomainFromUrl}") />
		<#assign imgZoomViewUrl = imgZoomViewUrl?replace("{dam}","${damNameFromUrl}") />

		<#assign imgExpViewWithZoomUrl = imgExpViewWithZoomUrl?replace("{domain}","${imgDomainFromUrl}") />
		<#assign imgExpViewWithZoomUrl = imgExpViewWithZoomUrl?replace("{dam}","${damNameFromUrl}") />

		<#assign imgExpViewWithoutZoomUrl = imgExpViewWithoutZoomUrl?replace("{domain}","${imgDomainFromUrl}") />
		<#assign imgExpViewWithoutZoomUrl = imgExpViewWithoutZoomUrl?replace("{dam}","${damNameFromUrl}") />

		<#if damNameFromUrl == 'kohlerchina'>
			<#assign imgShareURL = imgShareURL?replace("kohlerchina","PAWEB") />
			<#assign imgShareURL = imgShareURL?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
			<#assign imgInitialViewUrl = imgInitialViewUrl?replace("kohlerchina","PAWEB") />
			<#assign imgInitialViewUrl = imgInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
			<#assign imgZoomViewUrl = imgZoomViewUrl?replace("kohlerchina","PAWEB") />
			<#assign imgZoomViewUrl = imgZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
			<#assign imgExpViewWithZoomUrl = imgExpViewWithZoomUrl?replace("kohlerchina","PAWEB") />
			<#assign imgExpViewWithZoomUrl = imgExpViewWithZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
			<#assign imgExpViewWithoutZoomUrl = imgExpViewWithoutZoomUrl?replace("kohlerchina","PAWEB") />
			<#assign imgExpViewWithoutZoomUrl = imgExpViewWithoutZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		</#if>
		
	<#else>
		<#assign imgGalleryURLWithFlag = galleryURLWithFlag?replace("{0}","${imgIso}") />
		<#assign imgGalleryUrl = galleryUrl?replace("{0}","${imgIso}") />
		<#assign imgShareURL = shareURL?replace("{0}","${imgIso}") />
		<#assign imgQuickViewUrl = quickViewUrl?replace("{0}","${imgIso}") />
		
		<#if product.isNew>
			<#assign imgZoomViewUrl = PDPzoomNewProductViewUrl?replace("{0}","${imgIso}") />
			<#assign imgInitialViewUrl = PDPgalleryNewProductUrl?replace("{0}","${imgIso}") />
			<#assign imgExpViewWithZoomUrl = PDPzoomNewProductViewUrl?replace("{0}","${imgIso}") />
			<#assign imgExpViewWithoutZoomUrl = PDPgalleryNewProductUrl?replace("{0}","${imgIso}") />
		<#else>	
			<#assign imgInitialViewUrl = initialViewUrl?replace("{0}","${imgIso}") />
			<#assign imgZoomViewUrl = zoomViewUrl?replace("{0}","${imgIso}") />
			<#assign imgExpViewWithZoomUrl = expViewWithZoomUrl?replace("{0}","${imgIso}") />
			<#assign imgExpViewWithoutZoomUrl = expViewWithoutZoomUrl?replace("{0}","${imgIso}") />
		</#if>
		
		<#if product.glamImg?? && product.glamImg?has_content>
			<#assign glamImg=product.glamImg />
			<#assign glamInitialViewUrl = imgInitialViewUrl?replace("{0}","${glamImg}") />
			<#assign glamZoomViewUrl = imgZoomViewUrl?replace("{0}","${glamImg}") />
			<#assign glamExpViewWithZoomUrl = imgExpViewWithZoomUrl?replace("{0}","${glamImg}") />
			<#assign glamExpViewWithoutZoomUrl = imgExpViewWithoutZoomUrl?replace("{0}","${glamImg}") />
		</#if>
	</#if>

	<#if product.shortDescription?? && product.shortDescription?has_content>
		<#assign shortDescription=product.shortDescription />
		<#assign shortDescriptionHead = shortDescription?replace("(R)","")/>
		<#assign shortDescriptionHead = shortDescriptionHead?replace("(TM)","")/>
		<#assign shortDescription = shortDescription?replace("(R)","<sup>&reg;</sup>")/>
    	<#assign shortDescription = shortDescription?replace("(TM)","<sup>&trade;</sup>")/>
	</#if>
	<#if product.brandName?? && product.brandName[0]?has_content>
		<#assign brandName=product.brandName[0] />
		<#assign brandNameHead = brandName?replace("(R)","")/>
    	<#assign brandNameHead = brandNameHead?replace("(TM)","")/>
		<#assign brandName = brandName?replace("(R)","<sup>&reg;</sup>")/>
    	<#assign brandName = brandName?replace("(TM)","<sup>&trade;</sup>")/>
	</#if>

    <input type="hidden" id="brandNameAndShortdescription" value="${brandName} ${shortDescription}" />
    <#if product.adCopy.narrativeDescription?? && product.adCopy.narrativeDescription?has_content>
		<#assign narrativeDescription=product.adCopy.narrativeDescription />
		<#assign narrativeDescription = narrativeDescription?replace("(R)","<sup>&reg;</sup>")/>
    	<#assign narrativeDescription = narrativeDescription?replace("(TM)","<sup>&trade;</sup>")/>
	</#if>
	<#if imgShareURL?? && imgShareURL?has_content>
		<#assign shareUrl=imgShareURL />
	</#if>
	
	 <#-- <@hst.headContribution category="title">
		    <title>${brandNameHead?html} ${shortDescriptionHead?html} | ${itemNo} | ${companyName}</title>
	 </@hst.headContribution> -->
   
	<section onload="loadImage();" class="c-koh-product-details v-koh-default c-koh-product-details-ap" >
	   <div class="koh-product-breadcrumb">
			<ul itemscope itemtype="https://schema.org/BreadcrumbList">
                <@hst.link var="link" siteMapItemRefId="root"/>
                <@hst.link var="category" siteMapItemRefId="${breadcrumbCatEnName}"/>
                <li class="koh-back-to-results"><a>${backToResults}</a></li>
                <li itemprop="itemListElement" itemscope itemtype="https://schema.org/ListItem"><#if category?? && category?has_content><a itemprop="item" href="${rootUrl}${category}"><span itemprop="name">${breadcrumbCatName}</span></a></#if><meta itemprop="position" content="1" /></li>
                <li itemprop="itemListElement" itemscope itemtype="https://schema.org/ListItem"><#if category?? && category?has_content><a itemprop="item" href="${rootUrl}${category}/${categories}/${breadcrumbSubCat}"><span itemprop="name">${breadcrumbSubCatName}</span></a><meta itemprop="position" content="2" /></#if></li>
            </ul>

	   	</div>
	   <div onload="loadImage();" class="koh-product-top-row" >
	      <div class="koh-product-image-sliders">
	         <div class="product-slider product-images__primary">
	           
		            <div class="product-slide">
		               <#if imgInitialViewUrl?? && imgZoomViewUrl??>           
			               <div class="product-zoom product-zoom-contract">
			                  <img class="koh-product-iso-image koh-product-img" src="${imgInitialViewUrl}" data-magnify-src="${imgZoomViewUrl}"   width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
			               <div class="product-zoom product-zoom-expand">
			                  <img class="koh-product-iso-image koh-product-img" src="${imgInitialViewUrl}" data-magnify-src="${imgZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
		               </#if>
		               <div class="product-slide-expand">
		                  <button class="product-slide-expand--open"></button>
		               </div>
		               <#if product.discontinued??>
		               		<h2>${discontinuedImageMessage}</h2>
		               </#if>		
		            </div>
		            <#if product.youtubeId?? && product.youtubeId?has_content>
		            
			            <#assign youtubeId = product.youtubeId/>
			            <#assign facebookId = facebookProductVideoURL?replace("{0}","${youtubeId}") />
			            <div class="product-slide product-images-video-wrapper">
			               <iframe src="${facebookId}"></iframe>
			            </div>
		            </#if>
	           
	            <#if glamImg??>
		            <#if glamImg?? && glamImg?has_content>
			            <div class="product-slide">
							<#if glamZoomViewUrl?? && glamInitialViewUrl??>
				               <div class="product-zoom product-zoom-contract">
				                  <img class="koh-product-img" src="${glamInitialViewUrl}" data-magnify-src="${glamZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
				               </div>
							</#if> 
							<#if glamExpViewWithZoomUrl?? && glamExpViewWithoutZoomUrl??>
				               <div class="product-zoom product-zoom-expand">
				                  <img class="koh-product-img" src="${glamExpViewWithoutZoomUrl}" data-magnify-src="${glamExpViewWithZoomUrl}" width="1800" height="800" alt="${defaultSwatchId}">
				               </div>
							</#if>   
			               <div class="product-slide-expand">
			                  <button class="product-slide-expand--open"></button>
			               </div>
			               <#if product.discontinued??>
			               		<h2>${discontinuedImageMessage}</h2>
			               </#if>
			            </div>
		            </#if>
	            </#if>
	            
	            <#list product.imgMap?keys as key>
	        			    <#assign img = product.imgMap[key] />
							
	        			   <#if img?starts_with("https")>
								<#assign prodImgWithoutHttps=img?remove_beginning("https://") />
								<#assign prodImgFromUrl=prodImgWithoutHttps?keep_after_last("/")>
								<#assign prodImgDomainFromUrl=prodImgWithoutHttps?keep_before_last("/") />
								<#assign prodImgDamNameFromUrl=prodImgDomainFromUrl?keep_after_last("/") />

	        			    	<#assign imageInitialViewUrl = common_initialViewUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageInitialViewUrl = imageInitialViewUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageInitialViewUrl = imageInitialViewUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#assign imageZoomViewUrl = common_zoomViewUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageZoomViewUrl = imageZoomViewUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageZoomViewUrl = imageZoomViewUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#assign imageExpViewWithZoomUrl = common_expViewWithZoomUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageExpViewWithZoomUrl = imageExpViewWithZoomUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageExpViewWithZoomUrl = imageExpViewWithZoomUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#assign imageExpViewWithoutZoomUrl = common_expViewWithoutZoomUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageExpViewWithoutZoomUrl = imageExpViewWithoutZoomUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageExpViewWithoutZoomUrl = imageExpViewWithoutZoomUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#if prodImgDamNameFromUrl == 'kohlerchina'>
									<#assign imageInitialViewUrl = imageInitialViewUrl?replace("kohlerchina","PAWEB") />
									<#assign imageInitialViewUrl = imageInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
									<#assign imageZoomViewUrl = imageZoomViewUrl?replace("kohlerchina","PAWEB") />
									<#assign imageZoomViewUrl = imageZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
									<#assign imageExpViewWithZoomUrl = imageExpViewWithZoomUrl?replace("kohlerchina","PAWEB") />
									<#assign imageExpViewWithZoomUrl = imageExpViewWithZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
									<#assign imageExpViewWithoutZoomUrl = imageExpViewWithoutZoomUrl?replace("kohlerchina","PAWEB") />
									<#assign imageExpViewWithoutZoomUrl = imageExpViewWithoutZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 						</#if>
	        			    <#else>
		        			    <#assign imageInitialViewUrl = initialViewUrl?replace("{0}","${img}") />
								<#assign imageZoomViewUrl = zoomViewUrl?replace("{0}","${img}") />
								<#assign imageExpViewWithZoomUrl = expViewWithZoomUrl?replace("{0}","${img}") />
								<#assign imageExpViewWithoutZoomUrl = expViewWithoutZoomUrl?replace("{0}","${img}") />	
							</#if>
				            <div class="product-slide">
					               <div class="product-zoom product-zoom-contract">
					                  <img class="koh-product-img" src="${imageInitialViewUrl}" data-magnify-src="${imageZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
					               </div>
					               <div class="product-zoom product-zoom-expand">
					                  <img class="koh-product-img" src="${imageExpViewWithoutZoomUrl}" data-magnify-src="${imageExpViewWithZoomUrl}" width="1800" height="800" alt="${defaultSwatchId}">
					               </div>
				               <div class="product-slide-expand">
				                  <button class="product-slide-expand--open"></button>
				               </div>
				               <#if product.discontinued??>
				               		<h2>${discontinuedImageMessage}</h2>
				               </#if>
				            </div>
	             </#list>
	            
	            
	         </div>
	         <div class="product-slider">
	            
		            <div class="product-images__navigation">
		               <#if imgInitialViewUrl?? && imgZoomViewUrl??>
			               <div class="product-slide product-slide-first">
			                  <img class="koh-product-iso-image" src="${imgInitialViewUrl}" data-magnify-src="${imgZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
		               </#if>
		               <#if product.youtubeId??>
			               <#assign facebookThumbId=product.youtubeId/>
			               <#assign facebookThumbURL = facebookProductVideoThumbURL?replace("{0}","${facebookThumbId}") /> 
			               <div class="product-slide product-slide-video-thumb">
			                  <img src="${facebookThumbURL}" data-magnify-src="" width="103" height="77" alt="${defaultSwatchId}">
			               </div>
		               </#if> 
		               <#if glamImg?? && glamImg?has_content && glamInitialViewUrl?? && glamZoomViewUrl??>
			               <div class="product-slide">
			                  <img src="${glamInitialViewUrl}" data-magnify-src="${glamZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
		               </#if>
		               <#list product.imgMap?keys as key>
		               		<#assign img = product.imgMap[key] />

		               		<#if img?starts_with("https")>
								<#assign prodImgWithoutHttps=img?remove_beginning("https://") />
								<#assign prodImgFromUrl=prodImgWithoutHttps?keep_after_last("/")>
								<#assign prodImgDomainFromUrl=prodImgWithoutHttps?keep_before_last("/") />
								<#assign prodImgDamNameFromUrl=prodImgDomainFromUrl?keep_after_last("/") />

								<#assign imageInitialViewUrl = common_initialViewUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageInitialViewUrl = imageInitialViewUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageInitialViewUrl = imageInitialViewUrl?replace("{dam}","${prodImgDamNameFromUrl}") />
								
								<#assign imageZoomViewUrl = common_zoomViewUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageZoomViewUrl = imageZoomViewUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageZoomViewUrl = imageZoomViewUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#if prodImgDamNameFromUrl == 'kohlerchina'>
									<#assign imageInitialViewUrl = imageInitialViewUrl?replace("kohlerchina","PAWEB") />
									<#assign imageInitialViewUrl = imageInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
									<#assign imageZoomViewUrl = imageZoomViewUrl?replace("kohlerchina","PAWEB") />
									<#assign imageZoomViewUrl = imageZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 						</#if>
		               		<#else>
		               			<#assign imageInitialViewUrl = initialViewUrl?replace("{0}","${img}") />
								<#assign imageZoomViewUrl = zoomViewUrl?replace("{0}","${img}") />
		               		</#if>
	        			    
				            <div class="product-slide">
			                  <img src="${imageInitialViewUrl}" data-magnify-src="${imageZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
	            	   </#list>
		            </div>
	         </div>
	         <div class="product-slider product-slider-mobile">
	            
		            <div class="product-images__navigation-mobile">
		               <#if imgInitialViewUrl?? && imgZoomViewUrl??>
			               <div class="product-slide product-slide-first">
			                  <img class="koh-product-iso-image" src="${imgInitialViewUrl}" data-magnify-src="${imgZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
		               </#if>
		               <#if product.youtubeId?? && product.youtubeId?has_content>
			               <#assign mobileFacebookThumbId=product.youtubeId />
			               <#assign facebookThumbURL = facebookProductVideoThumbURL?replace("{0}","${mobileFacebookThumbId}") /> 
			               <div class="product-slide">
			                  <img src="${facebookThumbURL}" data-magnify-src="" width="103" height="77" alt="${defaultSwatchId}">
			               </div>
		               </#if> 
		               <#if glamImg?? && glamImg?has_content && glamInitialViewUrl?? && glamZoomViewUrl??>
			               <div class="product-slide">
			                  <img src="${glamInitialViewUrl}" data-magnify-src="${glamZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			               </div>
		               </#if> 
		               <#list product.imgMap?keys as key>
		               		<#assign img = product.imgMap[key] />

		               		<#if img?starts_with("https")>
								<#assign prodImgWithoutHttps=img?remove_beginning("https://") />
								<#assign prodImgFromUrl=prodImgWithoutHttps?keep_after_last("/")>
								<#assign prodImgDomainFromUrl=prodImgWithoutHttps?keep_before_last("/") />
								<#assign prodImgDamNameFromUrl=prodImgDomainFromUrl?keep_after_last("/") />

		               			<#assign imageInitialViewUrl = common_initialViewUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageInitialViewUrl = imageInitialViewUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageInitialViewUrl = imageInitialViewUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#assign imageZoomViewUrl = common_zoomViewUrl?replace("{0}","${prodImgFromUrl}") />
								<#assign imageZoomViewUrl = imageZoomViewUrl?replace("{domain}","${prodImgDomainFromUrl}") />
								<#assign imageZoomViewUrl = imageZoomViewUrl?replace("{dam}","${prodImgDamNameFromUrl}") />

								<#if prodImgDamNameFromUrl == 'kohlerchina'>
									<#assign imageInitialViewUrl = imageInitialViewUrl?replace("kohlerchina","PAWEB") />
									<#assign imageInitialViewUrl = imageInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
									<#assign imageZoomViewUrl = imageZoomViewUrl?replace("kohlerchina","PAWEB") />
									<#assign imageZoomViewUrl = imageZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 						</#if>
		               		<#else>
			               		<#assign imageInitialViewUrl = initialViewUrl?replace("{0}","${img}") />
								<#assign imageZoomViewUrl = zoomViewUrl?replace("{0}","${img}") />
		               		</#if>
	        			    
				            <div class="product-slide">
			                  <img src="${imageInitialViewUrl}" data-magnify-src="${imageZoomViewUrl}" width="1800" height="800" alt="${defaultSwatchId}">
			                </div>
	            	   </#list>
		            </div>
	           
	         </div>
	      </div>
	      <div class="koh-product-expand-container">
	         <div class="koh-product-expand-inner">
	         </div>
	         <div class="product-slide-expand">
	            <button class="product-slide-expand--close"></button>
	         </div>
	      </div>
	      <div class="koh-product-details">
	         <div class="koh-product-name-and-short-description">
	            <#if brandName?? && brandName?has_content>
	            	<h1 class="koh-product-name">${brandName}</h1>
	            </#if>
	            <#if shortDescription??>
	            	<div class="koh-product-short-description">${shortDescription}</div>
	            </#if>
	            <#if product.adCopy?? >
		            <div class="koh-products-description-files-print">
		               <#if narrativeDescription?? && narrativeDescription?has_content>
		              	 <div class="koh-product-long-description">${narrativeDescription}</div>
		               </#if>
		               <#if specpdfurl??>
			               <div class="koh-product-specs">
			                  <div class="koh-product-specs-geometry">
			                     <a target="_blank" rel="noopener noreferrer" href="${specpdfurl}" class="koh-product-pdf koh-pdf-link">${dimensionsMeasurements}</a>
			                  </div>
			               </div>
			           </#if>
		            </div>
	            </#if>
	         </div>
	         <div class="koh-product-skus-colors">
          	  	<span class="koh-product-sku koh-sku-k" data-product-sku="${defaultSwatchId}" data-default-category="${defaultCategory}">K-${defaultSwatchId}</span>
          	  	<#if product.skus?? && product.skus[0]?has_content>
	               <#list product.skus as sku>
	               		<#if sku.productPrice??>
							<#if sku.skuId == defaultSwatchId>
								<#if currencyCode?? && currencyCode?has_content && sku.productPrice?has_content>
									<#assign productPriceValue=sku.productPrice />
									<#assign productPriceNumber = (productPriceValue?number) * productVat/>
									<div class="koh-product-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</div>
								</#if>
						    </#if>
		               </#if>
	               </#list>
                </#if>
	            <div class="koh-product-colors">
	               <span class="koh-product-colors-title">${productDetailColor}&nbsp;:</span>
	               <#if product.skus?? && product.skus[0]?has_content>
		               <#list product.skus as sku>
			               <#if sku.skuId == defaultSwatchId>
				               <span class="koh-product-colors-selected">
				               ${sku.colorFinishName}</span>
			               </#if>		  
		               </#list>
	               </#if>
	               <ul>
	                  <#assign availableInCountries ="" />
	                  <#assign availableInCountriesDefaultSku ="" />
	                  <#if product.skus?? && product.skus?has_content>
	                  		<#if !product.discontinued?has_content>
			                  <#list product.skus as skuItem>
			                  		<#assign imgSwatch = skuItem.imgSwatch/>
			                  		<#assign imgswatchUrl = imgswatch?replace("{0}","${imgSwatch}") />
			                  		<#assign skuItemColorFinishName="" />
			                  		<#if skuItem.colorFinishName??>
			                  			<#assign skuItemColorFinishName=skuItem.colorFinishName />
			                  		</#if>
									 <#if skuItem.productPrice?? && skuItem.productPrice?has_content>
										<#assign skuItemPrice = skuItem.productPrice />
										<#assign productPriceNumber = (skuItemPrice?number) * productVat/>
									 </#if>
			                  		<#if  skuItem.discontinued??>	
			                  			
			                  		<#else>
				                  	  <#assign itemSwatchId = skuItem.skuId/>
					                  <#assign imgItemIso = skuItem.imgItemIso/>
					                  
					                  <#if imgItemIso?starts_with("https")>
											<#assign skuImgWithoutHttps=imgItemIso?remove_beginning("https://") />
											<#assign skuImgFromUrl=skuImgWithoutHttps?keep_after_last("/")>
											<#assign skuImgDomainFromUrl=skuImgWithoutHttps?keep_before_last("/") />
											<#assign skuImgDamNameFromUrl=skuImgDomainFromUrl?keep_after_last("/") />

					                  	  <#if product.isNew>
											<#assign skuZoomViewUrl = common_PDPzoomNewProductViewUrl?replace("{0}","${skuImgFromUrl}") />
											<#assign skuInitialViewUrl = common_PDPgalleryNewProductUrl?replace("{0}","${skuImgFromUrl}") />
											<#assign skuExpViewWithZoomUrl = skuZoomViewUrl />
											<#assign skuExpViewWithoutZoomUrl = skuInitialViewUrl />
										<#else>
											<#assign skuInitialViewUrl = common_initialViewUrl?replace("{0}","${skuImgFromUrl}") />
											<#assign skuZoomViewUrl = common_zoomViewUrl?replace("{0}","${skuImgFromUrl}") />
											<#assign skuExpViewWithZoomUrl = common_expViewWithZoomUrl?replace("{0}","${skuImgFromUrl}") />
											<#assign skuExpViewWithoutZoomUrl = common_expViewWithoutZoomUrl?replace("{0}","${skuImgFromUrl}") />
										</#if>
										
										<#assign skuInitialViewUrl = skuInitialViewUrl?replace("{domain}","${skuImgDomainFromUrl}") />
										<#assign skuInitialViewUrl = skuInitialViewUrl?replace("{dam}","${skuImgDamNameFromUrl}") />

										<#assign skuZoomViewUrl = skuZoomViewUrl?replace("{domain}","${skuImgDomainFromUrl}") />
										<#assign skuZoomViewUrl = skuZoomViewUrl?replace("{dam}","${skuImgDamNameFromUrl}") />

										<#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("{domain}","${skuImgDomainFromUrl}") />
										<#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("{dam}","${skuImgDamNameFromUrl}") />

										<#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("{domain}","${skuImgDomainFromUrl}") />
										<#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("{dam}","${skuImgDamNameFromUrl}") />
										<#if skuImgDamNameFromUrl == 'kohlerchina'>
											<#assign skuInitialViewUrl = skuInitialViewUrl?replace("kohlerchina","PAWEB") />
											<#assign skuInitialViewUrl = skuInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
											<#assign skuZoomViewUrl = skuZoomViewUrl?replace("kohlerchina","PAWEB") />
											<#assign skuZoomViewUrl = skuZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
											<#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("kohlerchina","PAWEB") />
											<#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
											<#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("kohlerchina","PAWEB") />
											<#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 								</#if>
					                  <#else>
						                  <#if product.isNew>
											  <#assign skuZoomViewUrl = PDPzoomNewProductViewUrl?replace("{0}","${imgItemIso}") />
											  <#assign skuInitialViewUrl = PDPgalleryNewProductUrl?replace("{0}","${imgItemIso}") />
											  <#assign skuExpViewWithZoomUrl = PDPzoomNewProductViewUrl?replace("{0}","${imgItemIso}") />
											  <#assign skuExpViewWithoutZoomUrl = PDPgalleryNewProductUrl?replace("{0}","${imgItemIso}") />
										  <#else>	
											  <#assign skuInitialViewUrl = initialViewUrl?replace("{0}","${imgItemIso}") />
											  <#assign skuZoomViewUrl = zoomViewUrl?replace("{0}","${imgItemIso}") />
											  <#assign skuExpViewWithZoomUrl = expViewWithZoomUrl?replace("{0}","${imgItemIso}") />
											  <#assign skuExpViewWithoutZoomUrl = expViewWithoutZoomUrl?replace("{0}","${imgItemIso}") />
										  </#if>
									  </#if>
					                  <#assign availableInCountries =skuItem.availableInCountries />
					                  <#if defaultSwatchId == itemSwatchId>
					                  	<#assign availableInCountriesDefaultSku = skuItem.availableInCountries />
					                  	<#assign retailerImageMapDefaultSku = skuItem.retailerImageMap />
					                  	<#assign retailerUrlMapDefaultSku = skuItem.retailerUrlMap />
					                  </#if>
					                  <#assign availableInCountriesString ="" />
					                  <#list availableInCountries?keys as key>
						                  <@fmt.message key="${key}" var="country"/>
						                  <#assign value = availableInCountries[key]/>
						                  <#assign firstSku = "" />
						                  <#assign valueArray = "" />
						                  <#list value as val> 
							                  <#if valueArray?has_content>
							                 	 <#assign valueArray=valueArray + ", K-" + val>
							                  <#else>
							                  	<#assign valueArray="K-"+val>
							                  	<#assign firstSku = val />
							                  </#if>	 
						                  </#list> 
						                  <#if availableInCountriesString?has_content>
						                  	<#assign availableInCountriesString = availableInCountriesString + "|,__" + key + "__" + firstSku +  "__" + country +" (" + valueArray + ")"/>
						                  <#else>
						                  	<#assign availableInCountriesString =  "__" + key + "__" + firstSku + "__"+ country +" (" + valueArray + ")"/>
						                  </#if>
					                  </#list>
					                  
					                  <#assign retailerMapString ="" />
					                  <#list skuItem.retailerImageMap?keys as imageMapKey>
					                  		<#if retailerMapString?has_content>
					                  			<#assign retailerMapString = retailerMapString + "|," />
											</#if>
											<#if skuItem.retailerImageMap[imageMapKey]?? && skuItem.retailerImageMap[imageMapKey]?has_content>
												<#assign retailerMapString = retailerMapString + skuItem.retailerImageMap[imageMapKey] + "__" />
											</#if>	
											<#if skuItem.retailerUrlMap[imageMapKey]?? && skuItem.retailerUrlMap[imageMapKey]?has_content>
												<#assign retailerMapString = retailerMapString + skuItem.retailerUrlMap[imageMapKey] />
											</#if>
									 </#list>
					                  <li>
					                     <span class="koh-product-variant <#if defaultSwatchId == itemSwatchId> koh-selected-variant </#if>" data-koh-image="${skuInitialViewUrl}" data-koh-image-zoom="${skuZoomViewUrl}" data-koh-image-expand="${skuExpViewWithoutZoomUrl}" data-koh-image-expand-zoom="${skuExpViewWithZoomUrl}" data-koh-sku="${skuItem.skuId}" data-koh-sku-k="K-${skuItem.skuId}" data-koh-color="${skuItemColorFinishName}" data-koh-price="${currencyCode} ${(productPriceNumber?round)*productMultiplication}" data-koh-countries="${availableInCountriesString}" data-koh-itemno="${itemNo}" data-default-category="${defaultCategory}" data-koh-retailer="${retailerMapString}">
					                     <button class="koh-product-color">
					                     <img src="${imgswatchUrl}" width="40" height="40" alt="${defaultSwatchId}"><span class="label"> ${skuItemColorFinishName}</span></button>
					                     </span>
					                  </li>
					                </#if>  
			                  </#list>
			               </#if>     
	                  </#if>
	               </ul>
	            <#if ( product.discontinued?? && product.discontinued?has_content )>
	            </div>
	            </#if>
	            <!-- Discontinued colors -->
				<div class="koh-product-discontinued-colors">
					<ul>
						<#if product.skus?? && product.skus?has_content>
		                  <#list product.skus as skuItem>
		                  		<#assign skuItemColorFinishName="" />
		                  		<#if skuItem.colorFinishName??>
		                  			<#assign skuItemColorFinishName=skuItem.colorFinishName />
		                  		</#if>
		                  		<#if  ( skuItem.discontinued?? && skuItem.discontinued?has_content ) || ( product.discontinued?? && product.discontinued?has_content )>	
			                  	    <#assign imgSwatch = skuItem.imgSwatch/>
									<#assign imgswatchUrl = imgswatch?replace("{0}","${imgSwatch}") />
			                  		<#assign itemSwatchId = skuItem.skuId/>
									<li>
										<#if ( product.discontinued?? && product.discontinued?has_content )>
											<span class="koh-product-variant  <#if defaultSwatchId == itemSwatchId> koh-selected-variant </#if>">
										<#else>	
											  <#assign imgItemIso = skuItem.imgItemIso/>

											   <#if imgItemIso?starts_with("https")>
													<#assign skuImgWithoutHttps=imgItemIso?remove_beginning("https://") />
													<#assign skuImgFromUrl=skuImgWithoutHttps?keep_after_last("/")>
													<#assign skuImgDomainFromUrl=skuImgWithoutHttps?keep_before_last("/") />
													<#assign skuImgDamNameFromUrl=skuImgDomainFromUrl?keep_after_last("/") />

												  <#assign skuInitialViewUrl = common_initialViewUrl?replace("{0}","${skuImgFromUrl}") />
												  <#assign skuInitialViewUrl = skuInitialViewUrl?replace("{domain}","${skuImgDomainFromUrl}") />
												  <#assign skuInitialViewUrl = skuInitialViewUrl?replace("{dam}","${skuImgDamNameFromUrl}") />
		
												  <#assign skuZoomViewUrl = common_zoomViewUrl?replace("{0}","${skuImgFromUrl}") />
												  <#assign skuZoomViewUrl = skuZoomViewUrl?replace("{domain}","${skuImgDomainFromUrl}") />
												  <#assign skuZoomViewUrl = skuZoomViewUrl?replace("{dam}","${skuImgDamNameFromUrl}") />

												  <#assign skuExpViewWithZoomUrl = common_expViewWithZoomUrl?replace("{0}","${skuImgFromUrl}") />
												  <#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("{domain}","${skuImgDomainFromUrl}") />
												  <#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("{dam}","${skuImgDamNameFromUrl}") />

												  <#assign skuExpViewWithoutZoomUrl = common_expViewWithoutZoomUrl?replace("{0}","${skuImgFromUrl}") />
												  <#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("{domain}","${skuImgDomainFromUrl}") />
												  <#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("{dam}","${skuImgDamNameFromUrl}") />
													<#if skuImgDamNameFromUrl == 'kohlerchina'>
														<#assign skuInitialViewUrl = skuInitialViewUrl?replace("kohlerchina","PAWEB") />
														<#assign skuInitialViewUrl = skuInitialViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
														<#assign skuZoomViewUrl = skuZoomViewUrl?replace("kohlerchina","PAWEB") />
														<#assign skuZoomViewUrl = skuZoomViewUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
														<#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("kohlerchina","PAWEB") />
														<#assign skuExpViewWithZoomUrl = skuExpViewWithZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
														<#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("kohlerchina","PAWEB") />
														<#assign skuExpViewWithoutZoomUrl = skuExpViewWithoutZoomUrl?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
													</#if>
											  <#else>
								                  <#assign skuInitialViewUrl = initialViewUrl?replace("{0}","${imgItemIso}") />
												  <#assign skuZoomViewUrl = zoomViewUrl?replace("{0}","${imgItemIso}") />
												  <#assign skuExpViewWithZoomUrl = expViewWithZoomUrl?replace("{0}","${imgItemIso}") />
												  <#assign skuExpViewWithoutZoomUrl = expViewWithoutZoomUrl?replace("{0}","${imgItemIso}") />
											  </#if>

											  
							                  <#assign availableInCountries =skuItem.availableInCountries />
							                  <#if defaultSwatchId == itemSwatchId>
							                  	<#assign availableInCountriesDefaultSku =skuItem.availableInCountries />
							                  	<#assign retailerImageMapDefaultSku = skuItem.retailerImageMap />
					                  			<#assign retailerUrlMapDefaultSku = skuItem.retailerUrlMap />
							                  </#if>
							                  <#assign availableInCountriesString ="" />
							                  <#list availableInCountries?keys as key>
								                  <@fmt.message key="${key}" var="country"/>
								                  <#assign value = availableInCountries[key]/>
								                  <#assign firstSku = "" />
								                  <#assign valueArray = "" />
								                  <#list value as val> 
									                  <#if valueArray?has_content>
									                 	 <#assign valueArray=valueArray + ", K-" + val>
									                  <#else>
									                  	<#assign valueArray="K-"+val>
									                  	<#assign firstSku = val />
									                  </#if>	 
								                  </#list> 
								                  <#if availableInCountriesString?has_content>
								                  		<#assign availableInCountriesString = availableInCountriesString + "|,__" + key + "__" + firstSku +  "__" + country +" (" + valueArray + ")"/>
								                  <#else>
								                  		<#assign availableInCountriesString =  "__" + key + "__" + firstSku + "__"+ country +" (" + valueArray + ")"/>
								                  </#if>
							                  </#list>
							                  
							                  <#assign retailerMapString ="" />
							                  <#list skuItem.retailerImageMap?keys as imageMapKey>
							                  		<#if retailerMapString?has_content>
							                  			<#assign retailerMapString = retailerMapString + "|," />
													</#if>
													<#if skuItem.retailerImageMap[imageMapKey]?? && skuItem.retailerImageMap[imageMapKey]?has_content>
														<#assign retailerMapString = retailerMapString + skuItem.retailerImageMap[imageMapKey] + "__" />
													</#if>	
													<#if skuItem.retailerUrlMap[imageMapKey]?? && skuItem.retailerUrlMap[imageMapKey]?has_content>
														<#assign retailerMapString = retailerMapString + skuItem.retailerUrlMap[imageMapKey] />
													</#if>
											 </#list>
											<span class="koh-product-variant<#if defaultSwatchId == itemSwatchId> koh-selected-variant</#if>" data-koh-image="${skuInitialViewUrl}" data-koh-image-zoom="${skuZoomViewUrl}" data-koh-image-expand="${skuExpViewWithoutZoomUrl}" data-koh-image-expand-zoom="${skuExpViewWithZoomUrl}" data-koh-sku="${skuItem.skuId}" data-koh-sku-k="K-${skuItem.skuId}" data-koh-color="${skuItemColorFinishName}" data-koh-countries="${availableInCountriesString}" data-koh-itemno="${itemNo}" data-default-category="${defaultCategory}"  data-koh-retailer="${retailerMapString}">
										</#if>
											<button class="koh-product-color">
													<img src="${imgswatchUrl}" width="40" height="40" alt="${defaultSwatchId}"><span class="label"> ${skuItemColorFinishName}  <#if  (skuItem.discontinued?? && skuItem.discontinued?has_content) && ( !product.discontinued?has_content) > (${discontinuedLableWithSku}) </#if></span></button>
												</button>
											</span>	
									</li>
								</#if>	
						   </#list>
						</#if>	
					</ul>
				  </div>
				<#if ( !product.discontinued?? || !product.discontinued?has_content )>
		            </div>
	            </#if>
	         </div>
	         
	         <#if availableInCountriesDefaultSku?? && availableInCountriesDefaultSku?has_content>
		         <div class="koh-country-available-in">
		            <div class="koh-available-in-title">Available In</div>
		            <#list availableInCountriesDefaultSku?keys as key>
			            <@fmt.message key="${key}" var="country"/>
			            <@fmt.message key="SiteUrl_${key}" var="siteURL"/>
			            <#assign value = availableInCountriesDefaultSku[key]/>
			            <#assign valueArray = "" />
			            <#assign valueFirst = "" />
			            <#list value as val> 
				            <#if valueArray?has_content>
				          	  <#assign valueArray=valueArray + ", K-" + val>
				            <#else>
				           	 <#assign valueArray=val>
				           	 <#assign valueFirst = val />
				            </#if>	 
			            </#list> 
			            <div class="koh-available-sku">
			            	<#if siteURL?has_content>
			            		<a href="${siteURL}productDetails/${itemNo}?skuid=${valueFirst}" target="_blank">${country} (K-${valueArray})</a>
			            	<#else>
			            		${country} (K-${valueArray})	
			            	</#if>	
			            </div>
		            </#list>  
		         </div>
	         </#if>    
	         <div class="koh-product-locator">
	            <a href="${storelink}" target="_self" rel="noopener noreferrer">${storeLocator}</a>
	         </div>
	         <div class="koh-product-locator">
	            <a href="${shareContactFormLink}" target="_self" rel="noopener noreferrer">${contactUsEnquiryWithUs}</a>
	         </div>
	         <div id="exclusiveAt" class="koh-retail-buy">
	         	<#if retailerImageMapDefaultSku?? && (retailerImageMapDefaultSku?size > 0) >
					<span>${featuredRetailers}:</span>
	                <#list retailerImageMapDefaultSku?keys as imageMapKey>
						<div class="koh-retail-buy-content sc7">
							<div class="koh-flex-image-button">
								<#if retailerImageMapDefaultSku[imageMapKey]?? && retailerImageMapDefaultSku[imageMapKey]?has_content>
									<div class="koh-img-div-wrap-one">
										<img src="${retailerImgUrl?replace("{0}",retailerImageMapDefaultSku[imageMapKey])}" class="koh-retail-logo display-none-md-only"> 
										  <img src="${retailerImgUrl?replace("{0}",retailerImageMapDefaultSku[imageMapKey])}" class="retail-logo display-block-md-only display-none-sm">
									</div>
								</#if>	
								<#if retailerUrlMapDefaultSku[imageMapKey]?? && retailerUrlMapDefaultSku[imageMapKey]?has_content>
									<a href="${retailerUrlMapDefaultSku[imageMapKey]}" target="_blank" rel="noopener noreferrer">
										<div id="product-detail__retail-buy-now" class="koh-btn-buy-now-green buttonBuyNow" <#if apacGA?? && apacGA?has_content>  onclick = "${buynowTracking}" </#if>>
											${buyNow}
										</div>
									</a>
								</#if>
							</div>
						</div>
					</#list>
				 </#if>	 
			 </div>
	         <div class="koh-product-tools">
	            <a data-href="${addToCompareLink}" class="koh-compare-add koh-compare-button-tile add_${product.itemNo}" data-product-itemno="${product.itemNo}" data-category="<#if breadcrumbSubCat?has_content>${breadcrumbSubCat}</#if>" data-product-itemno-multianscestors="${itemNoForCompare}">${compare}</a>
	            <a data-href="${addToCompareLink}" class="koh-compare-removes koh-compare-button-tile remove_${product.itemNo}" data-product-itemno="${product.itemNo}" data-category="<#if breadcrumbSubCat?has_content>${breadcrumbSubCat}</#if>" data-product-itemno-multianscestors="${itemNoForCompare}">${compare}</a>
	            <div class="koh-product-share">
	               <button class="koh-product-button" data-hasqtip="1"><span class="icon" data-icon="&#xe603;"></span><span class="label">${share}</span></button>
	               <ul class="koh-product-popover v-koh-share">
	                  <li>
	                     <button class="koh-share-facebook"><span class="icon" data-icon="&#xe609"></span><span class="label">${facebook}</span></button>
	                  </li>
	                  <li>
	                     <button class="koh-share-twitter"><span class="icon" data-icon="&#xe60d"></span><span class="label">${twitter}</span></button>
	                  </li>
	                  <li>
						 <button class="koh-share-email" data-remodal-id="share-mail-popup"><span class="icon" data-icon="&#xe60d"></span><span class="label">${sentEmail}</span></button>
					  </li>
	               </ul>
	            </div>
	            <div class="koh-product-print">
	               <button onclick="javascript:window.print()" class="koh-product-button" data-hasqtip="2">
	               <span class="icon" data-icon="&#xe620;"></span>
	               <span class="label">${print}</span>
	               </button>
	            </div>
	         </div>
	      </div>
	   </div>
	   <div class="koh-product-content">
	   		<!-- Summary -->
	   		<#if product.discontinued??>
	   			<@fmt.message key="productDiscontinuedDescription" var="productDiscontinuedDescription"/>
				<div class="koh-product-summary">
					<!-- Discontinued -->
					<span class="koh-product-discontinued">${productDiscontinuedDescription}</span>
				</div>
	   	    </#if>	
	      <div class="koh-product-features">
	         <div class="koh-product-features-description-files">
	            <#if narrativeDescription??>
	           	 <div class="koh-product-long-description">${narrativeDescription}</div>
	            </#if>
	             <#if specpdfurl??>
		            <div class="koh-product-specs">
		               <div class="koh-product-specs-geometry">
		                  <a target="_blank" rel="noopener noreferrer" href="${specpdfurl}" class="koh-product-pdf koh-pdf-link">${dimensionsMeasurements}</a>
		               </div>
		            </div>
		          </#if>
	         </div>
	       
	       	 
	         <#list product.adCopy.webFeatures?keys as key>
		         <#assign nChack1 = product.adCopy.webFeatures[key] />
		         <#break>
	         </#list>
	           <#list product.adCopy.webInstallation?keys as key>
		         <#assign nChack2 = product.adCopy.webInstallation[key] />
		         <#break>
	         </#list>
	         <#list product.adCopy.webMaterial?keys as key>
		         <#assign nChack3 = product.adCopy.webMaterial[key] />
		         <#break>
	         </#list>
	            <#list product.adCopy.webRebates?keys as key>
		         <#assign nChack4 = product.adCopy.webRebates[key] />
		         <#break>
	         </#list>
	          <#list product.adCopy.webHydrotherapy?keys as key>
		         <#assign nChack5 = product.adCopy.webHydrotherapy[key] />
		         <#break>
	         </#list>
	         <#list product.adCopy.webTechnology?keys as key>
		         <#assign nChack6 = product.adCopy.webTechnology[key] />
		         <#break>
	         </#list>
	         <#if nChack1?? || nChack2?? || nChack3?? || nChack4?? || nChack5?? || nChack6??>
	        	 <hr class="gray-line no-print">
	         </#if>
	         <#if nChack1??>
		         <h2 class="koh-product-features-title">${features}</h2>
		         <ul class="koh-product-features-general">
		            <#list product.adCopy.webFeatures?keys as feature>
		                <#assign webFeatures = product.adCopy.webFeatures[feature]> 
		                <#assign webFeatures =  webFeatures?replace("(R)","<sup>&reg;</sup>")/>
	                    <#assign webFeatures =  webFeatures?replace("(TM)","<sup>&trade;</sup>")/>
		            	<li>${webFeatures}</li>
		            </#list>
		         </ul>
	         </#if>
	         <#if nChack6??>
		         <div class="koh-product-features-subtitle">${technology}</div>
		         <ul class="koh-product-features-conservation">
		            <#list product.adCopy.webTechnology?keys as webTechnology>
		           		 <li>${product.adCopy.webTechnology[webTechnology]}</li>
		            </#list>
		         </ul>
	         </#if> 
	         <#if nChack2??>
		         <div class="koh-product-features-subtitle">${installation}</div>
		         <ul class="koh-product-features-installation">
		            <#list product.adCopy.webInstallation?keys as installation>
		            	<li>${product.adCopy.webInstallation[installation]}</li>
		            </#list> 
		         </ul>
	         </#if>   
	         <#if nChack3??>
		         <div class="koh-product-features-subtitle">${material}</div>
		         <ul class="koh-product-features-conservation">
		            <#list product.adCopy.webMaterial?keys as material>
		           		 <li>${product.adCopy.webMaterial[material]}</li>
		            </#list>
		         </ul>
	         </#if> 
	         <#if nChack4??>
		         <div class="koh-product-features-subtitle">${rebates}</div>
		         <ul class="koh-product-features-conservation">
		            <#list product.adCopy.webRebates?keys as rebate>
		           		 <li>${product.adCopy.webRebates[rebate]}</li>
		            </#list>
		         </ul>
	         </#if> 
	         <#if nChack5??>
		         <div class="koh-product-features-subtitle">${hydrotherapy}</div>
		         <ul class="koh-product-features-conservation">
		            <#list product.adCopy.webHydrotherapy?keys as hydrotherapy>
		           		 <li>${product.adCopy.webHydrotherapy[hydrotherapy]}</li>
		            </#list>
		         </ul>
	         </#if> 
	        <@fmt.message key="visitBrandExperience" var="visitBrandExperience"/>
	         <#if product.pdpArticleURL??>
	         		<#assign visitBrandExperience = visitBrandExperience?replace("{0}","${product.pdpArticleURL}") />
	            <div class="koh-product-features-brand-experience">${visitBrandExperience}</div>
	         </#if>   
	         <#if product.logosToDisplay??>
				<ul class="koh-product-brands">
					<#list product.logosToDisplay?split("|") as logoesToDisplay>
						<@fmt.message key="${logoesToDisplay}" var="logoesToDisplayLink"/>
						<@fmt.message key="${logoesToDisplay}Image" var="logoesToDisplayImage"/>
						<li><a href="${logoesToDisplayLink}"><img src="${logoesToDisplayImage}" width="70" height="41" alt="${logoesToDisplay}"></a></li>
					</#list>	
				</ul>
			 </#if>	
	      </div>
	     
	      <div class="koh-product-resources">
	         <#if product.cadDetail.dwgPlanView?? ||product.cadDetail.dxfPlanView?? ||  product.cadDetail.dwgFrontView?? || product.cadDetail.dxfFrontView?? || product.cadDetail.dwgSideView?? || product.cadDetail.dxfSideView?? || product.cadDetail.dwgAllViews?? || product.cadDetail.dxfAllViews?? || product.cadDetail.td_REVIT?? || product.cadDetail.td_SYMBOL?? || product.cadDetail.td_DXF_SYMBOL?? || product.cadDetail.td_3DS?? || product.cadDetail.td_OBJ?? || product.cadDetail.td_SKETCHUP?? || product.cadDetail.cut_OUT_DXF??>
		         <hr class="gray-line no-print">
		         <span class="koh-product-resources-title">${resources}</span>
		         <span class="koh-product-resources-subtitle">${cadTemplates}</span>
		         <div class="koh-product-resources-templates">
		            <#if product.cadDetail.dwgPlanView?? ||product.cadDetail.dxfPlanView?? ||  product.cadDetail.dwgFrontView?? || product.cadDetail.dxfFrontView?? || product.cadDetail.dwgSideView?? || product.cadDetail.dxfSideView?? || product.cadDetail.dwgAllViews?? || product.cadDetail.dxfAllViews??>
		            	<div class="koh-product-templates-2d">
			               <span class="koh-templates-title">${twocadfile}</span>   
			               <#if product.cadDetail.dwgPlanView?? ||product.cadDetail.dxfPlanView??>
				               <span class="koh-templates-label">${plan}</span>
				               <ul class="koh-templates-links">
				                  <#if product.cadDetail.dwgPlanView??>
				                 	 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dwgPlanView}" class="koh-file-link">${dwg}</a></li>
				                  </#if>
				                  <#if product.cadDetail.dxfPlanView??>    
				                	  <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dxfPlanView}" class="koh-file-link">${dxf}</a></li>
				                  </#if>
				               </ul>
			               </#if> 
			               <#if product.cadDetail.dwgFrontView?? ||product.cadDetail.dxfFrontView??>  
				               <span class="koh-templates-label">${front}</span>
				               <ul class="koh-templates-links">
				                  <#if product.cadDetail.dwgFrontView??>
				                  		<li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dwgFrontView}" class="koh-file-link">${dwg}</a></li>
				                  </#if> 
				                  <#if product.cadDetail.dxfFrontView??>
				                 		 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dxfFrontView}" class="koh-file-link">${dxf}</a></li>
				                  </#if> 
				               </ul>
			               </#if> 
			               <#if product.cadDetail.dwgSideView?? ||product.cadDetail.dxfSideView??>
				               <span class="koh-templates-label">${side}</span>
				               <ul class="koh-templates-links">
				                  <#if product.cadDetail.dwgSideView??>
				                 		 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dwgSideView}" class="koh-file-link">${dwg}</a></li>
				                  </#if> 
				                  <#if product.cadDetail.dxfSideView??>
				                 		 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dxfSideView}" class="koh-file-link">${dxf}</a></li>
				                  </#if> 
				               </ul>
			               </#if>
			               <#if product.cadDetail.dwgAllViews?? ||product.cadDetail.dxfAllViews??>
				               <span class="koh-templates-label">${overall}</span>
				               <ul class="koh-templates-links">
				                  <#if product.cadDetail.dwgAllViews??>
				                 		 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dwgAllViews}" class="koh-file-link">${dwg}</a></li>
				                  </#if> 
				                  <#if product.cadDetail.dxfAllViews??>
				                 		 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.dxfAllViews}" class="koh-file-link">${dxf}</a></li>
				                  </#if> 
				               </ul>
			               </#if>
		               </div>
	               	</#if>
	               	<#if product.cadDetail.td_SYMBOL?? || product.cadDetail.td_DXF_SYMBOL?? || product.cadDetail.td_3DS?? || product.cadDetail.td_OBJ?? || product.cadDetail.td_SKETCHUP?? || product.cadDetail.td_REVIT?? || product.cadDetail.cut_OUT_DXF??>
		            	<div class="koh-product-templates-3d">
		                   <span class="koh-templates-title">${threeDCadfile}</span>   
			               <ul class="koh-templates-links">
			                  <#if product.cadDetail.td_SYMBOL??>
			                 	 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.td_SYMBOL}" class="koh-file-link">${dwg}</a></li>
			                  </#if>
			                  <#if product.cadDetail.td_DXF_SYMBOL??>    
			                	  <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.td_DXF_SYMBOL}" class="koh-file-link">${dxf}</a></li>
			                  </#if>
			                  <#if product.cadDetail.td_3DS??>
			                 	 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.td_3DS}" class="koh-file-link">${tds}</a></li>
			                  </#if>
			                  <#if product.cadDetail.td_OBJ??>    
			                	  <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.td_OBJ}" class="koh-file-link">${obj}</a></li>
			                  </#if>
			                  <#if product.cadDetail.td_SKETCHUP??>
			                 	 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.td_SKETCHUP}" class="koh-file-link">${sketchup}</a></li>
			                  </#if>
			                  <#if product.cadDetail.td_REVIT??>    
			                	  <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.td_REVIT}" class="koh-file-link">${revit}</a></li>
			                  </#if>
			               </ul>
		                <#if product.cadDetail.cut_OUT_DXF??>
			               <span class="koh-templates-label">${cutoutTemplates}</span>
			               <ul class="koh-templates-links">
			                  <#if product.cadDetail.cut_OUT_DXF??>
			                 		 <li><a target="_blank" rel="noopener noreferrer" href="${cadUrl}${product.cadDetail.cut_OUT_DXF}" class="koh-file-link">${cutoutTemplates}</a></li>
			                  </#if> 
			               </ul>
		               </#if>
		            </div>
	            </#if>
		         </div>
	         </#if>
	         <#if product.youtubeId?? && product.youtubeId?has_content>
	       		 <hr class="gray-line no-print">
		         <#assign descYoutubeId=product.youtubeId/>
		         <div class="koh-product-resources-videos">
		         	<span class="koh-product-resources-subtitle">${videos}</span>	
		            <div class="koh-product-article v-koh-video">
		               <div class="koh-article-video">
		                  <#assign facebookId = facebookProductDescVideoURL?replace("{0}","${descYoutubeId}") />
		                  <a href="${facebookId}" target="_blank" rel="noopener noreferrer">
			                  <span class="koh-article-icon">
			                  <span class="icon" data-icon=""></span>
			                  </span>
			                  <#assign facebookThumbURL = facebookProductVideoThumbURL?replace("{0}","${descYoutubeId}") />
			                  <img src="${facebookThumbURL}" alt="<#if product.itemNo?? && product.itemNo?has_content>${product.itemNo}</#if>" width="191" height="164">
		                  </a>
		               </div>
		            </div>
		         </div>
	         </#if>
	         <div class="koh-product-service">
	            <hr class="gray-line">
	            <#assign contacttodistributor = contacttodistributor?replace("{0}",storelink) />
	            <#assign contacttodistributor = contacttodistributor?replace("{1}",contactuslink) />
	            <span class="koh-product-service-title">${serviceSupport}</span>
	            <p>${haveQuestProduct}</p>
	            <p>${contacttodistributor}</p>
	         </div>
	         
	         <div class="koh-product-specs">
	         	<#if product.techDetail??>
		            <hr class="gray-line">
		            <span class="koh-product-resources-subtitle koh-product-resources-subtitle--noline">${technicalInfo}</span>
		            <div class="koh-product-specs-geometry">
		            <span class="koh-product-col-title">${dimensions}</span>
		               <span class="koh-product-col-description">
			               <#if product.techDetail.overAllLength??>
			              	 <strong>L</strong>&nbsp
			              	 ${product.techDetail.overAllLength} ${dimensionsdetail},&nbsp
			               </#if>
			               <#if product.techDetail.overAllHeight??>
			              	 <strong>H</strong>&nbsp
			              	 ${product.techDetail.overAllHeight} ${dimensionsdetail},&nbsp
			               </#if>
			               <#if product.techDetail.overAllWidth??>
			               	 <strong>W</strong>&nbsp
			               	 ${product.techDetail.overAllWidth} ${dimensionsdetail},&nbsp
			               </#if>
			            </span>
		               <#if product.techDetail.gfLineArt??>
		              	 <img src="${lineArtURL}${product.techDetail.gfLineArt}" alt="">
		               </#if>
		               <#if product.techDetail.installationWithoutSpPdf?? || product.techDetail.SpecPDF?? || product.techDetail.installationPdf??>
			            	<span class="koh-product-col-description">   
				               <ul class="koh-product-resources-technical-info">
				               	  <#if product.techDetail.specPDF??>
				               	  		<@fmt.message key="SPEC_PDF_FILE_NAME" var="specPdfLabel"/>
				               	  		<#assign specIndex = "">
				               	  	 	<#list  product.techDetail.specPDF as specPdf>
				               	  	 		<li><a target="_blank" rel="noopener noreferrer" href="${pdfUrl}${specPdf}" class="koh-pdf-link">${specPdfLabel}${specIndex}</a></li>
				               	  	 		<#assign specIndex = specPdf?index + 2 />
				               	  	 	</#list>
				               	  </#if> 	
				               	  <#if product.techDetail.installationWithoutSpPdf??>
				                  	<#if product.techDetail.installationWithoutSpPdf?size &gt; 1>
				                  		<#if product.techDetail.installationWithoutSpPdf[1]?? && product.techDetail.installationWithoutSpPdf[1]=="Installation_Instructions">
				                  			<@fmt.message key="INSTALLATION_PDF_FILE_NAME" var="installationInstruction"/>
				                  			<li><a target="_blank" rel="noopener noreferrer" href="${pdfUrl}${product.techDetail.installationWithoutSpPdf[0]}" class="koh-pdf-link">${installationInstruction}</a></li>
			                  			<#elseif product.techDetail.installationWithoutSpPdf[1]?? && product.techDetail.installationWithoutSpPdf[1]=="Installation_Without_Service_Part">
			                  				<@fmt.message key="INSTALLATION_WITHOUT_SP_PDF" var="withoutServicePart"/>
			                  				<li><a target="_blank" rel="noopener noreferrer" href="${pdfUrl}${product.techDetail.installationWithoutSpPdf[0]}" class="koh-pdf-link">${withoutServicePart}</a></li>
		                  				<#-- <#elseif product.techDetail.installationWithoutSpPdf[1]?? && product.techDetail.installationWithoutSpPdf[1]=="With_Service_Part">
		                  					<@fmt.message key="With_Service_Part" var="withServicePart"/>
		                  					<li><a target="_blank" rel="noopener noreferrer" href="${pdfUrl}${product.techDetail.installationWithoutSpPdf[0]}" class="koh-pdf-link">${withServicePart}</a></li> -->
				                  		</#if>
			                  		<#else>
			                  			<@fmt.message key="INSTALLATION_WITHOUT_SP_PDF" var="CPDBwithoutServicePart"/>
		                  				<li><a target="_blank" rel="noopener noreferrer" href="${pdfUrl}${product.techDetail.installationWithoutSpPdf[0]}" class="koh-pdf-link">${CPDBwithoutServicePart}</a></li>
				                  	</#if>
				                  </#if>
				                  <#if product.techDetail.installationPdf??> 	
					                  <#list product.techDetail.installationPdf?keys as key>
					                  		<@fmt.message key=key var="keyLabel"/>
						                	<li><a target="_blank" rel="noopener noreferrer" href="${pdfUrl}${product.techDetail.installationPdf[key]}" class="koh-pdf-link">${keyLabel}</a></li>
					                  </#list>
				                  </#if>
				               </ul>
			           		</span>
			           </#if>       
		           </div>
            	</#if> 
	            <div class="koh-product-specs-other">
	               <#if product.collection?? && product.collection?has_content>	
		               <span class="koh-product-col-title">${collection}</span>
		               <#list product.collection as collectionName>
		                	<#assign collectionVal = "${collectionName}" />
		               			<#assign collectionVal = collectionVal?replace("(R)","<sup>&reg;</sup>")/>
    				   		<#assign collectionVal = collectionVal?replace("(TM)","<sup>&trade;</sup>")/>
		               		<span class="koh-product-col-description">${collectionVal}</span>
		               </#list>	
	               </#if>
	               <#if product.material?? && product.material?has_content>
		               <span class="koh-product-col-title">${material}:</span>
		               <span class="koh-product-col-description">${product.material}</span>
	               </#if>
	               <#if product.installationType?? && product.installationType[0]?has_content>
		               <span class="koh-product-col-title">${installation}:</span>
		               <span class="koh-product-col-description">${product.installationType[0]}</span>
	               </#if>
	            </div>
	         </div>
	         <div class="koh-product-disclaimer">${pleaseNoteProduct}</div>
	      </div>
	      <div class="koh-product-required-items-print">
	      </div>
	   </div>
	  
	    <#assign listsize = product.crossSellingDetail.crossSellingAccessories["recommendedAccessories"]?size />   
	   <#if product.crossSellingDetail.crossSellingAccessories??>
		   <#list product.crossSellingDetail.crossSellingAccessories?keys as item>
		   	  <#assign listsize = product.crossSellingDetail.crossSellingAccessories[item]?size /> 
		   	  <#if listsize &gt; 0>
		   	  		<@fmt.message key="${item}" var="itemLabel" />
				   <div class="koh-product-pairs-with">
				      <h2 class="koh-product-pairs-title">${itemLabel}</h2>
				      <div class="koh-product-tiles">
				         <#list product.crossSellingDetail.crossSellingAccessories[item] as crossSelling>
				         	<#if crossSelling??>
					         <div class="koh-product-tile">
					         	 <#assign productFamily="" />	
						         <#assign productDescription="" />
						         <#assign defaultSku="" />
						         <#assign defaultImage="blank" />
						         <#assign productPrice="" />
						         <#if crossSelling.attributes??>
						         	<#list crossSelling.attributes as attr>
							            <#if attr.key == "BRAND_NAME">
							            	<#assign productFamily = attr.value[0]?replace("(R)","<sup>&reg;</sup>")/>
				    						<#assign productFamily = productFamily?replace("(TM)","<sup>&trade;</sup>")/>
										</#if>
										<#if attr.key == "DESCRIPTION_PRODUCT_SHORT"> 
											<#assign productDescription = attr.value[0]?replace("(R)","<sup>&reg;</sup>")/>
				    						<#assign productDescription = productDescription?replace("(TM)","<sup>&trade;</sup>")/>
										</#if>
				                        <#if attr.key == "DEFAULT_SKU"> 
											 <#assign defaultSku=attr.value[0] />
										</#if>
										<#if attr.key == "IMG_ISO"> 
											<#assign defaultImage=attr.value[0] />
										</#if>
						            </#list>
						         </#if>
						         <#if defaultImage == "blank">
						         	<#list crossSelling.skus as skuSub>   
								  	   <#if skuSub.sku == defaultSku>
							  	   			<#assign defaultSkuValue=skuSub />
								  	   </#if>
							  	     </#list>
							  	     <#list defaultSkuValue.attributes as skuAttribute>
										<#if skuAttribute.key == "IMG_ITEM_ISO"> 
									    	<#if skuAttribute.value[0]?? && skuAttribute.value[0]?has_content>
										  		<#assign defaultImage=skuAttribute.value[0] />
										  	</#if>	
									 	</#if>
						     	     </#list>
						         </#if>
						         <#list crossSelling.skus as skuSub>   
							  	   <#if skuSub.sku == defaultSku>
						  	   			<#assign defaultSkuValue=skuSub />
							  	   </#if>
						  	     </#list>
						  	     <#list defaultSkuValue.attributes as skuAttribute>
									<#if skuAttribute.key == "LIST_PRICE"> 
								    	<#if skuAttribute.value[0]?? && skuAttribute.value[0]?has_content>
									  		<#assign productPrice=skuAttribute.value[0] />
									  		<#assign productPriceNumber = (productPrice?number) * productVat/>
									  	</#if>	
								 	</#if>
					     	     </#list>
					            <div class="koh-product-tile-content">
					               <a href="${productDetailslink}/${crossSelling.itemNo}?skuid=${defaultSku}">
										<div class="koh-product-image">
											<#assign defImg = defaultImage />
											<#if defImg?starts_with("https")>
												<#assign prodImgWithoutHttps=defImg?remove_beginning("https://") />
												<#assign prodImgFromUrl=prodImgWithoutHttps?keep_after_last("/")>
												<#assign prodImgDomainFromUrl=prodImgWithoutHttps?keep_before_last("/") />
												<#assign prodImgDamNameFromUrl=prodImgDomainFromUrl?keep_after_last("/") />

												
												<#if crossSelling.isNew>
													<#assign defImg = common_gridImageNewProductUrl?replace("{0}","${prodImgFromUrl}") />
													<#assign defImg = defImg?replace("{domain}","${prodImgDomainFromUrl}") />
												    <#assign defImg = defImg?replace("{dam}","${prodImgDamNameFromUrl}") />

													<#if prodImgDamNameFromUrl == 'kohlerchina'>
														<#assign defImg = defImg?replace("kohlerchina","PAWEB") />
														<#assign defImg = defImg?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 										  	</#if>
												<#else>
													<#assign defImg = common_galleryUrl?replace("{0}","${prodImgFromUrl}") />
													<#assign defImg = defImg?replace("{domain}","${prodImgDomainFromUrl}") />
												    <#assign defImg = defImg?replace("{dam}","${prodImgDamNameFromUrl}") />
													<#if prodImgDamNameFromUrl == 'kohlerchina'>
														<#assign defImg = defImg?replace("kohlerchina","PAWEB") />
														<#assign defImg = defImg?replace("product_src=is{PAWEB","product_src=is{kohlerchina") />
		 										  	</#if>
												</#if>
											<#else>
											  <#if crossSelling.isNew>
											  		<#assign defImg = gridImageNewProductUrl?replace("{0}","${defaultImage}") />
											  <#else>										     										     
                                              		<#assign defImg = galleryUrl?replace("{0}","${defaultImage}") />
                                              </#if>
                                            </#if> 		
		                     	              <img src="${defImg}" width="300" height="232" alt="${defaultSku}">
											</div>
										<span class="koh-product-description">
											<span class="koh-product-family">${productFamily}</span>
                                             ${productDescription}
										</span>
										<span class="koh-product-sku" data-product-sku="${defaultSku}" data-default-category="${defaultCategory}">K-${defaultSku}</span>
										<#if currencyCode?? && currencyCode?has_content && productPrice?has_content>
											<span class="koh-product-price">${currencyCode} ${(productPriceNumber?round)*productMultiplication}</span>
										</#if>
									</a>
					            </div>
					         </div>
					         </#if> 
				         </#list>
				      </div>
				   </div>
			   </#if>
		   </#list>
	   </#if> 
	 <#-- 
	   <div class="koh-similar-products" >
	      <h2 class="koh-similar-products-title">Similar Products</h2>
	      <div class="koh-product-tiles" id="koh-product-tiles">
	      </div>
	   </div>-->
	   <div id="back-to-top">
	      <button class="btn--square btn--gray">
	      <i class="icon--chevron-up"></i>
	      <span>${backTotop}</span>
	      </button>
	   </div>
	</section>
	<#-- 
	<@hst.headContribution category="ext-scripts">
		<script type="text/javascript"> 
		   $(document).ready( function() {
		   	handleSimilarProduct('${product.itemNo}'); 
		   });	
		</script>
	</@hst.headContribution> -->

	<div id="koh-share-email-remodal" class="koh-share-email-remodal"></div>
	
	<@hst.webfile path="/" var="link" />
	<@hst.headContribution category="ext-scripts">
			<script src="${link}/js/c-koh-product-details.min.js" type="text/javascript"></script>
	</@hst.headContribution>
	<@hst.headContribution category="ext-scripts">
			<script type="text/javascript" src="${link}/js/custom/koh-compare-load.min.js"></script>
	</@hst.headContribution>
	<@hst.headContribution category="ext-scripts">
			<script src="${link}/js/zoom/jquery.zoom.min.js" type="text/javascript"></script>
	</@hst.headContribution>
	<@hst.headContribution category="ext-scripts">
			<script src="${link}/js/magnify/jquery.magnify.min.js" type="text/javascript"></script>
	</@hst.headContribution>
	<@hst.headContribution category="ext-scripts">
			<script src="${link}/js/imageviewer/imageviewer.min.js" type="text/javascript"></script>
	</@hst.headContribution>
	
	<@hst.headContribution category="meta-tags">
			<meta name="twitter:card" content="${shareUrl}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta name="twitter:title" content="${brandNameHead?html}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta name="twitter:description" content="${shortDescriptionHead?html}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta name="twitter:site" content="@${companyName}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta name="twitter:image:src" content="${shareUrl}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta property="og:title" content="${brandNameHead?html} | ${shortDescriptionHead?html}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta property="og:image" content="${shareUrl}" />
	</@hst.headContribution>
	<#assign metaDesc= metaDesc?replace("{0}","${brandNameHead}")  />
	<#assign metaDesc= metaDesc?replace("{1}","${shortDescriptionHead}")  />
	<@hst.headContribution category="meta-tags">
			<#if product.adCopy?? && product.adCopy.metaDescription?? && product.adCopy.metaDescription?has_content>
				<meta property="og:description" content="${product.adCopy.metaDescription?html}" />
			<#else>
				<meta property="og:description" content="${metaDesc?html}"/>
			</#if>	 	
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
			<meta property="og:site_name" content="${companyName}" />
	</@hst.headContribution>
	<#assign SEOKeyword="" />
	<#if itemNo?? && itemNo?has_content>
		<#assign SEOKeyword = itemNo />
	</#if>
	<#if brandNameHead?? && brandNameHead?has_content>
		<#assign SEOKeyword = SEOKeyword + ", " + brandNameHead />
	</#if>
	<#if shortDescriptionHead?? && shortDescriptionHead?has_content>
		<#assign SEOKeyword = SEOKeyword + ", " + shortDescriptionHead?html />
	</#if>
	<#if breadcrumbCatName?? && breadcrumbCatName?has_content>
		<#assign SEOKeyword = SEOKeyword + ", " + breadcrumbCatName />
	</#if>
	<#if breadcrumbSubCat?? && breadcrumbSubCat?has_content>
		<#assign SEOKeyword = SEOKeyword + ", "  + breadcrumbSubCat />
	</#if>
	<@hst.headContribution category="meta-tags">
		<meta name="keywords" content="${SEOKeyword?html}" />
	</@hst.headContribution>
   <#-- <@hst.headContribution category="meta-tags">
		<#if product.adCopy.metaDescription?? && product.adCopy.metaDescription?has_content>
			<meta name="description" content="${product.adCopy.metaDescription?html}"/>
		<#else>
			<meta name="description" content="${metaDesc?html}"/>
		</#if>	 
	</@hst.headContribution> --> 
	<#if isSchemaPresent?? && isSchemaPresent?has_content>
		<@hst.headContribution category="schema">
			<#if product.shortDescription?? && product.shortDescription?has_content>
				<#assign shortDesc=product.shortDescription/>
			</#if>
			<#if product.adCopy.narrativeDescription?? && product.adCopy.narrativeDescription?has_content>
				<#assign description=product.adCopy.narrativeDescription />
			</#if>
			<#if product.defaultSKU?? && product.defaultSKU?has_content>
				<#assign defaultSwatchId = product.defaultSKU/>
			</#if>
			<script type="application/ld+json">
				{
				  "@context": "https://schema.org",
				  "@type": "Product",
				  "url": "${currentURL}",
				  "name": "${shortDesc}",
				  "description": "${description}",
				  "sku": "K-${defaultSwatchId}"
			  	}
			</script>
		</@hst.headContribution>
	</#if>
</#if>
