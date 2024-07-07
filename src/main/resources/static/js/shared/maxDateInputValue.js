document.addEventListener("DOMContentLoaded", () => {
   const today = new Date().toISOString().split("T")[0];
   let birthDateInput = document.getElementById("birth-date");
   if (!validateObject(birthDateInput)) {
      birthDateInput = document.getElementById("birthDate");
   }
   birthDateInput.max = today;
   if (!validateObject(birthDateInput.value)) {
      birthDateInput.value = today;
   }

});