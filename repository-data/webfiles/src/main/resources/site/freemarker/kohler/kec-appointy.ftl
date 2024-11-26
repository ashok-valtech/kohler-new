<#include "../include/imports.ftl">
<@fmt.message key="appointy-title" var="appointyTitle" />
<@fmt.message key="appointy-sub-title" var="appointySubTitle" />
<@fmt.message key="appointy-URL" var="appointyUrl" />
	
<section class="c-koh-warranty-registration-form">
    <div class="koh-contact-container">
        <div class="koh-contact-header">
            <span class="koh-contact-title">${appointyTitle}</span>
        </div>
     <div class="koh-contact-form-wrapper">
         <div class="form-group">
          <h3 class="text-center">${appointySubTitle}</h3>
         </div>
     </div>
    </div>
</section>
<iframe id="appointy-iframe" class="no-border" src="${appointyUrl}" scrolling="no" width="100%" frameBorder="0"></iframe>
<script>
	(function() {
	const ifrm = document.getElementById("appointy-iframe");
	window.addEventListener("message", function(e) {
		const d = e.data || {};
		if (d.type === "height") {
			ifrm.style.height = d.data + 'px';
		}
		if (d.type === "scroll") {
			ifrm.scrollIntoView();
		}
	});
})();
</script>

