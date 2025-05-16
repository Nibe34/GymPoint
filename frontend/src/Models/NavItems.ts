export const NavPaths = {
  SHOP: '/shop',
  SERVICES: '/services',
  COACHES : '/coaches',
  ABOUT_US: '/about-us',
} as const;

export type NavPath = typeof NavPaths[keyof typeof NavPaths];

export interface NavItem {
  path: NavPath;
  label: string;
}

export const navItems: NavItem[] = [
  { path: NavPaths.SHOP, label: 'Shop' },
  { path: NavPaths.SERVICES, label: 'Services' },
  { path: NavPaths.COACHES, label: 'coaches' },
  { path: NavPaths.ABOUT_US, label: 'about us' },
];