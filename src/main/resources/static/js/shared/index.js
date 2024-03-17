document.getElementById('toggle-password').addEventListener('click', function () {
   const passwordInput = document.getElementById('password'),
      eyeIcon = document.getElementById('eye-icon');
   // Cambia il tipo di input da password a text o viceversa
   if (passwordInput.type === 'password') {
      eyeIcon.classList.remove('fa-eye-slash');
      eyeIcon.classList.add('fa-eye');
      passwordInput.type = 'text';
   } else {
      eyeIcon.classList.remove('fa-eye');
      eyeIcon.classList.add('fa-eye-slash');
      passwordInput.type = 'password';
   }
});