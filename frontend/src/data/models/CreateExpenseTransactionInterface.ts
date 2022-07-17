export interface CreateExpenseTransactionInterface {
  description: string;
  amount: number;
  expenseGroupId: number | undefined;
}
