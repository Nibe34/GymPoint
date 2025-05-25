import React, { useContext } from "react";
import { Context } from '../main';
import { AdminRoutes, authRoutes, publicRoutes } from "../routes";
import { Route, Routes } from "react-router-dom";

const AppRouter = () => {
    const { store } = useContext(Context);

    const isAuth = store.isAuth;
    const isAdmin = store.user.role === "ROLE_ADMIN";

    return (
        <Routes>
            {isAuth && authRoutes.map(({ path, Component }) => (
                <Route key={path} path={path} element={<Component />} />
            ))}

            {publicRoutes.map(({ path, Component }) => (
                <Route key={path} path={path} element={<Component />} />
            ))}

            {isAuth && isAdmin && AdminRoutes.map(({ path, Component }) => (
                <Route key={path} path={path} element={<Component />} />
            ))}
        </Routes>
    );
};

export default AppRouter;
