const ctx = document.getElementById("chart").getContext("2d");

//Chart.defaults.elements.bar.borderWidth = 2;

const productCategoriesToNumberOfSales = [];

let productCategoriesX, numberOfSalesY;

const CHART_TYPES = {
   bar: "bar",
   line: "line",
   horizontalBar: "horizontal-bar",
   pie: "pie"
};

axios.get(`${baseAPIURI}chartData`)
   .then(response => {
      const chartData = response.data;
      if (validateObject(chartData) && response.status === 200) {
         chartData.forEach(chartDataRow => {
            productCategoriesToNumberOfSales.push({
               productCategory: chartDataRow[0],
               numberOfSales: chartDataRow[1],
            });
         });
         productCategoriesX = productCategoriesToNumberOfSales.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.productCategory);
         numberOfSalesY = productCategoriesToNumberOfSales.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.numberOfSales);
         chart = new Chart(ctx, {
            type: CHART_TYPES.bar,
            data: {
               labels: productCategoriesX,
               datasets: [{
                  label: " Number of Sales",
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
                     text: "Sales per Product category",
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


