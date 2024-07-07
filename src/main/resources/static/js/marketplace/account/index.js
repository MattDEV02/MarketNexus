document.addEventListener("DOMContentLoaded", () => {
   const
      cancelButton = document.getElementById("cancel-button"),
      updateAccountForm = document.getElementById("update-account-form"),
      confirmUpdateAccountButton = document.getElementById("confirm-update-account-button"),
      confirmDeleteAccountButton = document.getElementById("confirm-delete-account-button");

   cancelButton.addEventListener("click", () => {
      updateAccountForm.reset();
   });
   /*
      confirmUpdateAccountButton.addEventListener("click", event => {
         event.preventDefault();
         axios.put(updateAccountForm.action);
      });
   */
   confirmDeleteAccountButton.addEventListener("click", event => {
      event.preventDefault();
      axios.delete(confirmDeleteAccountButton.href)
         .then(response => {
            window.location.href = "/logout";
            console.log(response);
         })
         .catch(error => console.log(error));
   });
});