const today = new Date().toISOString().split("T")[0];
const birthDateInput = document.getElementById("birth-date");
birthDateInput.max = today;
birthDateInput.value = today;
// Italy selezionata
const italySelectOptionInput = document.getElementById("Italy#1");
italySelectOptionInput.selected = true;
// password === confirm_password
const onSubmit = event => {
   const passwordInput = document.getElementById("password");
   const confirmPasswordInput = document.getElementById("confirm-password");
   if (passwordInput.value !== confirmPasswordInput.value) {
      event.preventDefault();
      window.alert("Password and Confirm password are different.");
   }
}