import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product, ProductRequest } from '../product.model';

@Injectable({
  providedIn: 'root',
})
export class InventoryService {
  private readonly http = inject(HttpClient);

  private baseUrl = 'http://localhost:8080/api/products';

  addProduct(product: ProductRequest): Observable<Product> {
    return this.http.post<Product>(this.baseUrl, product);
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl);
  }

  getProductByID(id: string): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/${id}`);
  }

  updateProduct(id: string, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.baseUrl}/${id}`, product);
  }

  deleteProduct(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
