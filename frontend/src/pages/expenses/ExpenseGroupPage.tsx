import { useParams } from "react-router-dom";

function ExpenseGroupPage() {
  let params = useParams();

  return (
    <>
      <h1>Expense Group Page</h1>

      <p>This is expense GROUP with id {params.id} </p>
    </>
  );
}

export default ExpenseGroupPage;
