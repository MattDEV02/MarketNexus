document.addEventListener("DOMContentLoaded", () => {
   const nationInput = document.getElementById("nation"),
      nationIdInput = document.getElementById("nation-id"),
      autocompleteList = document.getElementById("autocomplete-list");

   nationInput.addEventListener("input", () => {
      const query = nationInput.value;
      if (validateString(query) && query.length >= 3) {
         axios.get("/marketplace/api/nations/search", {
            params: {query}
         })
            .then(response => {
               console.log(response);
               if (validateObject(response) && validateString(response.data) && response.status === 200) {
                  const nations = response.data;
                  autocompleteList.innerHTML = "";
                  const span = document.createElement("span");
                  span.className = "bg-secondary text-light fw-bold py-2 text-center";
                  span.textContent = `Nations (${nations.length}):`;
                  autocompleteList.appendChild(span);
                  nations.forEach(nation => {
                     const a = document.createElement("a");
                     a.className = "list-group-item list-group-item-action rounded-2";
                     a.textContent = nation.name;
                     a.title = "Select a Nation"
                     a.dataset.id = nation.id;
                     autocompleteList.appendChild(a);
                  });
               }
            })
            .catch(error => console.error("Error: ", error));
      } else {
         autocompleteList.innerHTML = "";
      }
   });

   autocompleteList.addEventListener("click", event => {
      const item = event.target;
      if (validateObject(item) && validateObject(item.classList) && item.classList.contains("list-group-item")) {
         nationIdInput.value = item.dataset.id;
         nationInput.value = item.textContent;
         autocompleteList.innerHTML = "";
      }
   });
});