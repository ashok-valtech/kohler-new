$(function () {
      $(".chat-open-dialog").click(function () {
        $(this).toggleClass("active");
        $('.chat-popup').toggleClass("active");
        $('.chat-button-destroy').toggleClass("active");
      });
    });

$(function () {
  $(".chat-button-destroy").click(function () {
    $('.chat-popup').removeClass("active");
    $('.chat-open-dialog').removeClass("active");
    $(this).removeClass("active");
  });
});