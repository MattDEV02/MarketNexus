document.addEventListener("readystatechange", () => {
      const body = document.body,
         loader = document.getElementById("loader");
      if (document.readyState !== "complete") {
         body.style.visibility = "hidden";
         loader.style.visibility = "visible";
      } else {
         loader.style.display = "none";
         body.style.visibility = "visible";
      }
   }
);