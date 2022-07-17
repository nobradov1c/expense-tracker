import { GroupInterface } from "../data/models/GroupInterface";
import { ExpenseInterface } from "../data/models/ExpenseInterface";
import { TransactionFormInterface } from "../models/forms/TransactionFormInterface";
import { GroupTypeFormInterface } from "../models/forms/GroupTypeFormInterface";
import expenseTrackerApi from "./config";
import { CreateExpenseTransactionInterface } from "../data/models/CreateExpenseTransactionInterface";

export function getAllExpenses(): Promise<ExpenseInterface[]> {
  // to showcase the loading state, we'll delay the response for 1 second
  // await (function timeout(ms: number) {
  //   return new Promise((resolve) => setTimeout(resolve, ms));
  // })(1000);

  return expenseTrackerApi
    .get("/expenses")
    .then((response) => response.data)
    .catch((error) => {
      // rethrow the error so we can handle it in the component
      throw error;
    });
}

export function deleteAnExpense(id: number): Promise<any> {
  return expenseTrackerApi
    .delete(`/expenses/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function createNewExpense(
  transactionFormData: TransactionFormInterface
): Promise<CreateExpenseTransactionInterface> {
  const newExpense: CreateExpenseTransactionInterface = {
    description: transactionFormData.description,
    amount: transactionFormData.amount,
    expenseGroupId: transactionFormData.groupId,
  };

  if (newExpense.expenseGroupId === -1) {
    delete newExpense.expenseGroupId;
  }

  return expenseTrackerApi
    .post("/expenses", newExpense)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function getAllExpenseGroups(): Promise<GroupInterface[]> {
  return expenseTrackerApi
    .get("/expenses/groups")
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      throw error;
    });
}

export function createNewExpenseGroup(
  groupTypeFormData: GroupTypeFormInterface
): Promise<GroupTypeFormInterface> {
  const newExpenseGroup: GroupTypeFormInterface = {
    description: groupTypeFormData.description,
    name: groupTypeFormData.name,
  };

  return expenseTrackerApi
    .post("/expenses/groups", newExpenseGroup)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function deleteAnExpenseGroup(id: number): Promise<any> {
  return expenseTrackerApi
    .delete(`/expenses/groups/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
