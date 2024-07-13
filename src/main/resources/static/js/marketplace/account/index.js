document.addEventListener("DOMContentLoaded", () => {
   const
      cancelButton = document.getElementById("cancel-button"),
      updateAccountForm = document.getElementById("update-account-form"),
      confirmDeleteAccountButton = document.getElementById("confirm-delete-account-button");

   cancelButton.addEventListener("click", () => {
      updateAccountForm.reset();
   });

   confirmDeleteAccountButton.addEventListener("click", event => {
      event.preventDefault();
      const URI = confirmDeleteAccountButton.href;
      if (validateURI(URI)) {
         axios.delete(confirmDeleteAccountButton.href)
            .then(response => {
               window.location.href = "/logout";
               console.log(response);
            })
            .catch(error => console.error("Error:", error));
      }
   });

   const confirmDeleteSaleButtons = document.querySelectorAll(".confirm-delete-sale-button");

   confirmDeleteSaleButtons.forEach(confirmDeleteSaleButton => {
      confirmDeleteSaleButton.addEventListener("click", event => {
         event.preventDefault();
         const URI = confirmDeleteSaleButton.href;
         if (validateURI(URI)) {
            axios.delete(confirmDeleteSaleButton.href)
               .then(response => {
                  console.log(response);
                  if (validateObject(response) && validateObject(response.data) && validateString(response.data.redirect)) {
                     const saleId = confirmDeleteSaleButton.id.replace("confirm-delete-sale-button-", "");
                     document.getElementById("close-confirm-delete-sale-modal-" + saleId).click();
                     window.location.href = response.data.redirect;
                  }
               })
               .catch(error => console.error("Error:", error));
         }
      });
   });

});