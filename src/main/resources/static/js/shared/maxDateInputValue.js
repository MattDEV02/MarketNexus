const today = new Date().toISOString().split("T")[0];
let birthDateInput = document.getElementById("birth-date");
if (birthDateInput === undefined || birthDateInput === null) {
   birthDateInput = document.getElementById("birthDate");
}
birthDateInput.max = today;
if (birthDateInput.value === undefined || birthDateInput.value === null || birthDateInput.value === "") {
   birthDateInput.value = today;
}
