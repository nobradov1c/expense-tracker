import "./AppFooter.sass";

function AppFooter() {
  return (
    <footer className="app-footer level has-background-black-ter has-text-white px-6 py-4">
      <div className="level-left">
        <div className="level-item">
          <p className="ma-0">Expense Tracker</p>
        </div>
      </div>
      <div className="level-right">
        <div className="level-item">
          <p>Obradovic Nikola</p>
        </div>
        <div className="level-item">
          <p> &copy; {new Date().getFullYear()}</p>
        </div>
      </div>
    </footer>
  );
}

export default AppFooter;
