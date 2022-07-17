import { Container, ThemeProvider } from "@mui/material";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "react-query";
// import { ReactQueryDevtools } from "react-query/devtools";
import AppFooter from "./components/layout/AppFooter/AppFooter";
import AppHeader from "./components/layout/AppHeader/AppHeader";
import ExpensePage from "./pages/expenses/ExpensePage";
import ExpensesPage from "./pages/expenses/ExpensesPage";
import ExpenseGroupsPage from "./pages/expenses/ExpenseGroupsPage";
import ExpenseGroupPage from "./pages/expenses/ExpenseGroupPage";
import HomePage from "./pages/HomePage";
import IncomePage from "./pages/incomes/IncomePage";
import IncomesPage from "./pages/incomes/IncomesPage";
import IncomeGroupsPage from "./pages/incomes/IncomeGroupsPage";
import IncomeGroupPage from "./pages/incomes/IncomeGroupPage";
import LoginPage from "./pages/LoginPage";
import NotFoundPage from "./pages/NotFoundPage";
import muiTheme from "./config/muiTheme";

const queryClient = new QueryClient();

function App() {
  return (
    <ThemeProvider theme={muiTheme}>
      <BrowserRouter>
        <QueryClientProvider client={queryClient}>
          <AppHeader />
          <Container
            disableGutters
            maxWidth="lg"
            component="main"
            className="app-main is-flex-grow-1 has-background-dark has-text-white px-4"
          >
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/expenses" element={<ExpensesPage />} />
              <Route path="/expenses/:id" element={<ExpensePage />} />
              <Route path="/expense-groups" element={<ExpenseGroupsPage />} />
              <Route
                path="/expense-groups/:id"
                element={<ExpenseGroupPage />}
              />
              <Route path="/incomes" element={<IncomesPage />} />
              <Route path="/incomes/:id" element={<IncomePage />} />
              <Route path="/income-groups" element={<IncomeGroupsPage />} />
              <Route path="/income-groups/:id" element={<IncomeGroupPage />} />
              <Route path="*" element={<NotFoundPage />} />
            </Routes>
          </Container>
          <AppFooter />
          {/* <ReactQueryDevtools initialIsOpen={false} /> */}
        </QueryClientProvider>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
