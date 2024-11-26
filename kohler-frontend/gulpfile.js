/*  gulpfile.js
    Front end project build for the Kohler website project, driven by Gulp.
*/


/* -------------------------------------------------- */
/* Dependencies and plugins
 */
var gulp = require('gulp');
var path = require('path');
var browserSync = require('browser-sync').create();
var del = require('del');
var pngquant = require('imagemin-pngquant');
var merge = require('merge-stream');
var autoprefixer = require('gulp-autoprefixer');
var concat = require('gulp-concat');
var gulpif = require('gulp-if');
var gutil = require('gulp-util');
var minifyCss = require('gulp-minify-css');
var imagemin = require('gulp-imagemin');
var newer = require('gulp-newer');
var notify = require('gulp-notify');
var plumber = require('gulp-plumber');
var removeEmptyLines = require('gulp-remove-empty-lines');
var rename = require('gulp-rename');
var replace = require('gulp-replace');
var sass = require('gulp-sass');
var sassLint = require('gulp-sass-lint');
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');
var bless = require('gulp-bless');
var watch = require('gulp-watch');

var doNothing = gutil.noop;

/* -------------------------------------------------- */
/* Global variables and configuration
 */

/* Flags */
var isDebug = (gutil.env.type === 'debug');
var isProd = (gutil.env.type === 'prod');

/* Vendor library directories */
var vendor = {};
vendor.bootstrap = {
    fonts: 'bower_components/bootstrap-sass/assets/fonts/**/*',
    scss: 'bower_components/bootstrap-sass/assets/stylesheets/bootstrap/**/*.{scss,sass}',
    js: 'bower_components/bootstrap-sass/assets/javascripts/bootstrap.min.js'
};
vendor.fontawesome = {
    fonts: 'bower_components/font-awesome/fonts/**/*',
    scss: 'bower_components/font-awesome/scss/**/*.{scss,sass}'
};
vendor.lodash = {
    js: 'bower_components/lodash/lodash.min.js'
};
vendor.slick = {
    fonts: 'bower_components/slick-carousel/slick/fonts/**/*',
    scss: 'bower_components/slick-carousel/slick/*.{scss,sass}',
    js: 'bower_components/slick-carousel/slick/*.min.js'
};
vendor.masonry = {
    js: 'bower_components/masonry/dist/masonry.pkgd.min.js'
};
vendor.imagesloaded = {
    js: 'bower_components/imagesloaded/imagesloaded.pkgd.min.js'
};
vendor.nouislider = {
    css: 'bower_components/nouislider/src/*.css',
    js: 'bower_components/nouislider/distribute/nouislider.min.js'
};
vendor.remodal = {
    css: 'bower_components/remodal/dist/*.css',
    js: 'bower_components/remodal/dist/remodal.min.js'
};
vendor.qtip2 = {
    css: ['bower_components/qtip2/*.css', '!bower_components/qtip2/*.min.css'],
    js: 'bower_components/qtip2/jquery.qtip.min.js'
};
vendor.zoom = {
    js: 'bower_components/jquery-zoom/jquery.zoom.min.js'
};
vendor.magnify = {
    js: 'bower_components/jquery-magnify/jquery.magnify.min.js'
};
vendor.imageviewer = {
    js: 'bower_components/imageviewer/imageviewer.min.js'
};
vendor.jquery = {
    js: 'bower_components/jquery/dist/jquery.min.js'
};
vendor.parsley = {
    js: 'bower_components/parsleyjs/dist/parsley.min.js'
};
vendor.parsley_pt_br = {
    js: 'bower_components/parsleyjs/dist/i18n/pt-br.js'
};

/* Source directory */
var src = {};
src.root = 'src/';
src.html = src.root + 'html/';
src.scss = src.root + 'css/scss/';
src.css = src.root + 'css/';
src.js = src.root + 'js/';
src.fonts = src.root + 'fonts/';
src.imgs = src.root + 'images/';
src.icons = src.root + 'icons/';
src.custom = src.js + 'custom/';

/* Target directory for static HTML Comps build */
var htmlComps = {};
htmlComps.root = 'public/';
htmlComps.html = htmlComps.root + 'html/';
htmlComps.css = htmlComps.root + 'css/';
htmlComps.js = htmlComps.root + 'js/';
htmlComps.fonts = htmlComps.root + 'fonts/';
htmlComps.imgs = htmlComps.root + 'images/';
htmlComps.icons = htmlComps.root + 'icons/';

var webfiles = {
    srcroot: 'public/',
    destroot: '../repository-data/webfiles/src/main/resources/',
    sites: [
        '/site'
    ]
};


/* Plugin configuration options */
var options = {
    sass: {
        includePaths: [
            src.scss.root
        ],
        outputStyle: 'expanded',
        precision: 10
    },
    autoprefixer: {
        browsers: ['last 2 versions'],
        cascade: true
    },
    uglify: {
        mangle: false,
        preserveComments: 'license'
    },
    minifyCss: {
        debug: true,
        mediaMerging: false
    },
    browsersync: {
        server: {
            baseDir: htmlComps.root,
            directory: true
        }
    },
    imagemin: {
        progressive: true,
        svgoPlugins: [{
            removeViewBox: false
        }],
        use: [pngquant()]
    }
};

/* -------------------------------------------------- */
/* Tasks
 */



var moveFiles = function(site) {

    var src = path.join(webfiles.srcroot + '**/*.*');
    console.log('[Move Files] Start');
    webfiles.sites.forEach(function(site) {
        var dest = path.join(webfiles.destroot + site);
        console.log('[Move Files] Moving front end assets to:' + dest);
        gulp.src(src)
            .pipe(gulp.dest(dest))
            .on('error', function(error) {
                err = true;
                console.log('[Move Files][ERROR] The following error occured:[' + error + ']');
            });
    });
    console.log('[Move Files] Complete');
};


gulp.task('develop', ['default'], moveFiles);

/* Primary Task
   Will be run by Maven build with the --type=prod flag */
//gulp.task ( 'default', ['pre-build','clean','setup','fonts','images','scripts','styles','html'] );
gulp.task('default', ['pre-build', 'clean', 'setup', 'fonts', 'images', 'scripts', 'custom', 'styles']);


/* Realized there really wasn't any reason to leave the HTML out of the default task */
gulp.task('comps', ['default']);

/* Pre-Build */
gulp.task('pre-build', function() {
    console.log("Initiating front end project build");
});

/* Clean */
var cleanTask = function() {
    return del.sync([
        /* Remove output directory */
        htmlComps.root,
        /* Remove any compiled CSS from src */
        src.css + "*.min.css",
        /* Remove jquery */
        src.js + "jquery/",
        /* Remove bootstrap fonts/scss/scripts */
        src.fonts + "bootstrap/",
        src.scss + "bootstrap/",
        src.js + "bootstrap/",
        /* Remove font-awesome fonts/scss */
        src.fonts + "font-awesome/",
        src.scss + "font-awesome/",
        /* Remove lodash */
        src.js + "lodash/",
        /* Remove slick scripts */
        src.fonts + "slick/",
        src.scss + "slick/",
        src.js + "slick/",
        /* Remove masonry scripts */
        src.js + "masonry/",
        /* Remove imagesloaded scripts */
        src.js + "imagesloaded/",
        /* Remove nouislider */
        src.js + "nouislider/",
        src.scss + "nouislider/",
        /* Remove remodal */
        /*src.scss + "remodal/",
        src.js + "remodal/",*/
        /* Remove qTip2 */
        src.scss + "qTip2/",
        src.js + "qTip2/",
        /* Remove zoom */
        src.js + "zoom/",
        /* Remove magnify */
        src.js + "magnify/",
        /* Remove imageviewer */
        src.js + "imageviewer/",
        /* Remove parsley */
        src.js + "parsleyjs/"
    ]);
};
gulp.task('clean', cleanTask);

/* Set Up */
gulp.task('setup', ['jquery', 'bootstrap', 'font-awesome', 'lodash', 'masonry', 'imagesloaded', 'slick', 'nouislider', 'remodal', 'qtip2', 'zoom', 'magnify', 'imageviewer', 'parsley', 'parsley_pt_br']);

var jqueryTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.jquery.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying jquery javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "jquery/"));
};
gulp.task('jquery', jqueryTask);

var bootstrapTask = function() {
    /* Note: This task processes three separate streams for fonts, scripts,
       and scss, and then merges them to return a single stream */

    /* Read the fonts from the bower directory */
    var fonts = gulp.src(vendor.bootstrap.fonts)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying Bootstrap fonts"))
        /* Copy the fonts to the bootstrap fonts directory */
        .pipe(gulp.dest(src.fonts)); // Bootstrap fonts are already in a bootstrap subfolder

    /* Read the scss from the bower directory */
    var scss = gulp.src(vendor.bootstrap.scss)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying Bootstrap styles"))
        /* Copy the scss to the bootstrap scss directory */
        .pipe(gulp.dest(src.scss + "bootstrap/"));

    /* Read the javascript from the bower directory */
    var js = gulp.src(vendor.bootstrap.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying Bootstrap javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "bootstrap/"));

    /* Merge streams from the multiple copy tasks */
    return merge(fonts, scss, js);
};
gulp.task('bootstrap', bootstrapTask);

var fontAwesomeTask = function() {
    /* Note: This task processes three separate streams for fonts, scripts,
       and scss, and then merges them to return a single stream */

    /* Read the fonts from the bower directory */
    var fonts = gulp.src(vendor.fontawesome.fonts)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying Font Awesome fonts"))
        /* Copy the fonts to the bootstrap fonts directory */
        .pipe(gulp.dest(src.fonts + "font-awesome/"));

    /* Read the scss from the bower directory */
    var scss = gulp.src(vendor.fontawesome.scss)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying Font Awesome styles"))
        /* Copy the scss to the bootstrap scss directory */
        .pipe(gulp.dest(src.scss + "font-awesome/"));

    /* Merge streams from the multiple copy tasks */
    return merge(fonts, scss);
};
gulp.task('font-awesome', fontAwesomeTask);

var lodashTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.lodash.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying lodash javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "lodash/"));
};
gulp.task('lodash', lodashTask);

var masonryTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.masonry.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying masonry javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "masonry/"));
};
gulp.task('masonry', masonryTask);

var imagesloadedTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.imagesloaded.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying imagesloaded javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "imagesloaded/"));
};
gulp.task('imagesloaded', imagesloadedTask);

var nouisliderTask = function() {
    /* Read the scss from the bower directory */
    var scss = gulp.src(vendor.nouislider.css)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying nouislider styles"))
        /* Rename to scss with _ prefix */
        .pipe(rename({ prefix: "_", extname: ".scss" }))
        /* Copy the scss to the nouislider scss directory */
        .pipe(gulp.dest(src.scss + "nouislider/"));

    /* Read the javascript from the bower directory */
    var js = gulp.src(vendor.nouislider.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying nouislider javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "nouislider/"));

    /* Merge streams from the multiple copy tasks */
    return merge(scss, js);
};
gulp.task('nouislider', nouisliderTask);

var remodalTask = function() {
    /* Read the scss from the bower directory */
    var scss = gulp.src(vendor.remodal.css)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying remodal styles"))
        /* Rename to scss with _ prefix */
        .pipe(rename({ prefix: "_", extname: ".scss" }))
        /* Copy the scss to the remodal scss directory */
        .pipe(gulp.dest(src.scss + "remodal/"));

    /* Read the javascript from the bower directory */
    var js = gulp.src(vendor.remodal.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying remodal javascript"))
        /* Copy the javascript to the remodal javascript directory */
        .pipe(gulp.dest(src.js + "remodal/"));

    /* Merge streams from the multiple copy tasks */
    return merge(scss, js);
};
gulp.task('remodal', remodalTask);

var qtip2Task = function() {
    /* Read the scss from the bower directory */
    var scss = gulp.src(vendor.qtip2.css)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying qtip2 styles"))
        /* Rename to scss with _ prefix */
        .pipe(rename({ prefix: "_", extname: ".scss" }))
        /* Copy the scss to the qtip2 scss directory */
        .pipe(gulp.dest(src.scss + "qtip2/"));

    /* Read the javascript from the bower directory */
    var js = gulp.src(vendor.qtip2.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying qtip2 javascript"))
        /* Copy the javascript to the qtip2 javascript directory */
        .pipe(gulp.dest(src.js + "qtip2/"));

    /* Merge streams from the multiple copy tasks */
    return merge(scss, js);
};
gulp.task('qtip2', qtip2Task);

var slickTask = function() {
    /* Note: This task processes three separate streams for scripts,
       and scss, and then merges them to return a single stream */

    /* Read the fonts from the bower directory */
    var fonts = gulp.src(vendor.slick.fonts)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying Bootstrap fonts"))
        /* Copy the fonts to the slick fonts directory */
        .pipe(gulp.dest(src.fonts + "slick/"));

    /* Read the scss from the bower directory */
    var scss = gulp.src(vendor.slick.scss)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying slick styles"))
        /* Copy the scss to the slick scss directory */
        .pipe(gulp.dest(src.scss + "slick/"));

    /* Read the javascript from the bower directory */
    var js = gulp.src(vendor.slick.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying slick javascript"))
        /* Copy the javascript to the slick javascript directory */
        .pipe(gulp.dest(src.js + "slick/"));

    /* Merge streams from the multiple copy tasks */
    return merge(scss, js);
};
gulp.task('slick', slickTask);

var zoomTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.zoom.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying zoom javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "zoom/"));
};
gulp.task('zoom', zoomTask);

var magnifyTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.magnify.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying magnify javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "magnify/"));
};
gulp.task('magnify', magnifyTask);

var imageviewerTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.imageviewer.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying imageviewer javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        .pipe(gulp.dest(src.js + "imageviewer/"));
};
gulp.task('imageviewer', imageviewerTask);

var parsleyTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.parsley.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying parsley javascript"))
        /* Copy the javascript to the parsley javascript directory */
        .pipe(gulp.dest(src.js + "parsley/"));
};
gulp.task('parsley', parsleyTask);

var parsleyPTBRTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(vendor.parsley_pt_br.js)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying parsley_pt_br javascript"))
        /* Copy the javascript to the parsley javascript directory */
        .pipe(rename("pt-br.min.js"))
        .pipe(gulp.dest(src.js + "parsley/"));
};
gulp.task('parsley_pt_br', parsleyPTBRTask);

/* Fonts */
var fontsTask = function() {
    /* Read the fonts from the source directory */
    return gulp.src(src.fonts + '**/*')

    /* Handle any errors with custom handler */
    .pipe(customErrorHandler("Error while copying fonts"))

    /* Only work with any fonts that have changed */
    .pipe(newer(htmlComps.fonts))

    /* Copy the fonts to the destination font directory */
    .pipe(gulp.dest(htmlComps.fonts));
};
gulp.task('fonts', ['setup'], fontsTask);

/* Images */
var imagesTask = function() {
    /* Copy image files */
    var imageFiles = customImageHandler(src.imgs + '**/*', htmlComps.imgs);

    /* Copy icon files */
    var iconFiles = customImageHandler(src.icons + '**/*', htmlComps.icons);

    /* Merge the streams */
    return merge(imageFiles, iconFiles);
};
gulp.task('images', ['setup'], imagesTask);

/* Scripts */
var scriptsTask = function() {
    /* Note: We want to handle scripts that need minifying separate
       from scripts that don't, so we'll make two streams and merge */

    /* Master script file; most scripts will be combined into this */
    var masterScript = customScriptsHandler(
        [src.js + 'utils.js',
            src.js + '**/*.js',
            '!' + src.js + '**/*.min.js',
            '!' + src.js + 'custom/*.js',
            '!' + src.js + 'c-koh-banner-carousel.js',
            '!' + src.js + 'c-koh-promo-grid.js',
            '!' + src.js + 'c-koh-collection-promo.js',
            '!' + src.js + 'c-koh-product-details.js',
            '!' + src.js + 'c-koh-product-search.js',
            '!' + src.js + 'c-koh-product-faceted-search.js',
            '!' + src.js + 'c-koh-tabbed-faqs.js',
            '!' + src.js + 'c-koh-simple-content.js',
            '!' + src.js + 'c-koh-press-list.js',
            '!' + src.js + 'c-koh-press-release.js',
            '!' + src.js + 'c-koh-compare-products.js',
            '!' + src.js + 'c-koh-contact-form.js',
            '!' + src.js + 'c-koh-rsg-contact-form.js',
            '!' + src.js + 'c-koh-mu-contact-form.js',
            '!' + src.js + 'c-koh-dealer-list.js',
            '!' + src.js + 'c-koh-banner-static.js',
            '!' + src.js + 'c-koh-tabbed-article.js'
        ],
        htmlComps.js,
        true, // minify the files
        !isProd, // generate source maps if not in production
        true, // concatenate the files into one
        'koh-master.js' // what to name the concatenated minified file
    );

    /* Handle any component-specific javascript files that should not
       be in the main script file in separate streams here */
    var componentScripts = customScriptsHandler(
        [src.js + 'c-koh-banner-carousel.js',
            src.js + 'c-koh-promo-grid.js',
            src.js + 'c-koh-collection-promo.js',
            src.js + 'c-koh-product-details.js',
            src.js + 'c-koh-product-search.js',
            src.js + 'c-koh-product-faceted-search.js',
            src.js + 'c-koh-tabbed-faqs.js',
            src.js + 'c-koh-simple-content.js',
            src.js + 'c-koh-press-list.js',
            src.js + 'c-koh-press-release.js',
            src.js + 'c-koh-compare-products.js',
            src.js + 'c-koh-contact-form.js',
            src.js + 'c-koh-rsg-contact-form.js',
            src.js + 'c-koh-mu-contact-form.js',
            src.js + 'c-koh-dealer-list.js',
            src.js + 'c-koh-banner-static.js',
            src.js + 'c-koh-tabbed-article.js'
        ],
        htmlComps.js,
        true, // minify the files
        !isProd, // generate source maps if not in production
        false, // concatenate the files into one
        '' // not used because we're not concatenating
    );

    /* Copy over any of the script files we don't want to touch (mostly the pre-minified ones) */
    var copiedScripts = customScriptsHandler(
        src.js + '**/*.min.js',
        htmlComps.js,
        false, // don't minify the files
        false, // don't generate source maps
        false, // don't concatenate files
        '' // will not be used since we're not concatenating
    );

    /* Merge the streams (but don't cross the streams) */
    return merge(masterScript, componentScripts, copiedScripts);
};
gulp.task('scripts', ['setup'], scriptsTask);

/* Styles */
var stylesTask = function() {
    /* Note: We want to handle SCSS files separately from plain CSS files,
       so again we're creating multiple streams and merging */

    /* Process SCSS files */
    var scssFiles = customCssHandler(
        [src.scss + '**/*.{scss,sass}', '!' + src.scss + '**/_*.{scss,sass}'],
        htmlComps.css,
        true, // minify css
        true, // run through bless to avoid IE selector limit
        isProd ? false : true, // generate source maps if not a production build
        false, // not sure why we'd want them concatenated, so not going to for now
        '' // not used since we're not concatenating
    );

    /* Copy over any plain old css files */
    var cssFiles = customCssHandler(
        [src.scss + '**/*.css', '!' + src.scss + '**/*.min.css'],
        htmlComps.css,
        true, // minify css
        true, // run through bless to avoid IE selector limit
        isProd ? false : true, // generate source maps if not a production build
        false, // not sure why we'd want them concatenated, so not going to for now
        '' // not used since we're not concatenating
    );

    /* Merge the streams (but don't cross the streams) */
    return merge(scssFiles, cssFiles);
};
gulp.task('styles', ['setup'], stylesTask);

/* HTML */
var htmlTask = function() {
    return customHtmlHandler(
        src.html + '**/*.html',
        htmlComps.html
    );
};
gulp.task('html', ['setup'], htmlTask);

/* -------------------------------------------------- */
/* Automagic Watching/Reloading Tasks
 */

/* Watch */
/* For right now, watch task assumes you want to build the static comps. Fancier stuff to come later. */
gulp.task('watch', ['develop', 'browserSync'], function() {
    /* Watch source directories for changes */
    watch(src.fonts + '**/*', fontsTask);
    watch([src.imgs + '**/*', src.icons + '**/*'], imagesTask);
    watch(src.js + '**/*.js', scriptsTask);
    //watch( [src.scss+'**/*.{scss,sass,css}'], stylesTask );
    watch(src.scss + '**/*', stylesTask);
    watch(src.html + '**/*.html', htmlTask);
});

gulp.task('browserSync', ['html'], function() {
    /* Initialize browsersync server to reload when output files change */
    browserSync.init(
        [
            htmlComps.fonts + '**/*',
            htmlComps.imgs + '**/*',
            htmlComps.icons + '**/*',
            htmlComps.js + '**/*',
            htmlComps.css + '**/*',
            htmlComps.html + '**/*'
        ],
        options.browsersync
    );
});

/* -------------------------------------------------- */
/* Helper functions
 */

/* Custom Error Handler */
function customErrorHandler(caption) {
    return plumber({
        errorHandler: notify.onError({
            title: caption || "Error running Gulp",
            message: "<%= error.message %>",
            sound: "Ping" // comment this out if sound is not desired
        })
    });
}

/* Custom Image Handler */
function customImageHandler(sourceFiles, targetFiles) {
    /* Read the images and icons from the source directory */
    return gulp.src(sourceFiles)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while processing images"))
        /* Only work with any images that have changed */
        .pipe(newer(targetFiles))
        /* Minify images */
        .pipe(imagemin(options.imagemin))
        /* Copy the fonts to the destination font directory */
        .pipe(gulp.dest(targetFiles));
}

/* Custom Scripts Processor */
function customScriptsHandler(sourceFiles, targetFiles, doMinify, doSourceMaps, doConcat, concatFilename) {
    /* Read the scripts from the source directory (excluding bootstrap) */
    return gulp.src(sourceFiles)
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while processing custom scripts handler"))
        /* Only work with any script files that have changed */
        .pipe(newer(targetFiles))
        /* Initialize generating source maps */
        .pipe(doSourceMaps ? sourcemaps.init() : doNothing())
        /* Concatenate into a single master script file */
        .pipe(doConcat ? concat(concatFilename) : doNothing())
        /* Minify the scripts */
        .pipe(doMinify ? uglify(options.uglify) : doNothing())
        .pipe(doMinify ? rename({ extname: ".min.js" }) : doNothing())
        /* Write out the source maps */
        .pipe(doSourceMaps ? sourcemaps.write() : doNothing())
        /* Copy the files to the destination font directory */
        .pipe(gulp.dest(targetFiles));
}

function customCssHandler(sourceFiles, targetFiles, doMinify, doBless, doSourceMaps, doConcat, concatFilename) {
    /* Handle any errors with custom handler */
    return customErrorHandler("Error while processing custom SASS handler")
        /* Read the scripts from the source directory (excluding bootstrap) */
        .pipe(gulp.src(sourceFiles))
        /* Only work with any files that have changed */
        .pipe(newer(targetFiles))
        /* Initialize generating source maps */
        .pipe(doSourceMaps ? sourcemaps.init() : doNothing())
        /* Process sass if the file type is .scss or .sass */
        .pipe(gulpif(/\.(scss|sass)$/, sass(options.sass)))
        .pipe(gulpif(/\.(scss|sass)$/, sassLint()))
        .pipe(gulpif(/\.(scss|sass)$/, sassLint.format()))
        .pipe(gulpif(/\.(scss|sass)$/, sassLint.failOnError()))
        /* Run autoprefixer */
        .pipe(autoprefixer(options.autoprefixer))
        /* Concatenate the files if desired */
        .pipe(doConcat ? concat(concatFilename) : doNothing())
        /* Minify the CSS if desired */
        .pipe(doMinify ? minifyCss(options.minifyCss) : doNothing())
        .pipe(doMinify ? rename({ extname: ".min.css" }) : doNothing())
        /* Write out the source maps */
        .pipe(doSourceMaps ? sourcemaps.write(".") : doNothing())
        /* Run bless to split out files for the IE selector limit */
        .pipe(doBless ? gulpif(/\.css$/, bless()) : doNothing())
        /* Copy the files to the destination font directory */
        .pipe(gulp.dest(targetFiles));
}

function customHtmlHandler(sourceFiles, targetFiles) {
    /* Handle any errors with custom handler */
    return customErrorHandler("Error while processing custom HTML handler")
        /* Read the scripts from the source directory (excluding bootstrap) */
        .pipe(gulp.src(sourceFiles))
        /* Only work with any files that have changed */
        .pipe(newer(targetFiles))
        /* Clean up DreamWeaver Templating comments */
        .pipe(replace(/<!-- (Instance|Template)(Begin|End|Param)(Editable)*( )*.* -->/g, ''))
        .pipe(replace(/<!-- #(Begin|End)LibraryItem( )*.* -->/g, ''))
        /* Remove blank lines */
        .pipe(removeEmptyLines())
        /* Copy the files to the destination font directory */
        .pipe(gulp.dest(targetFiles));
}


var customTask = function() {
    /* Read the javascript from the bower directory */
    return gulp.src(src.custom + '**/*')
        /* Handle any errors with custom handler */
        .pipe(customErrorHandler("Error while copying custom javascript"))
        /* Copy the javascript to the bootstrap javascript directory */
        
        .pipe( uglify(options.uglify) )
        .pipe( rename({ extname: ".min.js" }) )

        .pipe(gulp.dest(htmlComps.js + "custom/"));
};
gulp.task('custom', customTask);
