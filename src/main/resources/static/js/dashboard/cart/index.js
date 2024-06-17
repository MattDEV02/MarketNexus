const cartLineItemQuantityInputs = document.querySelectorAll(".cartlineitem-quantity-input"),
   baseUrl = "http://localhost:80/dashboard/cart/updateCartLineItemQuantity/";


cartLineItemQuantityInputs.forEach(cartLineItemQuantityInput => {
   cartLineItemQuantityInput.addEventListener("change", event => {
      event.preventDefault();
      const
         id = cartLineItemQuantityInput.id.replace("quantity#", ""),
         quantity = parseInt(cartLineItemQuantityInput.value, 10);
      const url = baseUrl + id;
      const data = {
         quantity
      };
      axios.post(url, data)
         .then(response => {
            console.log(response);
            console.log(response.data);
            //document.getElementById("cartContainer").innerHTML = response.data;
            window.location.reload();
         })
         .catch(error => console.error("Error:", error));
   });
});