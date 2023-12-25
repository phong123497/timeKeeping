import React from "react";
import { Route, Routes } from "react-router-dom";
import { Outlet } from 'react-router-dom';

import { routes as dashboardRoutes, authRoutes } from "./index";
import MainLayout from "../layouts/MainLayout";
import AuthLayout from "../layouts/AuthLayout";


const ModifiedMainLayout = () => {
    return (
        <MainLayout>
            <Outlet />
        </MainLayout>
    )
};

const ModifiedAuthLayout = () => {
    return (
        <AuthLayout>
            <Outlet />
        </AuthLayout>
    )
};

const AppRoutes = () => {

    const isAuthenticated = sessionStorage.getItem("TOKEN_AUTH");

    return (
        <>
            {/* {isAuthenticated ?  */}
            {/* <Routes>
                    <Route element={<ModifiedMainLayout/>}>
                    {dashboardRoutes.map((route) => (
                        <Route
                            key={route.key}
                            path={route.path}
                            element={<route.component />}
                        />
                    ))}
                    </Route>
                </Routes> */}
            {/* : */}
            <React.Fragment>
                <Routes>
                    <Route element={<ModifiedMainLayout />}>
                        {dashboardRoutes.map((route) => (
                            <Route
                                key={route.key}
                                path={route.path}
                                element={<route.component />}
                            />
                        ))}
                    </Route>
                    <Route element={<ModifiedAuthLayout />}>
                        {authRoutes.map((route) => (
                            <Route
                                key={route.key}
                                path={route.path}
                                element={<route.component />}
                            />
                        ))}
                    </Route>
                </Routes>
            </React.Fragment>
            {/* } */}
        </>
    )
}

export default AppRoutes;