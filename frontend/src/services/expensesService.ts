import { GroupInterface } from "../data/models/GroupInterface";
import { ExpenseInterface } from "../data/models/ExpenseInterface";
import { TransactionFormInterface } from "../models/forms/TransactionFormInterface";
import { GroupTypeFormInterface } from "../models/forms/GroupTypeFormInterface";
import expenseTrackerApi from "./config";
import { CreateExpenseTransactionInterface } from "../data/models/CreateExpenseTransactionInterface";

export async function getAllExpenses(): Promise<ExpenseInterface[]> {
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

export async function getExpenseById(id: number): Promise<ExpenseInterface> {
  return expenseTrackerApi
    .get(`/expenses/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export async function updateExpenseById({
  id,
  values: groupTypeFormData,
}: {
  id: number;
  values: TransactionFormInterface;
}): Promise<GroupInterface> {
  const payload = {
    ...groupTypeFormData,
    expenseGroupId:
      groupTypeFormData.groupId === -1 ? undefined : groupTypeFormData.groupId,
  };
  return expenseTrackerApi
    .put(`/expenses/${id}`, payload)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export async function deleteAnExpense(id: number): Promise<any> {
  return expenseTrackerApi
    .delete(`/expenses/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export async function createNewExpense(
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

export async function getAllExpenseGroups(): Promise<GroupInterface[]> {
  return expenseTrackerApi
    .get("/expenses/groups")
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      throw error;
    });
}

export async function getExpenseGroupById(id: number): Promise<GroupInterface> {
  return expenseTrackerApi
    .get(`/expenses/groups/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export async function createNewExpenseGroup(
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

export async function updateExpenseGroupById({
  id,
  values: groupTypeFormData,
}: {
  id: number;
  values: GroupTypeFormInterface;
}): Promise<GroupInterface> {
  return expenseTrackerApi
    .put(`/expenses/groups/${id}`, groupTypeFormData)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export async function deleteAnExpenseGroup(id: number): Promise<any> {
  return expenseTrackerApi
    .delete(`/expenses/groups/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
