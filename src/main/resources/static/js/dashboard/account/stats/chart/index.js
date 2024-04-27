const ctx = document.getElementById("chart").getContext("2d");

//Chart.defaults.elements.bar.borderWidth = 2;

const weekDaysXToNumberOfSales = [];

let weekDaysX, numberOfSalesY;

const CHART_TYPES = {
   bar: "bar",
   line: "line",
   horizontalBar: "horizontal-bar",
   pie: "pie"
};

axios.get(`${baseAPIURI}chartData`)
   .then(response => {
      const chartData = response.data;
      console.log(chartData);
      if (validateObject(chartData) && response.status === 200) {
         chartData.forEach(chartDataRow => {
            weekDaysXToNumberOfSales.push({
               weekDay: chartDataRow[0],
               numberOfSales: chartDataRow[1],
            });
         });
         weekDaysX = weekDaysXToNumberOfSales.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.weekDay);
         numberOfSalesY = weekDaysXToNumberOfSales.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.numberOfSales);
         chart = new Chart(ctx, {
            type: CHART_TYPES.bar,
            data: {
               labels: weekDaysX,
               datasets: [{
                  label: " Number of Sales published in this day",
                  data: numberOfSalesY,
                  borderWidth: 1,
                  backgroundColor: "#1D86BA",
                  borderColor: "#000000",
                  //pointRadius: 3,
                  //pointBackgroundColor: "red",
               }]
            },
            options: {
               responsive: true,
               maintainAspectRatio: false,
               plugins: {
                  title: {
                     display: true,
                     text: "Number of sales published in last week",
                     fullSize: true,
                     font: {
                        weight: "bold",
                        size: 15.5
                     }
                  },
                  legend: {
                     labels: {
                        font: {
                           size: 15
                        }
                     }
                  },
               },
               scales: {
                  x: {
                     beginAtZero: true,
                     ticks: {
                        font: {
                           size: 14
                        },
                     }
                  },
                  y: {
                     beginAtZero: true,
                     ticks: {
                        font: {
                           size: 13
                        },
                        callback: (value) => parseInt(value) === value ? value : null
                     }
                  }
               }
            }
         });

      }
   })
   .catch(error => console.error("Error:", error));


