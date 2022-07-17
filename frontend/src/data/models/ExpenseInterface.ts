export interface ExpenseInterface {
  id: number;
  description: string;
  amount: number;
  expenseGroupId: number | undefined;
}
