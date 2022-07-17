import { useParams } from "react-router-dom";

function ExpensePage() {
  let params = useParams();

  return (
    <>
      <h1>ExpensePage</h1>

      <p>This is expense with id {params.id} </p>
    </>
  );
}

export default ExpensePage;
