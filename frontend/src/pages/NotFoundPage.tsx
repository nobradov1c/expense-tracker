import { Link } from "react-router-dom";

function NotFoundPage() {
  return (
    <>
      <h2>404 Not Found</h2>
      <p>That page cannot be found</p>
      <Link to="/">Back to the homepage...</Link>
    </>
  );
}

export default NotFoundPage;
