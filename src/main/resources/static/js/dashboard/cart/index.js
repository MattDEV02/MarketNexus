document.addEventListener("DOMContentLoaded", () => {
   const cartContainer = document.getElementById("cart-container"),
      baseUrl = "http://localhost:80/dashboard/cart/updateCartLineItemQuantity/";

   cartContainer.addEventListener("change", event => {
      if (validateObject(event) && validateObject(event.target) && event.target.classList.contains("cartlineitem-quantity-input")) {
         const cartLineItemQuantityInput = event.target;
         // id e quantity del CartLineItem i-esimo.
         const
            id = cartLineItemQuantityInput.id.replace("quantity#", ""),
            quantity = parseInt(cartLineItemQuantityInput.value, 10);
         const url = baseUrl + id;
         const data = {
            quantity
         };
         if (validateURI(url)) {
            axios.post(url, data)
               .then(response => {
                  console.log(response);
                  cartContainer.innerHTML = response.data;
               })
               .catch(error => console.error("Error:", error))
               .finally(() => cartLineItemQuantityInput.focus());
         }
      }
   });
});

