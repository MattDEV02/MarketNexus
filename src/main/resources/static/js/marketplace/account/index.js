const
   cancelButton = document.getElementById("cancel-button"),
   updateAccountForm = document.getElementById("update-account-form");


cancelButton.addEventListener("click", () => {
   updateAccountForm.reset();
});

