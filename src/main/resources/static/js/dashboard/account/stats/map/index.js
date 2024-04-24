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
         // Se ci sono risultati, prendi le coordinate del primo risultato
         const coordinatesData = response.data;
         console.log(coordinatesData);
         if (validateObject(coordinatesData) && response.status === 200) {
            L.marker([coordinatesData[0].lat, coordinatesData[0].lon])
               .addTo(map)
               .bindPopup(`${nationToNumberOfUsers.nationName} has ${nationToNumberOfUsers.numbersOfUsers} MarketNexus ${nationToNumberOfUsers.numbersOfUsers > 1 ? "users" : "user"}.`);
         } else {
            console.warn("Nation not found.");
         }
      })
      .catch(error => console.error("Error:", error));
}

let nationsToNumberOfUsers = [];

axios.get(`${baseAPIURI}mapData`)
   .then(response => {
      const mapData = response.data;
      if (validateObject(mapData) && response.status === 200) {
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
