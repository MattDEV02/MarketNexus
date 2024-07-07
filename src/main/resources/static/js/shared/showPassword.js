document.addEventListener("DOMContentLoaded", () => {
   const togglePasswordVisibilityButton = document.getElementById('toggle-password-visibility');

   const passwordInput = document.getElementById('password');

   togglePasswordVisibilityButton.addEventListener('click', () => {
      // Cambia il tipo di input da password a text o viceversa
      const eyeIcon = document.getElementById('eye-icon');
      if (passwordInput.type === 'password') {
         eyeIcon.classList.remove('fa-eye');
         eyeIcon.classList.add('fa-eye-slash');
         passwordInput.type = 'text';
      } else {
         eyeIcon.classList.remove('fa-eye-slash');
         eyeIcon.classList.add('fa-eye');
         passwordInput.type = 'password';
      }
   });
});