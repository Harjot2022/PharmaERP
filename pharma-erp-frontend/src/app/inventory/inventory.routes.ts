// inventory/inventory.routes.ts
import { Routes } from '@angular/router';
import { ProductList } from './components/product-list/product-list';
import { ProductForm } from './components/product-form/product-form';

export const inventoryRoutes: Routes = [
  { path: 'products/new', component: ProductForm },
  { path: 'products/:id/edit', component: ProductForm },
  { path: 'products', component: ProductList },
];
