var consentURL = $('#consentURL').val();
document.addEventListener("DOMContentLoaded", function () {
	if (consentURL) {
	    var docBod = document.body;
	    //Create and set page positioning for a div to hold the banner elements
	    var consentBannerGrp = document.createElement('div');
	    consentBannerGrp.setAttribute('style', 'position:fixed;bottom:0px;width:100%;z-index:1000;');
	    //Create the div elements that trustarc will insert content into
	    /*var consentBar = document.createElement('div');
	    consentBar.setAttribute('id', 'teconsent');
	    consentBannerGrp.appendChild(consentBar);*/
	    var divBar = document.createElement('div');
	    divBar.setAttribute('id', 'consent_blackbar');
	    divBar.setAttribute('style', 'width:100%;');
	    consentBannerGrp.appendChild(divBar);
	    //Add the banner elements HTML to the page
	    docBod.insertBefore(consentBannerGrp, docBod.firstChild);
	    //Add the trustarc URL to a script tag to be executed on the page
	    var importScript = document.createElement("script");
	    // importScript.src = "https://consent.trustarc.com/notice?domain=kohler.com&c=teconsent&country=de&language=de&js=nj&noticeType=bb";
	    importScript.src = consentURL;
	    document.getElementsByTagName("head")[0].appendChild(importScript);
	}
});