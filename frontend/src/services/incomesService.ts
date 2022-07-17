import { CreateIncomeTransactionInterface } from "../data/models/CreateIncomeTransactionInterface";
import { GroupInterface } from "../data/models/GroupInterface";
import { IncomeInterface } from "../data/models/IncomeInterface";
import { GroupTypeFormInterface } from "../models/forms/GroupTypeFormInterface";
import { TransactionFormInterface } from "../models/forms/TransactionFormInterface";
import expenseTrackerApi from "./config";

export function getAllIncomes(): Promise<IncomeInterface[]> {
  return expenseTrackerApi
    .get("/incomes")
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function deleteAnIncome(id: number): Promise<any> {
  return expenseTrackerApi
    .delete(`/incomes/${id}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function createNewIncome(
  transactionFormData: TransactionFormInterface
): Promise<CreateIncomeTransactionInterface> {
  const newIncome: CreateIncomeTransactionInterface = {
    description: transactionFormData.description,
    amount: transactionFormData.amount,
    incomeGroupId: transactionFormData.groupId,
  };

  if (newIncome.incomeGroupId === -1) {
    delete newIncome.incomeGroupId;
  }

  return expenseTrackerApi
    .post("/incomes", newIncome)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function getAllIncomeGroups(): Promise<GroupInterface[]> {
  return expenseTrackerApi
    .get("/incomes/groups")
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

export function createNewIncomeGroup(
  groupTypeFormData: GroupTypeFormInterface
): Promise<GroupInterface> {
  const newIncomeGroup: GroupInterface = {
    id: Math.floor(Math.random() * 1000000),
    description: groupTypeFormData.description,
    name: groupTypeFormData.name,
  };

  return expenseTrackerApi
    .post("/incomes/groups", newIncomeGroup)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}

export function deleteAnIncomeGroup(id: number): Promise<any> {
  return expenseTrackerApi
    .delete(`/incomes/groups/${id}`)
    .then((res) => res.data)
    .catch((error) => {
      throw error;
    });
}
