// Javascript Namespacing setup
var axKOH = axKOH || {};
axKOH.main = axKOH.main || {};
axKOH.main.componentManager = axKOH.main.componentManager || {};

// Replace "componentName" with something representing which component this is
axKOH.main.componentManager.componentName = (function () {   
	var componentClass = "c-koh-component-name";
	
	// Main init function; contains the main loop through component instances
	var initComponents = function() {
		// Find all instances of the component on the page
		var $components = $("."+componentClass);
	
		// For each component instance, initialize it
		$components.each( function(componentIndex) {
			var $component = $(this); // the single component instance we're currently operating on
			
			// Do whatever needs to be done for each component instance here.
			// -- componentIndex is provided to be usable for generating component-level unique IDs (ideally with a prefix)
			/* -- Locate elements within the $component via calls to .find() or .children()
			      -- Remember that in any case, .find() or .children() may return multiples, so either reduce to a single
			         element by calling .first() if there should only ever be one, or loop through via .each()
			*/
		});
	};
	
	/* Defines functions exposed through manager object */
    return {
        initialize: function () { 
			initComponents();
        },
		onLoad: function () {
			// call whatever needs to happen on load for all component instances here
		}
    };
})();

$(function () {
    $(document).ready(function() {
        // executes when HTML-Document is loaded and DOM is ready
        axKOH.main.componentManager.componentName.initialize();    
    });
	
	$(window).load(function() {
		// executes after the window is fully loaded, including all images, etc.
		axKOH.main.componentManager.componentName.onLoad();
	});
});