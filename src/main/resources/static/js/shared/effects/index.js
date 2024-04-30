// Mostra il corpo della pagina con fadeIn effect quando il DOM è pronto
document.addEventListener('DOMContentLoaded', function () {
   const body = document.body;
   body.style.display = 'block'; // Mostra il corpo della pagina
   fadeIn(body); // Applica l'effetto fadeIn
});

const fadeInTimeout = 95; // MS

// Funzione per applicare l'effetto fadeIn a un elemento
const fadeIn = element => {
   let opacity = 0;
   const interval = setInterval(() => {
      if (opacity < 1) {
         opacity += 0.05; // Incrementa l'opacità gradualmente
         element.style.opacity = opacity;
      } else {
         clearInterval(interval); // Interrompe l'intervallo quando l'opacità raggiunge 1
      }
   }, fadeInTimeout); // Intervallo di aggiornamento dell'opacità (50 ms)
}