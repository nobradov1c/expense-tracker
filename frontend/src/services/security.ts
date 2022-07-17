import { LoginResponseInterface } from "../data/models/LoginResponseInterface";
import { LoginRequestInterface } from "../data/models/request/LoginRequestInterface";
import expenseTrackerApi from "./config";

export async function login(
  loginRequest: LoginRequestInterface
): Promise<LoginResponseInterface> {
  await (function timeout(ms: number) {
    return new Promise((resolve) => setTimeout(resolve, ms));
  })(400);

  return expenseTrackerApi
    .post("/auth/login", loginRequest)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
