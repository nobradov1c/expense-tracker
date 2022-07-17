import React from "react";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";

import { useQuery } from "react-query";
import { getTotal } from "../services/common";

ChartJS.register(ArcElement, Tooltip, Legend);

function HomePage() {
  const {
    data: total,
    isError,
    isFetching,
  } = useQuery("total", getTotal, { cacheTime: 0, staleTime: 0 });

  let data = {
    labels: ["Expenses", "Incomes"],
    datasets: [
      {
        data: [0, 0],
        backgroundColor: ["rgba(255, 99, 132, 0.2)", "rgba(54, 162, 235, 0.2)"],
        borderColor: ["rgba(255, 99, 132, 1)", "rgba(54, 162, 235, 1)"],
        borderWidth: 1,
      },
    ],
  };

  let content = "Loading...";
  if (total && total.totalAmount) {
    const sign = total.totalAmount > 0 ? "+" : "";
    content = `Total: ${sign}${total.totalAmount}`;
    data.datasets[0].data = [total.totalExpenses, total.totalIncomes];
  } else if (isFetching) {
    content = "Loading...";
  } else if (isError) {
    content = "Error fetching total";
  }

  return (
    <>
      <h1>Best expense tracker ever</h1>

      <p>{content}</p>
      <div className="content is-flex is-align-items-center is-justify-content-center">
        <div className="pie-container">
          <Pie data={data} />
        </div>
      </div>
    </>
  );
}

export default HomePage;
