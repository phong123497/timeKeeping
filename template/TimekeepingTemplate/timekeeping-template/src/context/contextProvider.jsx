import { useState } from "react";
import { SidebarSelectedMenuTitleContext, HeaderContext } from "./context";

export const ContextProvider = ({ children }) => {
  const [menuTitle, setMenuTitle] = useState("Home");
   const [isHeaderVisible, setIsHeaderVisible] = useState(true);
  return (
   <HeaderContext.Provider value={{ isHeaderVisible, setIsHeaderVisible }}>
      <SidebarSelectedMenuTitleContext.Provider
        value={{ menuTitle, setMenuTitle }}
      >
        {children}
      </SidebarSelectedMenuTitleContext.Provider>
   </HeaderContext.Provider>
  );
};

