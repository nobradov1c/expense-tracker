export interface CreateIncomeTransactionInterface {
  description: string;
  amount: number;
  incomeGroupId: number | undefined;
}
