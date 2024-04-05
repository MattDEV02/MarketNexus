const regExp = new RegExp("^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$");

const validateURL = (URL) =>
   URL !== undefined && URL !== null && URL !== "" && URL !== " " &&
   regExp.test(
      URL
   );


const sendDeleteCartRequest = (URI) => {
   // Esegui una richiesta al controller Spring Boot
   let xhr = new XMLHttpRequest();
   const method = "GET",
      async = true;
   xhr.open(method, URI, async);
   xhr.onload = () => {
      console.log(`Request: [ ${method} ${URI} ] ${xhr.status === 200 ? "Sent" : "NOT sent"}.`);
   };
   xhr.onreadystatechange = () => {
      if (xhr.readyState === XMLHttpRequest.DONE) {
         if (xhr.status === 302) {
            const redirectURL = xhr.getResponseHeader("Location");
            validateURL(redirectURL) ?
               window.location.href = redirectURL :
               window.alert("There was a problem with the Server.");
         }
      }
   };
   xhr.send();
}

const deleteCartButton = document.getElementById('delete-cart');

deleteCartButton.addEventListener('click', (event) => {
   event.preventDefault();
   if (window.confirm("Are you sure you want to delete this cart line?")) {
      sendDeleteCartRequest(deleteCartButton.href);
      window.alert("Cart line deleted.");
   } else {
      window.alert("Cart line not deleted.");
   }
});