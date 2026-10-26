import { Routes } from '@angular/router';

export const routes: Routes = [
  //inventory
  {
    path: 'inventory',
    loadChildren: () =>
      import('./inventory/inventory.routes').then((m) => m.inventoryRoutes),
  },

  {
    path: '',
    redirectTo: 'inventory',
    pathMatch: 'full',
  },
];
