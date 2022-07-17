import React from "react";
import ReactDOM from "react-dom/client";
import "@fontsource/roboto/300.css";
import "@fontsource/roboto/400.css";
import "@fontsource/roboto/500.css";
import "@fontsource/roboto/700.css";
import App from "./App";
import "./styles/index.sass";
import { CssBaseline } from "@mui/material";
import WithSplashscreen from "./components/Splashscreen/WithSplashscreen";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <CssBaseline />
    {/* <App /> */}
    <WithSplashscreen WrappedComponent={App} />
  </React.StrictMode>
);
