import { useParams } from "react-router-dom";

function IncomeGroupPage() {
  let params = useParams();

  return (
    <>
      <h1>Income Group Page</h1>

      <p>This is income GROUP with id {params.id} </p>
    </>
  );
}

export default IncomeGroupPage;
