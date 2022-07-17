import { TotalAmountInterface } from "../data/models/TotalAmountInterface";
import expenseTrackerApi from "./config";

export async function getTotal(): Promise<TotalAmountInterface> {
  await (function timeout(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  })(400);

  return expenseTrackerApi
    .get("/total-amount")
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
