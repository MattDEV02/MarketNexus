document.addEventListener("DOMContentLoaded", () => {
   const saleBaseURI = baseURI + "sales/sale/";

   const sales = [], orders = [];

   const getSalesEvent = sales => {
      return sales.map(sale => {
         return {
            id: `${sale.id}-${sale.productName}-${sale.inserted_at}`,
            title: sale.productName + " in sale.",
            start: sale.inserted_at,
            url: saleBaseURI + sale.id,
            color: "#FFFF00",
            editable: false,
            classNames: ["sale-event"],
            extendedProps: {
               description: "MarketNexus Sale",
               place: "MarketNexus",
            },
         };
      });
   };

   const getOrdersEvent = orders => {
      return orders.map(order => {
         return {
            id: `${order.id}-${order.productName}-${order.inserted_at}`,
            title: order.productName + " ordered.",
            start: order.inserted_at,
            url: saleBaseURI + order.id,
            color: "#038A03",
            editable: false,
            classNames: ["order-event"],
            extendedProps: {
               description: "MarketNexus Order",
               place: "MarketNexus",
            },
         };
      });
   };
   let calendarEl = document.getElementById("user-calendar");
   let calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: "dayGridMonth",
      headerToolbar: {
         start: "prevYear,nextYear",
         center: "title",
         end: "today prev,next"
      },
      events: [],
   });

   const salesRequest = axios.get(`${baseAPIURI}calendarData/sales`),
      ordersRequest = axios.get(`${baseAPIURI}calendarData/orders`);

   const parallelRequests = [salesRequest, ordersRequest];
   /*
      Definiamo due richieste Axios, request1 e request2, che puntano a due URL diversi.
      Utilizziamo Promise.all([request1, request2]) per eseguire le due richieste in parallelo.
      Quando entrambe le richieste sono completate con successo, la funzione all'interno di .then()
      viene eseguita e riceve un array contenente le risposte delle due richieste.
      Possiamo quindi accedere alle risposte tramite l'array responses e gestirle di conseguenza.
      Se una delle richieste fallisce, la funzione .catch() verrà eseguita e stamperà l'errore accorso.
   */

   Promise.all(parallelRequests)
      .then(responses => {
         const salesResponse = responses[0],
            ordersResponse = responses[1];
         if (validateObject(salesResponse) && validateObject(salesResponse.data) && salesResponse.status === 200) {
            const salesCalendarData = salesResponse.data;
            salesCalendarData.forEach(saleCalendarDataRow => {
               sales.push({
                  id: saleCalendarDataRow.id,
                  productName: saleCalendarDataRow.product.name,
                  inserted_at: saleCalendarDataRow.insertedAt,
               });
            });
            const salesEvent = getSalesEvent(sales);
            salesEvent.forEach(saleEvent => calendar.addEvent(saleEvent));
         }
         if (validateObject(ordersResponse) && validateObject(ordersResponse.data) && ordersResponse.status === 200) {
            const ordersCalendarData = ordersResponse.data;
            ordersCalendarData.forEach(orderCalendarData => {
               orders.push({
                  id: orderCalendarData[0],
                  productName: orderCalendarData[1],
                  inserted_at: orderCalendarData[2],
               });
            });
            const ordersEvent = getOrdersEvent(orders);
            ordersEvent.forEach(orderEvent => calendar.addEvent(orderEvent));
         }
      })
      .catch(error => console.error("Error:", error));

   calendar.render();
});

