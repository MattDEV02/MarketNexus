const
   deleteAccountButton = document.getElementById("delete-account-button"),
   cancelButton = document.getElementById("cancel-button"),
   updateAccountForm = document.getElementById("update-account-form");


cancelButton.addEventListener("click", () => {
   updateAccountForm.reset();
});

