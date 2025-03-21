window.addEventListener("load", function () {
  const list = document.querySelector("ol.koh-cmf-tab-list");
  const listItems = document.querySelectorAll(".koh-cmf-tab-list li");

  listItems.forEach(function (item) {
    item.addEventListener("click", function (e) {
      listItems.forEach(function (item) {
        item.classList.remove("active");
      });

      this.classList.add("active");
      sessionStorage.setItem("selected-tab", this.id);
      const rect = this.getBoundingClientRect();
      if (
        rect.left < list.offsetLeft ||
        rect.right > list.offsetLeft + list.clientWidth
      ) {
        list.scroll({
          left:
            rect.left - list.offsetLeft - (list.clientWidth - rect.width) / 2,
          behavior: "smooth",
        });
      }
    });
    const activeListItem = sessionStorage.getItem("selected-tab");

    if (activeListItem) {
      const activeItem = document.getElementById(activeListItem);
      if (activeItem) {
        activeItem.classList.add("active");
        const rect = activeItem.getBoundingClientRect();
        if (
          rect.left < list.offsetLeft ||
          rect.right > list.offsetLeft + list.clientWidth
        ) {
          list.scroll({
            left:
              rect.left - list.offsetLeft - (list.clientWidth - rect.width) / 2,
            behavior: "smooth",
          });
        }
      }
    }
  });
});
