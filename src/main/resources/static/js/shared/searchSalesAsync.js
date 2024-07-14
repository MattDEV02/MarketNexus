document.addEventListener('DOMContentLoaded', () => {
   const productNameInput = document.getElementById("product-name"),
      categorySelect = document.getElementById("category"),
      salesContainer = document.getElementById("sales-container");

   const handleSearchSalesChange = () => {
      const searchSalesForm = document.getElementById("search-products-form");
      const URI = searchSalesForm.action;
      if (validateURI(URI)) {
         axios({
            method: searchSalesForm.method,
            url: URI,
            params: {
               "product-name": productNameInput.value,
               category: categorySelect.value,
               isAsyncSearch: true,
            }
         }).then(response => {
            console.log(response);
            if (validateObject(response) && validateObject(response.data) && response.status === 200) {
               salesContainer.innerHTML = response.data;
            }
         }).catch(error => {
            console.error("Error:", error);
         });
      }
   };

   productNameInput.addEventListener("input", handleSearchSalesChange);

   categorySelect.addEventListener("input", handleSearchSalesChange);
});