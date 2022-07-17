import { useEffect, useState } from "react";
import LoadingSVG from "./LoadingSVG";
import "./Splashscreen.scss";

type Props = {
  WrappedComponent: () => JSX.Element;
};

function WithSplashscreen({ WrappedComponent }: Props) {
  const [isLoading, setIsLoading] = useState(true);

  const LoadingArt = () => {
    return (
      <div className="splash-container">
        <div className="splash-screen">
          <div className="svg-container">
            <LoadingSVG />
          </div>
        </div>
      </div>
    );
  };

  useEffect(() => {
    setTimeout(() => {
      setIsLoading(false);
    }, 3400);
  }, []);

  return isLoading ? <LoadingArt /> : <WrappedComponent />;
}

export default WithSplashscreen;
