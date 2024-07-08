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
      axios.delete(confirmDeleteAccountButton.href)
         .then(response => {
            window.location.href = "/logout";
            console.log(response);
         })
         .catch(error => console.error(error));
   });
});