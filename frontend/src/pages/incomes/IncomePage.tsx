import { useParams } from "react-router-dom";

function IncomePage() {
  let params = useParams();

  return (
    <>
      <h1>IncomePage</h1>

      <p>This is income with id {params.id} </p>
    </>
  );
}

export default IncomePage;
