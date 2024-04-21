const
   deleteAccountButton = document.getElementById("delete-account-button"),
   cancelButton = document.getElementById("cancel-button"),
   updateAccountForm = document.getElementById("update-account-form");

const sendDeleteAccountRequest = (URI) => {
   if (validateURI(URI)) {
      window.location.href = URI;
   } else {
      window.alert("Server error.");
   }
}

cancelButton.addEventListener("click", () => {
   updateAccountForm.reset();
});

deleteAccountButton.addEventListener("click", (event) => {
   event.preventDefault();
   if (window.confirm("Are you sure you want to permanently delete your MarketNexus account ?")) {
      sendDeleteAccountRequest(deleteAccountButton.href);
   } else {
      window.alert("You are still with us !");
   }
});