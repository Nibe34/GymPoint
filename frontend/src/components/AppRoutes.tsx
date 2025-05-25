import React, { useContext } from "react";
import { Context } from '../main';
import { AdminRoutes, authRoutes, publicRoutes } from "../routes";
import { Route, Routes } from "react-router-dom";
import { observer } from "mobx-react-lite";
const AppRouter = observer(() => {
    const { store } = useContext(Context);

    // Рендеримо loader, поки не ініціалізовано store (тобто поки не завершено checkAuth)
    if (!store.isInitialized) {
        return <div style={{ textAlign: 'center', marginTop: 40 }}>Завантаження...</div>;
    }

    // Далі твій звичайний рендер
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
});


export default AppRouter;
