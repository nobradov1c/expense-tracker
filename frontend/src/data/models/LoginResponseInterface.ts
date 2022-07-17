export interface LoginResponseInterface {
  name: string;
  email: string;
  role: "USER" | "ADMIN";
  token: string;
}
