import { createContext } from "react";

export const SidebarSelectedMenuTitleContext = createContext({
  menuTitle: "",
  setMenuTitle: (mnuTitle) => {},
});

export const HeaderContext = createContext({
  isHeaderVisible: true,
  setIsHeaderVisible: (isHeaderVisible) => {},
});
