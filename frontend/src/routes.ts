// routes.ts або AppRouter.tsx
import About from "./pages/About/About";
import Admin from "./pages/Admin";
import Coaches from "./pages/Coaches/Coaches";
import Home from "./pages/Home/Home";
import MyProfile from "./pages/Profile/MyProfile/Profile";
import Registration from "./pages/Registration/Registration";
import Services from "./pages/Services";
import Shop from "./pages/Shop";
import CoachProfilePage from "./pages/Profile/CoachProfile/CoachProfilePage"; // Новий компонент

import { ABOUT_ROUTE, ADMIN_ROUTE, COACHES_ROUTE, HOME_ROUTE, PROFILE_ROUTE, REGISTRATION_ROUTE, SERVICES_ROUTE, SHOP_ROUTE, COACH_ROUTE } from "./utils/consts";


export const authRoutes = [
  {
     path: COACH_ROUTE,
    Component: CoachProfilePage
  },
  {
    path: PROFILE_ROUTE,
    Component: MyProfile
    }
];

export const publicRoutes = [
  {
    path: HOME_ROUTE,
    Component: Home,
  },
  {
    path: SERVICES_ROUTE,
    Component: Services
    },
      {
    path: SHOP_ROUTE,
    Component: Shop
    },
      {
    path: ABOUT_ROUTE,
    Component: About
    },
      {
    path: COACHES_ROUTE,
    Component: Coaches
    },
      {
    path: REGISTRATION_ROUTE,
    Component: Registration
    },
];


export const AdminRoutes = [
  {
    path: ADMIN_ROUTE,
    Component: Admin,
  },
];