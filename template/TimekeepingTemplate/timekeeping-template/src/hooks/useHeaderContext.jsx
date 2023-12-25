import { useContext } from "react";
import { HeaderContext } from "../context";

export const useHeaderContext = () => {
  return useContext(HeaderContext);
};
