document.addEventListener("DOMContentLoaded", () => {
   const map = L.map("map").setView([0, 0], 1.5);

   L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
      minZoom: 1,
      maxZoom: 17,
      attribution: "&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a>"
   }).addTo(map);


// control that shows state info on hover
   const info = L.control();

   info.onAdd = function (map) {
      this._div = L.DomUtil.create("div", "info");
      this.update();
      return this._div;
   };

   info.update = function (props) {
      const contents = "Click a state with a marker";
      this._div.innerHTML = `<h4>Nations that use MarketNexus</h4>${contents}`;
   };

   info.addTo(map);


   const highlightNation = nationToNumberOfUsers => {
      axios.get(`https://nominatim.openstreetmap.org/search?format=json&q=${nationToNumberOfUsers.nationName}`)
         .then(response => {
            console.log(response);
            // Se ci sono risultati, prendi le coordinate del primo risultato
            if (validateObject(response) && validateObject(response.data) && response.status === 200) {
               const coordinatesData = response.data;
               L.marker([coordinatesData[0].lat, coordinatesData[0].lon])
                  .addTo(map)
                  .bindPopup(`${nationToNumberOfUsers.nationName} has ${nationToNumberOfUsers.numbersOfUsers} MarketNexus ${nationToNumberOfUsers.numbersOfUsers > 1 ? "users" : "user"}.`);
            } else {
               console.warn(`Nation "${nationToNumberOfUsers.nationName}" not found.`);
            }
         })
         .catch(error => console.error("Error:", error));
   }

   let nationsToNumberOfUsers = [];

   axios.get(`${baseAPIURI}mapData`)
      .then(response => {
         console.log(response);
         if (validateObject(response) && validateObject(response.data) && response.status === 200) {
            const mapData = response.data;
            mapData.forEach(mapDataRow => {
               nationsToNumberOfUsers.push({
                  nationName: mapDataRow[0],
                  numbersOfUsers: mapDataRow[1],
               });
            });
            nationsToNumberOfUsers.forEach(nationToNumberOfUsers =>
               highlightNation(nationToNumberOfUsers)
            );
         }
      })
      .catch(error => console.error("Error:", error));

});