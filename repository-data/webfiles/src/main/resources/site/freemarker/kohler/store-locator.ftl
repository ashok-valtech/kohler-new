<#include "../include/imports.ftl">

<@hst.include ref="container"/>	

<@hst.headContributions xhtml=true categoryExcludes="ext-scripts"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="copy-right" var="copyRight"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSecretKey"/>
<@fmt.message key="default-search-map-lat" var="defaultLat"/>
<@fmt.message key="default-search-map-long" var="defaultLong"/>
<@fmt.message key="bingdatasource" var="bingdatasource"/>
<@fmt.message key="bingdatasourcekeys" var="bingdatasourcekeys"/>
<@fmt.message key="bingCurrentLocationKey" var="bingMapCurrentLocationKey"/>
<@fmt.message key="store-type-kec" var="storetypekec"/>
<@fmt.message key="store-type-distributors" var="storetypedistributors"/>
<@fmt.message key="store-type-stardealers" var="storetypestardealers"/>
<@fmt.message key="store-type-dealers" var="storetypedealers"/>
<@fmt.message key="kec-tooltip" var="kecTooltip"/>
<@fmt.message key="distributor-tooltip" var="distributorTooltip"/>
<@fmt.message key="dealer-tooltip" var="dealerTooltip"/>
<@fmt.message key="star-dealer-tooltip" var="starDealerTooltip"/>
<@fmt.message key="where-to-buy-detail" var="findAStore"/>
<@fmt.message key="store-locator-search-error-message" var="searchErrorMessage"/>
<@fmt.message key="store-locator-search-success-message" var="searchSuccessMessage"/>
<@fmt.message key="store-locator-search" var="search"/>
<@fmt.message key="store-locator-map" var="map"/>
<@fmt.message key="store-locator-list" var="list"/>
<@fmt.message key="bingMapSource" var="bingMapSource"/>
<@fmt.message key="km-digit" var="kmDigit"/>
<@fmt.message key="km-label" var="kmLabel"/>
<@fmt.message key="storeLocator-searchBox-placeHolder" var="searchBoxPlaceHolder"/>
<@fmt.message key="storeLocator-searchResult-limit" var="searchResultLimit"/>
<@fmt.message key="storeLocator-viewDetails-Label" var="viewDetailsLabel"/>
<#assign searchErrorMessage = searchErrorMessage?replace("{0}","${kmLabel}")/>
<#assign aDateTime = .now>
<#assign copyRight = copyRight?replace("{0}","${aDateTime?string.yyyy}") />
<#assign language = hstRequest.getLocale().getLanguage() />
<@fmt.message key="kec-store-contactclassification" var="kecstorecontactclassification"/>
<input type="hidden" id="pdpEmailTemplateCopyRight" value="${copyRight}" />
<input type="hidden" id="capthcaClientSecretKey" value="${clientSecretKey}" />
<input type="hidden" id="defaultLatId" value="${defaultLat}" />
<input type="hidden" id="defaultLongId" value="${defaultLong}" />
<input type="hidden" id="defaultLatLong" value="" />
<input type="hidden" id="bingMapCurrentLocationKeyId" value="${bingMapCurrentLocationKey}" />
<input type="hidden" id="searchErrorMessageId" value="${searchErrorMessage}">
<input type="hidden" id="searchSuccessMessageId" value="${searchSuccessMessage}">
<input type="hidden" id="kmDigitId" value="${kmDigit}">
<input type="hidden" id="searchResultLimitId" value="${searchResultLimit}">
<input type="hidden" id="viewDetailsLabelId" value="${viewDetailsLabel}">

<input type="hidden" class="koh-bingdatasource" data-bingdatasource="${bingdatasource}">
<input type="hidden" class="koh-bingdatasourcekeys" data-bingdatasourcekeys="${bingdatasourcekeys}">
<input type="hidden" class="koh-bingdatasourcelanguage" data-bingdatasourcelanguage="${language}">

<section class="c-koh-store-locator">
	<div class="container">
        <div class="koh-map-form-inner-content">
            <h1 class="text-center koh-map-main-header">${findAStore}</h1>
            <div class="row text-center">
                <div class="col-sm-9">
                    <input type="text" class="form-control koh-search-text" name="search-text" id="koh-search-text" value="" placeholder="${searchBoxPlaceHolder}">
                </div>
                <div class="col-sm-3 pad-left-0">
                    <button type="button" class="btn btn-primary koh-store-search-btn" id="button-type">${search}</button>
                </div>
            </div>
            <!-- Checkboxes for filters starts here -->
            <div class="form-check-radio" id="koh-map-check">
                <div class="form-group">
                    <div class="form-options">
                        <div class="checkbox-inline">
                            <input value="${kecstorecontactclassification}" checked="checked" id="koh-exp-center" type="checkbox">
                            <label for="koh-exp-center" class="koh-tooltip">
                                <div class="koh-dotted-span">${storetypekec}</div>
                                <span>${kecTooltip}</span>
                            </label>
                        </div>
                        <div class="not-first-checkbox">
                            <div class="checkbox-inline">
                                <input value="distributor" checked="checked" id="koh-distributor" type="checkbox">
                                <label for="koh-distributor" class="koh-tooltip">
                                    <div class="koh-dotted-span">${storetypedistributors}</div>
                                    <span>${distributorTooltip}</span>
                                </label>
                            </div>
                        </div>
                    <#if storetypestardealers?? && storetypestardealers?has_content>
                        <div class="not-first-checkbox">
                            <div class="checkbox-inline">
                                <input value="star-dealer" checked="checked" id="koh-star-dealer" type="checkbox">
                                <label for="koh-star-dealer" class="koh-tooltip">
                                    <div class="koh-dotted-span">${storetypestardealers}</div>
                                    <span>${starDealerTooltip}</span>
                                </label>
                            </div>
                        </div>
                    </#if>
                        <div class="not-first-checkbox">
                            <div class="checkbox-inline">
                                <input value="dealer" checked="checked" id="koh-dealer" type="checkbox">
                                <label for="koh-dealer" class="koh-tooltip">
                                    <div class="koh-dotted-span">${storetypedealers}</div>
                                    <span>${dealerTooltip}</span>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Checkboxes for filters ends here -->
        </div>
        <hr>
        <div class="row koh-map-outer-container">
            <div id="koh-no-result">
                <h3 class="text-center-info"></h3>
            </div>
            <ul id="koh-response-tab" class="nav nav-pills row">
                <li id="koh-list-map-tab" class="active"><a data-toggle="pill" href="#koh-store-locator-results">${list}</a></li>
                <li id="koh-map-tab"><a data-toggle="pill" href="#koh-map-container">${map}</a></li>
            </ul>
            <div id="koh-store-locator-results" class="col-sm-4 pad-right-0 hide">
                <ul>
                </ul>
            </div>
            <div id="koh-map-container" class="col-sm-12">
                <div id="koh-legend">
                    
                    <div class="koh-tooltip">
                        <i class="koh-square-color-icon koh-square-color-icon-green"></i>
                        <div class="koh-dotted-span">${storetypekec}</div>
                        <span>${kecTooltip}</span>
                    </div>
                    <div class="koh-tooltip">
                        <i class="koh-square-color-icon koh-square-color-icon-blue"></i>
                        <div class="koh-dotted-span">${storetypedistributors}</div>
                        <span>${distributorTooltip}</span>
                    </div>
                <#if storetypestardealers?? && storetypestardealers?has_content>
                    <div class="koh-tooltip">
                        <i class="koh-square-color-icon koh-square-color-icon-black"></i>
                        <div class="koh-dotted-span">${storetypestardealers}</div>
                        <span>${starDealerTooltip}</span>
                    </div>
                </#if>
                    <div class="koh-tooltip">
                        <i class="koh-square-color-icon koh-square-color-icon-grey"></i>
                        <div class="koh-dotted-span">${storetypedealers}</div>
                        <span>${dealerTooltip}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="koh-map"></div>
    <div id="koh-book-appointment-remodal" class="koh-book-appointment-remodal"></div>
</section>

<@hst.webfile path="/" var="link" />

<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${bingMapSource}"></script>
</@hst.headContribution>

<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-store-locater.min.js"></script>
</@hst.headContribution>
