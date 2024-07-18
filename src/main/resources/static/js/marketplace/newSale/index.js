document.addEventListener("DOMContentLoaded", () => {
   const copyProductNameButton = document.getElementById("copy-product-name-button");

   copyProductNameButton.addEventListener("click", event => {
      const productName = document.getElementById("name").value;

      // Usa l'API Clipboard per copiare il testo
      navigator.clipboard.writeText(productName)
         .then(() => window.alert("Product name '" + productName + "' successfully copied to clipboard."))
         .catch(error => console.error("Error: ", error));
   });
});