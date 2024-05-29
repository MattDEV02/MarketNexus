document.addEventListener('DOMContentLoaded', () => {
   // Inizializza tutti i tooltip al caricamento della pagina
   const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
   tooltipTriggerList.map(tooltipTriggerEl =>
      new bootstrap.Tooltip(tooltipTriggerEl)
   ); // const tooltipList
});
