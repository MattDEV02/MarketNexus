// Italy selezionata
const italySelectOptionInput = document.getElementById("Italy#1");
italySelectOptionInput.selected = true;

// password === confirm_password
document.getElementById('registration-form').addEventListener('submit', (event) => {
   const passwordInput = document.getElementById("password");
   const confirmPasswordInput = document.getElementById("confirm-password");
   if (passwordInput.value !== confirmPasswordInput.value) {
      event.preventDefault();
      window.alert("Password and Confirm password are different.");
   }
});